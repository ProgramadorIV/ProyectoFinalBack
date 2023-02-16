package com.salesianos.socialrides.repository;

import com.salesianos.socialrides.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsername(String username);

    boolean existsByUsername(String username);

    @EntityGraph("user-with-posts")
    Optional<User> findById(UUID id);
}
