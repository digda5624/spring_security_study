package com.security.demo.jwt;

import com.security.demo.entity.Role;
import com.security.demo.entity.User;
import com.security.demo.login.jwtLogin.jwtutils.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtUtilsTest {

    @Test
    @DisplayName("[success] JWT 토큰 발급 테스트")
    public void createToken(){
        // given
        User user = new User(1L, "1234", "현승구", Role.ROLE_ADMIN);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null);

        // then
        System.out.println("userAccessToken : " + JwtUtils.createAccessToken(authenticationToken));
    }

}
