package org.example.dtn2509_order_service.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.client.ProductClient;
import org.example.dtn2509_order_service.client.dto.request.LockProductRequest;
import org.example.dtn2509_order_service.client.dto.request.ProductFilter;
import org.example.dtn2509_order_service.client.dto.response.ProductResponse;
import org.example.dtn2509_order_service.dto.BaseResponse;
import org.example.dtn2509_order_service.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductClientIml implements ProductClient
{

    private final WebClient.Builder webClientBuilder;

    @Value("${client.product.uri}")
    private String productUri;

    @Override
    public List<ProductResponse> search(ProductFilter productFilter) {
        BaseResponse<List<ProductResponse>> response = webClientBuilder.build()
                .post()
                .uri(productUri + "/v1/products/search")
                .bodyValue(productFilter)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ProductResponse>>>() {
                })
                .block();

        if (response == null || response.getData() == null)
        {
            throw new ApplicationException("No data!");
        }

        return response.getData();
    }

    @Override
    public Boolean lockProducts(LockProductRequest lockProductRequest) {
        BaseResponse<Boolean> response = webClientBuilder.build()
                .put()
                .uri(productUri + "/v1/products/lock")
                .bodyValue(lockProductRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<Boolean>>() {
                })
                .block();

        if (response == null || response.getData() == null)
        {
            throw new ApplicationException("No data!");
        }

        if (!response.getData())
        {
            throw new ApplicationException("Product stock was not locked!");
        }

        return response.getData();
    }
}
