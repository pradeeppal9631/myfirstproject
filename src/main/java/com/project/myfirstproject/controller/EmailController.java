package com.project.myfirstproject.controller;


import com.project.myfirstproject.service.EmailServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailServices emailService;

    public EmailController(EmailServices emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/test")
    public String testEmail(
            @RequestParam String email) {

        emailService.sendWelcomeEmail(
                email,
                "pradeepal"
        );

        return "Email sent successfully";
    }
}



