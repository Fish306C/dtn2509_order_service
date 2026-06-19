package org.example.dtn2509_order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse
{
    private String id;
    private String customerId;
    private String status;
    private Integer totalAmount;
    private List<OrderItemResponse> orderItems;
}
