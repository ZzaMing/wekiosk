package com.project.wekiosk.payment.repository;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.payment.domain.Payment;

import com.project.wekiosk.payment.repository.search.PaymentSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentSearch {

    @Query("select DATE(p.pay_date), sum(p.total_price) from Payment p where p.orders.store.sno = :sno and YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month group by DATE(p.pay_date) order by DATE(p.pay_date)")
//    @Query("select sum(p.total_price) from Payment p where YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month group by DATE(p.pay_date) order by DATE(p.pay_date)")
    List<Object[]> dailySalesByMonth(@Param("sno") Long sno,@Param("year") int year,@Param("month") int month);

    @Query("select sum(p.total_price) from Payment p where p.orders.store.sno = :sno and YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month")
    Long lastMonthSale(@Param("sno") Long sno, @Param("year") int year,@Param("month") int month);

    @Query("select p.pay_status, count(*) from Payment p where p.orders.store.sno = :sno and YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month and DAY(p.pay_date) = :day group by p.pay_status")
    List<Object[]> pStatusCount(@Param("sno") Long sno, @Param("year") int year,@Param("month") int month, @Param("day") int day);

    @Query("select o.ostatus, count(*) from Payment p left outer join Orders o on p.orders = o where o.store.sno = :sno and YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month and DAY(p.pay_date) = :day group by o.ostatus")
    List<Object[]> oStatusCount(@Param("sno") Long sno, @Param("year") int year, @Param("month") int month, @Param("day") int day);

//    @Query("select o from Payment p left outer join Orders o on p.orders = o left outer join OrderDetail d on o.details = d where p.payno = :payno")
//    List<Options[]> getOptionInfo(@Param("payno") Long payno);

}
