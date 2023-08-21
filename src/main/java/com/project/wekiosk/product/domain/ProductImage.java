package com.project.wekiosk.product.domain;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@Getter
@Embeddable
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fname;

    private int ord;




}
