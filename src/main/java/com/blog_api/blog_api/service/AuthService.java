package com.blog_api.blog_api.service;

import com.blog_api.blog_api.dto.SignInDto;
import com.blog_api.blog_api.entity.User;

public interface AuthService {
    User signUp(User user);

    User login(SignInDto loginRequest);
}
