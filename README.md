# ProyectoFinalBack

## Social Rides API

The users provided to test this API are:

| Username | Password |
| --- | --- |
| Admin | admin |
| User | user |

Getting Started
---------------

To use the Weather API, you will need to login to obtain a Token in order to access to certain endpoints. You can obtain this token by the signing up and login with the endpoints indicated below.

Once you have a token, you can make requests to the API using the url <http://localhost:8080> followed by the following endpoints:

Endpoints
---------

This API provides the following endpoints:

### User

Login ->
`POST /auth/login`
Logs in the user and returns an authentication token.


Register ->
`POST /auth/register`
Registers a new user. The request must contain the user´s username and password.


Change Password ->
`PUT /auth/user/changePassword`
Changes the user password. The request must contain the old password, the new password and a verification of the new password.


Get User Profile ->
`GET /user/{username}`
Gets the user's profile for the given username.


Edit User Details ->
`PUT /auth/user/edit`
Updates the user's details. The request must contain the new data for the user (name, surname, email, birthday) and an optional profile picture.


Get Liked Posts ->
`GET /auth/user/like`
Gets the posts that the user has liked.


Get My Profile ->
`GET /auth/user/profile`
Gets the profile of the currently authenticated user.


### Admin

Register Admin ->
`POST /auth/register`
Registers a new admin user. The request must contain the user's username and password.

### Post

Create Post -> 
`POST /auth/post/{postId}`
Creates a new post. The request must contain the post´s title and location.


Get All Posts ->
`GET /post`
Gets all the posts.


Get One Post ->
`GET /post/{postId}`
Gets the post for the given id.


Edit Post ->
`UPDATE /auth/post/{postId}`
Edits the post for the given id. The request must contain the title, description, location and an optional picture.


Get User Posts ->
`GET /auth/post`
Gets the post published by the logged user.


Delete Post ->
`DELETE /auth/post/{postId}`
Deletes the post for the given id.


Error Handling
--------------

If an error occurs while processing a request, the API will return an error code and a message describing the error.

| Error Code | Description |
| --- | --- |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |

Support and Contact Information
-------------------------------

If you need help using the Social Rides API you can contact us at <antoniojz1998@gmail.com>.
