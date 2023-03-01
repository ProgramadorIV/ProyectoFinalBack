package com.salesianos.socialrides.model.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianos.socialrides.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUserResponse extends UserResponse{

    private String token;
    private String refreshedToken;

    public JwtUserResponse(UserResponse userResponse) {
        id = userResponse.getId();
        username = userResponse.getUsername();
        avatar = userResponse.getAvatar();
        name = userResponse.getName();
        surname = userResponse.getSurname();
        email = userResponse.getEmail();
        createdAt = userResponse.getCreatedAt();
    }

    public static JwtUserResponse of (User user, String token, String refreshedToken){
        JwtUserResponse result = new JwtUserResponse(UserResponse.fromUser(user));
        result.setToken(token);
        result.setRefreshedToken(refreshedToken);
        return result;
    }

}
