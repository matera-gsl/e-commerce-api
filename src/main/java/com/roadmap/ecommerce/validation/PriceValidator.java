package com.roadmap.ecommerce.validation;

import com.roadmap.ecommerce.util.MoneyParser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<ValidPrice, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            Integer cents = MoneyParser.toCents(value);
            return cents != null && cents > 0;
        } catch (Exception e) {
            return false;
        }
    }
}