package com.project.wekiosk.payment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class PageRequestDTO {

    private int page = 1;

    private int size = 5;

    private String type, keyword;

    public PageRequestDTO() {
        this(1, 5);
    }

    public PageRequestDTO(int page, int size) {
        this(page, size, null, null);
    }

    public PageRequestDTO(int page, int size, String type, String keyword) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 || size > 100 ? 5 : size;
        this.type = type;
        this.keyword = keyword;
    }

}
