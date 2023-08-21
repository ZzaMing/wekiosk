package com.project.wekiosk.member.service;

import com.project.wekiosk.fcm.service.FcmNotificationService;
import com.project.wekiosk.member.domain.Member;
import com.project.wekiosk.member.domain.MemberRole;
import com.project.wekiosk.member.dto.MemberDTO;
import com.project.wekiosk.member.dto.MemberProfileDTO;
import com.project.wekiosk.member.repository.MemberRepository;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final FcmNotificationService fcmNotificationService;

    // 런타임익셉션 -> 언체크드익셉션
    public static final class MemberLoginException extends RuntimeException {

        public MemberLoginException(String msg) {
            super(msg);
        }
    }

    @Override
    public MemberDTO login(String memail, String mpw, String fcmtoken) {

        MemberDTO memberDTO = null;

        try {
            Optional<Member> result = memberRepository.findById(memail);

            Member member = result.orElseThrow();

            if (member.getMstatus() == 1) {
                throw new MemberLoginException("Account Deleted");
            }

            member.updateFcmToken(fcmtoken);
            memberRepository.save(member);

            memberDTO = new MemberDTO(member.getMemail(), "", member.getMname(), false, null, 0, "");

        } catch (Exception e) {
            throw new MemberLoginException(e.getMessage());

        }

        return memberDTO;
    }

    @Override
    public void updateFcmtoken(MemberProfileDTO dto) {

        Member member = memberRepository.findById(dto.getMemail()).orElseThrow();

        member.updateFcmToken(dto.getFcmtoken());

        memberRepository.save(member);
    }

    @Override
    public String fcmTokenCheck(String memail) {

        Member member = memberRepository.findById(memail).orElseThrow();

        return member.getFcmtoken();
    }

    @Override
    public int duplicateCheck(String memail) {

        Optional<Member> result = memberRepository.findById(memail);

        return result.isPresent() ? 1 : 0;
    }

    @Override
    public String register(MemberDTO memberDTO) {

        memberDTO.setMpw(passwordEncoder.encode(memberDTO.getMpw()));

        Member member = modelMapper.map(memberDTO, Member.class);

        member.addRole(MemberRole.USER);

        memberRepository.save(member);

        return memberDTO.getMemail();
    }

    @Override
    public MemberProfileDTO getOne(List<String> kakaoInfo) {

        String memail = kakaoInfo.get(0);

        log.info("==========================================================");
        log.info(kakaoInfo.get(0));
        log.info(kakaoInfo.get(1));

        Optional<Member> result = memberRepository.findById(memail);

        if (result.isPresent()) {

            Member member = result.orElseThrow();

            MemberProfileDTO dto = modelMapper.map(member, MemberProfileDTO.class);

            return dto;
        }

        // 데이터베이스에 존재하지 않는 이메일이라면
        Member socialMember = Member.builder()
                .memail(memail)
                .mpw(passwordEncoder.encode(UUID.randomUUID().toString()))
                .mname(kakaoInfo.get(1))
                .social(true)
                .build();

        memberRepository.save(socialMember);
        try {
            
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        Member socialMemail = memberRepository.findById(socialMember.getMemail()).orElseThrow();

        MemberProfileDTO dto = modelMapper.map(socialMemail, MemberProfileDTO.class);

        return dto;
    }

    @Override
    public void pwModifier(MemberDTO memberDTO) {

        Optional<Member> result = memberRepository.findById(memberDTO.getMemail());

        Member member = result.orElseThrow();

        member.changeMpw(passwordEncoder.encode(memberDTO.getMpw()));

        memberRepository.save(member);
    }

    @Override
    public void delete(String memail) {

        Optional<Member> result = memberRepository.findById(memail);

        Member member = result.orElseThrow();

        member.delAccount();

        memberRepository.save(member);
    }

    private String ePw = createKey();

    private MimeMessage createMessage(String memail, String ePw) throws Exception {

        log.info("보내는 대상 : " + memail);
        log.info("인증 번호 : " + ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, memail);// 보내는 대상
        message.setSubject("WE'KIOSK 인증번호입니다.");// 제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<img src='http://localhost:3000/img/logo.png' />";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "<p>회원가입 인증번호입니다.<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.<p>";
        msgg += "<br>";
        msgg += "<div>";
        msgg += "<h3 style='color:blue;'>인증번호: </h3>";
        msgg += "<strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용
        message.setFrom(new InternetAddress("wekiosk@kiosk.com", "WEKIOSK"));// 보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // 0부터 9까지의 난수 생성
            key.append(digit);
        }

        return key.toString();
    }

    @Override
    public Map<String, Object> sendSimpleMessage(String memail) throws Exception {
        String ePw = createKey();

        // TODO Auto-generated method stub
        MimeMessage message = createMessage(memail, ePw);

        try {// 예외처리

            emailSender.send(message);
            return Map.of("email", memail, "code", ePw);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

    }

}
