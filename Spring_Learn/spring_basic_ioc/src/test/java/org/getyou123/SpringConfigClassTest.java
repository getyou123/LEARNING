package org.getyou123;

import org.getyou123.congfig.ASpringConfig;
import org.getyou123.pojo.Student;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class SpringConfigClassTest {
    @Test
    public void testGetStudentEight() {
        // setter进行注入
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ASpringConfig.class);
        Student studentEight = (Student) ac.getBean("studentEight");
        System.out.println(studentEight);
    }
}
