package ru.bellintegrator.test_service.mappers;

import ru.bellintegrator.test_service.dto.UserDto;
import ru.bellintegrator.test_service.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto entityToDto(User user);
}
