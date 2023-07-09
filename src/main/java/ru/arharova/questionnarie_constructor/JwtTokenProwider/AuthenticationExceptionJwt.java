package ru.arharova.questionnarie_constructor.JwtTokenProwider;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;


@Getter
public class AuthenticationExceptionJwt extends AuthenticationException {
    private HttpStatus httpStatus;
    public AuthenticationExceptionJwt(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
