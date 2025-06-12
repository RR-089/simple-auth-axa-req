package com.axa.simple_auth_axa_req.service;

import com.axa.simple_auth_axa_req.dto.common.PaginationDTO;
import com.axa.simple_auth_axa_req.dto.user.UpsertUserDTO;
import com.axa.simple_auth_axa_req.dto.user.UserInfoDTO;
import com.axa.simple_auth_axa_req.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    PaginationDTO<List<UserInfoDTO>> getAllUsers(Pageable pageable);

    UserInfoDTO upsertUser(Long userId, UpsertUserDTO dto);

    void deleteUser(Long userId);

    User findUserEntity(Long userId);
}

