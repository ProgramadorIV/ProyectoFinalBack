package com.salesianos.socialrides.validation.validator;

import com.salesianos.socialrides.validation.annotation.IsLocation;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsLocationValidator implements ConstraintValidator<IsLocation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(StringUtils.hasText(value)){
            String [] latLong = value.split(",");
            double lat = Double.parseDouble(latLong[0]);
            double lon = Double.parseDouble(latLong[1]);
            if(latLong.length == 2)
                return (lat <= 90 && lat >= -90) && (lon <= 180 && lon >= -180);
        }
        return false;
    }
}
