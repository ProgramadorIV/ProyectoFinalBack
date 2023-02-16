package com.salesianos.socialrides.model.user.dto;

import com.salesianos.socialrides.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotEmpty(message = "{createUserRequest.username.notempty}")
    @UniqueUsername
    private String username;

    //@PasswordFriendly
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String password;

    // @ Que se verifique con la otra
    @NotEmpty(message = "{createUserRequest.verifyPassword.notempty}")
    private String verifyPassword;

    @NotEmpty(message = "{createUserRequest.email.notempty}")
    @Email(message = "{createUserRequest.email.email}")
    //@UniqueEmail
    private String email;

    @NotEmpty(message = "{createUserRequest.name.notempty}")
    private String name;

    @NotEmpty(message = "{createUserRequest.surname.notempty}")
    private String surname;

    private LocalDate birthDate;
}
