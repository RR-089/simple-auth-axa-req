package com.axa.simple_auth_axa_req.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpsertUserDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
