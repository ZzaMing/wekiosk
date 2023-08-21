package com.project.wekiosk.faq;

import com.project.wekiosk.faq.domain.Faq;
import com.project.wekiosk.faq.repository.FaqRepository;
import com.project.wekiosk.member.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class FaqTests {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsert(){

        for(int i = 0; i < 121; i++){
            Faq faq = Faq.builder()
                    .qtitle("자주 묻는 질문 테스트"+i+"번")
                    .qcontent(i+"번 내용")

                    .build();

            faqRepository.save(faq);
        }
    }

    @Test
    public void testReadOne(){

        Optional<Faq> result = faqRepository.findById(1L);

        Faq faq = result.orElseThrow();

        log.info(faq);
    }

    @Test
    public void testDelete(){

        faqRepository.deleteById(5L);
    }

    @Test
    public void testModify(){

        Long qno = 3L;

        Optional<Faq> result = faqRepository.findById(qno);

        Faq faq = result.orElseThrow();

        faq.changeQcontent("변경된 "+qno+"번 내용");
        faq.changeQtitle("변경된 "+qno+"번 제목");

        faqRepository.save(faq);
    }

}
