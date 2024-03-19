package com.blog_api.blog_api.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.blog_api.blog_api.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenProvider {

    @Value("${security.jwt.token.secret-key:defaultSecretKey}")
    // not safe but for test purposes we have a default secret key
    private String jwtSecretKey;

    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey);
            return JWT.create().withSubject(user.getUsername()).withClaim("username", user.getUsername()).withExpiresAt(genAccessExpirationDate()).sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("ERROR, while generating token", e);
        }
    }


    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("error while validating Token", e);
        }
    }

    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
