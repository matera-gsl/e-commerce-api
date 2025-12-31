package com.roadmap.ecommerce.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, String prop, Object id) {
        super(String.format("%s not found with $s: %s", entity, prop, id));
    }
}