package com.project.wekiosk.store.service;

import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.faq.dto.PageRequestDTO;
import com.project.wekiosk.faq.dto.PageResponseDTO;
import com.project.wekiosk.store.dto.StoreDTO;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface StoreService {

    List<StoreDTO> getList(String memail);

    String register(StoreDTO storeDTO);

    StoreDTO getOne(Long sno);

    void modifier(StoreDTO storeDTO);

    void delete(Long sno);
}
