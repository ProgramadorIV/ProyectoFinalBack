package com.salesianos.socialrides.controller;

import com.salesianos.socialrides.model.post.dto.CreatePostRequest;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.service.PostService;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "Post endpoints controller")
@RequestMapping("/post")
public class PostController {

    private final PostService postService;


    @Operation(summary = "Creates a new post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                         "img": "",
                                         "title": "Escaleritas potentes",
                                         "description": "Pues un classic set de 12 escaleras con barra.",
                                         "location": "12.4,1.5"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "There are no posts yet.",
                    content = @Content) })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "New post data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreatePostRequest.class),
                    examples = @ExampleObject(value = """
                            {
                                "title": "Escaleritas potentes",
                                "description": "Pues un classic set de 12 escaleras con barra.",
                                "img": "",
                                "location": "12.4,1.5",
                                "dateTime": "16/02/2023 06:18:11"
                            }
                            """)
            )}
    )
    @PostMapping("/")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest post,
                                                   @AuthenticationPrincipal User loggedUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                PostResponse.of(postService.createPost(post, loggedUser))
        );
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
                                                                    "title": "Escaleritas potentes",
                                                                    "description": "Pues un classic set de 12 escaleras con barra.",
                                                                    "img": "ngsvi",
                                                                    "location": "12.4,1.5"
                                                                },
                                                                {
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
    @GetMapping("/")
    public Page<List<PostResponse>> getAllPosts(/*@PageableDefault(page = 0, size = 10)*/@PageableDefault Pageable pageable){
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
                                        "dateTime": "16/02/2023 09:21:39"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "No post with id: 1" ,
                    content = @Content) })
    @Parameter(description = "ID of the post", required = true)
    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Long id){
        return postService.findOne(id);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<PostResponse> editPost(@PathVariable Long id, @Valid @RequestBody CreatePostRequest editedPost,
                                                 @AuthenticationPrincipal User user){

    }*/


}
