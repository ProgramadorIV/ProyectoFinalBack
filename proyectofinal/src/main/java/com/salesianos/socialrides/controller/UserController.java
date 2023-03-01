package com.salesianos.socialrides.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianos.socialrides.model.post.dto.PostResponse;
import com.salesianos.socialrides.model.user.User;
import com.salesianos.socialrides.model.user.dto.*;
import com.salesianos.socialrides.security.jwt.access.JwtProvider;
import com.salesianos.socialrides.security.jwt.refresh.RefreshToken;
import com.salesianos.socialrides.security.jwt.refresh.RefreshTokenException;
import com.salesianos.socialrides.security.jwt.refresh.RefreshTokenRequest;
import com.salesianos.socialrides.security.jwt.refresh.RefreshTokenService;
import com.salesianos.socialrides.service.UserService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User endpoints controller.")
@Validated
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @JsonView(View.UserView.CreatedView.class)
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestPart("user") CreateUserRequest newUser,
                                                               @RequestPart("file")MultipartFile file){

        User user = userService.createUserWithUserRole(newUser, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @JsonView(View.UserView.CreatedView.class)
    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@Valid @RequestPart("user") CreateUserRequest newUser,
                                                                @RequestPart("file")MultipartFile file){

        User user = userService.createUserWithAdminRole(newUser, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        User user = (User) authentication.getPrincipal();

        refreshTokenService.deleteByUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(user, token, refreshToken.getToken()));
    }
    @JsonView(View.UserView.CreatedView.class)
    @PutMapping("/auth/user/changePassword")
    public ResponseEntity<UserResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {

        // Este código es mejorable.
        // La validación de la contraseña nueva se puede hacer con un validador.
        // La gestión de errores se puede hacer con excepciones propias
        try {
            if (userService.passwordMatch(loggedUser, changePasswordRequest.getOldPassword())) {
                Optional<User> modified = userService.editPassword(loggedUser.getId(), changePasswordRequest.getNewPassword());
                if (modified.isPresent())
                    return ResponseEntity.ok(UserResponse.fromUser(modified.get()));
            } else {
                // Lo ideal es que esto se gestionara de forma centralizada
                // Se puede ver cómo hacerlo en la formación sobre Validación con Spring Boot
                // y la formación sobre Gestión de Errores con Spring Boot
                throw new RuntimeException();
            }
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Data Error");
        }

        return null;
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        String refreshedToken = refreshTokenRequest.getRefreshedToken();

        return refreshTokenService.findByToken(refreshedToken)
                .map(refreshTokenService::verify)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.generateToken(user);
                    refreshTokenService.deleteByUser(user);
                    RefreshToken refreshedTokenV2 = refreshTokenService.createRefreshToken(user);

                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(JwtUserResponse.builder()
                                    .token(token)
                                    .refreshedToken(refreshedTokenV2.getToken())
                                    .build()
                            );
                }).orElseThrow(() -> new RefreshTokenException("Refresh token could not be found"));
    }

    @Operation(summary = "Returns searched user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                            "id": "4ae23df7-4194-4941-8a9d-0310c37c3d30",
                                            "username": "Antonio",
                                            "name": "Antonio",
                                            "surname": "JimÃ©nez",
                                            "birthday": "2022-12-13",
                                            "email": "antonio@gmail.com",
                                            "createdAt": "20/02/2023 20:40:13",
                                            "posts": [
                                                {
                                                    "id": 1,
                                                    "title": "Setas de sevilla",
                                                    "description": "Un sitio muy bonito",
                                                    "img": "gigig.img",
                                                    "location": "1.2,1.9",
                                                    "dateTime": "20/02/2023 08:40:13"
                                                }
                                            ]
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "User with specified username not found" ,
                    content = @Content) })
    @Parameter(description = "Username of the user", required = true)
    @JsonView({View.UserView.ProfileView.class})
    @GetMapping("/user/{username}")
    public UserResponse getUserDetails(@PathVariable String username){
        return userService.findUserByUsername(username);
    }


    @Operation(summary = "Edits the details from the user authenticated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details edited successfully",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                            "name": "Jonathan",
                                            "surname": "Infante",
                                            "birthday": "2021-12-13",
                                            "email": "hola@gmail.com"
                                    }
                                    """)
                    )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "The data provided is incorrect",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Actualized data of the user",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EditUserRequest.class),
                    examples = @ExampleObject( value = """
                                {
                                     "avatar": "avatar.img",
                                     "name": "Jonathan",
                                     "surname": "Infante",
                                     "email": "hola@gmail.com",
                                     "birthday": "2021-12-13"
                                 }
                            """)
            )}
    )
    @JsonView({View.UserView.DetailsView.class})
    @PutMapping("/auth/user/edit")
    public UserResponse editUser(@Valid @RequestPart("user") EditUserRequest editUserRequest,
                                 @RequestPart("file") MultipartFile file,
                                 @AuthenticationPrincipal User loggedUser){
        return userService.edit(editUserRequest, file, loggedUser);
    }

    @Operation(summary = "Returns list with all the liked posts by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of liked posts from users found",
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
                    description = "This user has not liked posts",
                    content = @Content)
    })
    @GetMapping("/auth/user/like")
    public Page<List<PostResponse>> getLikedPosts(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                  @AuthenticationPrincipal User user){
        return userService.getLikedPosts(pageable, user.getId());
    }


    @Operation(summary = "Returns the profile of the logged user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Profile of the user found",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                     "id": "4ae23df7-4194-4941-8a9d-0310c37c3d30",
                                                                     "username": "Antonio",
                                                                     "name": "Antonio",
                                                                     "surname": "JimÃ©nez",
                                                                     "birthday": "2022-12-13",
                                                                     "email": "antonio@gmail.com",
                                                                     "createdAt": "21/02/2023 00:22:37",
                                                                     "posts": [
                                                                         {
                                                                             "id": 1,
                                                                             "title": "Setas de sevilla",
                                                                             "description": "Un sitio muy bonito",
                                                                             "img": "gigig.img",
                                                                             "location": "1.2,1.9",
                                                                             "dateTime": "21/02/2023 12:22:37"
                                                                         }
                                                                     ],
                                                            }
                                                            """
                                            )
                                    })
                    }),
            @ApiResponse(responseCode = "404",
                    description = "This user can´t be found",
                    content = @Content)
    })
    @JsonView(View.UserView.ProfileView.class)
    @GetMapping("/auth/user/profile")
    public UserResponse getProfile(@AuthenticationPrincipal User user){
        return userService.getProfile(user.getId());
    }
}
