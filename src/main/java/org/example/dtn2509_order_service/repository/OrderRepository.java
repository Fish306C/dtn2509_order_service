package org.example.dtn2509_order_service.repository;

import org.example.dtn2509_order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String>
{
}
