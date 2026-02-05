package ru.bellintegrator.TestService.controllers;

import ru.bellintegrator.TestService.dto.UserDto;
import ru.bellintegrator.TestService.dto.UserSearchRequest;
import ru.bellintegrator.TestService.dto.AddUserRequest;
import ru.bellintegrator.TestService.mappers.UserMapper;
import ru.bellintegrator.TestService.models.User;
import ru.bellintegrator.TestService.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final Environment env;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    ResponseEntity<Page<UserDto>> getAll(@ModelAttribute UserSearchRequest searchRequest, Pageable pageable) {
        String[] activeProfiles = env.getActiveProfiles();
        System.out.println("Active profiles: " + Arrays.toString(activeProfiles));
        return ResponseEntity.ok(userService.getAll(searchRequest, pageable));
    }

    @PostMapping
    ResponseEntity<UserDto> create(@RequestBody @Valid AddUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @PutMapping(path="/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable("userId") UUID id, @RequestBody User request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.update(id, request));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("userId") UUID id) {
        userService.deleteById(id);
    }

}
