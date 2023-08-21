package com.project.wekiosk.payment.dto;

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
public class PaymentListDTO {

    private Long payno;

    private Long total_price;

    private String pay_status;

    private LocalDateTime pay_date;

    private String pname;

    private long pcount;

    private int ostatus;

}
