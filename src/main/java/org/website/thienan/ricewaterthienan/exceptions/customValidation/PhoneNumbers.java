package org.website.thienan.ricewaterthienan.exceptions.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {PhoneValidation.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumbers {
    String message() default "Invalid Phone Numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}