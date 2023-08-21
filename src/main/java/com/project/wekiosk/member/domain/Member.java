package com.project.wekiosk.member.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id
    private String memail;

    private String mpw;

    private String mname;


    // 회원의 상태. -1 = 계정 정지,
    //             0 = 활성화,
    //             1 = 회원 탈퇴(비활성화)
    private int mstatus;

    private String fcmtoken;

    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    public void addRole(MemberRole memberRole){

        memberRoleList.add(memberRole);
    }

    public void clearRole(){
        memberRoleList.clear();
    }

    public void changeMpw(String mpw) {
        this.mpw = mpw;
    }

    public void updateFcmToken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    public void delAccount() {
        this.mstatus = 1;
    }

}
