package org.website.thienan.ricewaterthienan.exceptions.customValidation;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import org.website.thienan.ricewaterthienan.enums.StatusOrderEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = StatusSubSetValidator.class)
public @interface StatusSubnet {
    StatusOrderEnum[] anyOf();
    String message() default "must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}