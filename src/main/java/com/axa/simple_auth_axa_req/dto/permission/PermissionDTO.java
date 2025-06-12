package com.axa.simple_auth_axa_req.dto.permission;

import com.axa.simple_auth_axa_req.dto.role.RoleInfoDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDTO {
    private Long id;

    private String type;

    private RoleInfoDTO role;
}
