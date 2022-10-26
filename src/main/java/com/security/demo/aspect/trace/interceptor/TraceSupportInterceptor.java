package com.security.demo.aspect.trace.interceptor;

import com.security.demo.aspect.trace.RequestInfo;
import com.security.demo.login.userDetail.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.UUID.randomUUID;

@Component
@Slf4j
public class TraceSupportInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<String> UUID = new ThreadLocal<>();
    // userId 를 홀딩 security 에서 진행
    private static final ThreadLocal<Long> userHolder = new ThreadLocal<>();
    // request 정보 담기 위해 사용 별도의 interceptor 활용 or security 에서... interceptor 로 따로 빼는 것이 좋을 듯...
    private static final ThreadLocal<RequestInfo> requestHolder = new ThreadLocal<>();
    // query 수 측정을 위한 카운터
    private static final ThreadLocal<Long> queryCountHolder = new ThreadLocal<>();

    /**
     * trace 에 대해 세팅하기
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) throws Exception {
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        String URI = request.getRequestURI();

        RequestInfo requestInfo = new RequestInfo(URI, httpMethod);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if(principal instanceof MyUserDetails){
            Long userId = ((MyUserDetails) principal).getUser().getId();
            userHolder.set(userId);
        } else {
            userHolder.set(-1L);
        }

        queryCountHolder.set(0L);
        requestHolder.set(requestInfo);
        UUID.set(randomUUID().toString().substring(0, 17));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.info("[UUID: {}, user: {}, request: {} method: {}] time = {}ms, queries = {}",
                currentUUID(),
                currentUserId(),
                currentURI(),
                currentHttpMethod(),
                null,
                currentQueryCnt()
        );
        releaseTraceInfos();
    }

    private void releaseTraceInfos(){
        userHolder.remove();
        requestHolder.remove();
        queryCountHolder.remove();
        UUID.remove();
    }

    public static Long currentUserId(){
        return userHolder.get();
    }

    public static String currentURI(){
        return requestHolder.get().getURI();
    }

    public static HttpMethod currentHttpMethod(){
        return requestHolder.get().getMethod();
    }

    public static String currentUUID(){
        return UUID.get();
    }

    public static Long currentQueryCnt(){
        return queryCountHolder.get();
    }

    public static void countQuery(){
        queryCountHolder.set(queryCountHolder.get() + 1);
    }
}
