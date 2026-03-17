package com.doctour.doctourbe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoints {

    @GetMapping("/")
    public String hello() {
        return "jo";
    }

    @GetMapping("/secure")
    public String secure(){
        return "this shit secure";
    }

//    @PostMapping("/create")
//    public String create(@RequestParam("username") String username, @RequestParam("password") String password){
//        return userService.create(username, password);
//    }

}
