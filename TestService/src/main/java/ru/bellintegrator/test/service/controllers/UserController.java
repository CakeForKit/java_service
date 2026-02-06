package ru.bellintegrator.test.service.controllers;

import ru.bellintegrator.test.service.dto.UserDto;
import ru.bellintegrator.test.service.dto.UserSearchRequest;
import ru.bellintegrator.test.service.dto.AddUserRequest;
import ru.bellintegrator.test.service.models.User;
import ru.bellintegrator.test.service.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
