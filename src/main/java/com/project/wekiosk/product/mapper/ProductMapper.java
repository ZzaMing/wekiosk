package com.project.wekiosk.product.mapper;

import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "name", target = "pname")
    @Mapping(source = "price", target = "pprice")
    @Mapping(source = "category.cateno", target = "cateno")
    ProductDTO toDTO(Product product);
}
