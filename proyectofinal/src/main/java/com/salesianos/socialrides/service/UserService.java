package com.salesianos.socialrides.service;

import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.model.user.UserRole;
import com.salesianos.socialrides.model.user.dto.CreateUserRequest;
import com.salesianos.socialrides.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public Optional<User> findById(UUID id){return userRepository.findById(id);}

    public Optional<User> findByUsername(String username){return userRepository.findFirstByUsername(username);}

    public User createUser(CreateUserRequest createUserRequest, EnumSet<UserRole> roles){
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest){
        return createUser(createUserRequest, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserRequest createUserRequest){
        return createUser(createUserRequest, EnumSet.of(UserRole.ADMIN));
    }
}
