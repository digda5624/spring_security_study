package com.security.demo.app.trace;

import com.security.demo.app.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TraceV1 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    /**
     * 로그를 시작한다.
     * 로그 메시지를 파라미터로 받아서 시작 로그를 출력한다.
     * 응답 결과로 현재 로그의 상태인 TraceStatus 를 반환한다.
     */
    public TraceStatus begin(User user, String request, String message) {
        TraceId traceId = new TraceId(user, request, 1);
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId, addSpace(START_PREFIX, traceId.getLevel()) ,message);
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
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if(e == null){
            log.info("[{}] {}{} time = {}ms", traceId, addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time = {}ms", traceId, addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
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
