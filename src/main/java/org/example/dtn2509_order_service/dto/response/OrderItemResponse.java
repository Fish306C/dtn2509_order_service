package org.example.dtn2509_order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse
{
    private String id;
    private String orderId;
    private String productId;
    private Integer price;
    private Integer quantity;
}
