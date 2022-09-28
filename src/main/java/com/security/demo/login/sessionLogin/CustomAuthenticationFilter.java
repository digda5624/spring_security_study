package com.security.demo.login.sessionLogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 인증을 위한 객체를 만든다.
        // 전송이 오면 AuthenticationFilter로 먼저 요청이 오게 되고, 아이디와 비밀번호를 기반으로 Token 을 발급해야한다.
        // 현재 principal 을 loginId 로 놓고 유효성을 검사하게 된다.
        log.info("CustomAuthenticationFilter {} {}", request.getMethod(), request.getRequestURL());
        UsernamePasswordAuthenticationToken authRequest;
        try {
            final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            authRequest = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setDetails(request, authRequest);
        // ProviderManager 에게 authenticate 를 위임함
        return getAuthenticationManager().authenticate(authRequest);
    }

}
