package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.dto.LoginResponse;
import com.blog_api.blog_api.dto.SignInDto;
import com.blog_api.blog_api.dto.UserDto;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.entity.User;
import com.blog_api.blog_api.exception.UserException;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.repository.UserRepository;
import com.blog_api.blog_api.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserServiceImplementation(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Failed to retrieve all users: " + e.getMessage());
            throw new UserException("Failed to retrieve all users", e.getMessage());
        }
    }

    @Override
    public User register(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to register user: " + e.getMessage());
            throw new UserException("Failed to register user", e.getMessage());
        }
    }

    @Override
    public LoginResponse login(SignInDto request) {
        String token = "Random token";
        LoginResponse response = LoginResponse.builder().token(token).build();
        return response;
    }

    @Override
    public UserDto getProfile(Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User with the given ID could not be found", "USER_NOT_FOUND"));
            List<Post> userPosts = postRepository.findByAuthorId(userId);
            return UserDto.builder().email(user.getEmail()).posts(userPosts).role(user.getRole()).username(user.getUsername()).build();
        } catch (Exception e) {
            log.error("Failed to get profile for user with ID:" + userId + " " + e.getMessage());
            throw new UserException("Failed to get profile for user with ID " + userId, e.getMessage());
        }
    }

    @Override
    public User editProfile(Long userId, User user) {
        try {
            User userToEdit = userRepository.findById(userId).orElseThrow(() -> new UserException("User with the given ID could not be found", "USER_NOT_FOUND"));
            userToEdit.setEmail(user.getEmail());
            userToEdit.setPassword(user.getPassword());
            userToEdit.setPassword(user.getPassword());
            return userRepository.save(userToEdit);
        } catch (Exception e) {
            log.error("Failed to edit profile for user with ID: " + userId + e.getMessage());
            throw new UserException("Failed to edit profile for user with ID " + userId, e.getMessage());
        }
    }

    @Override
    public String deleteProfile(Long userId) {
        try {
            User userToDelete = userRepository.findById(userId).orElseThrow(() -> new UserException("User with the given ID could not be found", "USER_NOT_FOUND"));
            userRepository.delete(userToDelete);
            return "User deleted successfully";
        } catch (Exception e) {
            log.error("Failed to delete profile for user with ID: " + userId + e.getMessage());
            throw new UserException("Failed to delete profile for user with ID " + userId, e.getMessage());
        }
    }
}
