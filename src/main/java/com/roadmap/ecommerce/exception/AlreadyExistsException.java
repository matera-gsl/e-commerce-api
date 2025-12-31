package com.roadmap.ecommerce.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String entity, String field, String value) {
        super(String.format("%s with %s '%s' already exists on the system.", entity, field, value));
    }
}