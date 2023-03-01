package com.salesianos.socialrides.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianos.socialrides.model.post.dto.CreatePostRequest;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.service.PostService;
import com.salesianos.socialrides.view.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "Post endpoints controller")
public class PostController {

    private final PostService postService;


    @Operation(summary = "Creates a new post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": 1,
                                        "title": "Escaleritas potentes",
                                        "description": "Pues un classic set de 12 escaleras con barra.",
                                        "img": "",
                                        "location": "12.4,1.5",
                                        "dateTime": "16/02/2023 06:18:11"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "There are no posts yet.",
                    content = @Content) })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "New post data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreatePostRequest.class),
                    examples = @ExampleObject(value = """
                            {
                                "img": "",
                                "title": "Escaleritas potentes",
                                "description": "Pues un classic set de 12 escaleras con barra.",
                                "location": "12.4,1.5"
                            }
                            """)
            )}
    )
    @PostMapping("/auth/post")
    @JsonView({View.PostView.PostWithEverythingView.class})
    public ResponseEntity<PostResponse> createPost(@Valid @RequestPart("post") CreatePostRequest post,
                                                   @RequestPart("file") MultipartFile file,
                                                   @AuthenticationPrincipal User loggedUser){
        return postService.createPost(post, file, loggedUser);
    }

    @Operation(summary = "Returns list with all the posts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of posts found",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            [
                                                                {
                                                                    "id": 1,
                                                                    "title": "Escaleritas potentes",
                                                                    "description": "Pues un classic set de 12 escaleras con barra.",
                                                                    "img": "ngsvi",
                                                                    "location": "12.4,1.5"
                                                                },
                                                                {
                                                                    "id": 2,
                                                                    "title": "Setas de Sevilla",
                                                                    "description": "Un spot clásico de la capital andaluza que debes visitar",
                                                                    "img": "ngsvi",
                                                                    "location": "1.2,1.5"
                                                                }
                                                            ]
                                                            """
                                            )
                                    })
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No posts found",
                    content = @Content)
    })
    @GetMapping("/post")
    //@JsonView({View.PostView.PostListView.class}) todo -- No sale porque esta dentro de un pageable
    public Page<List<PostResponse>> getAllPosts(/*@PageableDefault(page = 0, size = 10)*/@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC)Pageable pageable){
        return postService.findAll(pageable);
    }


    @Operation(summary = "Returns post details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "title": "Escaleritas potentes",
                                        "description": "Pues un classic set de 12 escaleras con barra.",
                                        "img": "ngsvi",
                                        "location": "12.4,1.5",
                                        "dateTime": "16/02/2023 09:21:39",
                                        "username": "Antonio",
                                        "likes": [
                                                        {
                                                            "username": "Antonio",
                                                            "dateTime": "19/02/2023 01:01:44"
                                                        }
                                                  ],
                                        "comments": [
                                                        {
                                                            "username": "Antonio",
                                                            "dateTime": "19/02/2023 01:01:44",
                                                            "body": "Me ha gustao"
                                                        }
                                                     ]
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "No post with id: 1" ,
                    content = @Content) })
    @Parameter(description = "ID of the post", required = true)
    @GetMapping("/post/{id}")
    @JsonView({View.PostView.PostWithEverythingView.class})
    public PostResponse getPostById(@PathVariable Long id){
        return PostResponse.of(postService.findPostWithInteractions(id));
    }


    @Operation(summary = "Returns list with all the posts published by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of posts from users found",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            [
                                                                {
                                                                    "id": 1,
                                                                    "title": "Escaleritas potentes",
                                                                    "description": "Pues un classic set de 12 escaleras con barra.",
                                                                    "img": "ngsvi",
                                                                    "location": "12.4,1.5"
                                                                },
                                                                {
                                                                    "id": 2,
                                                                    "title": "Setas de Sevilla",
                                                                    "description": "Un spot clásico de la capital andaluza que debes visitar",
                                                                    "img": "ngsvi",
                                                                    "location": "1.2,1.5"
                                                                }
                                                            ]
                                                            """
                                            )
                                    })
                    }),
            @ApiResponse(responseCode = "404",
                    description = "This user has not published posts",
                    content = @Content)
    })
    @GetMapping("/auth/post")
    //@JsonView({View.PostView.PostListView.class})
    public Page<List<PostResponse>> getAllUserPost(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                   @AuthenticationPrincipal User user){
        return postService.findAllByUser(pageable, user.getId());
    }


    @Operation(summary = "Edits the post provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post edited successfully",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "title": "Escaleritas potentes",
                                        "description": "Pues un classic set de 12 escaleras con barra.",
                                        "img": "ngsvi",
                                        "location": "1.2,1.5",
                                        "dateTime": "19/02/2023 11:23:31",
                                        "username": "antonio",
                                        "likes": [],
                                        "commnets": []
                                    }
                                    """)
                    )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "The data provided is incorrect",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Actualized data of the post",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreatePostRequest.class),
                    examples = @ExampleObject( value = """
                                {
                                    "img": "ngsvi",
                                    "title": "Escaleritas potentes",
                                    "description": "Pues un classic set de 12 escaleras con barra.",
                                    "location": "1.2,1.5"
                                }
                            """)
            )}
    )
    @Parameter(description = "Post to edit id", name = "id", required = true)
    @JsonView({View.PostView.PostWithEverythingView.class})
    @PreAuthorize("@postRepository.existsById(#id)? @postRepository.findById(#id).get().user.username == authentication.principal.getUsername() : false")
    @PutMapping("auth/post/{id}")
    public PostResponse editPost(@PathVariable Long id,
                                 @Valid @RequestPart("post") CreatePostRequest editedPost,
                                 @RequestPart("file") MultipartFile file){
        return postService.editPost(id, editedPost, file);
    }
    //TODO-- Preguntar si el preauthorize está bien

    @Operation(summary = "Deletes a post specified by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @Parameter(description = "Post id", name = "id", required = true)
    @PreAuthorize("@postRepository.existsById(#id)? @postRepository.findById(#id).get().user.username == authentication.principal.getUsername() : false")
    @DeleteMapping("auth/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
