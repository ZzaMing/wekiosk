package com.project.wekiosk.security.handler;


import com.google.gson.Gson;
import com.project.wekiosk.fcm.service.FcmNotificationService;
import com.project.wekiosk.member.dto.MemberDTO;
import com.project.wekiosk.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        log.info("-------------------------");
        log.info("APILoginSuccessHandler authen: " + authentication);
        log.info("=========================");

        MemberDTO memberDTO = (MemberDTO)authentication.getPrincipal();

        log.info("APILoginSuccessHandler dto: " + memberDTO);
        log.info("-------------------------");


        Map<String, Object> claims = memberDTO.getClaims();

        String accessToken = JWTUtil.generateToken(claims, 10);
        String refreshToken = JWTUtil.generateToken(claims, 60 * 24);

//        log.info(accessToken);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        log.info(claims.get("accessToken"));

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        log.info("#####################@"+jsonStr);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}