package com.security.demo.login.jwtLogin.authenticate;

import com.security.demo.login.userDetail.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(userName);

        if(!userDetails.getPassword().equals(password)){
            throw new RuntimeException("password 틀립니다");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUser(), null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isInstance(UsernamePasswordAuthenticationToken.class);
    }

}
