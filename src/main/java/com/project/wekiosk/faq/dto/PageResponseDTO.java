package com.project.wekiosk.faq.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private long totalCount;

    private List<Integer> pageNums;

    private boolean prev, next;

    private PageRequestDTO requestDTO;

    private int page, size, start, end;

    public PageResponseDTO(List<E> dtoList, long totalCount, PageRequestDTO pageRequestDTO){
        this.dtoList = dtoList;
        this.totalCount = totalCount;
        this.requestDTO = pageRequestDTO;

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        // 13 -> 1.3 -> 2.0
        int tempEnd = (int) (Math.ceil(page/10.0) * 10);

        // 임시 끝번호 - 9 = 시작번호, 이전 버튼은 시작번호가 1이아니면 생성
        this.start = tempEnd - 9;
        this.prev = this.start != 1;

        // 178 / 10 -> 17.8
        int realEnd = (int)(Math.ceil(totalCount / (double)size));

        this.end = tempEnd > realEnd ? realEnd : tempEnd;

        this.next = (this.end * this.size) < totalCount;

        // 시작번호부터 끝번호까지의 숫자를 리스트로 변환
        this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();

    }

}