package org.example.dtn2509_order_service.client.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockPromotionResponse
{
    private String promotionCode;
    private Integer discountAmount;
    private Integer finalAmount;
}
