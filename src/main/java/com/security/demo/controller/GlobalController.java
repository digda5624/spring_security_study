package com.security.demo.controller;

import com.security.demo.entity.Role;
import com.security.demo.entity.User;
import com.security.demo.login.userDetail.MyUserDetails;
import com.security.demo.repository.UserRepository;
import com.security.demo.trace.TraceStatus;
import com.security.demo.trace.TraceV1;
import com.security.demo.trace.TraceV2;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;

@RestController
@Slf4j
@AllArgsConstructor
public class GlobalController {

    UserRepository userRepository;
    TraceV2 traceV2;

    @PostMapping("/join")
    @Transactional
    public User join(){
        User user = new User(1L, "1234", "현승구", Role.ROLE_ADMIN);
        userRepository.save(user);
        return user;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        log.info("session {}", session.getId());
        return "login.html";
    }

    @GetMapping("/user")
    public String user(HttpServletRequest request){
        log.info("user Controller 수행");
        HttpSession session = request.getSession(false);
        log.info("session {}", session.getId());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info(context.getAuthentication().toString());
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "login";
    }

    @GetMapping("/pass")
    public String pass(HttpServletRequest request) throws InterruptedException {
        TraceStatus status = traceV2.begin(request.getSession().getId().substring(1,3), "pass 컨트롤러");
        Thread.sleep((int) (Math.random() * 1000));
        TraceStatus status2 = traceV2.begin(request.getSession().getId().substring(1,3), "pass 컨트롤러 2");
        Thread.sleep((int) (Math.random() * 1000));
        TraceStatus status3 = traceV2.begin(request.getSession().getId().substring(1,3), "pass 컨트롤러 3");
        Thread.sleep((int) (Math.random() * 1000));
        traceV2.end(status3);
        Thread.sleep((int) (Math.random() * 1000));
        traceV2.end(status2);
        Thread.sleep((int) (Math.random() * 1000));
        traceV2.end(status);
        return "pass";
    }

}
