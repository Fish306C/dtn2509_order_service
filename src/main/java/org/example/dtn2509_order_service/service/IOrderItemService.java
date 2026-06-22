package org.example.dtn2509_order_service.service;

import org.example.dtn2509_order_service.dto.request.CreateOrderItemRequest;
import org.example.dtn2509_order_service.dto.response.OrderItemResponse;

import java.util.List;

public interface IOrderItemService
{
    OrderItemResponse create(CreateOrderItemRequest createOrderItemRequest);

    List<OrderItemResponse> getAll();
}
