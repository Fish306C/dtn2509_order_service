package org.example.dtn2509_order_service.client.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockPromotionRequest
{
    private String orderId;
    private String promotionCode;
    private Integer orderAmount;
}
