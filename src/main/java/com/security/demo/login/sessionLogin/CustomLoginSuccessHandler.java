package com.security.demo.login.sessionLogin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 인증에 성공시
        log.info("CustomLoginSuccessHandler : 로그인 성공");
        // 성공하여 반환된 Authentication 객체를 context 에 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        PrintWriter writer = response.getWriter();
        // 인증에 성공시 response 로 Login Success 를 반환
        writer.println("Login Success");
    }

}
