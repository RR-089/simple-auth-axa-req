package com.axa.simple_auth_axa_req.service;

import com.axa.simple_auth_axa_req.dto.common.PaginationDTO;
import com.axa.simple_auth_axa_req.dto.user.UpsertUserDTO;
import com.axa.simple_auth_axa_req.dto.user.UserInfoDTO;
import com.axa.simple_auth_axa_req.exception.NotFoundException;
import com.axa.simple_auth_axa_req.model.User;
import com.axa.simple_auth_axa_req.repository.UserRepository;
import com.axa.simple_auth_axa_req.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        User user = User.builder().id(1L).username("testUser").build();
        Page<User> userPage = new PageImpl<>(List.of(user));

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        PaginationDTO<List<UserInfoDTO>> result = userService.getAllUsers(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalRecords());
        assertEquals(1, result.getData().size());
        assertEquals("testUser", result.getData().get(0).getUsername());
    }

    @Test
    void testUpsertUser_Create() {
        UpsertUserDTO dto = UpsertUserDTO.builder().username("newUser").password("password").build();
        User user = User.builder().username("newUser").build();
        User savedUser = User.builder().id(1L).username("newUser").build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        UserInfoDTO result = userService.upsertUser(0L, dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newUser", result.getUsername());
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpsertUser_Update() {
        UpsertUserDTO dto = UpsertUserDTO.builder().username("updatedUser").password("password").build();
        User existingUser = User.builder().id(1L).username("existingUser").build();
        User updatedUser = User.builder().id(1L).username("updatedUser").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserInfoDTO result = userService.upsertUser(1L, dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("updatedUser", result.getUsername());
        verify(userRepository).findById(1L);
        verify(userRepository).save(existingUser);
        verify(passwordEncoder, never()).encode(anyString()); // Password encoding is skipped in updates
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testFindUserEntity_Success() {
        User user = User.builder().id(1L).username("testUser").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findUserEntity(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testUser", result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void testFindUserEntity_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findUserEntity(1L));

        verify(userRepository).findById(1L);
    }

}
