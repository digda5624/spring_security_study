package com.security.demo;

import com.security.demo.login.jwtLogin.config.JwtSecurityConfig;
import com.security.demo.login.sessionLogin.CustomAuthenticationFilter;
import com.security.demo.login.sessionLogin.config.CustomSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.Map;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

/**
 * 따로 spring security 설정을 하지 않으면 AutoConfig 로 등록된다.
 * 1. 모든 요청에 대해 인증을 요구
 * 2. 인증 방식은 폼 로그인 방식과 httpBasic 로그인 방식을 제공
 * 3. 기본 로그인 페이지 제공
 * 4. 기본 계정 제공 : user / password (Application 로딩 시점에 console 에서 확인)
 */
@SpringBootApplication(scanBasePackages = {"com.security.demo.app", "com.security.demo.login.userDetail"})
@Import(JwtSecurityConfig.class)
//@Import(CustomSecurityConfig.class)
public class DemoApplication {

	/**
	 * 스프링 부트 자동 구성
	 * Spring Security의 기본 구성을 활성화 하여 서블릿 Filter springSecurityFilterChain 을 생성 한다.
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
		Map<String, Filter> bean = run.getBeansOfType(Filter.class);
		System.out.println("ddd");
	}

}
