package com.roadmap.ecommerce.assembler;

import com.roadmap.ecommerce.controller.CartController;
import com.roadmap.ecommerce.dto.cart.CartResponseDTO;
import com.roadmap.ecommerce.mapper.ShoppingCartMapper;
import com.roadmap.ecommerce.model.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@RequiredArgsConstructor
public class CartAssembler implements RepresentationModelAssembler<ShoppingCart, EntityModel<CartResponseDTO>> {

        private final ShoppingCartMapper cartMapper;
        private final CartItemAssembler cartItemAssembler;

        @Override
        public EntityModel<CartResponseDTO> toModel(ShoppingCart entity) {
                CartResponseDTO dto = cartMapper.toResponse(entity);

                var itemsWithLinks = entity.getItems().stream()
                                .map(cartItemAssembler::toModel)
                                .toList();

                CartResponseDTO finalDto = new CartResponseDTO(
                                dto.id(),
                                itemsWithLinks,
                                dto.total(),
                                entity.getUpdatedAt());

                return EntityModel.of(finalDto,
                                linkTo(methodOn(CartController.class).findUserCart()).withSelfRel(),
                                linkTo(methodOn(CartController.class).clearCart()).withRel("clear"));
        }
}