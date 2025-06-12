package com.axa.simple_auth_axa_req.enums.converter;

import com.axa.simple_auth_axa_req.enums.PermissionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PermissionTypeConverter implements AttributeConverter<PermissionType,
        String> {
    @Override
    public String convertToDatabaseColumn(PermissionType permissionType) {
        return permissionType != null ? permissionType.getValue() : null;

    }

    @Override
    public PermissionType convertToEntityAttribute(String s) {
        return s != null ? PermissionType.fromValue(s) : null;
    }
}
