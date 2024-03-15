//package com.blog_api.blog_api.service.implementation;
//
//import com.blog_api.blog_api.dto.LoginRequest;
//import com.blog_api.blog_api.entity.User;
//import com.blog_api.blog_api.repository.UserRepository;
//import com.blog_api.blog_api.service.AuthService;
//import jakarta.transaction.Transactional;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class AuthenticationService implements AuthService {
//
//    private final UserRepository repository;
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder) {
//        this.repository = repository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public User signUp(User request) {
//        String email = request.getEmail();
//        Optional<User> existingUser = repository.findByEmail(email);
//        if (existingUser.isPresent()) {
//            throw new DuplicateException(String.format("User with the email address '%s' already exists.", email));
//        }
//
//        String hashedPassword = passwordEncoder.encode(request.password());
//        User user = new User(request.name(), email, hashedPassword);
//        repository.add(user);
//        return user;
//    }
//
//
//    @Override
//    public User login(LoginRequest loginRequest) {
//        return null;
//    }
//}
