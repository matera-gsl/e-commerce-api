package com.roadmap.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.roadmap.ecommerce.dto.product.ProductRequestDTO;
import com.roadmap.ecommerce.dto.product.ProductResponseDTO;
import com.roadmap.ecommerce.model.Product;
import com.roadmap.ecommerce.util.MoneyParser;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "price", source = "price", qualifiedByName = "toCents")
    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "price", source = "price", qualifiedByName = "fromCents")
    ProductResponseDTO toResponse(Product entity);

    @Named("toCents")
    default Integer toCents(Object price) {
        return MoneyParser.toCents(price);
    }

    @Named("fromCents")
    default String fromCents(Integer price) {
        return MoneyParser.format(price);
    }
}