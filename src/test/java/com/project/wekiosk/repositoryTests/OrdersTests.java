package com.project.wekiosk.repositoryTests;

import com.google.gson.Gson;
import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.order.domain.OrderDetail;
import com.project.wekiosk.order.domain.Orders;
import com.project.wekiosk.order.repository.OrderDetailRepository;
import com.project.wekiosk.order.repository.OrdersRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.store.domain.Store;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class OrdersTests {

    @Autowired
    OrdersRepository repository;

    @Autowired
    OrderDetailRepository detailRepository;

    @Autowired
    OptionsRepository optionsRepository;

    @Test
    @Commit
    @Transactional
    public void testInsert() {

        for (int i = 0; i < 4; i++) {

            Store store = Store.builder()
                    .sno(1L)
                    .build();

            Product product = Product.builder()
                    .pno(2L)
                    .build();
            Product product2 = Product.builder()
                    .pno(3L)
                    .build();

            Orders orders = Orders.builder()
                    .ostatus(0)
                    .store(store)
                    .build();


            repository.save(orders);

        }//end for

    }//end testInsert

    @Test
    public void testSelect() {

        log.info(repository.findById(5L));
        log.info("--------------------------");
        log.info(repository.findAll());

    }//end testSelect

    @Test
    public void testDelete() {

        Optional<Orders> result = repository.findById(4L);

        Orders orders = result.orElseThrow();

        orders.changeOstatus(1);

        repository.save(orders);

    }

    @Test
    public void testSelectDetailList() {

        List<OrderDetail> detailList = detailRepository.selectDetilList(115L);

        detailList.forEach(d ->
                log.info(d + " ------- " + d.getOptions()));
    }

    @Test
    public void testInsertDetail() {
//ono 23~ 46
        for (int j = 0; j < 3; j++) {
            for (long i = 23; i < 47; i++) {

                Orders orders = Orders.builder().ono(i).build();

                Product product = Product.builder().pno((i % 9) + 1L).build();

                OrderDetail detail = OrderDetail.builder()
                        .quantity((int) (i % 5) + 1)
                        .orders(orders)
                        .product(product)
                        .build();

                log.info("--------before findByPno---------");

                List<Long> ordList = optionsRepository.findByPno(product.getPno());

                log.info(ordList);

                long finalI = i;
                ordList.forEach(ord -> {

                    if ((ord + finalI) % 2 == 0) {

                        Options options = optionsRepository.findById(ord).orElseThrow();

                        String oname = options.getOname();
                        Long oprice = options.getOprice();

                        detail.addOrderOption(ord, oname, oprice);
                    }
                });

                detailRepository.save(detail);
            }
        }
    }

}
