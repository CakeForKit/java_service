package ru.bellintegrator.test_service.services;

import ru.bellintegrator.test_service.dto.UserDto;
import ru.bellintegrator.test_service.dto.UserSearchRequest;
import ru.bellintegrator.test_service.dto.AddUserRequest;
import ru.bellintegrator.test_service.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface UserService {
    UserDto getById(UUID id);
    Page<UserDto> getAll(UserSearchRequest searchRequest, Pageable pageable);
    UserDto create(AddUserRequest request);
    UserDto update(UUID id, User user) throws Exception;
    void deleteById(UUID id);
}
