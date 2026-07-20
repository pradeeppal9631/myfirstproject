package com.project.myfirstproject.controller;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.entity.JournalEntry;
import com.project.myfirstproject.repository.Clientrentryrepo;
import com.project.myfirstproject.service.Cliententryservice;
import com.project.myfirstproject.service.Userentryservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientControllercopy {

    @Autowired
    private Cliententryservice cliententryservice;

    @GetMapping
    public List<ClientEntry> getAllClientEntry(){

        return cliententryservice.getAll();
    }
    @Autowired
    private Clientrentryrepo clientrentryrepo;




     @PostMapping

     public void createClientEntry(@RequestBody ClientEntry myclient){

         cliententryservice.saveUser(myclient);
     }

     //@PutMapping("/{clientName}")

//   // public ResponseEntity<?> updateClientEntry(@RequestBody ClientEntry myclient,@PathVariable String clientName){
//       ClientEntry clientIndb= cliententryservice.findByClientName((clientName));
//        if(clientIndb!=null){
//            clientIndb.setClientName(myclient.getClientName());
//            clientIndb.setPassword(myclient.getPassword());
//            cliententryservice.saveEntry(clientIndb);
//        }
//
//          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
@PutMapping
public ResponseEntity<?> updateUser(@RequestBody ClientEntry myclient) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    System.out.println("Authentication = " + authentication);
    System.out.println("Username = " + authentication.getName());

    ClientEntry clientIndb = cliententryservice.findByClientName(authentication.getName());

    System.out.println("User from DB = " + clientIndb);

    if (clientIndb != null) {
        clientIndb.setClientName(myclient.getClientName());
        clientIndb.setPassword(myclient.getPassword());

        cliententryservice.saveUser(clientIndb);

        return ResponseEntity.ok("Updated");
    }

    return ResponseEntity.notFound().build();
}


}