package com.salesianos.socialrides.repository;

import com.salesianos.socialrides.model.comment.Comment;
import com.salesianos.socialrides.model.comment.CommentPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, CommentPk> {
}
