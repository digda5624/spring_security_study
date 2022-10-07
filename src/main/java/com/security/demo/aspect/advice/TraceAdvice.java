package com.security.demo.aspect.advice;

import com.security.demo.entity.Role;
import com.security.demo.entity.User;
import com.security.demo.login.userDetail.MyUserDetails;
import com.security.demo.trace.Trace;
import com.security.demo.trace.TraceStatus;
import com.security.demo.trace.TraceV2;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TraceAdvice {

    private final TraceV2 tracer;

    @Around("com.security.demo.aspect.pointcut.Pointcuts.allController()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;

        try{
            User user = new User(1L, null, "현승구", Role.ROLE_ADMIN);
            MyUserDetails userDetails = new MyUserDetails(user);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            status = tracer.begin("request", joinPoint.getSignature().toShortString());
            Object result = joinPoint.proceed();
            tracer.end(status);
            return result;
        } catch (Exception e) {
            tracer.exception(status, e);
            throw e;
        }
    }

}
