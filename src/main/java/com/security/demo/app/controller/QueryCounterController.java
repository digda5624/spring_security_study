package com.security.demo.app.controller;

import com.security.demo.app.entity.User;
import com.security.demo.app.repository.UserRepository;
import com.security.demo.hibernateInterceptor.HibernateInterceptorEX;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QueryCounterController {

    private final HibernateInterceptorEX queryCounter;
    private final UserRepository userRepository;

    /**
     * 쿼리 한번 진행 되므로 1이 되어야 한다.
     *
     * 추가적으로 hibernate 에서의 쿼리 진행과정을 살펴볼 수 있었다.
     * Transaction Begin 을 한 이후로 onPrepareStatement 를 통한 query 문 준바
     */
    @GetMapping("/v1")
    @Transactional
    public Long JPQL_COUNTS(){
        userRepository.someQuery();
        return queryCounter.getQueryCount();
    }

    @GetMapping("/v2")
    @Transactional
    public Long JPQL_FLUSH(){
        userRepository.someQuery();
        log.info("someQuery 실행");
        userRepository.save(new User());
        log.info("save 실행");
        userRepository.findByName("");
        return queryCounter.getQueryCount();
    }

}
