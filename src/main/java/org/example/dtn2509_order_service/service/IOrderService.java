package org.example.dtn2509_order_service.service;

import org.example.dtn2509_order_service.dto.request.CreateOrderRequest;
import org.example.dtn2509_order_service.dto.response.OrderResponse;

import java.util.List;

public interface IOrderService
{
    OrderResponse create(CreateOrderRequest createOrderRequest);

    List<OrderResponse> getAll();
}
