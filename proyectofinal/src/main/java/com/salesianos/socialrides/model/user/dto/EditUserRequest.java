package com.salesianos.socialrides.model.user.dto;

import com.salesianos.socialrides.validation.annotation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserRequest {

    @NotEmpty(message = "{createUserRequest.email.notempty}")
    @Email(message = "{createUserRequest.email.email}")
    @UniqueEmail
    private String email;

    @NotEmpty(message = "{createUserRequest.name.notempty}")
    @Size(max = 100, message = "{createUserRequest.name.size}")
    private String name;

    @NotEmpty(message = "{createUserRequest.surname.notempty}")
    @Size(max = 100, message = "{createUserRequest.surname.size}")
    private String surname;

    @Past(message = "{createUserRequest.birthday.past}")
    @NotNull(message = "{createUserRequest.birthday.notnull}")
    private LocalDate birthday;
}
