package com.salesianos.socialrides.repository;

import com.salesianos.socialrides.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
