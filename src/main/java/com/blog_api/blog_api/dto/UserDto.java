package com.blog_api.blog_api.dto;

import com.blog_api.blog_api.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private String username;
    private String email;
    private String role;
    private List<Post> posts;
}
