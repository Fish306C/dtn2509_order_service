package org.example.dtn2509_order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.client.ProductClient;
import org.example.dtn2509_order_service.client.dto.request.LockProductItemRequest;
import org.example.dtn2509_order_service.client.dto.request.LockProductRequest;
import org.example.dtn2509_order_service.client.dto.request.ProductFilter;
import org.example.dtn2509_order_service.client.dto.response.ProductResponse;
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
    private final ProductClient productClient;

    @Override
    @Transactional
    public OrderItemResponse create(CreateOrderItemRequest createOrderItemRequest) {
        throw new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Order item must be created with an order!");
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemResponse> getAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(orderMapper::toRes)
                .toList();
    }

    private ProductResponse getProduct(String productId)
    {
        ProductFilter productFilter = new ProductFilter();
        productFilter.setIds(List.of(productId));

        return productClient.search(productFilter)
                .stream()
                .filter(product -> productId.equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(400, HttpStatus.BAD_REQUEST.value(), "Product does not exist!"));
    }
}
