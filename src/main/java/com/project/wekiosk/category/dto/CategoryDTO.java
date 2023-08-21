package com.project.wekiosk.category.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Long cateno;
    private String catename;
    private Long storeSno;


    public Long getStoreSno() {
        return storeSno;
    }

    public void setStoreSno(Long storeSno) {
        this.storeSno = storeSno;
    }
}
