package com.salesianos.socialrides.service;

import com.salesianos.socialrides.exception.post.NoPostsException;
import com.salesianos.socialrides.exception.post.NoUserPostsException;
import com.salesianos.socialrides.exception.post.PostNotFoundException;
import com.salesianos.socialrides.model.post.Post;
import com.salesianos.socialrides.model.post.dto.CreatePostRequest;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post findById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    /*public PostResponse findOne(Long id){
        return postRepository.findOne(id)
                .orElseThrow(() -> new PostNotFoundException(id));

    }*/

    public Page<List<PostResponse>> findAllByUser(Pageable pageable, UUID id){
         Page<List<PostResponse>> posts = postRepository.findAllByUser(pageable, id);
         if(posts.isEmpty())
             throw new NoUserPostsException();

         return posts;
    }

    public Post findPostWithInteractions(Long id){
        return postRepository.findPost(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public ResponseEntity<PostResponse> createPost(CreatePostRequest newPost, User u){

        Post post = Post.builder()
                        .img(newPost.getImg()==null? null: newPost.getImg())
                        .title(newPost.getTitle())
                        .description(newPost.getDescription())
                        .location(newPost.getLocation())
                        .build();
        post.addToUser(u);
        postRepository.save(post);

        return ResponseEntity.created(ServletUriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("post/{id}")
                .buildAndExpand(post.getId()).toUri())
                .body(PostResponse.of(post));
    }

    public Page<List<PostResponse>> findAll(Pageable pageable){

        Page<List<PostResponse>> posts = postRepository.findAllPosts(pageable);
        if (posts.isEmpty())
            throw new NoPostsException();

        return posts;
    }

    public ResponseEntity<PostResponse> editPost(Long id, CreatePostRequest editedPost){

        return ResponseEntity.ok(PostResponse.of(postRepository.findById(id).map(post -> {
            post.setTitle(editedPost.getTitle());
            post.setImg(editedPost.getImg());
            post.setDescription(editedPost.getDescription());
            post.setLocation(editedPost.getLocation());
            return postRepository.save(post);
        }).orElseThrow(() -> new PostNotFoundException(id))));
    }
}
