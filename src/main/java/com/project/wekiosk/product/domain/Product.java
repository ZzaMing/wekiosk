package com.project.wekiosk.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.option.domain.Options;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@ToString(exclude = {"category", "images", "options"})
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pno")
    private Long pno;
    private String pname;
    private Long pprice;

    private boolean isShow;

    private boolean delFlag;

    @ManyToOne
    @JoinColumn(name = "cateno", referencedColumnName = "cateno")
    @JsonIgnore
    private Category category;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Options> options = new ArrayList<>();


    public void addImage(String name) {
        ProductImage pImage = ProductImage.builder().fname(name).build();
        images.add(pImage);
    }


    public void clearOptions() {
        options.clear();
    }

    public void clearImages() {
        images.clear();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addOption(Options option) {
        options.add(option);
    }

    public void setCateno(Long cateno) {
    }

    public void changeShow(boolean isShow){
        this.isShow = isShow;
    }

    public void changePname(String pname){
        this.pname = pname;
    }

    public void changePprice(Long pprice){
        this.pprice = pprice;
    }

    public void changeOptions(List<Options> options){
        this.options = options;
    }

    public void changeDel(boolean delFlag){
        this.delFlag = delFlag;
    }
}
