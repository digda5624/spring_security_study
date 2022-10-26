package com.security.demo.app.config;

import com.security.demo.app.controller.interceptor.InterceptorForQueryCounter;
import com.security.demo.hibernateInterceptor.HibernateInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
// application.yml 지정
@PropertySource(value = {"classpath:application.yml"})
public class WebConfig implements WebMvcConfigurer {

    private final Environment env;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorForQueryCounter())
                .addPathPatterns("/**");
    }

    @Bean
    public InterceptorForQueryCounter interceptorForQueryCounter(){
        return new InterceptorForQueryCounter(hibernateInterceptor());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource
    ) {
        Map<String, Object> jpaProperties = new HashMap<>();
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        // jpa properties 설정을 java 로 하기
        jpaProperties.put("hibernate.ejb.interceptor", hibernateInterceptor());
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // sql 찍기
        jpaProperties.put("hibernate.format_sql", true);
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.security");
        emf.setJpaPropertyMap(jpaProperties);
        return emf;
    }

    @Bean
    public HibernateInterceptor hibernateInterceptor(){
        return new HibernateInterceptor();
    }

}
