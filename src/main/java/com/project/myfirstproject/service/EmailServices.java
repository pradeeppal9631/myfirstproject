package com.project.myfirstproject.service;

import com.project.myfirstproject.service.EmailServices ;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    private final JavaMailSender mailSender;

    public EmailServices(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String to, String username) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Welcome to MyFirstProject");
        message.setText(
                "Hello " + username + ",\n\n" +
                        "Welcome to MyFirstProject!\n\n" +
                        "Your account has been successfully created."
        );

        mailSender.send(message);
    }


}
