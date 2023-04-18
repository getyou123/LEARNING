package org.getyou123;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class calcTest {
    @Test
    public void testAspect(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-anno-aop.xml");
        Calculator calculatorPure = ac.getBean(Calculator.class);
        int add = calculatorPure.add(1, 2);
        System.out.println(add);
    }

    @Test
    public void testAspectDivZero(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-anno-aop.xml");
        Calculator calculatorPure = ac.getBean(Calculator.class);
        int add = 0;
        try {
            add = calculatorPure.div(1, 0);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(add);
    }
}
