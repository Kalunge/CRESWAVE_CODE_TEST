package com.blog_api.blog_api.exception;

public class PostNotFoundException extends RuntimeException {

    private String errorCode;

    public PostNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
