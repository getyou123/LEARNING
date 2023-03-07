package org.getyou123;

import org.getyou123.pojo.HelloSpring;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloSpringTest {
    @Test
    public void test(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloSpring helloSpring = (HelloSpring) ac.getBean("hellospring");
        helloSpring.sayHello();
    }
}
