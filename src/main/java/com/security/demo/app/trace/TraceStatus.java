package com.security.demo.app.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그를 찍을 때 필요한 정보들 집합
 *
 * 로그의 상태 정보 관련 클래스 =>
 */
@AllArgsConstructor
@Getter
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
//    private String method;
    private String message;

}
