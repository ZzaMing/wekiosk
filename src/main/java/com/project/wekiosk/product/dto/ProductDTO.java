package com.project.wekiosk.product.dto;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.dto.OptionsDTO;
import com.project.wekiosk.product.domain.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ProductDTO {


    private String pname; // 상품 이름
    private Long pprice; // 상품 가격
    private Long cateno;
    private List<OptionsDTO> options; // 옵션 리스트
    private List<MultipartFile> images;
    private List<String> gimages;
    private Long pno;// 상품 이미지 파일 리스트

    private int quantity;


    // productDTO를 product 엔티티로 변환하는 메서드
    public Product toEntity(Category category) {
        Product product = new Product();
        product.setPname(this.pname);
        product.setPprice(this.pprice);


        product.setCategory(category);

        // 옵션 리스트 변환
        List<Options> options = convertToOptionEntities(this.options);
        product.setOptions(options);

        return product;
    }

    // OptionDTO 리스트를 Options 엔티티 리스트로 변환하는 메서드
    private List<Options> convertToOptionEntities(List<OptionsDTO> optionDTOList) {
        List<Options> options = new ArrayList<>();
        for (OptionsDTO optionDTO : optionDTOList) {
            Options option = new Options();
            option.setOname(optionDTO.getOname());
            option.setOprice(optionDTO.getOprice());
            options.add(option);
        }
        return options;
    }


    public void setCateno(Long cateno) {
        this.cateno = cateno;
    }
    public Long getCateno() {
        return this.cateno;
    }

    public Long getPno() {
        return this.pno;
    }
    public void setPno(Long pno) {
        this.pno = pno;
    }

}
