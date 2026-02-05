package com.example.TestService.mappers;

import com.example.TestService.dto.UserDto;
import com.example.TestService.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto entityToDto(User user);
}
