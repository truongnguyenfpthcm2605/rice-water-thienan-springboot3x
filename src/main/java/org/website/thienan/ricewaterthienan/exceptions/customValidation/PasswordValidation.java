package org.website.thienan.ricewaterthienan.exceptions.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidation implements ConstraintValidator<PasswordRegex,String> {
    @Override
    public void initialize(PasswordRegex constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");
    }
}
