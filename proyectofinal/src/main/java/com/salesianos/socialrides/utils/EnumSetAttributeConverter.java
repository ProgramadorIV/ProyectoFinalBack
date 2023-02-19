package com.salesianos.socialrides.utils;

import com.salesianos.socialrides.model.user.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter
public class EnumSetAttributeConverter implements AttributeConverter<EnumSet<UserRole>, String> {

    private final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(EnumSet<UserRole> attribute) {

        if (attribute.isEmpty())
            return null;

        return attribute.stream().map(UserRole::name).collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public EnumSet<UserRole> convertToEntityAttribute(String dbData) {
        if(dbData != null && !dbData.isBlank())
            return Arrays.stream(dbData.split(SEPARATOR))
                    .map(UserRole::valueOf)
                    .collect(
                            Collectors.toCollection(() -> EnumSet.noneOf(UserRole.class))
                    );

        return EnumSet.noneOf(UserRole.class);
    }
}
