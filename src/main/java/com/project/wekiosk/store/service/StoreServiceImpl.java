package com.project.wekiosk.store.service;

import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.faq.dto.PageRequestDTO;
import com.project.wekiosk.faq.dto.PageResponseDTO;
import com.project.wekiosk.store.domain.Store;
import com.project.wekiosk.store.dto.StoreDTO;
import com.project.wekiosk.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<StoreDTO> getList(String memail) {

        List<Store> stores = storeRepository.getListByEmail(memail);
        // Store 엔티티를 StoreDTO로 변환하여 리스트로 반환합니다.
        return stores.stream()
                .map(store -> modelMapper.map(store, StoreDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String register(StoreDTO storeDTO) {

        Store store = modelMapper.map(storeDTO, Store.class);

        storeRepository.save(store);

        return storeDTO.getSname();
    }

    @Override
    public StoreDTO getOne(Long sno) {

        Optional<Store> result = storeRepository.findById(sno);

        Store store = result.orElseThrow();

        StoreDTO dto = modelMapper.map(store, StoreDTO.class);

        return dto;
    }

    @Override
    public void modifier(StoreDTO storeDTO) {

        Optional<Store> result = storeRepository.findById(storeDTO.getSno());

        Store store = result.orElseThrow();

        store.changeSaddress(storeDTO.getSaddress());
        store.changeSname(storeDTO.getSname());
        store.changeScontact(storeDTO.getScontact());

        storeRepository.save(store);
    }

    @Override
    public void delete(Long sno) {

        Optional<Store> result = storeRepository.findById(sno);

        Store store = result.orElseThrow();

        store.delAccount();

        storeRepository.save(store);
    }
}
