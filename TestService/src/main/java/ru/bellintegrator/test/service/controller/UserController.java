package ru.bellintegrator.test.service.controller;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import ru.bellintegrator.test.service.appender.MyAppender;
import ru.bellintegrator.test.service.dto.UserDto;
import ru.bellintegrator.test.service.dto.UserSearchDto;
import ru.bellintegrator.test.service.dto.CreateUserDto;
import ru.bellintegrator.test.service.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.test.service.service.UserService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path="/user")
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        log.debug("GET /user/{}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    ResponseEntity<Page<UserDto>> getAll(@ModelAttribute UserSearchDto searchRequest, Pageable pageable) {
        log.debug("GET /user {} {}", searchRequest, pageable);
        return ResponseEntity.ok(service.getAll(searchRequest, pageable));
    }

    @PostMapping
    ResponseEntity<UserDto> create(@RequestBody @Valid CreateUserDto request) {
        log.debug("POST /user {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping(path="/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable("userId") UUID id, @RequestBody User request) throws Exception {
        log.debug("PUT /user/{} {}", id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(id, request));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("userId") UUID id) {
        log.debug("DELETE /user/{}", id);
        service.deleteById(id);
    }

}
