package com.security.demo.app.aspect.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 따로 포인트컷 지정 필요없음
 */
public class Pointcuts {

    /**
     * pointcut 서순 접근제어자(?) 반환타입 선언타입?메서드이름(파라미터) 예외?)
     */
    @Pointcut("execution(* com.security.demo.controller..*.pass(..))")
    public void allController(){}

}
