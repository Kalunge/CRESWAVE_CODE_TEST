package com.blog_api.blog_api.dto;

import com.blog_api.blog_api.entity.Comment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostDto {
    private String title;
    private String content;
    private String author;
    private List<Comment> comments;
}
