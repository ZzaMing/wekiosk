package com.project.wekiosk.member.dto;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO extends User implements OAuth2User{

    private String memail;

    private String mpw;

    private String mname;

    private List<String> roleNames = new ArrayList<>();

    private int mstatus;

    private String fcmtoken;

    private boolean social;

    public MemberDTO(String memail, String mpw, String mname, boolean social, List<String> roleNames, int mstatus, String fcmtoken){

        super(memail, mpw,
                (roleNames != null) ?
                        roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList()) :
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));  // 기본적으로 ROLE_USER 추가

        this.memail = memail;
        this.mpw = mpw;
        this.roleNames = (roleNames != null) ? roleNames : new ArrayList<>();
        this.mname = mname;
        this.social = social;
        this.mstatus = mstatus;
        this.fcmtoken = fcmtoken;
    }

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

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return this.memail;
    }
}
