package com.salesianos.socialrides.model.post;

import com.salesianos.socialrides.model.comment.Comment;
import com.salesianos.socialrides.model.like.Likee;
import com.salesianos.socialrides.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "post_entity")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*@NamedEntityGraphs(
        @NamedEntityGraph(name = "post-with-likes-and-comments",
        attributeNodes = {
                @NamedAttributeNode(value = "user"),
                @NamedAttributeNode(value = "likes", subgraph = "like-users"),
                @NamedAttributeNode(value = "comments", subgraph = "comment-users")
        }, subgraphs = {
                @NamedSubgraph(name = "like-users", attributeNodes = {
                        @NamedAttributeNode("user")
                }),
                @NamedSubgraph(name = "comment-users", attributeNodes = {
                        @NamedAttributeNode("user")
                })
        })
)*/
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    /*
    String con la url o path*/
    private String img;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
            name = "FK_POST_USER"
    ))
    private User user;

    private String description;

    private String location;

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Likee> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();

    //HELPERS
    public void addToUser(User u){
        user = u;
        u.getPosts().add(this);
    }

    public void removeFromUser(User u){
        u.getPosts().remove(this);
        user = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
