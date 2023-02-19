package com.salesianos.socialrides.model.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianos.socialrides.model.comment.dto.CommentResponse;
import com.salesianos.socialrides.model.like.dto.LikeResponse;
import com.salesianos.socialrides.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PostResponse {

    public PostResponse(Long id, String title, String description, String img, String location){
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
        this.location = location;
    }

    private Long id;

    private String title;

    private String description;

    private String img;

    private String location;

    @JsonFormat(pattern = "dd/MM/yyyy hh:HH:ss")
    private LocalDateTime dateTime;

    private String username;

    private List<LikeResponse> likes = new ArrayList<>();

    private List<CommentResponse> comments = new ArrayList<>();


    public static PostResponse of (Post post){
        return PostResponse.builder()
                //Puede que necesite el ternario en el img
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .description(post.getDescription()==null ? null : post.getDescription())
                .img(post.getImg())
                .location(post.getLocation())
                .dateTime(post.getDateTime())
                .likes(
                        post.getLikes().stream().map(LikeResponse::of).toList()
                )
                .comments(
                        post.getComments().stream().map(CommentResponse::of).toList()
                )
                .build();
    }
}
