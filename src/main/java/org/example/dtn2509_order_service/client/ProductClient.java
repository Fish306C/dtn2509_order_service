package org.example.dtn2509_order_service.client;

import org.example.dtn2509_order_service.client.dto.request.LockProductRequest;
import org.example.dtn2509_order_service.client.dto.request.ProductFilter;
import org.example.dtn2509_order_service.client.dto.response.ProductResponse;

import java.util.List;

public interface ProductClient
{
    List<ProductResponse> search(ProductFilter productFilter);

    Boolean lockProducts(LockProductRequest lockProductRequest);
}
