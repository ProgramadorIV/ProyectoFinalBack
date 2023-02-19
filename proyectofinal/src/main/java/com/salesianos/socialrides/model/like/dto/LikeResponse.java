package com.salesianos.socialrides.model.like.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianos.socialrides.model.like.Likee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {

    private String username;

    @JsonFormat(pattern = "dd/MM/yyyy hh:HH:ss")
    private LocalDateTime dateTime;

    public static LikeResponse of(Likee likee){
        return LikeResponse.builder()
                .dateTime(likee.getDateTime())
                .username(likee.getUser().getUsername())
                .build();
    }
}
