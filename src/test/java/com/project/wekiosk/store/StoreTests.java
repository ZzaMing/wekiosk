package com.project.wekiosk.store;

import com.project.wekiosk.member.domain.Member;
import com.project.wekiosk.member.repository.MemberRepository;
import com.project.wekiosk.store.domain.Store;
import com.project.wekiosk.store.repository.StoreRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class StoreTests {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void testInsert(){

//        Optional<Member> result = memberRepository.findById("admin@kiosk.com");
//
//        Member member = result.orElseThrow();

        Member member = Member.builder().memail("admin@kiosk.com").build();

        Store store = Store.builder()
                .sname("행복분식2호점")
                .saddress("종로3가")
                .scontact("02-1234-5678")
                .member(member)
                .build();

        storeRepository.save(store);
    }
}
