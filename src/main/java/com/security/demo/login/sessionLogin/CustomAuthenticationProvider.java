package com.security.demo.login.sessionLogin;

import com.security.demo.app.entity.User;
import com.security.demo.login.userDetail.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * AuthenticationProvider 생성 => UsernamePasswordAuthentication token 이면 이 로직으로 인증 검사를 진행
 */
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    /**
     * 이곳에서 인증을 진행하게 된다.
     * ProviderManager 가 AuthenticationProvider 를 호출하게 된다.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // getName 을 해도 된다. 현재는 name 으로 인증 과정을 진행하므로 (String)으로 다운캐스팅을 진행한다.
        String userName = (String) token.getPrincipal();
        // token 에 저장했던 비밀번호를 가져온다.
        String password = (String) token.getCredentials();

        // userDetailsService 에서 DB 에서 회원정보를 가져온후 user 정보를 추출한다.
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(userName);
        User user = userDetails.getUser();

        // user의 password 와 request 의 password 가 맞는지 확인하는 절차를 가진다.
        if(!user.getPassword().equals(password)){
            throw new RuntimeException("user Password 가 일치하지 않습니다.");
        }

        // 비밀번호 기밀 유지를 위해 credentials 에 비밀번호를 없앤다.
        return new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
