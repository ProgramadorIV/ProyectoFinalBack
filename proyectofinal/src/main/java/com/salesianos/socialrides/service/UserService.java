package com.salesianos.socialrides.service;

import com.salesianos.socialrides.exception.user.NotLikedPostsException;
import com.salesianos.socialrides.exception.user.UserNotFoundException;
import com.salesianos.socialrides.files.service.StorageService;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.model.user.UserRole;
import com.salesianos.socialrides.model.user.dto.CreateUserRequest;
import com.salesianos.socialrides.model.user.dto.EditUserRequest;
import com.salesianos.socialrides.model.user.dto.UserResponse;
import com.salesianos.socialrides.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final StorageService storageService;

    public Optional<User> findById(UUID id){return userRepository.findById(id);}

    public Optional<User> findByUsername(String username){
        return userRepository.findFirstByUsername(username);
    }

    public UserResponse findUserByUsername(String username){
        return userRepository.findFirstByUsername(username)
                .map(UserResponse::of)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User createUser(CreateUserRequest createUserRequest, MultipartFile file, EnumSet<UserRole> roles){

        String filename = storageService.store(file);

        User user = User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .avatar(filename)
                .name(createUserRequest.getName())
                .surname(createUserRequest.getSurname())
                .email(createUserRequest.getEmail())
                .birthday(createUserRequest.getBirthday())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest, MultipartFile file){
        return createUser(createUserRequest, file, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserRequest createUserRequest, MultipartFile file){
        return createUser(createUserRequest, file, EnumSet.of(UserRole.ADMIN));
    }

    public Optional<User> editPassword(UUID userId, String newPassword){
        return userRepository.findById(userId)
                .map( user -> {
                    user.setPassword(passwordEncoder.encode((newPassword)));
                    return userRepository.save(user);
                }).or(Optional::empty);
    }

    public UserResponse edit(EditUserRequest editedUser, MultipartFile file, User user) {

        String filename = storageService.store(file);

        return UserResponse.toDetails(userRepository.findById(user.getId())
                .map(u -> {
                    u.setAvatar(filename);
                    u.setName(editedUser.getName());
                    u.setSurname(editedUser.getSurname());
                    u.setEmail(editedUser.getEmail());
                    u.setBirthday(editedUser.getBirthday());
                    return userRepository.save(u);
                }).orElseThrow(UserNotFoundException::new));
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

    public Page<List<PostResponse>> getLikedPosts(Pageable pageable, UUID userId){

        Page<List<PostResponse>> page = userRepository.findLikedPosts(pageable, userId);

        if(page.isEmpty())
            throw new NotLikedPostsException();
        return page;
    }

    public UserResponse getProfile(UUID id){
        return UserResponse.of(
                userRepository.findById(id)
                        .orElseThrow(UserNotFoundException::new)
        );
    }

}
