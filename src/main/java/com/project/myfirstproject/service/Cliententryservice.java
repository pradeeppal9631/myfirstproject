package com.project.myfirstproject.service;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.entity.JournalEntry;
import com.project.myfirstproject.repository.Clientrentryrepo;
import com.project.myfirstproject.repository.Userentryrepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.project.myfirstproject.kafka.KafkaProducer;

import java.util.List;
import java.util.Optional;


@Component
public class Cliententryservice {

    @Autowired
    private Clientrentryrepo clientrentryrepo;

    //private  static final Logger logger=  LoggerFactory.getLogger(Cliententryservice.class);

    public ClientEntry saveEntry(ClientEntry clientEntry) {

        return clientrentryrepo.save(clientEntry);
    }

     public List<ClientEntry> getAll(){
        return clientrentryrepo.findAll();

     }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaProducer kafkaProducer;

    public ClientEntry saveUser(ClientEntry user) {

        System.out.println("Before Save ID : " + user.getId());
        System.out.println("Before Save Name : " + user.getClientName());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));

        ClientEntry saved = clientrentryrepo.save(user);

        System.out.println("After Save ID : " + saved.getId());
        System.out.println("After Save Name : " + saved.getClientName());


        //kafkaProducer.sendUserMessage("New user registered: " + saved.getClientName());

        kafkaProducer.sendUserMessage(saved);


        return saved;
    }

     public Optional<ClientEntry> findById(ObjectId id){
        return clientrentryrepo.findById(id);
     }

     public void deleteById(ObjectId id){
         clientrentryrepo.deleteById(id);
     }
    @Cacheable(value = "client", key = "#clientName")
    public  ClientEntry findByClientName(String clientName){
        return clientrentryrepo.findByClientName(clientName);
    }


    public void setclientName(Object o) {

    }

    public void deleteById(Object clientName) {

    }

    public void sendExistingUserToKafka(String clientName) {

        ClientEntry user = clientrentryrepo.findByClientName(clientName);

        if (user == null) {
            throw new RuntimeException("User not found: " + clientName);
        }

        kafkaProducer.sendUserMessage(user);

        System.out.println("Existing user sent to Kafka: " + clientName);
    }



}

// controller --> service_--> repository
