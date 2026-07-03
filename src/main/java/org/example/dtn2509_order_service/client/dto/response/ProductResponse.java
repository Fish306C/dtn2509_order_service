package org.example.dtn2509_order_service.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse
{
    private String id;
    private String name;
    private Integer price;
    private Integer stock;
    private String categoryId;
}
