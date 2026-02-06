package ru.bellintegrator.test.service.services;

import ru.bellintegrator.test.service.models.User;

import java.util.UUID;

public interface ComsumerUserService {
    void save(User user);
    void deleteById(UUID id);
}
