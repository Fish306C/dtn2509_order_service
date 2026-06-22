package org.example.dtn2509_order_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.dto.BaseResponse;
import org.example.dtn2509_order_service.dto.request.CreateOrderItemRequest;
import org.example.dtn2509_order_service.dto.response.OrderItemResponse;
import org.example.dtn2509_order_service.service.IOrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/order-items")
public class OrderItemController
{
    private final IOrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderItemResponse>> create(@RequestBody @Valid CreateOrderItemRequest createOrderItemRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(orderItemService.create(createOrderItemRequest), "Created"));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<OrderItemResponse>>> getAll()
    {
        return ResponseEntity.ok(new BaseResponse<>(orderItemService.getAll(), "Success"));
    }
}
