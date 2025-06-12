package com.axa.simple_auth_axa_req.controller;

import com.axa.simple_auth_axa_req.dto.common.ResponseDTO;
import com.axa.simple_auth_axa_req.dto.user.UpsertUserDTO;
import com.axa.simple_auth_axa_req.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(Pageable pageable) {
        var data = userService.getAllUsers(pageable);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Get all users successful")
                           .data(data)
                           .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UpsertUserDTO dto
    ) {
        var data = userService.upsertUser(0L, dto);

        HttpStatus status = HttpStatus.CREATED;

        return ResponseEntity.status(status).body(
                ResponseDTO.builder()
                           .status(status.value())
                           .message("Create user successful")
                           .data(data)
                           .build()
        );
    }


    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateRole(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpsertUserDTO dto
    ) {
        var data = userService.upsertUser(id, dto);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Update user successful")
                           .data(data)
                           .build()
        );
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") Long id
    ) {
        userService.deleteUser(id);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                           .status(HttpStatus.OK.value())
                           .message("Delete user successful")
                           .data(null)
                           .build()
        );
    }

}
