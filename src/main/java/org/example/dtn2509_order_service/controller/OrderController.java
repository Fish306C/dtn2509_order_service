package org.example.dtn2509_order_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtn2509_order_service.dto.BaseResponse;
import org.example.dtn2509_order_service.dto.request.CreateOrderRequest;
import org.example.dtn2509_order_service.dto.response.OrderResponse;
import org.example.dtn2509_order_service.service.IOrderService;
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
@RequestMapping("/v1/orders")
public class OrderController
{
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> create(@RequestBody @Valid CreateOrderRequest createOrderRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(orderService.create(createOrderRequest), "Created"));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAll()
    {
        return ResponseEntity.ok(new BaseResponse<>(orderService.getAll(), "Success"));
    }
}
