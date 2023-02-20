package com.salesianos.socialrides.model.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianos.socialrides.model.comment.Comment;
import com.salesianos.socialrides.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    @JsonView({View.PostView.PostWithEverythingView.class})
    private String username;

    @JsonFormat(pattern = "dd/MM/yyyy hh:HH:ss")
    @JsonView({View.PostView.PostWithEverythingView.class})
    private LocalDateTime dateTime;

    @JsonView({View.PostView.PostWithEverythingView.class})
    private String body;

    public static CommentResponse of(Comment comment){
        return CommentResponse.builder()
                .username(comment.getUser().getUsername())
                .body(comment.getBody())
                .dateTime(comment.getDateTime())
                .build();
    }
}
