package ru.antonovmikhail.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {

    @org.hibernate.validator.constraints.UUID
    private UUID id;
    @NotBlank
    private String login;
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String password;

}
