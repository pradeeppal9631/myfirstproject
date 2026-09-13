package com.project.myfirstproject.controller;

import com.project.myfirstproject.jwtlearn.Jwtlearning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final Jwtlearning jwtlearning;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            Jwtlearning jwtlearning) {

        this.authenticationManager = authenticationManager;
        this.jwtlearning = jwtlearning;
    }

    @PostMapping("/login")
    public  String login(@RequestBody Jwtloginrequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return jwtlearning.generateToken(
                request.getUsername()
        );
    }

    @PostMapping("/logout")
    public String logout(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "Invalid Authorization header";
        }

        String token = authHeader.substring(7);

        Date expiration = jwtlearning.extractExpiration(token);

        long ttl = expiration.getTime() - System.currentTimeMillis();

        if (ttl > 0) {

            redisTemplate.opsForValue().set(
                    "blacklist:" + token,
                    true,
                    ttl,
                    TimeUnit.MILLISECONDS
            );

            return "Logout successful";
        }

        return "Token already expired";
    }

}

