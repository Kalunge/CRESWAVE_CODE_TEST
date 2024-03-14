package com.blog_api.blog_api.controller;

import com.blog_api.blog_api.dto.LoginRequest;
import com.blog_api.blog_api.dto.LoginResponse;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.entity.User;
import com.blog_api.blog_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    //  TODO   POST /api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  TODO   POST /api/users/register
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            User userCreate = userService.register(user);
            return new ResponseEntity<>(userCreate, HttpStatus.CREATED);
        } catch (Exception e) {
//            TODO log error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
//  TODO   POST /api/users/login

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        try {
            LoginResponse response = userService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // TODO log error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//  TODO   GET /api/users/profile

    @GetMapping("/{id}")
    public ResponseEntity<User> getProfile(@PathVariable("id") Long userId) {
        try {
            User user = userService.getProfile(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            // TODO log error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//  TODO  PUT /api/users/profile

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable("id") Long userId, @RequestBody User user) {
        try {
            User editedUser = userService.editProfile(userId, user);
            return new ResponseEntity<>(editedUser, HttpStatus.OK);
        } catch (Exception e) {
            // TODO log error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  TODO  DELETE /api/users/profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable("id") long userId) {
        userService.deleteProfile(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
