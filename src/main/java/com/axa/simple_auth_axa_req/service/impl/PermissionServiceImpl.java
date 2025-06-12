package com.axa.simple_auth_axa_req.service.impl;

import com.axa.simple_auth_axa_req.dto.permission.PermissionDTO;
import com.axa.simple_auth_axa_req.dto.permission.UpsertPermissionDTO;
import com.axa.simple_auth_axa_req.repository.PermissionRepository;
import com.axa.simple_auth_axa_req.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionDTO> getAllPermissions() {
        return List.of();
    }

    @Override
    public PermissionDTO upsertPermission(Long permissionId, UpsertPermissionDTO dto) {
        return null;
    }

    @Override
    public void deletePermission(Long permissionId) {

    }
}
