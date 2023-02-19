package com.salesianos.socialrides.model.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianos.socialrides.model.comment.Comment;
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

    private String username;

    @JsonFormat(pattern = "dd/MM/yyyy hh:HH:ss")
    private LocalDateTime dateTime;

    private String body;

    public static CommentResponse of(Comment comment){
        return CommentResponse.builder()
                .username(comment.getUser().getUsername())
                .body(comment.getBody())
                .dateTime(comment.getDateTime())
                .build();
    }
}
