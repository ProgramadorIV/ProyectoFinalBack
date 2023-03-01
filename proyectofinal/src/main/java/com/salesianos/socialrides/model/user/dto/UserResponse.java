package com.salesianos.socialrides.model.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.view.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {

    @JsonView({View.UserView.ProfileView.class,
            View.UserView.CreatedView.class
    })
    protected String id;

    @JsonView({View.UserView.ProfileView.class,
            View.UserView.CreatedView.class
    })
    protected String username;

    @JsonView({View.UserView.ProfileView.class,
            View.UserView.CreatedView.class,
            View.UserView.DetailsView.class
    })
    protected String avatar;

    @JsonView({View.UserView.ProfileView.class,
            View.UserView.DetailsView.class,
            View.UserView.CreatedView.class
    })
    protected String name;

    @JsonView({View.UserView.ProfileView.class,
            View.UserView.DetailsView.class,
            View.UserView.CreatedView.class
    })
    protected String surname;

    @JsonView({View.UserView.ProfileView.class,
            View.UserView.DetailsView.class})
    protected LocalDate birthday;

    @JsonView({View.UserView.ProfileView.class,
            View.UserView.DetailsView.class,
            View.UserView.CreatedView.class
    })
    protected String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonView({View.UserView.ProfileView.class,
            View.UserView.CreatedView.class})
    protected LocalDateTime createdAt;

    @JsonView({View.UserView.ProfileView.class})
    protected List<PostResponse> posts;


    public static UserResponse toDetails(User user){
        return UserResponse.builder()
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .name(user.getName())
                .surname(user.getSurname())
                .birthday(user.getBirthday())
                .build();
    }

    public static UserResponse fromUser(User user){

        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static UserResponse of(User user){
        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .name(user.getName())
                .surname(user.getSurname())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .posts(
                        user.getPosts()
                                .stream()
                                .map(PostResponse::fromUser)
                                .toList()
                )
                .build();
    }
}
