package com.salesianos.socialrides.model.comment;

import com.salesianos.socialrides.model.post.Post;
import com.salesianos.socialrides.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Comment {

    public Comment(User user, Post post, String body){
        this.user = user;
        this.post = post;
        this.body = body;
        commentPk.setUserId(user.getId());
        commentPk.setPostId(post.getId());
    }

    @EmbeddedId
    private CommentPk commentPk = new CommentPk();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_COMMENT_USER"), columnDefinition = "uuid")
    private User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_COMMENT_POST"))
    private Post post;

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();

    private String body;

    private void addToPost(Post p){
        post = p;
        p.getComments().add(this);
    }

    private void removeFromPost(Post p){
        p.getComments().remove(this);
        post = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentPk.equals(comment.commentPk);
    }


}
