package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.repository.UserRepository;
import com.blog_api.blog_api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final JwtTokenProvider jwtTokenProvider;
private final AuthenticationManager authenticationManager;


}
