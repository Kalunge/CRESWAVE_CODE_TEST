package com.blog_api.blog_api.service;

import com.blog_api.blog_api.dto.LoginRequest;
import com.blog_api.blog_api.entity.User;

public interface AuthService {
    User signUp(User user);

    User login(LoginRequest loginRequest);
}
