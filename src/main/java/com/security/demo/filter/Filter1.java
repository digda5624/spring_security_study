package com.security.demo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class Filter1 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /**
         * 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때 호출된다.
         */
        log.info("Filter1 init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /**
         * 요청이 올 때마다 해당 메서드가 호출된다.
         */
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        try {
            log.info("Filter1 호출");
            log.info("REQUEST  [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("Filter1 종료");
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
            log.info("Filter1 종료");
        }
    }

    @Override
    public void destroy() {
        /**
         * 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출 된다.
         */
        log.info("Filter1 destroy");
    }
}
