package com.salesianos.socialrides.repository;

import com.salesianos.socialrides.model.post.Post;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT new com.salesianos.socialrides.model.post.dto.PostResponse(
            p.id, p.title, p.description, p.img, p.location, p.dateTime)
            FROM Post p
            """)
    Page<List<PostResponse>> findAllPosts(Pageable pageable);

    @Query("""
            SELECT new com.salesianos.socialrides.model.post.dto.PostResponse(
            p.id, p.title, p.description, p.img, p.location, p.dateTime)
            FROM Post p
            WHERE p.user.id = :id
            """)
    Page<List<PostResponse>> findAllByUser(Pageable pageable, UUID id);

    /*@Query("""
            SELECT new com.salesianos.socialrides.model.post.dto.PostResponse(
            p.title, p.description, p.img, p.location, p.dateTime, p.user.username)
            FROM Post p
            WHERE p.id = :id
            """)
    Optional<PostResponse> findOne(Long id);*/


    /*@EntityGraph(value = "post-with-likes-and-comments", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Post>findById(Long id);*/

    @Query("""
            SELECT DISTINCT p
            FROM Post p LEFT JOIN FETCH Likee l ON p.id = l.post.id
                        LEFT JOIN FETCH Comment c ON p.id = c.post.id
            WHERE p.id = :id
            """)
    Optional<Post> findPost(Long id);


    /*@Query("""
            SELECT DISTINCT l
            FROM Like l LEFT JOIN FECTH l.likePK.userId
            WHERE p.id = :id
            """)*/
}
