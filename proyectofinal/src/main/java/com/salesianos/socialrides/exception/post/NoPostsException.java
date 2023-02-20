package com.salesianos.socialrides.exception.post;

import javax.persistence.EntityNotFoundException;

public class NoPostsException extends EntityNotFoundException {

    public NoPostsException(){super("No posts found");}
}
