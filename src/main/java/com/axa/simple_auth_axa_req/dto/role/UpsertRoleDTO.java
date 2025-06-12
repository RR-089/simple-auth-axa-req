package com.axa.simple_auth_axa_req.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class UpsertRoleDTO {
    @NotBlank
    private String name;

    @NotNull
    private List<Long> permissionIds;
}
