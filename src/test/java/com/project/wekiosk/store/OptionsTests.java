package com.project.wekiosk.store;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OptionsTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OptionsRepository optionsRepository;

    @Test
    public void addOptions() {

        for (Long j = 1L; j < 4; j++) {

            Product product = productRepository.findById(6L).orElseThrow();

            Options options = Options.builder()
                    .oname("shot")
                    .oprice(1000L)
                    .product(product)
                    .build();

            optionsRepository.save(options);
        }
    }
}
