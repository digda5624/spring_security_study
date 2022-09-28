package com.security.demo.login.jwtLogin.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 인증 필터를 만드는 내용 기본적으로 UsernamePasswordAuthenticationFilter 사용하자
 * 우리가 구현할 로그인은 user - password 기반이기 때문이다.
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        super.setAuthenticationManager(authenticationManager);
    }
}
