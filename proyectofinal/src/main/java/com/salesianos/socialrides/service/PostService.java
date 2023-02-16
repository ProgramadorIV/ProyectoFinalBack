package com.salesianos.socialrides.service;

import com.salesianos.socialrides.exception.post.NoPostsException;
import com.salesianos.socialrides.exception.post.PostNotFoundException;
import com.salesianos.socialrides.model.post.Post;
import com.salesianos.socialrides.model.post.dto.CreatePostRequest;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Optional<Post> findById(Long id){return postRepository.findById(id);}

    public PostResponse findOne(Long id){
        return postRepository.findOne(id)
                .orElseThrow(() -> new PostNotFoundException(id));

    }

    public Post createPost(CreatePostRequest newPost, User u){

        Post post = Post.builder()
                        .img(newPost.getImg()==null? null: newPost.getImg())
                        .title(newPost.getTitle())
                        .description(newPost.getDescription())
                        .location(newPost.getLocation())
                        .build();
        post.addToUser(u);
        postRepository.save(post);
        return post;
    }

    public Page<List<PostResponse>> findAll(Pageable pageable){

        Page<List<PostResponse>> posts = postRepository.findAllPosts(pageable);
        if (posts.isEmpty())
            throw new NoPostsException();

        return posts;
    }

}
