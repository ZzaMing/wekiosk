package com.project.wekiosk.product.service;

import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.dto.ProductListDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findProductInCategory(Long cateno, Long pno);

    PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO);

    Long register(ProductDTO productDTO);


    void remove(Long pno);

    void modifyProduct(Long pno, ProductDTO productDTO);

    List<ProductDTO> getProductsByCategory(Long cateno);

    ProductDTO readOneInCategory(Long cateno, Long pno);

    Long setProduct(ProductDTO productDTO);

    void toggleShowProduct(Long cateno, List<Long> pnoList);

    List<ProductDTO> showProduct(Long cateno);

}
