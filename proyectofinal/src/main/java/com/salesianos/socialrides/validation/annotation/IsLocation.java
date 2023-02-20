package com.salesianos.socialrides.validation.annotation;

import com.salesianos.socialrides.validation.validator.IsLocationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint( validatedBy = IsLocationValidator.class)
@Documented
public @interface IsLocation {

    String message() default "The value provided is not a valid location";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
