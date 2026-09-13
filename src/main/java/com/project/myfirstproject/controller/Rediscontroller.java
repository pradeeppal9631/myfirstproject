package com.project.myfirstproject.controller;

//import com.project.myfirstproject.service.RedisServices;
import com.project.myfirstproject.service.Redistest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class Rediscontroller {

    @Autowired
    private Redistest redistest;
    //private RedisServices redisServices;

    @PostMapping("/save")
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

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {

        redistest.deleteData(key);

        return "Data deleted successfully";
    }
    @GetMapping("/test")
    public String testRedis() {
        return redistest.testRedis();
    }
}