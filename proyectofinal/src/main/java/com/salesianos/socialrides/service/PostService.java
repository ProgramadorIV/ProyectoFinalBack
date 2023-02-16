package com.salesianos.socialrides.service;

import com.salesianos.socialrides.model.post.Post;
import com.salesianos.socialrides.model.post.dto.CreatePostRequest;
import com.salesianos.socialrides.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Optional<Post> findById(Long id){return postRepository.findById(id);}

    public Post createPost(CreatePostRequest newPost){

        return postRepository.save(
                Post.builder()
                        .img(newPost.getImg()==null? null: newPost.getImg())
                        .title(newPost.getTitle())
                        .description(newPost.getDescription())
                        .location(newPost.getLocation())
                        .build()
        );
    }
}
