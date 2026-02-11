package ru.bellintegrator.test.service.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@SQLRestriction("IS_DELETED = false")
public class User {
    @Id
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy =  GenerationType.UUID)
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

    @Column(name = "IS_DELETED")
    @Builder.Default
    private Boolean isDeleted = false;
}
