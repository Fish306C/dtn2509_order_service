package org.example.dtn2509_order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.dto.request.CreateOrderRequest;
import org.example.dtn2509_order_service.dto.response.OrderResponse;
import org.example.dtn2509_order_service.entity.OrderEntity;
import org.example.dtn2509_order_service.mapper.OrderMapper;
import org.example.dtn2509_order_service.repository.OrderRepository;
import org.example.dtn2509_order_service.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService
{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse create(CreateOrderRequest createOrderRequest) {
        OrderEntity orderEntity = orderMapper.toEnt(createOrderRequest);
        orderEntity.setIsDeleted(false);

        int totalAmount = 0;
        for (var orderItem : orderEntity.getOrderItems())
        {
            orderItem.setOrder(orderEntity);
            orderItem.setIsDeleted(false);
            totalAmount += orderItem.getPrice() * orderItem.getQuantity();
        }
        orderEntity.setTotalAmount(totalAmount);

        OrderEntity createdOrderEntity = orderRepository.save(orderEntity);
        return orderMapper.toRes(createdOrderEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toRes)
                .toList();
    }
}
