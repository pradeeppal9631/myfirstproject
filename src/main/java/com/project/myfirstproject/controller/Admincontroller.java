package com.project.myfirstproject.controller;


import ch.qos.logback.core.net.server.Client;
import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.service.Cliententryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.logging.log4j.util.LambdaUtil.getAll;

@RestController
@RequestMapping("/admin")
public class Admincontroller {

    @Autowired
    private Cliententryservice cliententryservice;

    @GetMapping("/all-clients")
    public ResponseEntity<?> getAllClient(){
       List<ClientEntry>all= cliententryservice.getAll();

       if(all!=null && !all.isEmpty()){
           return new ResponseEntity<>(all, HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
