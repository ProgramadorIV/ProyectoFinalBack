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
    @NotEmpty(message = "{changePasswordRequest.newPassword.notempty}")
    private String newPassword;
    @NotEmpty(message = "{changePasswordRequest.verifyNewPassword.notempty}")
    private String verifyNewPassword;

}
