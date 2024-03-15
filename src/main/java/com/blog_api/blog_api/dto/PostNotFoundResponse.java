package com.blog_api.blog_api.dto;

public class PostNotFoundResponse {
    private String message;
    private long postId;

    public PostNotFoundResponse(String message, long postId) {
        this.message = message;
        this.postId = postId;
    }
}
