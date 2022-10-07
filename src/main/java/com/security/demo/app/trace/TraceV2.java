package com.security.demo.app.trace;

import com.security.demo.app.entity.User;
import com.security.demo.login.userDetail.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// timestamp , url, user정보, method 정보, 시간 + // 쿼리수

@Slf4j
@Component
public class TraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceHolder = new ThreadLocal<>();

    /**
     * 로그를 시작한다.
     * 로그 메시지를 파라미터로 받아서 시작 로그를 출력한다.
     * 응답 결과로 현재 로그의 상태인 TraceStatus 를 반환한다.
     */
    public TraceStatus begin(String request, String message) {
        syncTraceId(request);
        TraceId traceId = traceHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}, {}] {}{}", traceId, traceId.getUser().getName(), addSpace(START_PREFIX, traceId.getLevel()) ,message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    /**
     * 로그를 정상 종료
     */
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * 로그 예외 처리
     */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e){
        Long stopTimeMs = System.currentTimeMillis();

        // 종료시 까지 걸린 시간
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = traceHolder.get();
//        TraceId traceId = status.getTraceId();
        if(e == null){
            log.info("[{}, {}] {}{} time = {}ms", traceId, traceId.getUser().getName(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}, {}] {}{} time = {}ms", traceId, traceId.getUser().getName(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        }
        releaseTraceId();
    }

    private void syncTraceId(String request){
        // spring security 의 경우 Thread Local 로 Security Context 를 관리하며, SecurityContextPersistenceFilter 에서
        // request 종료 시점에 clear 를 하므로 여러 요청에 대해 stateless 하기 때문에 안전하다.
        TraceId traceId = traceHolder.get();
        if(traceId == null){
            SecurityContext context = SecurityContextHolder.getContext();
            MyUserDetails principal = (MyUserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            User user = principal.getUser();
            traceHolder.set(new TraceId(user, request));
        } else {
//            traceHolder.set(traceId.createNextId());
            traceId.levelUp();
        }
    }

    private void releaseTraceId() {
        TraceId traceId = traceHolder.get();
        if (traceId.isFirstLevel()) {
            traceHolder.remove();//destroy
        } else {
//            traceHolder.set(traceId.createPreviousId());
            traceHolder.get().levelDown();
        }
    }

    /**
     * level 에 따라서 prefix 의 위치를 변경 할 수 있다.
     */
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
