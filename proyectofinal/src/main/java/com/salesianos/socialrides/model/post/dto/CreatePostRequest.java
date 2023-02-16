package com.salesianos.socialrides.model.post.dto;

import com.salesianos.socialrides.validation.annotation.IsLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {

    private String img;

    @NotEmpty(message = "{createPost.title.notempty}")
    private String title;

    @Size(max = 1000, message = "{createPost.description.size}")
    private String description;

    @NotEmpty(message = "{createPost.location.notempty}")
    //@ es una ubicación lat, long
    @IsLocation
    private String location;
}
