package com.security.demo.login.sessionLogin.config;

import com.security.demo.login.sessionLogin.CustomAuthenticationFilter;
import com.security.demo.login.sessionLogin.CustomAuthenticationProvider;
import com.security.demo.login.sessionLogin.CustomLoginSuccessHandler;
import com.security.demo.login.userDetail.UserDetailsServiceImpl;
import com.security.demo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * spring autoConfig 방지를 위한 어노테이션 @EnableWebSecurity
 */
@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf().disable()
                // 세션기반 인증을 진행하지만 Html Form 을 사용하지 않으므로 formLogin을 disable 한다.
                .formLogin().disable()
                // 로그인 필터를 구현
                .addFilterBefore(customAuthenticationFilter(authenticationManager), LogoutFilter.class)
                .authenticationManager(authenticationManager)
                // 현재는 모든 요청들에 대하여 인가 확인을 하지 않는다.
                .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }

    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
//        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(new CustomLoginSuccessHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    @Primary
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        return new CustomAuthenticationProvider(userDetailsService);
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider){
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    @Primary
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
