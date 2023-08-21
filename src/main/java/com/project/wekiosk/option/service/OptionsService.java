package com.project.wekiosk.option.service;

import com.project.wekiosk.option.dto.OptionsDTO;

import java.util.List;

public interface OptionsService {
//
    List<OptionsDTO> getAllOptions();

    void addOptions(String oname, Long oprice, Long pno);

    List<OptionsDTO> getOptionListByPno(Long pno);


}
