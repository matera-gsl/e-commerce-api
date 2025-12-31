package com.roadmap.ecommerce.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, Object id) {
        super(String.format("%s not found with ID: %s", entity, id));
    }
}