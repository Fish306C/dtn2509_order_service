package org.example.dtn2509_order_service.client.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LockProductRequest
{
    private List<LockProductItemRequest> items;
}
