package com.security.demo.aspect.trace.filter;

import com.security.demo.aspect.trace.TraceSupports;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        TraceSupports.syncFilter((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
        TraceSupports.releaseTraceInfos();
    }

}
