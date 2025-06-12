package com.axa.simple_auth_axa_req.service.impl;


import com.axa.simple_auth_axa_req.dto.role.RoleDTO;
import com.axa.simple_auth_axa_req.dto.role.UpsertRoleDTO;
import com.axa.simple_auth_axa_req.model.Role;
import com.axa.simple_auth_axa_req.repository.RoleRepository;
import com.axa.simple_auth_axa_req.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public static RoleDTO mapToRoleDTO(Role role) {
        return RoleDTO.builder()
                      .id(role.getId())
                      .name(role.getName())
                      .user(UserServiceImpl.mapToInfoDTO(role.getUser()))
                      //.permissions()
                      .build();
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return List.of();
    }

    @Override
    public RoleDTO upsertRole(Long roleId, UpsertRoleDTO dto) {
        return null;
    }

    @Override
    public void deleteRole(Long roleId) {

    }
}
