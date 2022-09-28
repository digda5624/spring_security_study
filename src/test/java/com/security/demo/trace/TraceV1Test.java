package com.security.demo.trace;

import com.security.demo.entity.Role;
import com.security.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraceV1Test {

    TraceV1 traceV1 = new TraceV1();

    @Test
    @DisplayName("[sucess] TraceV1_테스트_성공")
    public void TraceV1_테스트_성공(){
        User user = new User(1L, "password","테스트 사용자",Role.ROLE_ADMIN);
        TraceStatus status = traceV1.begin(user, "[GET] /Trace_Test", "로그 작업 확인하기");
        traceV1.end(status);
        traceV1.exception(status, new Exception("예외발생"));

    }

}