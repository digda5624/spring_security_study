package com.security.demo.login.jwtLogin.config;

import com.security.demo.login.jwtLogin.authenticate.JwtAuthenticationProvider;
import com.security.demo.login.jwtLogin.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

//@Configuration
//@EnableWebSecurity
public class JwtSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf().disable()
                // jwt 토큰을 사용하므로 세션 저장소에 Context를 저장할 필요없다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
                .authenticationManager()
                // 일단 현재에서는 Test를 위해서 모든 요청들에 대해 인가 검사를 하지 않게 적용했다.
                .authorizeRequests()
                .anyRequest()
                .permitAll();

        return http.build();
    }

    /**
     * jwtAuthenticationFilter Bean 등록
     */


    /**
     * AuthenticationManager 등록
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider){
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        return new JwtAuthenticationProvider(userDetailsService);
    }

    /**
     * 스프링 시큐리티 프레임워크에서 제공하는 클래스 중 하나로 비밀번호를 암호화하는 데 사용할 수 있는 메서드를 가진 클래스이다.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
