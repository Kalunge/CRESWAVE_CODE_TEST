package com.blog_api.blog_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private Long postId;
    private String postTitle;
    private Long userId;
    private String author;
}
