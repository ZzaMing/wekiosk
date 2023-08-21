package com.project.wekiosk.option.domain;

import com.project.wekiosk.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ord")
    private Long ord;

    private String oname;
    private Long oprice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public void setPno(Long pno) {
    }
}
