package com.project.wekiosk.order.repository;

import com.project.wekiosk.order.domain.OrderDetail;
import com.project.wekiosk.order.domain.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @EntityGraph(attributePaths = {"options"})
    @Query("select d from OrderDetail d where d.orders.ono = :ono")
    List<OrderDetail> selectDetilList(@Param("ono") Long ono);

}
