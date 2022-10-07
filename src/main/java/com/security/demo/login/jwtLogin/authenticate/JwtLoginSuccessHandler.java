package com.security.demo.login.jwtLogin.authenticate;

import com.security.demo.login.jwtLogin.jwtutils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 들어온 Authentication 기반으로 토큰 설정하기
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        // Authentication 기반으로 토큰생성
        String accessToken = JwtUtils.createAccessToken(authentication);
        response.setHeader("Access-Token", accessToken);
    }
}
