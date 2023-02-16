package com.salesianos.socialrides.repository;

import com.salesianos.socialrides.model.post.Post;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT new com.salesianos.socialrides.model.post.dto.PostResponse(
            p.title, p.description, p.img, p.location)
            FROM Post p
            """)
    Page<List<PostResponse>> findAllPosts(Pageable pageable);

    @Query("""
            SELECT new com.salesianos.socialrides.model.post.dto.PostResponse(
            p.title, p.description, p.img, p.location, p.dateTime)
            FROM Post p
            WHERE p.id = :id
            """)
    Optional<PostResponse> findOne(Long id);
}
