package com.axa.simple_auth_axa_req.dto.role;

import com.axa.simple_auth_axa_req.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleInfoDTO {
    private Long id;

    private String name;

    private User user;
}
