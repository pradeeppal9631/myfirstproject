package com.project.myfirstproject.controller;

import com.project.myfirstproject.service.Redistest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class Rediscontroller {

    @Autowired
    private Redistest redistest;

    @PostMapping("/set")
    public String setValue(
            @RequestParam String key,
            @RequestParam String value) {

        redistest.setValue(key, value);

        return "Value saved in Redis";
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {

        return redistest.getValue(key);
    }
}