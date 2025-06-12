package com.axa.simple_auth_axa_req.service;

import com.axa.simple_auth_axa_req.dto.permission.PermissionDTO;
import com.axa.simple_auth_axa_req.dto.permission.UpsertPermissionDTO;
import com.axa.simple_auth_axa_req.enums.PermissionType;
import com.axa.simple_auth_axa_req.exception.NotFoundException;
import com.axa.simple_auth_axa_req.model.Permission;
import com.axa.simple_auth_axa_req.model.Role;
import com.axa.simple_auth_axa_req.repository.PermissionRepository;
import com.axa.simple_auth_axa_req.service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTests {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Test
    void testGetAllPermissions() {
        Permission permission = Permission.builder()
                                          .id(1L)
                                          .type(PermissionType.READ)
                                          .role(Role.builder()
                                                    .id(1L)
                                                    .name("Admin")
                                                    .build())
                                          .build();

        when(permissionRepository.findAll()).thenReturn(List.of(permission));

        List<PermissionDTO> result = permissionService.getAllPermissions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Read", result.get(0).getType());
        assertEquals("Admin", result.get(0).getRole().getName());
    }

    @Test
    void testUpsertPermission_Create() {
        UpsertPermissionDTO dto = UpsertPermissionDTO.builder()
                                                     .type(PermissionType.READ)
                                                     .build();

        Permission savedPermission = Permission.builder()
                                               .id(1L)
                                               .type(PermissionType.READ)
                                               .build();

        when(permissionRepository.save(any(Permission.class))).thenReturn(savedPermission);

        PermissionDTO result = permissionService.upsertPermission(0L, dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Read", result.getType());
        verify(permissionRepository).save(any(Permission.class));
    }

    @Test
    void testUpsertPermission_Update() {
        UpsertPermissionDTO dto = UpsertPermissionDTO.builder()
                                                     .type(PermissionType.READ_WRITE)
                                                     .build();

        Permission existingPermission = Permission.builder()
                                                  .id(1L)
                                                  .type(PermissionType.READ)
                                                  .build();

        Permission updatedPermission = Permission.builder()
                                                 .id(1L)
                                                 .type(PermissionType.READ_WRITE)
                                                 .build();

        when(permissionRepository.findById(1L)).thenReturn(Optional.of(existingPermission));
        when(permissionRepository.save(existingPermission)).thenReturn(updatedPermission);

        PermissionDTO result = permissionService.upsertPermission(1L, dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Read_Write", result.getType());
        verify(permissionRepository).findById(1L);
        verify(permissionRepository).save(existingPermission);
    }

    @Test
    void testUpsertPermission_NotFound() {
        UpsertPermissionDTO dto = UpsertPermissionDTO.builder()
                                                     .type(PermissionType.READ_WRITE)
                                                     .build();

        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> permissionService.upsertPermission(1L, dto));

        verify(permissionRepository).findById(1L);
        verify(permissionRepository, never()).save(any(Permission.class));
    }

    @Test
    void testDeletePermission_Success() {
        when(permissionRepository.existsById(1L)).thenReturn(true);

        permissionService.deletePermission(1L);

        verify(permissionRepository).existsById(1L);
        verify(permissionRepository).deleteById(1L);
    }

    @Test
    void testDeletePermission_NotFound() {
        when(permissionRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> permissionService.deletePermission(1L));

        verify(permissionRepository).existsById(1L);
        verify(permissionRepository, never()).deleteById(anyLong());
    }

    @Test
    void testFindAllPermissionsById() {
        Permission permission1 = Permission.builder()
                                           .id(1L)
                                           .type(PermissionType.READ)
                                           .build();

        Permission permission2 = Permission.builder()
                                           .id(2L)
                                           .type(PermissionType.READ_WRITE)
                                           .build();

        when(permissionRepository.findByIdIn(List.of(1L, 2L))).thenReturn(List.of(permission1, permission2));

        List<Permission> result = permissionService.findAllPermissionsById(List.of(1L, 2L));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(1L)));
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(2L)));
    }
}
