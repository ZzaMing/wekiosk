package com.project.wekiosk.category.domain;

import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.store.domain.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cateno;
    private String catename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_sno")
    private Store store;

    public Category(Long cateno) {
        this.cateno = cateno;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public void setCatename(String catename){
        this.catename = catename;
    }

    public void setCateno(Long cateno) {
        this.cateno = cateno;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
