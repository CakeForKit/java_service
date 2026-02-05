package com.example.TestService.services;

import com.example.TestService.dto.AddUserRequest;
import com.example.TestService.models.UserModel;

import java.util.UUID;

public interface ComsumerUserService {
    void save(UserModel user);
    void deleteById(UUID id);
}
