package com.security.demo.aspect.advice;

import FIS.iLUVit.aspect.trace.Trace;
import FIS.iLUVit.aspect.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TraceAdvice {

    private final Trace trace;

    @Around("FIS.iLUVit.aspect.pointcuts.TracePointcuts.allController() || " +
            "FIS.iLUVit.aspect.pointcuts.TracePointcuts.allRepository() || " +
            "FIS.iLUVit.aspect.pointcuts.TracePointcuts.allService() || " +
            "FIS.iLUVit.aspect.pointcuts.TracePointcuts.traceAnnotation()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;

        try{
            status = trace.begin(joinPoint.getSignature().toString());
            Object result = joinPoint.proceed();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }
}
