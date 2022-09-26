# Spring Security 의 인증

인증 - 신원 증명
인가 - 권한 

## SecurityContextHolder
스프링 시큐리티의 심장부이다. SecurityContext를 포함하고 있다.

![image](https://user-images.githubusercontent.com/69373314/192182691-bf0a4d30-53b5-4e71-b8e4-5edab490c6a7.png)

ContextHolder 는 인증된 사람의 세부 정보를 저장하는 곳이다.
내부에 값이 있을 경우 현재 인증된 사용자로 인식하고 사용된다.

🖥 Simple Way to store UserDetails
```java
SecurityContext context = SecurityContextHolder.createEmptyContext(); 
Authentication authentication = new TestingAuthenticationToken("username", "password", "ROLE_USER"); 
context.setAuthentication(authentication);

SecurityContextHolder.setContext(context);
```

SecurityContextHolder는 말그대로 Context를 저장하는 저장소의 개념이다.
ContextHolder는 여러개의 Context를 가지고 Context는 하나의 Authentication 객체를
가지게 된다.

ContextHolder는 Thread-safe 하기 위해 기본전략으로 LocalThread를 사용하게 된다.

![image](https://user-images.githubusercontent.com/69373314/192196059-eaa5075b-0569-434f-8057-8080a737483b.png)

따라서 ContextHolder를 통해서 Authentication을 얻는 방법은 다음과 같아진다.

🖥 Authentication 정보 얻기
```java
// Thread Local을 통해서 getContext를 한다.
SecurityContext context = SecurityContextHolder.getContext();
// 얻어진 context를 통해 인증 정보를 얻는다.
Authentication authentication = context.getAuthentication();
String username = authentication.getName();
Object principal = authentication.getPrincipal();
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
```

## SecurityContext
SecurityContext는 Authentication 객체를 가진다.


## Authentication
Authentication은 스프링 시큐리티에서 두가지로 사용된다.

1. AuthenticationManager 에게 인증을 할 수단으로써의 사용
2. 현재 인증된 user를 대표하는 수단으로써의 사용

즉, 정리하자면 인증을 받기 위한 수단 혹은 현재 인증 유저의 정보를 제공하는 것으로 사용된다.

내부적으로는 다음과 같은 것들이 존재한다.

* principal : 사용자를 식별하는데 사용
* credentials : 대부분 암호를 나타낸다.
* authorities : 권한을 나타내고 주로 role, scope 가 존재

## GrantedAuthority
유저에게 주어진 권한이 그것이다. 주로 roles 나 scopes 를 사용한다.

GrantedAuthority 는 Authentication.getAuthorities()를 통해 얻어진다.

## AuthenticationManager
인증에 대한 부분은 AuthenticationManager를 통해서 처리, 실질적으로는 AuthenticationManager
를 implement한 ProviderManager를 통해 인증된다.

결국 인증에 대한 추상화가 이루어진 부분이 AuthenticationManager이다.

## ProviderManager
주로 사용되는 AuthenticationManager의 구현체이다. 내부적으로 AuthenticationProvider
를 리스트로 가지고 있고, 스프링 시큐리티의 filterChain처럼 대리자 역할을 수행한다.

즉 ProviderManager 는 내부적으로 인증을 AuthenticationProvider 스트림에 
인증을 위임하고 구성된 AuthenticationProvider 중 어느것도 인증할 수 없으면
AuthenticationException 인 ProviderNotFoundException을 throw 하게 된다.

![image](https://user-images.githubusercontent.com/69373314/192215662-f1e61d72-bc34-4f8a-829b-c9f6b4cca6f7.png)

## UserDetails

