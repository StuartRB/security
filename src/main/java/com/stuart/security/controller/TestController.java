package com.stuart.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String everyone(){
        return "Hi everyone";
    }

    @GetMapping("/user")
    public String user(){
        return "Hi users";
    }

//    @Secured(value = "ADMIN")
    @GetMapping("/admin")
    public String admin(){
        return "Hi admins";
    }
}
