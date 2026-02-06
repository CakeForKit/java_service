package ru.bellintegrator.test_service.services;

import ru.bellintegrator.test_service.models.User;

import java.util.UUID;

public interface ComsumerUserService {
    void save(User user);
    void deleteById(UUID id);
}
