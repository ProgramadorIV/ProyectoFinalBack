package com.salesianos.socialrides.model.like;

import com.salesianos.socialrides.model.post.Post;
import com.salesianos.socialrides.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "like_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Like {

    public Like(User user, Post post, LocalDateTime dateTime){
        this.user = user;
        this.post = post;
        this.dateTime = dateTime;
        likePk.setUserId(user.getId());
        likePk.setPostId(post.getId());
    }

    @EmbeddedId
    private LikePk likePk = new LikePk();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_LIKE_USER"))
    private User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_LIKE_POST"))
    private Post post;

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return likePk.equals(like.likePk);
    }



}
