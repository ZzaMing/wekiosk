package com.project.wekiosk.category.service;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.dto.CategoryDTO;
import com.project.wekiosk.category.repository.CategoryRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.domain.ProductImage;
import com.project.wekiosk.product.repository.ProductRepository;
import com.project.wekiosk.store.domain.Store;
import com.project.wekiosk.store.domain.Store;
import com.project.wekiosk.store.dto.StoreDTO;
import com.project.wekiosk.store.repository.StoreRepository;
import com.project.wekiosk.util.FileUploader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    private final ModelMapper modelMapper;

    private final FileUploader fileUploader = new FileUploader();


    @Override
    public List<CategoryDTO> getAllCategories(Long sno) {

        List<Category> categories = categoryRepository.findCategoriesBySno(sno);
        System.out.println("snoooooooooooooo"+ sno);

        //List<Category> categories = categoryRepository.getListBySno(sno);

        return categories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

//    @Override
//    public List<CategoryDTO> getListBySno(Long sno) {
//
//        List<Category> categories = categoryRepository.getListBySno(sno);
//        // Store 엔티티를 StoreDTO로 변환하여 리스트로 반환합니다.
//        return categories.stream()
//                .map(category -> modelMapper.map(category, CategoryDTO.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public CategoryDTO getCategoryById(Long cateno) {
        Category category = categoryRepository.findById(cateno)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDTO(category);
    }


    @Override
    public void registerCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCatename(categoryDTO.getCatename());


        Store store = storeRepository.findById(categoryDTO.getStoreSno()).orElseThrow(() -> new EntityNotFoundException("Store not found"));


        category.setStore(store);

        categoryRepository.save(category);
    }







    @Override
    public void updateCategory(Long cateno, CategoryDTO categoryDTO) {
        // cateno로 기존 카테고리를 찾습니다.
        Category categoryToUpdate = categoryRepository.findById(cateno)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with cateno: " + cateno));

        // categoryDTO의 정보로 카테고리를 업데이트합니다.
        categoryToUpdate.setCatename(categoryDTO.getCatename());

        // 업데이트된 카테고리를 저장합니다.
        categoryRepository.save(categoryToUpdate);
    }

    @Override
    public void deleteCategory(Long cateno) {
        // 카테고리 조회
        Category category = categoryRepository.findById(cateno)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. cateno: " + cateno));

        // 카테고리에 속하는 상품들 조회
        List<Product> products = productRepository.findByCategory(category);

        // 상품들의 파일 삭제
        products.forEach(product -> {
            List<String> fileNames = product.getImages().stream()
                    .map(ProductImage::getFname)
                    .collect(Collectors.toList());
            fileUploader.removeFiles(fileNames);
        });

        // 카테고리와 카테고리에 속하는 상품들 삭제
        productRepository.deleteInBatch(products);
        categoryRepository.delete(category);
    }


    // mapToDTO: Category 엔티티 객체를 DTO 객체로 변환
    private CategoryDTO mapToDTO(Category category) {
        return CategoryDTO.builder()
                .cateno(category.getCateno())
                .catename(category.getCatename())
                .storeSno(category.getStore().getSno()) // 변경된 부분
                .build();
    }


    private Category mapToEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .cateno(categoryDTO.getCateno())
                .catename(categoryDTO.getCatename())
                .store(storeRepository.findById(categoryDTO.getStoreSno()).orElseThrow())
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public boolean hasProductsInCategory(Long cateno) {
        Category category = categoryRepository.findById(cateno)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. cateno: " + cateno));

        return productRepository.existsByCategory(category);
    }
}
