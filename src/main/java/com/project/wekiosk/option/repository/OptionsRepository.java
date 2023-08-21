package com.project.wekiosk.option.repository;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OptionsRepository extends JpaRepository<Options, Long> {

//    Optional<Options> findByPnoAndOrd(Long pno, Long ord);


    Optional<Options> findById(Long ord);


    @Query("SELECT MAX(o.ord) FROM Options o WHERE o.product.pno = ?1")
    Optional<Long> findMaxOrdByPno(Long pno);


    @Query(value = "select ord from options where product_pno = ?1", nativeQuery = true)
    List<Long> findByPno(Long pno);

    @Query("select o from Options o where o.product.pno = :pno order by o.ord asc")
    List<Options> getListByPno(@Param("pno") Long pno);

}
