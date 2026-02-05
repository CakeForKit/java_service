package com.example.TestService.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @NotNull
    @Column(name = "ID")
    private UUID id;

    @NotNull
    @NotBlank(message="Firstname is required")
    @Size(min=1, max=256, message="Firstname must be at least 1 character long and max 256.")
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @NotBlank(message="Lastname is required")
    @Size(min=1, max=256, message="Lastname must be at least 1 character long and max 256.")
    @Column(name = "LASTNAME")
    private String lastname;

    @NotNull
    @Column(name = "AGE")
    private Integer age;

    @NotNull
    @Column(name = "IS_DELETED")
    private Boolean isDeleted;
}
