package com.blog_api.blog_api.controller;

import com.blog_api.blog_api.config.auth.TokenProvider;
import com.blog_api.blog_api.dto.JwtDto;
import com.blog_api.blog_api.dto.SignInDto;
import com.blog_api.blog_api.dto.SignUpDto;
import com.blog_api.blog_api.entity.User;
import com.blog_api.blog_api.exception.AuthException;
import com.blog_api.blog_api.exception.InvalidJwtException;
import com.blog_api.blog_api.service.implementation.AuthenticationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Log4j
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final AuthenticationService authService;

    private final TokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, AuthenticationService authService, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Validated SignUpDto data) {
        try {
            UserDetails details = authService.signUp(data);
            return new ResponseEntity<>(details, HttpStatus.CREATED);
        } catch (InvalidJwtException e) {
            log.error(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email must be unique, please try again");
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong please try again later");
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Validated SignInDto data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
            var authUser = authenticationManager.authenticate(usernamePassword);
            var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
            return ResponseEntity.ok(new JwtDto(accessToken));
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Username and/or password, please try again");
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong please try again later");
        }
    }

}
