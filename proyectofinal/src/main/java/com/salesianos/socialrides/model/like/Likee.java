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
public class Likee {

    public Likee(User user, Post post, LocalDateTime dateTime){
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
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_LIKE_USER"), columnDefinition = "uuid")
    private User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_LIKE_POST"))
    private Post post;

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();

    private void addToUser(User u){
        user = u;
        u.getLikes().add(this);
    }

    private void removeFromUser(User u){
        u.getLikes().remove(this);
        user = null;
    }

    private void addToPost(Post p){
        post = p;
        p.getLikes().add(this);
    }

    private void removeFromPost(Post p){
        p.getLikes().remove(this);
        post = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Likee like = (Likee) o;
        return likePk.equals(like.likePk);
    }



}
