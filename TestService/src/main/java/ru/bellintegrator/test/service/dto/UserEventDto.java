package ru.bellintegrator.test.service.dto;

import lombok.Builder;
import ru.bellintegrator.test.service.models.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDto {
    @NotNull
    private ActionUser action;

    @NotNull
    private User user;
}
