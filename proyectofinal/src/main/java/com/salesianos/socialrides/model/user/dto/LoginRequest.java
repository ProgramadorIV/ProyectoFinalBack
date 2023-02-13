package com.salesianos.socialrides.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "{}")
    private String username;
    @NotEmpty(message = "{}")
    private String password;
}
