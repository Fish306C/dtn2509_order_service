package org.example.dtn2509_order_service.service;

import lombok.RequiredArgsConstructor;
import org.example.dtn2509_order_service.client.dto.request.LockProductRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventProducer
{
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.product-lock-requested}")
    private String productLockRequestedTopic;

    public void sendLockProductRequest(String orderId, LockProductRequest lockProductRequest)
    {
        kafkaTemplate.send(productLockRequestedTopic, orderId, lockProductRequest);
    }
}
