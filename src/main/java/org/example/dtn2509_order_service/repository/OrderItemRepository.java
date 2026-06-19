package org.example.dtn2509_order_service.repository;

import org.example.dtn2509_order_service.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, String>
{
}
