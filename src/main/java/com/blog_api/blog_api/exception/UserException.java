package com.blog_api.blog_api.exception;

public class UserException extends RuntimeException {

    private String errorCode;

    public UserException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
