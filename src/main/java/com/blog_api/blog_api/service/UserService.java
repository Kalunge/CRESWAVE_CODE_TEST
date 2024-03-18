package com.blog_api.blog_api.service;

import com.blog_api.blog_api.dto.LoginResponse;
import com.blog_api.blog_api.dto.SignInDto;
import com.blog_api.blog_api.dto.UserDto;
import com.blog_api.blog_api.entity.User;

import java.util.List;

public interface UserService {

    //  TODO   POST /api/users
    List<User> getAllUsers();

    //  TODO   POST /api/users/register
    User register(User user);

    //  TODO   POST /api/users/login
    LoginResponse login(SignInDto request);

    //  TODO   GET /api/users/profile
    UserDto getProfile(Long userId);

    //  TODO  PUT /api/users/profile
    User editProfile(Long userId, User user);

    //  TODO  DELETE /api/users/profile
    String deleteProfile(Long userId);

}
