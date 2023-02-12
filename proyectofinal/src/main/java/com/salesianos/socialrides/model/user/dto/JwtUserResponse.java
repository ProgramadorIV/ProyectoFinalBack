package com.salesianos.socialrides.model.user.dto;

import com.salesianos.socialrides.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class JwtUserResponse extends UserResponse{

    private String token;

    public JwtUserResponse(UserResponse userResponse) {
        id = userResponse.getId();
        username = userResponse.getUsername();
        email = userResponse.getEmail();
        createdAt = userResponse.getCreatedAt();
    }

    public static JwtUserResponse of (User user, String token){
        JwtUserResponse result = new JwtUserResponse(UserResponse.fromUser(user));
        result.setToken(token);
        return result;
    }

}
