package com.blog_api.blog_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInDto {
    private String username;
    private String password;
}
