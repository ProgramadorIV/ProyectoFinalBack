package com.salesianos.socialrides.repository;

import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(value = "user-with-posts", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findFirstByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @EntityGraph(value = "user-with-posts-and-likes", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(UUID id);

    @Query("""
            SELECT DISTINCT new com.salesianos.socialrides.model.post.dto.PostResponse(
            p.id, p.title, p.description, p.img, p.location, p.dateTime)
            FROM Post p LEFT JOIN FETCH Likee l ON p.id = l.post.id
            WHERE l.user.id = :id
            """)
    Page<List<PostResponse>> findLikedPosts(Pageable pageable, UUID id);
}
