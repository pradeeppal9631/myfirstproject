package com.project.myfirstproject.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.myfirstproject.UserRegistrationEvent;
import com.project.myfirstproject.entity.ClientEntry;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    //@KafkaListener(topics = "user-topic", groupId = "email-group")

    public void sendUserMessage(ClientEntry user) {

        try {

           UserRegistrationEvent event = new UserRegistrationEvent(user.getClientName(), user.getEmail());

            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("user-topic", message);

            System.out.println("Kafka message sent: " + message);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Failed to convert user to JSON", e
            );
        }
    }
}