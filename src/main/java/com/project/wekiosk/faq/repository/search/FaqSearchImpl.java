package com.project.wekiosk.faq.repository.search;


import com.project.wekiosk.faq.domain.QFaq;
import com.project.wekiosk.faq.domain.Faq;
import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.faq.dto.PageRequestDTO;
import com.project.wekiosk.faq.dto.PageResponseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class FaqSearchImpl extends QuerydslRepositorySupport implements FaqSearch{

    public FaqSearchImpl() {
        super(Faq.class);
    }

    @Override
    public PageResponseDTO<FaqDTO> search(PageRequestDTO requestDTO) {

        Pageable pageable = makePageable(requestDTO);

        QFaq faq = QFaq.faq;

        JPQLQuery<Faq> query = from(faq);

        String keyword = requestDTO.getKeyword();
        String searchType = requestDTO.getType();

        if(keyword != null && searchType != null){

            // tc -> [t, c]
            String[] searchArr = searchType.split("");

            // 우선순위 연산자 ( ... ) ...
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {
                switch(type) {
                    // or연산
                    case "t" -> searchBuilder.or(faq.qtitle.contains(keyword));
                    case "c" -> searchBuilder.or(faq.qcontent.contains(keyword));
                }
            } // end for

            query.where(searchBuilder);
        }

        this.getQuerydsl().applyPagination(pageable, query);
        query.groupBy(faq);

        // 오늘 중요 포인트
        JPQLQuery<FaqDTO> listQuery = query.select(
                Projections.bean(FaqDTO.class,
                        faq.qno,
                        faq.qtitle,
                        faq.qcontent
                )
        );


        List<FaqDTO> list = listQuery.fetch();

        log.info("------------------------");
        log.info(list);

        long totalCount = listQuery.fetchCount();

        return new PageResponseDTO<>(list, totalCount, requestDTO);
    }
}
