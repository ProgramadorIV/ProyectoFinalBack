package com.salesianos.socialrides.model.user.dto;

import com.salesianos.socialrides.validation.annotation.FieldsMatch;
import com.salesianos.socialrides.validation.annotation.PasswordFriendly;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldsMatch(field = "newPassword", fieldMatch = "verifyNewPassword", message = "{changePasswordRequest.password.nomatch}")
public class ChangePasswordRequest {

    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String oldPassword;
    @NotEmpty(message = "{changePasswordRequest.newPassword.notempty}")
    @PasswordFriendly
    private String newPassword;
    @NotEmpty(message = "{changePasswordRequest.verifyNewPassword.notempty}")
    private String verifyNewPassword;

}
