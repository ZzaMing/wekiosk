package com.project.wekiosk.order.repository;

import com.project.wekiosk.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
