package org.getyou123;

import org.getyou123.pojo.Student;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {
    @Test
    public void testSetterDI() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentOne = (Student) ac.getBean("studentOne");
        System.out.println(studentOne);

        // 构造函数进行注入
        Student studentTwo = (Student) ac.getBean("studentTwo");
        System.out.println(studentTwo);

    }

}
