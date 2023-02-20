package com.salesianos.socialrides.exception.user;

import javax.persistence.EntityNotFoundException;

public class NotLikedPostsException extends EntityNotFoundException {

    public NotLikedPostsException(){super("This user has no liked posts");}
}
