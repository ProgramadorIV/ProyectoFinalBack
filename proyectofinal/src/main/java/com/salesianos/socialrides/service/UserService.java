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
                .name(createUserRequest.getName())
                .surname(createUserRequest.getSurname())
                .email(createUserRequest.getEmail())
                .birthday(createUserRequest.getBirthday())
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

    public Optional<User> editPassword(UUID userId, String newPassword){
        return userRepository.findById(userId)
                .map( user -> {
                    user.setPassword(passwordEncoder.encode((newPassword)));
                    return userRepository.save(user);
                }).or(Optional::empty);
    }

    public Optional<User> edit(User user) {

        return userRepository.findById(user.getId())
                .map(u -> {
                    u.setEmail(user.getEmail());
                    return userRepository.save(u);
                }).or(Optional::empty);

    }

    public boolean passwordMatch(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    public void delete(User user){ deleteById(user.getId());}

    public void deleteById(UUID id){
        if(userRepository.existsById(id))
            userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){ return userRepository.existsByEmail(email); }

}
