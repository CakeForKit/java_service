package com.example.TestService.services;

import com.example.TestService.models.User;

import java.util.UUID;

public interface ComsumerUserService {
    void save(User user);
    void deleteById(UUID id);
}
