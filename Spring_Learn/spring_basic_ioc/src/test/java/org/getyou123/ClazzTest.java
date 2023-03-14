package org.getyou123;

import org.getyou123.pojo.Clazz;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClazzTest {
    @Test
    public void testGetClassTwo() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Clazz clazzTwo = (Clazz) ac.getBean("clazzTwo");
        System.out.println(clazzTwo);
    }
}
