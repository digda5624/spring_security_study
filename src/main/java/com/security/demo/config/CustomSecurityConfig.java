package com.security.demo.config;

import com.security.demo.sessionLogin.CustomAuthenticationFilter;
import com.security.demo.sessionLogin.CustomAuthenticationProvider;
import com.security.demo.sessionLogin.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 세션기반 인증 사용 안하므로
        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(customAuthenticationFilter(), LogoutFilter.class)
                .authorizeRequests()
//                .antMatchers("/user").authenticated()
                .anyRequest().permitAll();

        return http.build();
    }

    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(providerManager());
//        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(successHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public ProviderManager providerManager(){
        return new ProviderManager(new CustomAuthenticationProvider());
    }

    public AuthenticationSuccessHandler successHandler(){
        return new CustomLoginSuccessHandler();
    }

}
