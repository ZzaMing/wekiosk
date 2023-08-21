package com.project.wekiosk.store.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StoreDTO {

    private Long sno;

    private String sname;

    private String saddress;

    private String scontact;

    private int sstatus;

    private String memail;
}
