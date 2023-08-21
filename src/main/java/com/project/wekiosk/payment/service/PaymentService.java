package com.project.wekiosk.payment.service;

import com.project.wekiosk.payment.dto.*;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Transactional
public interface PaymentService {

    PageResponseDTO<PaymentListDTO> list(Long sno, PageRequestDTO requestDTO);

    PaymentDTO getOne(Long payno);

    Long register(RegPaymentDTO regPaymentDTO);

    void modify(PaymentDTO paymentDTO);

    Map<String, Long> getSales(Long sno, LocalDate date);

}
