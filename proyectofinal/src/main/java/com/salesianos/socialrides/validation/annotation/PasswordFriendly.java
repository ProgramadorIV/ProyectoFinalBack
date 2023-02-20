package com.salesianos.socialrides.validation.annotation;

import com.salesianos.socialrides.validation.validator.PasswordFriendlyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordFriendlyValidator.class)
@Documented
public @interface PasswordFriendly {

    String message() default """
            The password is not strong enough. It must have:\s
            - 8 characters minimum
            - Capitalize characters
            - Alphabetical character
            - Numeric character
            - Special character""";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 8;
    int max() default Integer.MAX_VALUE;

    boolean hasUpper() default true;
    boolean hasLower() default true;

    boolean hasAlpha() default true;
    boolean hasNumber() default true;

    boolean hasSpecial() default true;

}
