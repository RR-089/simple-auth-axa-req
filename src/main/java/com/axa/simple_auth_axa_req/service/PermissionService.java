package com.axa.simple_auth_axa_req.service;

import com.axa.simple_auth_axa_req.dto.permission.PermissionDTO;
import com.axa.simple_auth_axa_req.dto.permission.UpsertPermissionDTO;

import java.util.List;

public interface PermissionService {
    List<PermissionDTO> getAllPermissions();

    PermissionDTO upsertPermission(Long permissionId, UpsertPermissionDTO dto);

    void deletePermission(Long permissionId);
}
