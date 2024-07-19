package org.website.thienan.ricewaterthienan.exceptions.customValidation;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.website.thienan.ricewaterthienan.enums.StatusOrderEnum;

public class StatusSubSetValidator implements ConstraintValidator<StatusSubnet, StatusOrderEnum> {
    private StatusOrderEnum[] genders;

    @Override
    public void initialize(StatusSubnet constraint) {
        this.genders = constraint.anyOf();
    }

    @Override
    public boolean isValid(StatusOrderEnum value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(genders).contains(value);
    }
}
