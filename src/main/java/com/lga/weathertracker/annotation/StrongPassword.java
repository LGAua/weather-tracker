package com.lga.weathertracker.annotation;

import com.lga.weathertracker.constraint.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default "Password should contain letters and numbers";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
