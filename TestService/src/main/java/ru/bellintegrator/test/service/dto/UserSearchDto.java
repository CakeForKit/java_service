package ru.bellintegrator.test.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSearchDto {
    private String firstname;
    private String lastname;
    private Integer age;
}
