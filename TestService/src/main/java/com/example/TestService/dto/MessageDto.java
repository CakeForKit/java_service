package com.example.TestService.dto;

import com.example.TestService.models.UserModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    @NotNull
    private ActionUser action;

    @NotNull
    private UserModel user;
}
