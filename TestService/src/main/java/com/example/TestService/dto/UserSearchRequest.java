package ru.bellintegrator.TestService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserSearchRequest {
    private String firstname;
    private String lastname;
    private Integer age;

    public UserSearchRequest() {
        firstname = "";
        lastname = "";
        age = null;
    }
}
