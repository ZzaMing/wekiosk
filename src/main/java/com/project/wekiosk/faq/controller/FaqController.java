package com.project.wekiosk.faq.controller;

import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.faq.service.FaqService;
import com.project.wekiosk.faq.dto.PageRequestDTO;
import com.project.wekiosk.faq.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/faq/")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class FaqController {

    private final FaqService faqService;

    @GetMapping("list")
    public PageResponseDTO<FaqDTO> list(PageRequestDTO requestDTO){

        log.info("------------------");
        log.info(requestDTO);

        return faqService.list(requestDTO);
    }

    @GetMapping("{qno}")
    public FaqDTO getOne(@PathVariable("qno") Long qno){

        log.info("qno...." + qno);

        return faqService.getOne(qno);
    }

    @PostMapping("")
    public Map<String, Long> regist(@RequestBody FaqDTO faqDTO){

        log.info(faqDTO);

        Long qno = faqService.register(faqDTO);

        return Map.of("result", qno);
    }

    @DeleteMapping("{qno}")
    public Map<String, Long> delete(@PathVariable("qno") Long qno){

        log.info("FAQ Delete...qno: " + qno);

        faqService.delete(qno);

        return Map.of("result", qno);
    }

    @PutMapping("{qno}")
    public Map<String, Long> modify(@PathVariable("qno")Long qno, @RequestBody FaqDTO faqDTO){

        log.info("FAQ Modify...qno: " + faqDTO.getQno());

        faqService.modifier(faqDTO);

        return Map.of("result", faqDTO.getQno());
    }
}
