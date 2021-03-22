package com.osekj.vis.converter;

import com.osekj.vis.model.enums.PermissionType;

import javax.persistence.AttributeConverter;
import java.util.Optional;

public class PermissionTypeToStringConverter implements AttributeConverter<PermissionType, String> {

    @Override
    public String convertToDatabaseColumn(PermissionType attribute) {
        return Optional.ofNullable(attribute).map(PermissionType::toString).orElse(null);
    }

    @Override
    public PermissionType convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData).map(PermissionType::fromString).orElse(null);
    }
}
