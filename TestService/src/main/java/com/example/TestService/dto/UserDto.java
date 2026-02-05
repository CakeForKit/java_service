package com.example.TestService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    private UUID id;

    @NotNull
    @NotBlank(message="Firstname is required")
    @Size(min=1, max=256, message="Firstname must be at least 1 character long and max 256.")
    private String firstname;

    @NotNull
    @NotBlank(message="Lastname is required")
    @Size(min=1, max=256, message="Lastname must be at least 1 character long and max 256.")
    private String lastname;

    @NotNull
    private Integer age;

    @NotNull
    private Boolean isDeleted;
}
