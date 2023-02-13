package com.salesianos.socialrides.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotEmpty(message = "{createUserRequest.username.notempty}")
    //@UniqueUsername
    private String username;
    //@PasswordFriendly
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String password;
    // @ Que se verifique con la otra
    private String verifyPassword;
    @NotEmpty(message = "{createUserRequest.email.notempty}")
    @Email(message = "{createUserRequest.email.email}")
    private String email;
    //Más adelante le meteré más atributos
}
