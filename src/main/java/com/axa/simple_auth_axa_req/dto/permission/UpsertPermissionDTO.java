package com.axa.simple_auth_axa_req.dto.permission;

import com.axa.simple_auth_axa_req.enums.PermissionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpsertPermissionDTO {
    @NotNull
    PermissionType type;
}
