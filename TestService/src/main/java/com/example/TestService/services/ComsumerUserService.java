package ru.bellintegrator.TestService.services;

import ru.bellintegrator.TestService.models.User;

import java.util.UUID;

public interface ComsumerUserService {
    void save(User user);
    void deleteById(UUID id);
}
