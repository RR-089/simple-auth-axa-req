package com.axa.simple_auth_axa_req.controller;


import com.axa.simple_auth_axa_req.dto.common.ResponseDTO;
import com.axa.simple_auth_axa_req.dto.role.UpsertRoleDTO;
import com.axa.simple_auth_axa_req.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        var data = roleService.getAllRoles();
        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Get all roles successful")
                           .data(data)
                           .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createRole(
            @Valid @RequestBody UpsertRoleDTO dto
    ) {
        var data = roleService.upsertRole(0L, dto);

        HttpStatus status = HttpStatus.CREATED;

        return ResponseEntity.status(status).body(
                ResponseDTO.builder()
                           .status(status.value())
                           .message("Create role successful")
                           .data(data)
                           .build()
        );
    }


    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateRole(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpsertRoleDTO dto
    ) {
        var data = roleService.upsertRole(id, dto);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Update role successful")
                           .data(data)
                           .build()
        );
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> updateRole(
            @PathVariable("id") Long id
    ) {
        roleService.deleteRole(id);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Delete role successful")
                           .data(null)
                           .build()
        );
    }

}
