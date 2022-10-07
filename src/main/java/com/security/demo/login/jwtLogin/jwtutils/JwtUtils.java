package com.security.demo.login.jwtLogin.jwtutils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.security.demo.entity.User;
import com.security.demo.login.userDetail.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public final class JwtUtils {

    private static final String secretKey = "digda's secretKey";
    private static final int jwtExpirationInMs = 1800;

    public static String createAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return accessToken(user);
    }

    private static String accessToken(User user){
        Map<String, Object> payload =
                Map.of("id", user.getId(), "name", user.getName(), "role", user.getRole().toString());

        return JWT.create()
                .withSubject("Digda_Test_token")
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationInMs * 1000L))
                .withPayload(Map.of("userInfo", payload))
                .sign(Algorithm.HMAC512(secretKey));
    }

}
