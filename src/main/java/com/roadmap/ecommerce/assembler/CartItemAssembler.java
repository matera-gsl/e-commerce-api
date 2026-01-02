package com.roadmap.ecommerce.assembler;

import com.roadmap.ecommerce.controller.ProductController;
import com.roadmap.ecommerce.dto.cart.CartItemResponseDTO;
import com.roadmap.ecommerce.mapper.ShoppingCartMapper;
import com.roadmap.ecommerce.model.ShoppingCartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@RequiredArgsConstructor
public class CartItemAssembler
        implements RepresentationModelAssembler<ShoppingCartItem, EntityModel<CartItemResponseDTO>> {

    private final ShoppingCartMapper cartMapper;

    @Override
    public EntityModel<CartItemResponseDTO> toModel(ShoppingCartItem entity) {
        CartItemResponseDTO dto = cartMapper.toItemResponse(entity);

        return EntityModel.of(dto,
                linkTo(methodOn(ProductController.class).findById(entity.getProduct().getId()))
                        .withRel("product-details"));
    }
}