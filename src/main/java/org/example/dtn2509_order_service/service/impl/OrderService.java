package org.example.dtn2509_order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.client.ProductClient;
import org.example.dtn2509_order_service.client.dto.request.ProductFilter;
import org.example.dtn2509_order_service.client.dto.response.ProductResponse;
import org.example.dtn2509_order_service.common.OrderStatus;
import org.example.dtn2509_order_service.dto.request.CreateOrderItemRequest;
import org.example.dtn2509_order_service.dto.request.CreateOrderRequest;
import org.example.dtn2509_order_service.dto.response.OrderResponse;
import org.example.dtn2509_order_service.entity.OrderEntity;
import org.example.dtn2509_order_service.entity.OrderItemEntity;
import org.example.dtn2509_order_service.event.OrderCreatedEvent;
import org.example.dtn2509_order_service.exception.ApplicationException;
import org.example.dtn2509_order_service.mapper.OrderMapper;
import org.example.dtn2509_order_service.repository.OrderItemRepository;
import org.example.dtn2509_order_service.repository.OrderRepository;
import org.example.dtn2509_order_service.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService
{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional
    public OrderResponse create(CreateOrderRequest createOrderRequest) {
        //Validate dữ liệu

        //CustomerId có tồn tại => bypass

        //Product Id có tồn tại không
        //Lấy các productId (không trùng lặp) trong bảng product)
        List<String> productIds = createOrderRequest.getOrderItems().stream()
                .map(CreateOrderItemRequest::getProductId)
                .distinct()
                .toList();

        // Đưa danh sách các productId vào Filter
        ProductFilter productFilter = new ProductFilter();
        productFilter.setIds(productIds);

        // Gọi sang Product-service thông qua Client, truyền vào là productFilter
        List<ProductResponse> existedProduct = productClient.search(productFilter);
        Map<String, ProductResponse> productIdsMap = new HashMap<>();
        for (ProductResponse productResponse: existedProduct)
        {
            productIdsMap.put(productResponse.getId(), productResponse);
        }


        //Khi tạo order sẽ là 1 order mới, cần save order xuống database để sinh ra uuid
        OrderEntity order = new OrderEntity();
        order.setCustomerId(createOrderRequest.getCustomerId());
        order.setStatus(OrderStatus.NEW.name());
        order.setTotalAmount(0);
        OrderEntity createdOrder = orderRepository.save(order);

        // Validate từng Item bên trong order
        List<CreateOrderItemRequest> orderItemRequests = createOrderRequest.getOrderItems();
        int totalAmount = 0;

        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();

        for (CreateOrderItemRequest createOrderItemRequest: orderItemRequests)
        {
            String productId = createOrderItemRequest.getProductId();

            ProductResponse productResponse = productIdsMap.get(productId);

            if (productResponse == null)
            {
                throw new ApplicationException("Product not found!");
            }

            // Kiểm tra trong stock có còn đủ số lượng hàng cung cấp
            if (productResponse.getStock() <  createOrderItemRequest.getQuantity())
            {
                throw new ApplicationException("Not enough stock!");
            }

            // Tạo orderItem với mỗi item trong order
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(createdOrder);
            orderItemEntity.setProductId(productId);
            orderItemEntity.setPrice(productResponse.getPrice());
            orderItemEntity.setQuantity(createOrderItemRequest.getQuantity());

            // Tính tổng giá
            totalAmount += orderItemEntity.getQuantity() * productResponse.getPrice();
            orderItemEntityList.add(orderItemEntity);

        }

        // Cập nhật tổng tiền đơn hàng
        createdOrder.setTotalAmount(totalAmount);
        createdOrder.setOrderItems(orderItemEntityList);

        orderItemRepository.saveAll(orderItemEntityList);


        OrderCreatedEvent orderCreatedEvent = orderMapper.toEvent(createdOrder);
        orderCreatedEvent.setOrderItems(orderItemEntityList.stream()
                .map(orderMapper::toRes)
                .toList());
        kafkaTemplate.send("order_created", orderCreatedEvent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Publish new order failed to order_created : [{}]", orderCreatedEvent, ex);
                        return;
                    }
                    log.info("Publish new order success to order_created, partition={}, offset={} : [{}]",
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset(),
                            orderCreatedEvent);
                });

        // Validate sản phẩm

        // Validate promotion

        return orderMapper.toRes(createdOrder);

    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toRes)
                .toList();
    }

    private void validateCreateOrderRequest(CreateOrderRequest createOrderRequest)
    {
        if (createOrderRequest == null)
        {
            throw new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Order request is required!");
        }
        if (createOrderRequest.getCustomerId() == null || createOrderRequest.getCustomerId().isBlank())
        {
            throw new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Customer id is required!");
        }
        if (createOrderRequest.getOrderItems() == null || createOrderRequest.getOrderItems().isEmpty())
        {
            throw new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Order items are required!");
        }

        for (CreateOrderItemRequest item : createOrderRequest.getOrderItems())
        {
            if (item.getProductId() == null || item.getProductId().isBlank())
            {
                throw new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Product id is required!");
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0)
            {
                throw new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Product quantity must be positive!");
            }
        }
    }
}
