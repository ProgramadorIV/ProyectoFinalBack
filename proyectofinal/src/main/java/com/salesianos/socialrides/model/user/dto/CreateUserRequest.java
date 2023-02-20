package com.salesianos.socialrides.model.user.dto;

import com.salesianos.socialrides.validation.annotation.FieldsMatch;
import com.salesianos.socialrides.validation.annotation.PasswordFriendly;
import com.salesianos.socialrides.validation.annotation.UniqueEmail;
import com.salesianos.socialrides.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldsMatch(field = "password", fieldMatch = "verifyPassword", message = "{changePasswordRequest.password.nomatch}")
public class CreateUserRequest {

    @NotEmpty(message = "{createUserRequest.username.notempty}")
    @UniqueUsername
    private String username;

    @PasswordFriendly
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String password;

    @NotEmpty(message = "{createUserRequest.verifyPassword.notempty}")
    private String verifyPassword;

    @NotEmpty(message = "{createUserRequest.email.notempty}")
    @Email(message = "{createUserRequest.email.email}")
    @UniqueEmail
    private String email;

    @NotEmpty(message = "{createUserRequest.name.notempty}")
    private String name;

    @NotEmpty(message = "{createUserRequest.surname.notempty}")
    private String surname;

    @Past(message = "{createUserRequest.birthday.past}")
    @NotNull(message = "{createUserRequest.birthday.notnull}")
    private LocalDate birthday;
}
