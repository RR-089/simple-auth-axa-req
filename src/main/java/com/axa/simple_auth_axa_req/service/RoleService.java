package com.axa.simple_auth_axa_req.service;

import com.axa.simple_auth_axa_req.dto.role.RoleDTO;
import com.axa.simple_auth_axa_req.dto.role.UpsertRoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();

    RoleDTO upsertRole(Long roleId, UpsertRoleDTO dto);

    void deleteRole(Long roleId);
}
