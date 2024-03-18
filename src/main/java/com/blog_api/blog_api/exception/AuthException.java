package com.blog_api.blog_api.exception;

public class AuthException extends RuntimeException {

    private String errorCode;

    public AuthException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
