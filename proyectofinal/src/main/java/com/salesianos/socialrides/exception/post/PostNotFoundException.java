package com.salesianos.socialrides.exception.post;

import javax.persistence.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {

    public PostNotFoundException(Long id){super(String.format("No post with id: "+ id));}
}
