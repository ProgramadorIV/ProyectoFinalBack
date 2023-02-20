package com.salesianos.socialrides.exception.user;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(Long id) {super(String.format("User with id: %d could not be found", id));}
    public UserNotFoundException(){super("The user could not be found.");}

    public UserNotFoundException(String username){super("User with username: "+ username +" not found");}
}
