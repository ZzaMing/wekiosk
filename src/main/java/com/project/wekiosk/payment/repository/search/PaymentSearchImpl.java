package com.project.wekiosk.payment.repository.search;



import com.project.wekiosk.order.domain.QOrderDetail;
import com.project.wekiosk.order.domain.QOrders;
import com.project.wekiosk.payment.domain.Payment;

import com.project.wekiosk.payment.domain.QPayment;
import com.project.wekiosk.payment.dto.PageRequestDTO;
import com.project.wekiosk.payment.dto.PageResponseDTO;
import com.project.wekiosk.payment.dto.PaymentListDTO;
import com.project.wekiosk.product.domain.QProduct;
import com.project.wekiosk.product.domain.QProductImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PaymentSearchImpl extends QuerydslRepositorySupport implements PaymentSearch {

    public PaymentSearchImpl() {
        super(Payment.class);
    }

    @Override
    public PageResponseDTO<PaymentListDTO> list(Long sno, PageRequestDTO pageRequestDTO) {

        QPayment payment = QPayment.payment;
        QOrders orders = QOrders.orders;
        QOrderDetail detail = QOrderDetail.orderDetail;
        QProduct product = QProduct.product;

        JPQLQuery<Payment> query = from(payment);
        query.leftJoin(payment.orders, orders);
//        query.leftJoin(detail.orders, orders);
        query.leftJoin(detail).on(detail.orders.eq(orders));
//        query.leftJoin(detail.product, product);
        query.leftJoin(product).on(product.eq(detail.product));

        query.where(orders.store.sno.eq(sno));

        int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;

        Pageable pageable = PageRequest.of(pageNum, pageRequestDTO.getSize());

        this.getQuerydsl().applyPagination(pageable, query);

        query.groupBy(payment);

        query.orderBy(payment.pay_date.desc());

        JPQLQuery<PaymentListDTO> dtoQuery = query.select(Projections.bean(
                PaymentListDTO.class,
                payment.payno,
                payment.total_price.min().as("total_price"),
                payment.pay_status.min().as("pay_status"),
                payment.pay_date.min().as("pay_date"),
                product.pname.min().as("pname"),
                product.pname.count().as("pcount"),
                orders.ostatus));

        List<PaymentListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList,totalCount,pageRequestDTO);

    }

}
