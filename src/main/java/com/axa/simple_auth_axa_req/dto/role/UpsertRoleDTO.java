package com.axa.simple_auth_axa_req.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class UpsertRoleDTO {
    @NotBlank
    private String name;

    private Long userId;

    private List<Long> permissionIds;
}
