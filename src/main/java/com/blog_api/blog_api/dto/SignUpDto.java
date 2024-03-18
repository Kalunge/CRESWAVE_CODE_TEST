package com.blog_api.blog_api.dto;


import com.blog_api.blog_api.dto.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignUpDto {
    private String username;
    private String email;
    private String password;
    private UserRole role;


}
