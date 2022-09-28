package com.security.demo.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그를 찍을 때 필요한 정보들 집합
 */
@AllArgsConstructor
@Getter
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message;

}
