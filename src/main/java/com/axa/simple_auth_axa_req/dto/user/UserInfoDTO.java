package com.axa.simple_auth_axa_req.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDTO {
    private Long id;

    private String username;
}
