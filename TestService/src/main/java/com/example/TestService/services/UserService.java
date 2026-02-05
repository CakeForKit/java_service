package com.example.TestService.services;

import com.example.TestService.dto.UserSearchRequest;
import com.example.TestService.dto.AddUserRequest;
import com.example.TestService.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface UserService {
    Optional<UserModel> getById(UUID id);
    Page<UserModel> getAll(UserSearchRequest searchRequest, Pageable pageable);
    UserModel create(AddUserRequest request);
    UserModel update(UUID id,  UserModel user);
    void deleteById(UUID id);
}
