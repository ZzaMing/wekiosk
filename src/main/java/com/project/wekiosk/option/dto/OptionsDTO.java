package com.project.wekiosk.option.dto;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionsDTO {

    private Long pno;
    private String oname;
    private Long oprice;
    private Long ord;

}
