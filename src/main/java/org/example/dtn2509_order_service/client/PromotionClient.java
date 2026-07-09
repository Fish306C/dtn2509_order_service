package org.example.dtn2509_order_service.client;

import org.example.dtn2509_order_service.client.dto.request.LockPromotionRequest;
import org.example.dtn2509_order_service.client.dto.response.LockPromotionResponse;

public interface PromotionClient
{
    LockPromotionResponse lockPromotion(LockPromotionRequest lockPromotionRequest);
}
