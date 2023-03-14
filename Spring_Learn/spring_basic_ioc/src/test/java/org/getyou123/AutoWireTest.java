package org.getyou123;

import org.getyou123.controller.StudentController;
import org.getyou123.dao.StudentDao;
import org.getyou123.service.StudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutoWireTest {


    /**
     * 测试基于xml管理bean
     */
    @Test
    public void testAutoWireByXml(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-autowire.xml");
        StudentController studentController = (StudentController) ac.getBean("StudentController");
        studentController.saveUser();
    }

    @Test
    public void testAutoWireByAnno(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-autowire-anno.xml");
        StudentController studentController = ac.getBean(StudentController.class);
        System.out.println(studentController);
        StudentService studentService = ac.getBean(StudentService.class);
        System.out.println(studentService);
        StudentDao StudentDao = ac.getBean(StudentDao.class);
        System.out.println(StudentDao);


    }
}
