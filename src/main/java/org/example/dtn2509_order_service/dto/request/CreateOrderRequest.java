package org.example.dtn2509_order_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest
{
    @NotBlank
    private String customerId;

    @Valid
    @NotEmpty
    private List<CreateOrderItemRequest> orderItems;
}
