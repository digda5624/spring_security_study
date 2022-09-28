package com.security.demo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@AllArgsConstructor
public class GlobalController {

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        return "login.html";
    }

    @GetMapping("/user")
    public String user(HttpServletRequest request){
        log.info("user Controller 수행");
        HttpSession session = request.getSession();
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info(context.getAuthentication().toString());
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "login";
    }

}
