package com.axa.simple_auth_axa_req.dto.role;

import com.axa.simple_auth_axa_req.dto.permission.PermissionDTO;
import com.axa.simple_auth_axa_req.dto.user.UserInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoleDTO {
    private Long id;

    private String name;

    private UserInfoDTO user;

    private Set<PermissionDTO> permissions;
}
