package com.axa.simple_auth_axa_req.enums;

import com.axa.simple_auth_axa_req.exception.BadRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionType {
    READ("Read"),
    READ_WRITE("Read-Write");

    private final String value;

    public static PermissionType fromValue(String value) {
        for (PermissionType status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }

        throw new BadRequestException("Unknown permission type", null);
    }
}
