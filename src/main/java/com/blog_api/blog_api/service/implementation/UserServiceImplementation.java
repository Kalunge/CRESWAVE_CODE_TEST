package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.dto.LoginRequest;
import com.blog_api.blog_api.dto.LoginResponse;
import com.blog_api.blog_api.dto.UserDto;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.entity.User;
import com.blog_api.blog_api.exception.UserException;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.repository.UserRepository;
import com.blog_api.blog_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserServiceImplementation(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String token = "Random token";
        LoginResponse response = LoginResponse.builder().token(token).build();
        return response;
    }

    @Override
    public UserDto getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User with the given id could not be found", "USER_NOT_FOUND"));
        List<Post> userPosts = postRepository.findByAuthorId(userId);

        UserDto userDto = UserDto.builder().email(user.getEmail()).posts(userPosts).role(user.getRole()).username(user.getUsername()).build();

        return userDto;
    }

    @Override
    public User editProfile(Long userId, User user) {
        User userToEdit = userRepository.findById(userId).orElseThrow(() -> new UserException("User with the given id could not be found", "USER_NOT_FOUND"));
        userToEdit.setEmail(user.getEmail());
        userToEdit.setPassword(user.getPassword());
        userToEdit.setPassword(user.getPassword());

        return userRepository.save(userToEdit);

    }

    @Override
    public String deleteProfile(Long userId) {
        User userToDelete = userRepository.findById(userId).orElseThrow(() -> new UserException("User with the given id could not be found", "USER_NOT_FOUND"));
        userRepository.delete(userToDelete);
        return "User deleted Successfully";
    }
}
