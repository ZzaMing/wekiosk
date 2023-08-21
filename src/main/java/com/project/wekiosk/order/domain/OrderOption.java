package com.project.wekiosk.order.domain;

import com.project.wekiosk.product.domain.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderOption {

    private Long ord;

    private String oname;

    private Long oprice;

}
