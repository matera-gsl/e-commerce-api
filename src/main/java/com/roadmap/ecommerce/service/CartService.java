package com.roadmap.ecommerce.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.roadmap.ecommerce.exception.EntityNotFoundException;
import com.roadmap.ecommerce.exception.NotEnoughItemsExcepction;
import com.roadmap.ecommerce.model.Product;
import com.roadmap.ecommerce.model.ShoppingCart;
import com.roadmap.ecommerce.model.ShoppingCartItem;
import com.roadmap.ecommerce.model.User;
import com.roadmap.ecommerce.repository.ShoppingCartRepository;
import com.roadmap.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ShoppingCartRepository cartRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    @Transactional
    public ShoppingCart addItem(UUID productId, Integer quantity) {
        User user = getAuthenticatedUser();

        ShoppingCart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productService.findById(productId);

        checkProductAvailableQuantity(product, quantity);

        Optional<ShoppingCartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(cart, product, quantity);
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    public ShoppingCart findUserCart() {
        User user = getAuthenticatedUser();
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("ShoppingCart", "User", user.getUsername()));
    }

    @Transactional
    public ShoppingCart updateItemQuantity(UUID productId, Integer quantity) {
        ShoppingCart cart = findUserCart();

        ShoppingCartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Product", "ID", productId));

        checkProductAvailableQuantity(item.getProduct(), quantity);

        item.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(UUID productId) {
        ShoppingCart cart = findUserCart();
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart() {
        ShoppingCart cart = findUserCart();
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }

    private void checkProductAvailableQuantity(Product product, Integer desiredQuantity) {
        if (!(product.getQuantity() >= desiredQuantity))
            throw new NotEnoughItemsExcepction(desiredQuantity);
    }
}
