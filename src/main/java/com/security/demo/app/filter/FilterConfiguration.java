package com.security.demo.app.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

//@Configuration
public class FilterConfiguration {

    /**
     * spring boot 에서 필터를 등록하기 위해서는 FilterRegistrationBean 을 사용해서 등록해야 한다.
     */
    @Bean
    public FilterRegistrationBean filter1(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new Filter1());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filter2(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new Filter2());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filter3(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new Filter3());
        filterRegistrationBean.setOrder(3);
        /**
         * /login 으로 요청이 들어오지 않는 다면 호출되지 않는다.
         */
        filterRegistrationBean.addUrlPatterns("/login");
        return filterRegistrationBean;
    }
}
