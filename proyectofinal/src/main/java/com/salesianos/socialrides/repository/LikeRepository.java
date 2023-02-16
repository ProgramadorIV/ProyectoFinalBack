package com.salesianos.socialrides.repository;

import com.salesianos.socialrides.model.like.Like;
import com.salesianos.socialrides.model.like.LikePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikePk> {
}
