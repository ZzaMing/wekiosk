package com.project.wekiosk.repositoryTests;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.payment.domain.Payment;
import com.project.wekiosk.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class PaymentTests {

    @Autowired
    PaymentRepository repository;


    @Test
    public void date(){
        log.info("---------------------------------");
        log.info(LocalDateTime.now().plusDays(1));
        log.info(LocalDateTime.now().minusDays(1));
    }


    @Test
    @Commit
    @Transactional
    public void testInsert(){

        for(int i = 1 ; i<11 ; i++) {

//            Orders orders = Orders.builder().ono((long)(i+12)).build();

            Payment payment = Payment.builder()
                    .total_price(20000L + i)
                    .pay_method("결제수단" + i)
                    .pay_status("상태" + i)
                    .pay_date(LocalDateTime.now().minusDays(i))
//                    .orders(orders)
                    .build();

            repository.save(payment);

        }//end for

    }//end testInsert

    @Test
    public void testSelectOne() {

        Long payno = 5L;

//        Object[] list = repository.selectOne(payno);
//
//        log.info(Arrays.toString(list));

//        list.forEach(obj -> log.info(obj));

        log.info(">>>" + repository.findById(payno));

    }//end testSelectOne

    @Test
    public void testSelectAll(){

        log.info(repository.findAll());

    }//end testSelectAll

    @Test
    @Commit
    @Transactional
    public void testDelete(){

//        Long payno = 3L;
//
//        repository.deleteById(payno);
//
//        log.info(repository.findById(payno));

    }//end testDelete

    @Test
    @Commit
    @Transactional
    public void testUpdate(){

        Long payno = 6L;

        log.info("Before >>> " + repository.findById(payno));

        Optional<Payment> result = repository.findById(payno);

        Payment payment = result.orElseThrow();

        payment.changePayStatus("변경된 상태 !");

        repository.save(payment);

        log.info("After >>> " + repository.findById(payno));

    }//end testUpdate

    @Test
    public void testFindByMonth(){

//        List<Long> list = repository.findByMonth(2023,7);
//
//        list.forEach(sale -> log.info(sale));
    }

    @Test
    public void testCount(){

        LocalDate lc = LocalDate.of(2023, 7, 27);
//        log.info(">>>>test" + repository.tsaleCount(1L, lc.getYear(), lc.getMonthValue(), lc.getDayOfMonth()));
//        List<Object[]> list = repository.oStatusCount(1L, lc.getYear(), lc.getMonthValue(), lc.getDayOfMonth());

    }

    @Test
    public void testGetOptionInfo() {

//        List<Options[]> optionInfo = repository.getOptionInfo(47L);
//
//        optionInfo.forEach(on-> log.info(on));
    }
}
