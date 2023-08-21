package com.project.wekiosk.payment.repository.search;

import com.project.wekiosk.payment.dto.PageRequestDTO;
import com.project.wekiosk.payment.dto.PageResponseDTO;
import com.project.wekiosk.payment.dto.PaymentListDTO;

public interface PaymentSearch {

    PageResponseDTO<PaymentListDTO> list(Long sno, PageRequestDTO pageRequestDTO);

}
