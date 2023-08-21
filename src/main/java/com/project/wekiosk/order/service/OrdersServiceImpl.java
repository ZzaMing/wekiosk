package com.project.wekiosk.order.service;

import com.google.firestore.v1.StructuredQuery;
import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.order.domain.OrderDetail;
import com.project.wekiosk.order.domain.OrderOption;
import com.project.wekiosk.order.domain.Orders;
import com.project.wekiosk.order.dto.OrderDTO;
import com.project.wekiosk.order.repository.OrderDetailRepository;
import com.project.wekiosk.order.repository.OrdersRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.store.domain.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository repository;
    private final OrderDetailRepository detailRepository;
    private final OptionsRepository optionsRepository;

    private final ModelMapper modelMapper;

    @Override
    public void modifyOs(OrderDTO orderDTO) {

        Optional<Orders> result = repository.findById(orderDTO.getOno());

        Orders orders = result.orElseThrow();

        orders.changeOstatus(orderDTO.getOstatus());

        repository.save(orders);

    }

    @Override
    public Long register(OrderDTO orderDTO) {

        Orders orders = Orders.builder()
                .ostatus(orderDTO.getOstatus())
                .store(Store.builder().sno(orderDTO.getSno()).build())
                .build();

        repository.save(orders);

        log.info("orders: " + orders);

        orderDTO.getDetails().forEach(d -> {

            OrderDetail detail = OrderDetail.builder()
                    .quantity(d.getQuantity())
                    .product(Product.builder().pno(d.getPno()).build())
                    .orders(orders)
                    .build();

            log.info("detail: " + detail);

            if (d.getOptions() != null) {
                d.getOptions().forEach(option -> {
                    detail.addOrderOption(option.getOrd(), option.getOname(), option.getOprice());
                });
            }

            log.info("detail2222: " + detail);

            detailRepository.save(detail);

        });

        return orders.getOno();
    }

}
