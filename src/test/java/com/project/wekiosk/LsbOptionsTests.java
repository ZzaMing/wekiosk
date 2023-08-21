package com.project.wekiosk;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LsbOptionsTests {

    @Autowired
    OptionsRepository optionsRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void addOptions() {

        for(Long i = 1L; i < 10; i++){
            for(Long j = 1L; j < 4; j++){


                // pno를 사용하여 product 엔티티를 가져옴
                Product product = productRepository.findById(i).orElseThrow();

                Options options = Options.builder()
                        .oname(j+"번 옵션")
                        .oprice(j*500)
                        .product(product) // product 엔티티를 설정하여 연관 관계를 맺음
                        .build();

                optionsRepository.save(options);
            }
        }
    }
}
