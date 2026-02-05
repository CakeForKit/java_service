package ru.bellintegrator.TestService.mappers;

import ru.bellintegrator.TestService.dto.UserDto;
import ru.bellintegrator.TestService.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto entityToDto(User user);
}
