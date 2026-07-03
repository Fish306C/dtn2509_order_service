package org.example.dtn2509_order_service.event;

import lombok.Getter;
import lombok.Setter;
import org.example.dtn2509_order_service.dto.response.OrderItemResponse;

import java.util.List;

@Getter
@Setter
public class OrderCreatedEvent
{
    private String id;
    private String customerId;
    private String status;
    private Integer totalAmount;
    private List<OrderItemResponse> orderItems;
}
