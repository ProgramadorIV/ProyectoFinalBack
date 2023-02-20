package com.salesianos.socialrides.validation.validator;

import com.salesianos.socialrides.validation.annotation.FieldsMatch;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {

        Object fieldValue = PropertyAccessorFactory
                .forBeanPropertyAccess(o).getPropertyValue(field);
        Object fieldMatchValue = PropertyAccessorFactory
                .forBeanPropertyAccess(o).getPropertyValue(fieldMatch);

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}
