package com.salesianos.socialrides.validation.annotation;

import com.salesianos.socialrides.validation.validator.FieldsMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsMatchValidator.class)
@Documented
public @interface FieldsMatch {

    String message() default "The fields values do not match";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field();
    String fieldMatch();

    /*@Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsMatch[] value();
    }*/
}
