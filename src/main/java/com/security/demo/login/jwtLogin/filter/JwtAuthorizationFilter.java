package com.security.demo.login.jwtLogin.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    /**
     * OncePerRequestFilter 는 request 처리 해준다. 한번만
     * doFilterInternal 을 통해서 사용자 정의를 내리면 된다.
     * 넘어온 filterChain 을 통해서 doFilter 를 진행 해도 되고 아니여도 된다.
     * 파싱 작업을 완료하고 context 에 담아서 내려주게 되면 FilterInterceptor 에서 Authorization 작업을 처리해주게 된다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtHeader = request.getHeader("Authorization");

        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
//
//        try{
//
//        }

    }

}
