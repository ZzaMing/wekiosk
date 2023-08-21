package com.project.wekiosk.faq.repository.search;

import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.faq.dto.PageRequestDTO;
import com.project.wekiosk.faq.dto.PageResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface FaqSearch {

    PageResponseDTO<FaqDTO> search(PageRequestDTO requestDTO);

    default Pageable makePageable(PageRequestDTO requestDTO){

        Pageable pageable =  PageRequest.of(
                requestDTO.getPage() -1,
                requestDTO.getSize(),
                Sort.by("qno").descending());

        return pageable;
    }
}
