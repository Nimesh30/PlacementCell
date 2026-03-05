package com.Project.PlacementCell.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException; // FIXED
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UsernameNotFoundException exception) {
        ApiError apiError = new ApiError(
                "Username not found: " + exception.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception) {
        ApiError apiError = new ApiError(
                "Authentication Failed: " + exception.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    //    return exception.getMessage();
    }

    @ExceptionHandler(JwtException.class)
    public String handleJwtException(JwtException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exception) {
         return   exception.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        ApiError apiError = new ApiError(
                "Unexpected error: " + exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
       //return  exception.getMessage();
    }
}