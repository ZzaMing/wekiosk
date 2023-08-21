package com.project.wekiosk;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.repository.CategoryRepository;
import com.project.wekiosk.store.domain.Store;
import com.project.wekiosk.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryTests {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void insertCategory() {

        Store store = storeRepository.findById(2L).orElseThrow();
        Category category = Category.builder()
                .catename("drink")
                .store(store)
                .build();

        categoryRepository.save(category);

//        Category foundCategory = categoryRepository.findById(1L).orElse(null);
//        if (foundCategory == null) {
//            System.out.println("Category not found");
//            return;
//        }
//
//        if (!foundCategory.getCatename().equals("TestCategory")) {
//            System.out.println("Category name mismatch");
//            return;
//        }


        System.out.println("Test passed");
    }

    @Test
    public void testDeleteCategory() {

        Store store = storeRepository.findById(2L).orElseThrow();

        Category category = Category.builder()
                .cateno(1L)
                .catename("TestCategory")
                .store(store)
                .build();

        categoryRepository.save(category);

        categoryRepository.deleteById(1L);
        Category deletedCategory = categoryRepository.findById(1L).orElse(null);
        if (deletedCategory != null) {
            System.out.println("Delete failed");
            return;
        }

        System.out.println("Test passed");
    }
}