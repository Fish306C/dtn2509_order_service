package org.example.dtn2509_order_service.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LockProductItemRequest
{
    private String productId;
    private Integer quantity;
}
