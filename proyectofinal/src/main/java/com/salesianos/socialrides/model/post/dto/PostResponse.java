package com.salesianos.socialrides.model.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianos.socialrides.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse {

    public PostResponse(String title, String description, String img, String location){
        this.title = title;
        this.description = description;
        this.img = img;
        this.location = location;
    }


    private String title;

    private String description;

    private String img;

    private String location;

    @JsonFormat(pattern = "dd/MM/yyyy hh:HH:ss")
    private LocalDateTime dateTime;

    public static PostResponse of (Post post){
        return PostResponse.builder()
                //Puede que necesite el ternario en el img
                .title(post.getTitle())
                .description(post.getDescription()==null ? null : post.getDescription())
                .img(post.getImg())
                .location(post.getLocation())
                .dateTime(post.getDateTime())
                .build();
    }
}
