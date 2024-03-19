package com.blog_api.blog_api.exception;

public class CommentException extends RuntimeException {

    private String errorCode;

    public CommentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
