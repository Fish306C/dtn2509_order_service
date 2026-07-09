package org.example.dtn2509_order_service.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.client.PromotionClient;
import org.example.dtn2509_order_service.client.dto.request.LockPromotionRequest;
import org.example.dtn2509_order_service.client.dto.response.LockPromotionResponse;
import org.example.dtn2509_order_service.dto.BaseResponse;
import org.example.dtn2509_order_service.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromotionClientImpl implements PromotionClient
{
    private final WebClient.Builder webClientBuilder;

    @Value("${client.promotion.uri}")
    private String promotionUri;

    @Override
    public LockPromotionResponse lockPromotion(LockPromotionRequest lockPromotionRequest)
    {
        BaseResponse<LockPromotionResponse> response = webClientBuilder.build()
                .put()
                .uri(promotionUri + "/v1/promotions/lock")
                .bodyValue(lockPromotionRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<LockPromotionResponse>>() {
                })
                .block();

        if (response == null || response.getData() == null)
        {
            throw new ApplicationException("Promotion was not locked!");
        }

        return response.getData();
    }
}
