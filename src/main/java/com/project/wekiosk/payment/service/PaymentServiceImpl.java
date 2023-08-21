package com.project.wekiosk.payment.service;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.dto.OptionsDTO;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.order.domain.OrderDetail;
import com.project.wekiosk.order.domain.Orders;
import com.project.wekiosk.order.repository.OrderDetailRepository;
import com.project.wekiosk.order.repository.OrdersRepository;
import com.project.wekiosk.payment.domain.Payment;
import com.project.wekiosk.payment.dto.*;
import com.project.wekiosk.payment.repository.PaymentRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.repository.ProductRepository;
import jakarta.persistence.Embeddable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository detailRepository;

    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<PaymentListDTO> list(Long sno, PageRequestDTO requestDTO) {

        return repository.list(sno, requestDTO);
    }

    @Override
    public PaymentDTO getOne(Long payno) {

        Payment payment = repository.findById(payno).orElseThrow();

        List<ProductDTO> productList = new ArrayList<>();

        Orders orders = ordersRepository.findById(payment.getOrders().getOno()).orElseThrow();

        List<OrderDetail> detailList = detailRepository.selectDetilList(orders.getOno());

        detailList.forEach(d -> {

            List<OptionsDTO> optionsList = new ArrayList<>();

            d.getOptions().forEach(o -> {
                optionsList.add(OptionsDTO.builder()
                        .pno(d.getProduct().getPno())
                        .oname(o.getOname())
                        .oprice(o.getOprice())
                        .ord(o.getOrd())
                        .build());
            });

            productList.add(ProductDTO.builder()
                    .pno(d.getProduct().getPno())
                    .pname(d.getProduct().getPname())
                    .pprice(d.getProduct().getPprice())
                    .quantity(d.getQuantity())
                    .options(optionsList)
                    .build());
        });

        return PaymentDTO.builder()
                .payno(payment.getPayno())
                .total_price(payment.getTotal_price())
                .pay_method(payment.getPay_method())
                .pay_status(payment.getPay_status())
                .pay_date(payment.getPay_date())
                .ostatus(payment.getOrders().getOstatus())
                .products(productList)
                .ono(payment.getOrders().getOno())
                .build();
    }

    @Override
    public Long register(RegPaymentDTO regPaymentDTO) {

        Payment payment = modelMapper.map(regPaymentDTO, Payment.class);

        return repository.save(payment).getPayno();
    }

    @Override
    public void modify(PaymentDTO paymentDTO) {

        Optional<Payment> result = repository.findById(paymentDTO.getPayno());

        Payment payment = result.orElseThrow();

        payment.changePayStatus(paymentDTO.getPay_status());

        repository.save(payment);
    }

    @Override
    public Map<String, Long> getSales(Long sno, LocalDate date) {

        Map<String, Long> salesMap = new HashMap<>();

        // 해당 일 결제 상태 건수 // [complete, refund]
        repository.pStatusCount(sno, date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                .forEach(result -> {
                    String status = (String) result[0];
                    Long count = (Long) result[1];
                    salesMap.put(status, count);
                });

        // 해당 일 주문 상태 건수 // [preparing, prepared]
        repository.oStatusCount(sno, date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                .forEach(result -> {
                    String status = (int) result[0] == 0 ? "preparing" : "prepared";
                    Long count = (Long) result[1];
                    salesMap.put(status, count);
                });

        // 해당 월 매출 // 날짜: 매출
        repository.dailySalesByMonth(sno, date.getYear(), date.getMonthValue())
                .forEach(result -> {
                    Date rDate = (Date) result[0];
                    Long sale = (Long) result[1];
                    salesMap.put(rDate.toString(), sale);
                });

        // 전달 월 매출 합계
        date = date.minusMonths(1);

        Long lastSale = repository.lastMonthSale(sno, date.getYear(), date.getMonthValue());

        salesMap.put(date.toString().substring(0, 7), lastSale == null ? 0L : lastSale);

        return salesMap;
    }

}
