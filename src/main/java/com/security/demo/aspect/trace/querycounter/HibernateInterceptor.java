package com.security.demo.aspect.trace.querycounter;

import FIS.iLUVit.aspect.trace.Trace;
import lombok.RequiredArgsConstructor;
import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

/**
 * 쿼리 수 측정을 위한 hibernateInterceptor 커스터 마이징
 */
@RequiredArgsConstructor
@Component
public class HibernateInterceptor extends EmptyInterceptor {

    private final Trace trace;

    /**
     *  JPA 의 onPrepareStatement 직전에 수행하게 되는 메서드
     */
    @Override
    public String onPrepareStatement(String sql) {
        Long queryCnt = trace.getQueryCountHolder().get();
        trace.getQueryCountHolder().set(queryCnt + 1);
        return sql;
    }
}
