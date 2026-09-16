package com.project.myfirstproject.kafka;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.myfirstproject.UserRegistrationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.project.myfirstproject.service.EmailServices;


@Service
public class KfkaConsumer {



    private final EmailServices emailServices;
    private final ObjectMapper objectMapper;
    public KfkaConsumer(EmailServices emailServices,ObjectMapper objectMapper) {
        this.emailServices = emailServices;
        this.objectMapper=objectMapper;
    }
    @KafkaListener(topics = "user-topic", groupId = "email-group")
    public void consumeUserMessage(String message) {

        //System.out.println("Kafka Consumer received: " + message);

        System.out.println(
                "CONSUMER INSTANCE: "
                        + System.getProperty("server.port")
                        + " | MESSAGE: "
                        + message
        );

        try {

            System.out.println("Kafka Consumer received: " + message);

            UserRegistrationEvent event = objectMapper.readValue(message, UserRegistrationEvent.class);

            System.out.println("User Name: " + event.getClientName());

            System.out.println("User Email: " + event.getEmail());


            emailServices.sendWelcomeEmail(event.getEmail(),
                    "Welcome " + event.getClientName()
                            + "!\n\nYour registration was successful."
            );

            System.out.println("Welcome email sent successfully!");

        } catch (JsonProcessingException e) {

            throw new RuntimeException("Failed to read Kafka message", e);
        }
    }

}
