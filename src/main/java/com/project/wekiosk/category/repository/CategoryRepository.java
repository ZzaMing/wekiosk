package com.project.wekiosk.category.repository;

import com.project.wekiosk.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByCateno(Long cateno);

    @Query("SELECT c FROM Category c WHERE c.store.sno = :sno")
    List<Category> findCategoriesBySno(@Param("sno") Long sno);
    //List<Category> getListBySno(Long sno);



}
