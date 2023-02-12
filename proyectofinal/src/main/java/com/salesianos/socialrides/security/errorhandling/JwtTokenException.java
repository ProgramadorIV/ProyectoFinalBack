package com.salesianos.socialrides.security.errorhandling;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String message) {super(message);}
}
