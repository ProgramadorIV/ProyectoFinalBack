package com.salesianos.socialrides.controller;

import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.model.user.dto.CreateUserRequest;
import com.salesianos.socialrides.model.user.dto.UserResponse;
import com.salesianos.socialrides.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@RequestBody CreateUserRequest newUser){

        User user = userService.createUserWithUserRole(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@RequestBody CreateUserRequest newUser){

        User user = userService.createUserWithAdminRole(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }
}
