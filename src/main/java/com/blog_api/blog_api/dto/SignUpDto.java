package com.blog_api.blog_api.dto;


import com.blog_api.blog_api.dto.enums.UserRole;
import lombok.Data;

@Data
public class SignUpDto {
    String username;
    String password;
}
