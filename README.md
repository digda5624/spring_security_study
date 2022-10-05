# Spring Security Stream

스프링 시큐리티의 제일 중요한 포인트는 인증(Authentication) 과 허가 (Authorization)이다.

* 인증 - '누구인지 증명하는 과정'
* 인가(허가) - '권한이 있는지 확인하는 과정'

모든 HTTP 통신이 거쳐야하는 FilterChain

크게 보면 사용자가 건드려야 할 것

* 인증 필터 => AuthenticationFilter
* 인가 필터 => FilterSecurityInterceptor

AuthenticationFilter 의 AuthenticationManager 에서 인증을 위임
FilterSecurityInterceptor 의 AccessDecisionManager 에서 인가를 위임

## Authentication 과정
만약 AuthenticationFilter 에서 인증이 실패 할 경우 (로그인 실패 할 경우)<br>
AuthenticationFailureHandler 를 호출하여 후처리

만약 성공 할 경우 AuthenticationSuccessHandler 를 통해서 후처리를 담당
후처리 과정에서 Session 로그인을 진행 할 경우 세션 저장소에 SecurityContext를 저장하는 등의 과정을 수행한다. 

즉, 만약 스프링 시큐리티의 Authentication 을 사용한다면 Filter 에서 인증을 처리 후에 결과 return 된다

인증을 하는 과정에는 주로 Username/Password를 사용하고 persistence level 에서 DB조회를 통한
확인 절차를 거치게 되는데, 주로 USerDetails, UserDetailsService 를 implement 해서 사용하게 된다.

![image](https://user-images.githubusercontent.com/69373314/192448016-f262310d-f5e5-4003-9e1e-2e9665d624be.png)


## Authorization 과정
기본적으로 FilterSecurityInterceptor 를 활용한 Authorization 을 사용하게 된다. 

여기서 또한 FilterSecurityInterceptor 는 인가를 AccessDecisionManager 에게 위임하게 되며
AccessDecisionManager는  투표를 기반으로 request에 대한 access 승인 요청을 내리게 된다.

투표를 담당하는 클래스는 AccessDecisionVoter 이고 그에 대한 구현체로 RoleVoter가 기본 값이다.

따라서 User 쪽 Authority 를 구현하게 될 때, ROLE_ prefix 를 고려해봄이 좋다.

인가에 실패하게 될 경우 FilterSecurityInterceptor 는 AccessDeniedException 을 throw
하게 되고, ExceptionTranslationFilter 에서 catch 하게 된다.
인가에 대한 오류 후처리는 ExceptionTranslationFilter 에서 담당하게 된다.

따라서 오류필터 내부에는 후처리기인 AccessDeniedHandler 가 존재하게 된다.

![image](https://user-images.githubusercontent.com/69373314/192447772-3ece95a7-8ca1-40ac-a0e1-554867fb3f6e.png)

## ⭐ 필터들간 사용하는 참조들 목록 ⭐
![image](https://user-images.githubusercontent.com/69373314/192928080-08a9843e-1823-4a45-a04e-d362be981ad4.png)
