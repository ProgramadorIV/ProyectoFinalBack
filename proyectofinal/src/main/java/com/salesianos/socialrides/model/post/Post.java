package com.salesianos.socialrides.model.post;

import com.salesianos.socialrides.model.like.Like;
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
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    /*
    String con la url o path*/
    private String img;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
            name = "FK_POST_USER"
    ))
    private User user;

    private String description;

    private String ubication;

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Like> likes = new HashSet<>();


    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();

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
