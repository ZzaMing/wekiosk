package com.project.wekiosk.category.service;

import com.project.wekiosk.category.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories(Long sno);

//    List<CategoryDTO> getListBySno(Long sno);

    CategoryDTO getCategoryById(Long cateno);

    void registerCategory(CategoryDTO categoryDTO);

    void updateCategory(Long cateno, CategoryDTO categoryDTO);

    void deleteCategory(Long cateno);

    boolean hasProductsInCategory(Long cateno);
}
