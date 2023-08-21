package com.project.wekiosk.store.domain;

import com.project.wekiosk.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    private String sname;

    private String saddress;

    private String scontact;

    // 회원의 상태. -1 = 계정 정지,
    //             0 = 활성화,
    //             1 = 회원 탈퇴(비활성화)
    private int sstatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeSname(String sname) {
        this.sname = sname;
    }

    public void changeSaddress(String saddress) {
        this.saddress = saddress;
    }

    public void changeScontact(String scontact) {
        this.scontact = scontact;
    }

    public void delAccount() {
        this.sstatus = 1;
    }
}
