package com.axa.simple_auth_axa_req.service.impl;


import com.axa.simple_auth_axa_req.dto.role.RoleDTO;
import com.axa.simple_auth_axa_req.dto.role.RoleInfoDTO;
import com.axa.simple_auth_axa_req.dto.role.UpsertRoleDTO;
import com.axa.simple_auth_axa_req.exception.NotFoundException;
import com.axa.simple_auth_axa_req.model.Permission;
import com.axa.simple_auth_axa_req.model.Role;
import com.axa.simple_auth_axa_req.model.User;
import com.axa.simple_auth_axa_req.repository.RoleRepository;
import com.axa.simple_auth_axa_req.service.PermissionService;
import com.axa.simple_auth_axa_req.service.RoleService;
import com.axa.simple_auth_axa_req.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;
    private final UserService userService;

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
        log.info("req upsert role, roleId: {}", roleId);

        Role foundRole = (roleId > 0) ? roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException("Role not found", null)) : Role.builder()
                                                                           .name(dto.getName())
                                                                           .build();

        User foundUser = userService.findUserEntity(dto.getUserId());

        Set<Permission> permissions =
                new HashSet<>(permissionService.findAllPermissionsById(dto.getPermissionIds()));

        if (roleId > 0) {
            foundRole.setName(dto.getName());
        }

        foundRole.setUser(foundUser);
        foundRole.setPermissions(permissions);

        return mapToDTO(roleRepository.save(foundRole));
    }

    @Override
    public void deleteRole(Long roleId) {
        log.info("req delete roles: {}", roleId);

        if (!roleRepository.existsById(roleId)) {
            throw new NotFoundException("role not found", null);
        }

        roleRepository.deleteById(roleId);
    }
}
