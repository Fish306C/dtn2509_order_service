package org.example.dtn2509_order_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemRequest
{
    @NotBlank
    private String productId;

    @Positive
    @NotNull
    private Integer quantity;
}
