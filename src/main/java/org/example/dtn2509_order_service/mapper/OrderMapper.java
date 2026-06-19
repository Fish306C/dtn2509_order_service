package org.example.dtn2509_order_service.mapper;

import org.example.dtn2509_order_service.dto.request.CreateOrderItemRequest;
import org.example.dtn2509_order_service.dto.request.CreateOrderRequest;
import org.example.dtn2509_order_service.dto.response.OrderItemResponse;
import org.example.dtn2509_order_service.dto.response.OrderResponse;
import org.example.dtn2509_order_service.entity.OrderEntity;
import org.example.dtn2509_order_service.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    OrderEntity toEnt(CreateOrderRequest createOrderRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    OrderItemEntity toEnt(CreateOrderItemRequest createOrderItemRequest);

    OrderResponse toRes(OrderEntity orderEntity);

    @Mapping(target = "orderId", source = "order.id")
    OrderItemResponse toRes(OrderItemEntity orderItemEntity);
}
