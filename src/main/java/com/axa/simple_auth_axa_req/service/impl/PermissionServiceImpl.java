package com.axa.simple_auth_axa_req.service.impl;

import com.axa.simple_auth_axa_req.dto.permission.PermissionDTO;
import com.axa.simple_auth_axa_req.dto.permission.UpsertPermissionDTO;
import com.axa.simple_auth_axa_req.exception.NotFoundException;
import com.axa.simple_auth_axa_req.model.Permission;
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

    public static PermissionDTO mapToDto(Permission permission) {
        return PermissionDTO.builder()
                            .id(permission.getId())
                            .type(permission.getType().getValue())
                            .role(RoleServiceImpl.mapToInfoDTO(permission.getRole()))
                            .build();
    }

    @Override
    public List<PermissionDTO> getAllPermissions() {
        log.info("req all permissions");

        return permissionRepository.findAll().stream().map(PermissionServiceImpl::mapToDto).toList();
    }

    @Override
    public PermissionDTO upsertPermission(Long permissionId, UpsertPermissionDTO dto) {
        log.info("req upsert permission");
        Permission foundPermission = permissionRepository.findById(permissionId)
                                                         .orElseGet(() -> Permission.builder()
                                                                                    .type(dto.getType())
                                                                                    .build());

        if (permissionId == 0) {
            foundPermission.setType(dto.getType());
        }

        return mapToDto(permissionRepository.save(foundPermission));
    }

    @Override
    public void deletePermission(Long permissionId) {
        log.info("req delete permission: {}", permissionId);

        if (!permissionRepository.existsById(permissionId)) {
            throw new NotFoundException("Permission not found", null);
        }

        permissionRepository.deleteById(permissionId);
    }
}
