package com.salesianos.socialrides.exception.post;

import javax.persistence.EntityNotFoundException;

public class NoUserPostsException extends EntityNotFoundException {

    public NoUserPostsException(){super("This user has not published posts");}
}
