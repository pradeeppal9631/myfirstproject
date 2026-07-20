package com.project.myfirstproject.controller;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.entity.JournalEntry;
import com.project.myfirstproject.service.Cliententryservice;
import com.project.myfirstproject.service.Userentryservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<?> getAllJournalEntriesofClienEntry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Username = " + authentication.getName());
        ClientEntry client= cliententryservice.findByClientName(authentication.getName());
        List<JournalEntry> all= client.getJournalEntry();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public  ResponseEntity<JournalEntry>  createEntry(@RequestBody JournalEntry myUser ) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            myUser.setDate(LocalDateTime.now());

            userentryservice.saveEntry(myUser, authentication.getName());

            return new ResponseEntity<>(myUser, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        ClientEntry client = cliententryservice.findByClientName(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        for (JournalEntry entry : client.getJournalEntry()) {
            if (entry.getId().equals(myId)) {
                return new ResponseEntity<>(entry, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Journal Entry Not Found", HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("id/{clientName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId  myId, Object clientName){
        userentryservice.deleteById(myId, (String) clientName);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //@PutMapping("id/{myId}")
//        public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry myUser){
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        ClientEntry client = cliententryservice.findByClientName(authentication.getName());
//
//         JournalEntry old = userentryservice.findById(id).orElse(null);
//         if(old!=null){
//
//             old.setTitle(myUser.getTitle()!=null && !myUser.getTitle().equals("")? myUser.getTitle(): old.getContent());
//             old.setContent(myUser.getContent()!=null && myUser.equals(" ") ? myUser.getContent(): old.getContent());
//         }
//
//        userentryservice.saveEntry(old, authentication.getName());
//        return new ResponseEntity<>(old, HttpStatus.OK);

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable("myId") ObjectId myId,
            @RequestBody JournalEntry newEntry) {

        // Logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Find logged-in user
        ClientEntry client = cliententryservice.findByClientName(username);

        if (client == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Find journal by id
        JournalEntry old = userentryservice.findById(myId).orElse(null);

        if (old == null) {
            return new ResponseEntity<>("Journal not found", HttpStatus.NOT_FOUND);
        }

        // Check whether this journal belongs to the logged-in user
        boolean isOwner = client.getJournalEntry()
                .stream()
                .anyMatch(entry -> entry.getId().equals(myId));

        if (!isOwner) {
            return new ResponseEntity<>("You are not authorized to update this journal",
                    HttpStatus.FORBIDDEN);
        }

        // Update title
        if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
            old.setTitle(newEntry.getTitle());
        }

        // Update content
        if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
            old.setContent(newEntry.getContent());
        }

        // Save updated journal
        userentryservice.updateEntry(old);

        return new ResponseEntity<>(old, HttpStatus.OK);
    }

}