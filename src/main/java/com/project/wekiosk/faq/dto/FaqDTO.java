package com.project.wekiosk.faq.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FaqDTO {

    private Long qno;

    private String qtitle;

    private String qcontent;

}
