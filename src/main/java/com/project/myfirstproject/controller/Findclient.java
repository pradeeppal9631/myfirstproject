package com.project.myfirstproject.controller;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.service.Cliententryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class Findclient {

    @Autowired
    private Cliententryservice cliententryservice;

    @GetMapping
    public String findclient() {
        return "OK";
    }

    @PostMapping("/ create-client")
    public void createClient(@RequestBody ClientEntry myclient) {
        cliententryservice.saveUser(myclient);
    }
}