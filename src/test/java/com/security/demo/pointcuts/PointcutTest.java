package com.security.demo.pointcuts;

import com.security.demo.controller.GlobalController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * pointcuts 테스트 하기 따로 @SpringBootTest 없이 AspectJExpression 사용
 */
public class PointcutTest {

    /**
     * pointcut 서순 접근제어자(?) 반환타입 선언타입?메서드이름(파라미터) 예외?)
     */
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();


    @Test
    @DisplayName("[success] ")
    public void pointcutBasicTest(){
        // given
        pointcut.setExpression("execution(String com.security.demo.controller.GlobalController.pass(..))");
        assertThat(pointcut.matches(GlobalController.class)).isTrue();

        pointcut.setExpression("execution(com.security.demo.controller.**.pass(..))");
        assertThat(pointcut.matches(GlobalController.class)).isTrue();
        // when

        // then
    }

}
