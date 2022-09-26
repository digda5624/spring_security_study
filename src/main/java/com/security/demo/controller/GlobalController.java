package com.security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GlobalController {

    @GetMapping("/login")
    public String login(){
//        log.info();
        return "login";
    }

    @GetMapping("/user")
    public String user(){
        log.info("user Controller 수행");
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "login";
    }

}
