package com.project.wekiosk.security.filter;


import com.google.gson.Gson;
import com.project.wekiosk.member.dto.MemberDTO;
import com.project.wekiosk.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter  {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // Preflight
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }

        // /api/todo/list /api/member/login
        String path = request.getRequestURI();

        if(path.startsWith("/api/member")){
            if(path.startsWith("/delete") || path.startsWith("/getone")){
                return false;
            }
            return true;
        }

        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("---------------doFilterInternal----------------");

        log.info("---------------doFilterInternal----------------");

        String authHeaderStr = request.getHeader("Authorization");

        log.info(authHeaderStr);

        try {
            //Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            String memail = (String) claims.get("memail");
            String mpw = (String) claims.get("mpw");
            String mname = (String) claims.get("mname");
            Boolean social = (Boolean) claims.get("social");
            List<String> roleNames = (List<String>) claims.get("roleNames");
            int mstatus = (int) claims.get("mstatus");
            String fcmtoken = (String) claims.get("fcmtoken");

            MemberDTO memberDTO = new MemberDTO(memail, mpw, mname, social.booleanValue(), roleNames, mstatus, fcmtoken);

            log.info("-----------------------------------");
            log.info(memberDTO);
            log.info(memberDTO.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDTO, mpw, memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            filterChain.doFilter(request, response);

        }catch(Exception e){

            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            if(e.getMessage().equals("Expired")){
                PrintWriter printWriter = response.getWriter();
                printWriter.println(gson.toJson(Map.of("error", "Expired")));
                printWriter.close();
            }

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();

        }


    }
}