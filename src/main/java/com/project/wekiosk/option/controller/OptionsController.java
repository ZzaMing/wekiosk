package com.project.wekiosk.option.controller;

import com.project.wekiosk.option.dto.OptionsDTO;
import com.project.wekiosk.option.service.OptionsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options/")
@RequiredArgsConstructor
@CrossOrigin
@Log4j2
public class OptionsController {
    private final OptionsService optionsService;

    //
    @GetMapping("/all")
    public List<OptionsDTO> getAllOptions() {
        return optionsService.getAllOptions();
    }

    @PostMapping("/add")
    public void addOptions(@RequestBody OptionsDTO optionsDTO) {

        optionsService.addOptions(optionsDTO.getOname(), optionsDTO.getOprice(), optionsDTO.getPno());
        log.info(optionsDTO);
    }

    @GetMapping("{pno}")
    public List<OptionsDTO> getList(@PathVariable("pno")Long pno){

        return optionsService.getOptionListByPno(pno);
    }

//    @GetMapping("/{pno}/{ord}")
//    public ResponseEntity<OptionsDTO> getOptions(@PathVariable Long pno, @PathVariable Long ord) {
//        OptionsDTO optionsDTO = optionsService.getOptionsByPnoAndOrd(pno, ord);
//        return ResponseEntity.ok(optionsDTO);
//    }
//
//    @PutMapping("/{ord}")
//    public ResponseEntity<Void> updateOptions(@PathVariable Long ord, @RequestBody OptionsDTO optionsDTO) {
//        optionsDTO.setOrd(ord);
//        optionsService.updateOptions(optionsDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{ord}")
//    public ResponseEntity<Void> deleteOptions(@PathVariable Long ord) {
//        optionsService.deleteOptions(ord);
//        return ResponseEntity.noContent().build();
//    }
}
