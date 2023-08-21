package com.project.wekiosk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderOptionDTO {

    private String oname;

    private Long oprice;

    private Long ord;
}
