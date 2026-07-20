package com.project.myfirstproject.service;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.entity.JournalEntry;
import com.project.myfirstproject.repository.Clientrentryrepo;
import com.project.myfirstproject.repository.Userentryrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class Cliententryservice {

    @Autowired
    private Clientrentryrepo clientrentryrepo;

    public ClientEntry saveEntry(ClientEntry clientEntry) {

        return clientrentryrepo.save(clientEntry);
    }

     public List<ClientEntry> getAll(){
        return clientrentryrepo.findAll();

     }
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientEntry saveUser(ClientEntry user) {

        System.out.println("Before Save ID : " + user.getId());
        System.out.println("Before Save Name : " + user.getClientName());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));

        ClientEntry saved = clientrentryrepo.save(user);

        System.out.println("After Save ID : " + saved.getId());
        System.out.println("After Save Name : " + saved.getClientName());

        return saved;
    }

     public Optional<ClientEntry> findById(ObjectId id){
        return clientrentryrepo.findById(id);
     }

     public void deleteById(ObjectId id){
         clientrentryrepo.deleteById(id);
     }

    public  ClientEntry findByClientName(String clientName){
        return clientrentryrepo.findByClientName(clientName);
    }


    public void setclientName(Object o) {

    }

    public void deleteById(Object clientName) {

    }



}

// controller --> service_--> repository
