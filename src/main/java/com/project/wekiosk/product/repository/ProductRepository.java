package com.project.wekiosk.product.repository;


import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.product.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


//    Product save(Product product);

    @Query("select p.pname, p.pprice from Product p where p.pno = :pno")
    Product selectOne(@Param("pno") Long pno);

    @EntityGraph(attributePaths = "images")
    @Query("SELECT p FROM Product p WHERE p.category.cateno = :cateno AND p.delFlag = false")
    List<Product> findAllByCategory(@Param("cateno") Long cateno);


    List<Product> findByCategory(Category category);

    @EntityGraph(attributePaths = "images")
    @Query("SELECT p FROM Product p WHERE p.category.cateno = :cateno AND p.pno = :pno")
    Optional<Product> findProductInCategory(@Param("cateno") Long cateno, @Param("pno") Long pno);

    boolean existsByCategory(Category category);

    @EntityGraph(attributePaths = "images")
    @Query("SELECT p FROM Product p WHERE p.category.cateno = :cateno AND p.isShow = TRUE AND p.delFlag = false")
    List<Product> findShowProduct(@Param("cateno") Long cateno);
}
