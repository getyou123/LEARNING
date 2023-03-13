package org.getyou123;

import org.getyou123.controller.StudentController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutoWireTest {
    @Test
    public void testAutoWire(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-autowire.xml");
        StudentController studentController = (StudentController) ac.getBean("StudentController");
        studentController.saveUser();
    }
}
