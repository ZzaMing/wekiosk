package com.project.wekiosk.member.service;

import com.project.wekiosk.member.dto.MemberDTO;
import com.project.wekiosk.member.dto.MemberProfileDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface MemberService {

    String register(MemberDTO memberDTO);

    MemberProfileDTO getOne(List<String> kakaoInfo);

    void pwModifier(MemberDTO memberDTO);

    void delete(String memail);

    MemberDTO login(String memail, String mpw, String fcmtoken);

    void updateFcmtoken(MemberProfileDTO dto);

    String fcmTokenCheck(String memail);

    int duplicateCheck(String memail);

    Map<String, Object> sendSimpleMessage(String to)throws Exception;


}
