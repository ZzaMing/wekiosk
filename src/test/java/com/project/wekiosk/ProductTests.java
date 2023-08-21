package com.project.wekiosk;//package com.project.wekiosk;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static com.project.wekiosk.product.domain.QProduct.product;

@SpringBootTest
public class ProductTests {

    @Autowired
    ProductRepository repo;

     @Test
     public void testInsert() {

         Category category = new Category(2L);


         for (int i = 0; i < 7; i++) {
             Product product = Product.builder()
                     .pname("닭칼국수")
                     .pprice(10000L)
                     .category(category) // 영속 상태의 Category 엔티티를 설정
                     .build();

             product.addImage(UUID.randomUUID().toString() + "_aaa.jpg");

             repo.save(product);
         }
     }


    @Transactional
    @Test
    public void testRead(){

        Optional<Product> result = repo.findById(1L);

        Product product = result.orElseThrow();

        System.out.println(product);
        System.out.println("--------------------------");
//        System.out.println(product.getImages());


    }
}
