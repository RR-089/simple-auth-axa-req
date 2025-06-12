package com.axa.simple_auth_axa_req.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpsertUserDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
