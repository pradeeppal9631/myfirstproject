package com.project.myfirstproject.controller;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.entity.JournalEntry;
import com.project.myfirstproject.service.Cliententryservice;
import com.project.myfirstproject.service.Userentryservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class UserControllercopy {

    @Autowired
    private Userentryservice userentryservice;

    @Autowired
    private Cliententryservice cliententryservice;

    @GetMapping("{clientName}")
    public ResponseEntity<?> getAllJournalEntriesofClienEntry(@PathVariable String clientName){
        ClientEntry client= cliententryservice.findByClientName(clientName);
        List<JournalEntry> all= client.getJournalEntry();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping ("{clientName}")
    public  ResponseEntity<JournalEntry>  createEntry(@RequestBody JournalEntry myUser, @PathVariable String clientName ) {

        try {


            myUser.setDate(LocalDateTime.now());

            userentryservice.saveEntry(myUser, clientName);

            return new ResponseEntity<>(myUser, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
       return userentryservice.findById(myId).orElse(null);
    }


    @DeleteMapping("id/{clientName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId  myId, Object clientName){
        userentryservice.deleteById(myId, (String) clientName);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
        public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry myUser, String clientName){
         JournalEntry old = userentryservice.findById(id).orElse(null);
         if(old!=null){

             old.setTitle(myUser.getTitle()!=null && !myUser.getTitle().equals("")? myUser.getTitle(): old.getContent());
             old.setContent(myUser.getContent()!=null && myUser.equals(" ") ? myUser.getContent(): old.getContent());
         }

        userentryservice.saveEntry(old, clientName);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }



}