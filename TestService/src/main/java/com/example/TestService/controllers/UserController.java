package com.example.TestService.controllers;

import com.example.TestService.dto.UserDto;
import com.example.TestService.dto.UserSearchRequest;
import com.example.TestService.dto.AddUserRequest;
import com.example.TestService.mappers.UserMapper;
import com.example.TestService.models.UserModel;
import com.example.TestService.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j  // add logger
@RestController
@RequestMapping(path="/user")  //  UserController будет обрабатывать запросы, пути в которых начинаются с /user
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        Optional<UserModel> user = userService.getById(id);
        return user.map(
                userModel -> new ResponseEntity<>(
                        userMapper.userModelToUserDto(userModel),
                        HttpStatus.OK)
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<Page<UserDto>> getAll(
            @ModelAttribute UserSearchRequest searchRequest,
//            @SpringQueryMap UserSearchRequest dto, ЭТО ДЛЯ http клиента аннотация
            Pageable pageable
    ) {
        Page<UserModel> pageModel = userService.getAll(searchRequest, pageable);
        Page<UserDto> pageDto = pageModel.map(userMapper::userModelToUserDto);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserDto> create(@RequestBody @Valid AddUserRequest request) {
        try {
            UserModel user = userService.create(request);
            return new ResponseEntity<>(userMapper.userModelToUserDto(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(path="/{userId}", consumes = "application/json")
    public ResponseEntity<UserDto> update(
            @PathVariable("userId") UUID id,
            @RequestBody UserModel request
    ) {
        try {
            UserModel user = userService.update(id, request);
            return new ResponseEntity<>(userMapper.userModelToUserDto(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("userId") UUID id) {
        userService.deleteById(id);
    }

}
