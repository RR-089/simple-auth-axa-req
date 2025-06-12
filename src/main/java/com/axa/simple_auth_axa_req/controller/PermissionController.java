package com.axa.simple_auth_axa_req.controller;


import com.axa.simple_auth_axa_req.dto.common.ResponseDTO;
import com.axa.simple_auth_axa_req.dto.permission.UpsertPermissionDTO;
import com.axa.simple_auth_axa_req.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<?> getAllPermissions() {
        var data = permissionService.getAllPermissions();
        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Get all permissions successful")
                           .data(data)
                           .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createPermission(
            @Valid @RequestBody UpsertPermissionDTO dto
    ) {
        var data = permissionService.upsertPermission(0L, dto);

        HttpStatus status = HttpStatus.CREATED;

        return ResponseEntity.status(status).body(
                ResponseDTO.builder()
                           .status(status.value())
                           .message("Create permission successful")
                           .data(data)
                           .build()
        );
    }


    @PutMapping(value = "{id}")
    public ResponseEntity<?> updatePermission(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpsertPermissionDTO dto
    ) {
        var data = permissionService.upsertPermission(id, dto);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Update permission successful")
                           .data(data)
                           .build()
        );
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> updatePermission(
            @PathVariable("id") Long id
    ) {
        permissionService.deletePermission(id);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Delete permission successful")
                           .data(null)
                           .build()
        );
    }

}
