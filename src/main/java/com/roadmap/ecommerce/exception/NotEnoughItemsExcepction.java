package com.roadmap.ecommerce.exception;

public class NotEnoughItemsExcepction extends RuntimeException {
    public NotEnoughItemsExcepction(Integer quantity) {
        super(String.format("Product quantity is bellow than %d", quantity));
    }
}
