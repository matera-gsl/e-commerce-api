package com.roadmap.ecommerce.assembler;

import com.roadmap.ecommerce.controller.ProductController;
import com.roadmap.ecommerce.dto.product.ProductResponseDTO;
import com.roadmap.ecommerce.mapper.ProductMapper;
import com.roadmap.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Pageable;

@Component
@RequiredArgsConstructor
public class ProductAssembler implements RepresentationModelAssembler<Product, EntityModel<ProductResponseDTO>> {

    private final ProductMapper productMapper;

    @Override
    public EntityModel<ProductResponseDTO> toModel(Product product) {
        ProductResponseDTO dto = productMapper.toResponse(product);

        return EntityModel.of(dto,
                linkTo(methodOn(ProductController.class).findById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).findAll(Pageable.unpaged(), null)).withRel("all-products"));
    }
}