package com.blog_api.blog_api.dto;


import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String role;
}
