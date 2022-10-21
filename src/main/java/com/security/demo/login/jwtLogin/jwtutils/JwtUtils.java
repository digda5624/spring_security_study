package com.security.demo.login.jwtLogin.jwtutils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.security.demo.app.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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
        Map<String, Object> payload = createClaims(user);

        return JWT.create()
                .withSubject("Digda_Test_token")
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationInMs * 1000L))
                .withPayload(Map.of("userInfo", payload))
                .sign(Algorithm.HMAC512(secretKey));
    }

    private static Map<String, Object> createClaims(User user){
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("role", user.getRole());

        return claims;
    }

    public void validateToken(String token) {
//        JWT.decode(token)

    }

}
