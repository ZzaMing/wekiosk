package com.project.wekiosk.order.domain;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.store.domain.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"product", "orders", "options"})
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dno;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderOption> options = new ArrayList<>();

    public void addOrderOption(Long ord, String oname, Long oprice) {

        OrderOption option = OrderOption.builder()
                .ord(ord)
                .oname(oname)
                .oprice(oprice)
                .build();

        options.add(option);

    }


}
