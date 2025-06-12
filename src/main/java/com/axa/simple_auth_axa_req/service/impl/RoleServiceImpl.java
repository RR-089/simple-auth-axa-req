package com.axa.simple_auth_axa_req.service.impl;


import com.axa.simple_auth_axa_req.dto.role.RoleDTO;
import com.axa.simple_auth_axa_req.dto.role.RoleInfoDTO;
import com.axa.simple_auth_axa_req.dto.role.UpsertRoleDTO;
import com.axa.simple_auth_axa_req.model.Role;
import com.axa.simple_auth_axa_req.repository.RoleRepository;
import com.axa.simple_auth_axa_req.service.PermissionService;
import com.axa.simple_auth_axa_req.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    public static RoleDTO mapToDTO(Role role) {
        return RoleDTO.builder()
                      .id(role.getId())
                      .name(role.getName())
                      .user(UserServiceImpl.mapToInfoDTO(role.getUser()))
                      .permissions(role.getPermissions()
                                       .stream().map(PermissionServiceImpl::mapToDto)
                                       .collect(Collectors.toSet()))
                      .build();
    }

    public static RoleInfoDTO mapToInfoDTO(Role role) {
        return RoleInfoDTO.builder()
                          .id(role.getId())
                          .name(role.getName())
                          .user(UserServiceImpl.mapToInfoDTO(role.getUser()))
                          .build();
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        log.info("req all roles");
        return roleRepository.findAll().stream().map(RoleServiceImpl::mapToDTO).toList();
    }

    @Override
    public RoleDTO upsertRole(Long roleId, UpsertRoleDTO dto) {
        return null;
    }

    @Override
    public void deleteRole(Long roleId) {

    }
}
