package com.project.wekiosk.order.service;

import com.project.wekiosk.order.dto.OrderDTO;
import com.project.wekiosk.payment.dto.PaymentDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface OrdersService {

    void modifyOs(OrderDTO orderDTO);

    Long register(OrderDTO orderDTO);

}
