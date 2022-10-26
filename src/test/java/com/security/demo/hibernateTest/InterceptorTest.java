package com.security.demo.hibernateTest;

import com.security.demo.app.controller.interceptor.InterceptorForQueryCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InterceptorTest {

    @Autowired
    InterceptorForQueryCounter queryCounter;



}
