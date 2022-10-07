package com.security.demo.login.jwtLogin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.app.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 필터를 만드는 내용 기본적으로 UsernamePasswordAuthenticationFilter 사용하자
 * 우리가 구현할 로그인은 user - password 기반이기 때문이다.
 *
 * 토큰 만들어 주는 로직 사용하기
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 인증을 위한 객체를 만든다.
        // 전송이 오면 AuthenticationFilter로 먼저 요청이 오게 되고, 아이디와 비밀번호를 기반으로 Token 을 발급해야한다.
        // 현재 principal 을 loginId 로 놓고 유효성을 검사하게 된다.
        log.info("JwtAuthenticationFilter {} {}", request.getMethod(), request.getRequestURL());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final User user = objectMapper.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
            return getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
