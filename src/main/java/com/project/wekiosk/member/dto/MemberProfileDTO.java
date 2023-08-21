package com.project.wekiosk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberProfileDTO {

    private String memail;

    private String mpw;

    private String mname;

    private int mstatus;

    private boolean social;

    private String fcmtoken;

    private List<String> roleNames = new ArrayList<>();

    private String accessToken;

    private String refreshToken;

        public Map<String, Object> getClaims() {

        Map<String, Object> map = new HashMap<>();

        map.put("memail", memail);
        map.put("mpw", mpw);
        map.put("mname", mname);
        map.put("social", social);
        map.put("roleNames", roleNames);
        map.put("mstatus", mstatus);
        map.put("fcmtoken", fcmtoken);
        // 뮤터블

        return map;
    }
}
