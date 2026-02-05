package ru.bellintegrator.TestService.services;

import ru.bellintegrator.TestService.dto.UserDto;
import ru.bellintegrator.TestService.dto.UserSearchRequest;
import ru.bellintegrator.TestService.dto.AddUserRequest;
import ru.bellintegrator.TestService.models.User;
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
