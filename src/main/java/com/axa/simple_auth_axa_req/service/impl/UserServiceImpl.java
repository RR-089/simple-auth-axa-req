package com.axa.simple_auth_axa_req.service.impl;

import com.axa.simple_auth_axa_req.dto.common.PaginationDTO;
import com.axa.simple_auth_axa_req.dto.user.UpsertUserDTO;
import com.axa.simple_auth_axa_req.dto.user.UserInfoDTO;
import com.axa.simple_auth_axa_req.exception.NotFoundException;
import com.axa.simple_auth_axa_req.model.User;
import com.axa.simple_auth_axa_req.repository.UserRepository;
import com.axa.simple_auth_axa_req.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public static UserInfoDTO mapToInfoDTO(User user) {
        return UserInfoDTO.builder()
                          .id(user.getId())
                          .username(user.getUsername())
                          .build();
    }

    @Override
    public PaginationDTO<List<UserInfoDTO>> getAllUsers(Pageable pageable) {
        log.info("req all users");

        Page<User> userPage = userRepository.findAll(pageable);

        List<UserInfoDTO> infoDTOList =
                userPage.getContent().stream().map(UserServiceImpl::mapToInfoDTO).toList();


        return PaginationDTO.<List<UserInfoDTO>>builder()
                            .totalPages(userPage.getTotalPages())
                            .totalRecords(userPage.getTotalElements())
                            .data(infoDTOList)
                            .build();
    }

    @Override
    public UserInfoDTO upsertUser(Long userId, UpsertUserDTO dto) {
        log.info("req upsert user, userId: {}", userId);

        User foundUser = (userId > 0) ?
                userRepository.findById(userId).orElseThrow(
                        () -> new NotFoundException("User not found", null)
                ) : User.builder()
                        .username(dto.getUsername())
                        .build();

        if (userId == 0) {
            foundUser.setUsername(dto.getUsername());
            foundUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return mapToInfoDTO(userRepository.save(foundUser));
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("req delete user: {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found", null);
        }

        userRepository.deleteById(userId);
    }
}
