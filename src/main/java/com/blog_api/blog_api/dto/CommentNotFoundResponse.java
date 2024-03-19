package com.blog_api.blog_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentNotFoundResponse {
    private String message;
    private long postId;
    private long commentId;

    public CommentNotFoundResponse(String message, long postId, long commentId) {
        this.message = message;
        this.postId = postId;
        this.commentId = commentId;
    }
}
