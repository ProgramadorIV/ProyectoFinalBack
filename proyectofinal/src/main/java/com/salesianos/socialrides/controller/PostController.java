package com.salesianos.socialrides.controller;

import com.salesianos.socialrides.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "Post endpoints controller")
public class PostController {

    private final PostService postService;

    /*@PostMapping("/")
    public ResponseEntity<> createPost(){

    }*/
}
