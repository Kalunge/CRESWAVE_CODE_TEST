package com.blog_api.blog_api.service.implementation;


import com.blog_api.blog_api.dto.SignUpDto;
import com.blog_api.blog_api.entity.User;
import com.blog_api.blog_api.exception.InvalidJwtException;
import com.blog_api.blog_api.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Log4j
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userRepository.findByUsername(username);
        log.info(user);
        return user;
    }

    public User signUp(SignUpDto data) throws InvalidJwtException {
        if (userRepository.findByUsername(data.getUsername()) != null) {
            throw new InvalidJwtException("Username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getUsername(), encryptedPassword, data.getRole(), data.getEmail());
        userRepository.save(newUser);

        return User.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .username(newUser.getUsername())
                .role(newUser.getRole())
                .build();
    }

}
