package com.roadmap.ecommerce.mapper;

import com.roadmap.ecommerce.dto.cart.CartItemResponseDTO;
import com.roadmap.ecommerce.dto.cart.CartResponseDTO;
import com.roadmap.ecommerce.model.ShoppingCart;
import com.roadmap.ecommerce.model.ShoppingCartItem;
import com.roadmap.ecommerce.util.MoneyParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    @Mapping(target = "total", source = "total", qualifiedByName = "fromCents")
    CartResponseDTO toResponse(ShoppingCart entity);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "unitPrice", source = "product.price", qualifiedByName = "fromCents")
    @Mapping(target = "subtotal", source = "entity", qualifiedByName = "calculateSubtotal")
    CartItemResponseDTO toItemResponse(ShoppingCartItem entity);

    @Named("fromCents")
    default String fromCents(Integer price) {
        return MoneyParser.format(price);
    }

    @Named("calculateSubtotal")
    default String calculateSubtotal(ShoppingCartItem item) {
        Integer subtotalCents = item.getProduct().getPrice() * item.getQuantity();
        return MoneyParser.format(subtotalCents);
    }
}