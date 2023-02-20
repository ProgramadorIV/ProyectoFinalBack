package com.salesianos.socialrides.validation.annotation;

import com.salesianos.socialrides.validation.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {

    String message() default "The email provided is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
