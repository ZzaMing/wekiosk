package com.project.wekiosk.store.repository;

import com.project.wekiosk.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select st from Store st where st.member.memail = :memail and st.sstatus = 0 order by st.sno asc")
    List<Store> getListByEmail(@Param("memail") String memail);

}
