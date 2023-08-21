package com.project.wekiosk.member;

import com.project.wekiosk.member.domain.Member;
import com.project.wekiosk.member.domain.MemberRole;
import com.project.wekiosk.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert(){

        Member member = Member.builder()
                .memail("lsb@kiosk.com")
                .mpw(passwordEncoder.encode("1111"))
                .mname("이아무개")
                .build();

        member.addRole(MemberRole.USER);

        repository.save(member);
    }

    @Test
    public void testInsertMember(){

        for (int i = 0; i < 10 ; i++) {

            Member member = Member.builder()
                    .memail("user"+i+"@aaa.com")
                    .mpw(passwordEncoder.encode("1111"))
                    .mname("홍길동"+i+"호")
                    .build();

            member.addRole(MemberRole.USER);

            if(i >= 5){
                member.addRole(MemberRole.MANAGER);
            }

            if(i >=8){
                member.addRole(MemberRole.ADMIN);
            }

            repository.save(member);

        }


    }

    @Test
    public void insertRole() {

        Member member = Member.builder()
                .memail("lsb@kiosk.com")
                .build();

        member.addRole(MemberRole.USER);

        repository.save(member);
    }

}
