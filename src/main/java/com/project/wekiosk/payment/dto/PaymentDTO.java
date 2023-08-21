package com.project.wekiosk.payment.dto;

import com.project.wekiosk.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long payno;

    private Long total_price;

    private String pay_method;

    private String pay_status;

    private LocalDateTime pay_date;

    private int ostatus;

    private List<ProductDTO> products;

    private Long ono;

}
