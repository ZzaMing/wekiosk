package com.project.wekiosk.payment.domain;

import com.project.wekiosk.order.domain.Orders;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "orders")
@Table(name = "payment")
@EntityListeners(value = {AuditingEntityListener.class})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payno;

    private Long total_price;

    private String pay_method;

    private String pay_status;

    @CreatedDate
    private LocalDateTime pay_date;

    @OneToOne(fetch = FetchType.LAZY)
    private Orders orders;

    public void changePayStatus(String payStatus){
        this.pay_status = payStatus;
    }
}
