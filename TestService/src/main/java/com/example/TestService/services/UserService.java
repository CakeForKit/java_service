package com.example.TestService.services;

import com.example.TestService.dto.UserDto;
import com.example.TestService.dto.UserSearchRequest;
import com.example.TestService.dto.AddUserRequest;
import com.example.TestService.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface UserService {
    UserDto getById(UUID id);
    Page<UserDto> getAll(UserSearchRequest searchRequest, Pageable pageable);
    UserDto create(AddUserRequest request);
    UserDto update(UUID id, User user) throws Exception;
    void deleteById(UUID id);
}
