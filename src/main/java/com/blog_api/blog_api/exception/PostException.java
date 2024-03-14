package com.blog_api.blog_api.exception;

import lombok.Data;

@Data
public class PostException extends RuntimeException {

    private String errorCode;

    public PostException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
