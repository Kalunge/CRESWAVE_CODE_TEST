package com.blog_api.blog_api.dto;

import org.springframework.security.core.GrantedAuthority;


public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}