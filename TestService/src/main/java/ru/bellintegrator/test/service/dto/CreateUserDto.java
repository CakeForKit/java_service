package ru.bellintegrator.test.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserDto {
    @NotNull
    @NotBlank(message="Firstname is required")
    @Size(min=1, max=256, message="Firstname must be at least 1 character long and max 256.")
    public String firstname;

    @NotNull
    @NotBlank(message="Lastname is required")
    @Size(min=1, max=256, message="Lastname must be at least 1 character long and max 256.")
    public String lastname;

    public int age;
}