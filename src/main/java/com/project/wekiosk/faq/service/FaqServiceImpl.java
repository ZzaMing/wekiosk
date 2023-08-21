package com.project.wekiosk.faq.service;


import com.project.wekiosk.faq.domain.Faq;
import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.faq.dto.PageRequestDTO;
import com.project.wekiosk.faq.dto.PageResponseDTO;
import com.project.wekiosk.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{

    private final FaqRepository faqRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<FaqDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("--------------------");
        log.info(pageRequestDTO);

        return faqRepository.search(pageRequestDTO);
    }

    @Override
    public FaqDTO getOne(Long qno) {

        Optional<Faq> result = faqRepository.findById(qno);

        Faq faq = result.orElseThrow();

        FaqDTO dto = modelMapper.map(faq, FaqDTO.class);

        return dto;
    }

    @Override
    public Long register(FaqDTO dto) {

        Faq faq = modelMapper.map(dto, Faq.class);

        Long newQno = faqRepository.save(faq).getQno();

        return newQno;
    }

    @Override
    public void modifier(FaqDTO dto) {

        Optional<Faq> result = faqRepository.findById(dto.getQno());

        Faq faq = result.orElseThrow();

        faq.changeQtitle(dto.getQtitle());
        faq.changeQcontent(dto.getQcontent());

        faqRepository.save(faq);

//        Faq faq = modelMapper.map(dto, Faq.class);
//
//        faqRepository.save(faq);
    }

    @Override
    public void delete(Long qno) {

        faqRepository.deleteById(qno);
    }
}
