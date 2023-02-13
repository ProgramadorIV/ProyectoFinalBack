package com.salesianos.socialrides.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String oldPassword;
    private String newPassword;
    private String verifyNewPassword;

}
