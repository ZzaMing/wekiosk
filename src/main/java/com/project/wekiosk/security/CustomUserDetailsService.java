package com.project.wekiosk.security;

import com.project.wekiosk.fcm.service.FcmNotificationService;
import com.project.wekiosk.member.domain.Member;
import com.project.wekiosk.member.dto.MemberDTO;
import com.project.wekiosk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final FcmNotificationService fcmNotificationService;

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("----------loadUserByUsername----------");
        log.info("----------loadUserByUsername----------");
        log.info(username);
        log.info("----------loadUserByUsername----------");
        log.info("----------loadUserByUsername----------");

        Member member = memberRepository.getWithRoles(username);

        if(member == null){
            throw new UsernameNotFoundException("Not Found");
        }

        MemberDTO memberDTO = new MemberDTO(
                member.getMemail(),
                member.getMpw(),
                member.getMname(),
                member.isSocial(),
                member.getMemberRoleList()
                        .stream()
                        .map(memberRole -> memberRole.name()).collect(Collectors.toList()),
                member.getMstatus(),
                member.getFcmtoken()

        );

        fcmNotificationService.sendLoginInfo(memberDTO.getMemail());

        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★"+memberDTO);

        return memberDTO;
    }
}