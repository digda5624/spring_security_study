package com.security.demo.app.controller.interceptor;

import com.security.demo.hibernateInterceptor.HibernateInterceptorEX;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 비동기 응답을 할 때에는 AsyncHandlerInterceptor 를 사용해야 preHandle 2번 타는 오류가 없다고 하는데...
@Slf4j
@RequiredArgsConstructor
public class InterceptorForQueryCounter implements HandlerInterceptor {

    private ThreadLocal<Long> timer = new ThreadLocal<>();
    private final HibernateInterceptorEX hibernateInterceptorEX;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        timer.set(System.currentTimeMillis());
        hibernateInterceptorEX.startCounter();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long time = System.currentTimeMillis() - timer.get();
        long queryCount = hibernateInterceptorEX.getQueryCount();
        log.info("[Total Time: {} ms] [Queries: {}] [{}, {}]", time, queryCount, request.getRequestURL(), request.getMethod());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hibernateInterceptorEX.clearCounter();
        timer.remove();
    }
}
