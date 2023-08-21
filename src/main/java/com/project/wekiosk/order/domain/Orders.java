package com.project.wekiosk.order.domain;

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
@ToString(exclude = {"store"})
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;

    private int ostatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public void changeOstatus(int ostatus){
        this.ostatus = ostatus;
    }

}
