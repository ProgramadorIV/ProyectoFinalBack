package com.salesianos.socialrides.validation.validator;

import com.salesianos.socialrides.service.UserService;
import com.salesianos.socialrides.validation.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserService userService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.hasText(value) || !userService.existsByUsername(value);
    }
}
