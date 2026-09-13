package com.project.myfirstproject.controller;



import com.project.myfirstproject.jwtlearn.Jwtlearning;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class Jwtcontroller {

    private final Jwtlearning jwtlearning;

    public Jwtcontroller(Jwtlearning jwtlearning) {
        this.jwtlearning = jwtlearning;
    }

    @GetMapping("/generate")
    public String generateToken(@RequestParam String username) {

        return jwtlearning.generateToken(username);
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {

        return jwtlearning.validateToken(token);
    }


}