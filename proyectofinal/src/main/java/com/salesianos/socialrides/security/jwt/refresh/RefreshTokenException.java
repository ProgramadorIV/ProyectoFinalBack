package com.salesianos.socialrides.security.jwt.refresh;


import com.salesianos.socialrides.security.errorhandling.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {

    public RefreshTokenException(String message){super(message);}
}
