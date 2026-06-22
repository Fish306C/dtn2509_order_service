package org.example.dtn2509_order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.dto.request.CreateOrderItemRequest;
import org.example.dtn2509_order_service.dto.response.OrderItemResponse;
import org.example.dtn2509_order_service.entity.OrderEntity;
import org.example.dtn2509_order_service.entity.OrderItemEntity;
import org.example.dtn2509_order_service.exception.ApplicationException;
import org.example.dtn2509_order_service.mapper.OrderMapper;
import org.example.dtn2509_order_service.repository.OrderItemRepository;
import org.example.dtn2509_order_service.repository.OrderRepository;
import org.example.dtn2509_order_service.service.IOrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService
{
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderItemResponse create(CreateOrderItemRequest createOrderItemRequest) {
        if (createOrderItemRequest.getOrderId() == null || createOrderItemRequest.getOrderId().isBlank())
        {
            throw new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Order id is required!");
        }

        OrderEntity orderEntity = orderRepository.findById(createOrderItemRequest.getOrderId())
                .orElseThrow(() -> new ApplicationException(404, HttpStatus.NOT_FOUND.value(), "Order not found!"));

        OrderItemEntity orderItemEntity = orderMapper.toEnt(createOrderItemRequest);
        orderItemEntity.setOrder(orderEntity);
        orderItemEntity.setIsDeleted(false);

        int currentTotalAmount = orderEntity.getTotalAmount() == null ? 0 : orderEntity.getTotalAmount();
        orderEntity.setTotalAmount(currentTotalAmount + orderItemEntity.getPrice() * orderItemEntity.getQuantity());

        OrderItemEntity createdOrderItemEntity = orderItemRepository.save(orderItemEntity);
        return orderMapper.toRes(createdOrderItemEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemResponse> getAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(orderMapper::toRes)
                .toList();
    }
}
