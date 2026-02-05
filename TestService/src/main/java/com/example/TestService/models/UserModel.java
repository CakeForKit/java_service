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
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {
    @Id
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
    @Column(name = "is_deleted")
    private Boolean isDeleted;

//    private Date createdAt = new Date();
}


// Свой бин objectMapper
