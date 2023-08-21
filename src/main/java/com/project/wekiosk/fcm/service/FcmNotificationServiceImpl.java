package com.project.wekiosk.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.project.wekiosk.fcm.dto.FcmNotificationDTO;
import com.project.wekiosk.member.domain.Member;
import com.project.wekiosk.member.repository.MemberRepository;
import com.project.wekiosk.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class FcmNotificationServiceImpl implements FcmNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;

//    public static final class FcmException extends RuntimeException {
//
//        public FcmException(String msg) {
//            super(msg);
//        }
//    }


    @Override
    public void sendLoginInfo(String email) {

        Optional<Member> result = memberRepository.findById(email);
        Member member = result.orElseThrow();

        if (member.getFcmtoken() == null) {
            log.info("-- -- -- FCM TOKEN IS NULL -- -- --");
            return;
        }

        String token = member.getFcmtoken();
        Message message = Message.builder()
                .putData("title", "로그인 알림")
                .putData("body", email + "님, 안녕하세요?")
                .putData("image", "https://192.168.0.29:3000/img/no3.jpg")
                .setToken(token)
                .build();

        log.info("--------------------------------fcm sendLoginInfo");

        send(message);
    }

    @Override
    public void sendPaymentInfo(String email) {

        Optional<Member> result = memberRepository.findById(email);
        Member member = result.orElseThrow();

        if (member.getFcmtoken() == null) {
            log.info("-- -- -- FCM TOKEN IS NULL -- -- --");
            return;
        }

        String token = member.getFcmtoken();
        Message message = Message.builder()
                .putData("title", "신규 주문 안내")
                .putData("body", "새로운 주문이 등록되었습니다.")
                .setToken(token)
                .build();

        log.info("--------------------------------fcm sendPaymentInfo");

        send(message);
    }


    public void send(Message message) {
        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
