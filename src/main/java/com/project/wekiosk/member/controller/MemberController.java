package com.project.wekiosk.member.controller;

import com.project.wekiosk.fcm.service.FcmNotificationService;
import com.project.wekiosk.member.dto.MemberDTO;
import com.project.wekiosk.member.dto.MemberProfileDTO;
import com.project.wekiosk.member.service.MemberService;
import com.project.wekiosk.member.service.SocialService;
import com.project.wekiosk.security.CustomUserDetailsService;
import com.project.wekiosk.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.ibatis.javassist.tools.framedump;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member/")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class MemberController {

    private final MemberService memberService;
    private final SocialService socialService;
    private final FcmNotificationService fcmNotificationService;
    private final CustomUserDetailsService customUserDetailsService;

    // private final JWTUtil jwtUtil;

    @GetMapping("kakao")
    public MemberProfileDTO getAuthCode(String code) {

        log.info("---------------------------------------");
        log.info(code);

        List<String> kakaoInfo = socialService.getKakaoEmail(code);

        MemberProfileDTO memberDTO = memberService.getOne(kakaoInfo);

        Map<String, Object> claims = memberDTO.getClaims();

        String accessToken = JWTUtil.generateToken(claims, 10);
        String refreshToken = JWTUtil.generateToken(claims, 60 * 24);

        memberDTO.setAccessToken(accessToken);

        memberDTO.setRefreshToken(refreshToken);

        log.info("Return: " + memberDTO);

        return memberDTO;
    }

    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO) {

        log.info("Parameter: " + memberDTO);

        MemberDTO result = memberService.login(
                memberDTO.getMemail(),
                memberDTO.getMpw(),
                memberDTO.getFcmtoken());

        fcmNotificationService.sendLoginInfo(memberDTO.getMemail());

        log.info("Return: " + result);

        // customUserDetailsService.loadUserByUsername(memberDTO.getMemail());

        return result;
    }

    @PostMapping("regist")
    public Map<String, String> regist(@RequestBody MemberDTO memberDTO) {

        log.info(memberDTO);

        String memail = memberService.register(memberDTO);

        return Map.of("result", memail);
    }

//    @GetMapping("getone/{memail}")
//    public MemberProfileDTO getOne(@PathVariable("memail") String memail) {
//
//        return memberService.getOne(memail);
//    }

    @PutMapping("pw")
    public Map<String, String> pwModify(@RequestBody MemberDTO memberDTO) {

        memberService.pwModifier(memberDTO);

        return Map.of("result", "변경완료");
    }

    @PutMapping("fcmtoken")
    public Map<String, String> updateFcmtoken(@RequestBody MemberProfileDTO dto) {

        log.info(dto);

        memberService.updateFcmtoken(dto);

        return Map.of("result", "갱신완료");
    }

    @DeleteMapping("delete/{memail}")
    public Map<String, String> delete(@PathVariable("memail") String memail) {

        memberService.delete(memail);

        return Map.of("result", "삭제완료");
    }

    @GetMapping("fcmtoken/{memail}")
    public String fcmtokenCheck(@PathVariable("memail") String memail) {

        return memberService.fcmTokenCheck(memail);
    }

    @GetMapping("duplicate/{memail}")
    public int duplicateCheck(@PathVariable("memail") String memail) {

        return memberService.duplicateCheck(memail);
    }

    @PostMapping("emailConfirm")
    public Map<String, Object> emailConfirm(@RequestParam String memail) throws Exception {

        memail = memail.trim();

        log.info("Email Confirm..." + memail);

        return memberService.sendSimpleMessage(memail);
    }
}
