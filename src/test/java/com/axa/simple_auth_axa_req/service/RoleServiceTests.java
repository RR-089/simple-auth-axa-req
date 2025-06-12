package com.axa.simple_auth_axa_req.service;

import com.axa.simple_auth_axa_req.dto.role.RoleDTO;
import com.axa.simple_auth_axa_req.dto.role.UpsertRoleDTO;
import com.axa.simple_auth_axa_req.enums.PermissionType;
import com.axa.simple_auth_axa_req.exception.NotFoundException;
import com.axa.simple_auth_axa_req.model.Permission;
import com.axa.simple_auth_axa_req.model.Role;
import com.axa.simple_auth_axa_req.model.User;
import com.axa.simple_auth_axa_req.repository.RoleRepository;
import com.axa.simple_auth_axa_req.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionService permissionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testGetAllRoles() {
        Role role = Role.builder()
                        .id(1L)
                        .name("Admin")
                        .user(User.builder().id(1L).username("testUser").build())
                        .permissions(Set.of(Permission.builder().id(1L).type(PermissionType.READ).role(
                                Role.builder()
                                    .id(1L)
                                    .name("Admin")
                                    .user(User.builder().id(1L).username("testUser").build())
                                    .build()
                        ).build()))
                        .build();

        when(roleRepository.findAll()).thenReturn(List.of(role));

        List<RoleDTO> result = roleService.getAllRoles();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getName());
        assertEquals(1L, result.get(0).getId());
        assertEquals("testUser", result.get(0).getUser().getUsername());
        assertEquals(1, result.get(0).getPermissions().size());
        assertTrue(result.get(0).getPermissions().stream().anyMatch(p -> p.getType().equals("Read")));
    }

    @Test
    void testUpsertRole_Create() {
        UpsertRoleDTO dto = UpsertRoleDTO.builder()
                                         .name("Manager")
                                         .userId(1L)
                                         .permissionIds(List.of(1L))
                                         .build();

        User user = User.builder().id(1L).username("testUser").build();
        Permission permission =
                Permission.builder().id(1L).type(PermissionType.READ_WRITE).role(
                        Role.builder()
                            .id(2L)
                            .name("Manager")
                            .user(user)
                            .build()
                ).build();
        Role savedRole = Role.builder()
                             .id(2L)
                             .name("Manager")
                             .user(user)
                             .permissions(Set.of(permission))
                             .build();

        when(userService.findUserEntity(1L)).thenReturn(user);
        when(permissionService.findAllPermissionsById(List.of(1L))).thenReturn(List.of(permission));
        when(roleRepository.save(any(Role.class))).thenReturn(savedRole);

        RoleDTO result = roleService.upsertRole(0L, dto);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Manager", result.getName());
        assertEquals("testUser", result.getUser().getUsername());
        assertEquals(1, result.getPermissions().size());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void testUpsertRole_Update() {
        UpsertRoleDTO dto = UpsertRoleDTO.builder()
                                         .name("Updated Manager")
                                         .userId(1L)
                                         .permissionIds(List.of(1L, 2L))
                                         .build();

        User user = User.builder().id(1L).username("testUser").build();
        Permission permission1 =
                Permission.builder().id(1L).type(PermissionType.READ)
                          .role(Role.builder()
                                    .id(2L)
                                    .name("Manager")
                                    .user(user)
                                    .build())
                          .build();
        Permission permission2 =
                Permission.builder().id(2L).type(PermissionType.READ_WRITE)
                          .role(Role.builder()
                                    .id(2L)
                                    .name("Manager")
                                    .user(user)
                                    .build())
                          .build();
        Role existingRole = Role.builder()
                                .id(2L)
                                .name("Manager")
                                .user(user)
                                .permissions(Set.of(permission1))
                                .build();
        Role updatedRole = Role.builder()
                               .id(2L)
                               .name("Updated Manager")
                               .user(user)
                               .permissions(Set.of(permission1, permission2))
                               .build();

        when(roleRepository.findById(2L)).thenReturn(Optional.of(existingRole));
        when(userService.findUserEntity(1L)).thenReturn(user);
        when(permissionService.findAllPermissionsById(List.of(1L, 2L)))
                .thenReturn(List.of(permission1, permission2));
        when(roleRepository.save(existingRole)).thenReturn(updatedRole);

        RoleDTO result = roleService.upsertRole(2L, dto);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Updated Manager", result.getName());
        assertEquals(2, result.getPermissions().size());
        verify(roleRepository).findById(2L);
        verify(roleRepository).save(existingRole);
    }

    @Test
    void testDeleteRole_Success() {
        when(roleRepository.existsById(1L)).thenReturn(true);

        roleService.deleteRole(1L);

        verify(roleRepository).existsById(1L);
        verify(roleRepository).deleteById(1L);
    }

    @Test
    void testDeleteRole_NotFound() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> roleService.deleteRole(1L));

        verify(roleRepository).existsById(1L);
        verify(roleRepository, never()).deleteById(anyLong());
    }
}
