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


    @Test
    public void testGetStudentThree() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentThree = (Student) ac.getBean("studentThree");
        System.out.println(studentThree);

    }


    @Test
    public void testGetStudentFour() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentFour = (Student) ac.getBean("studentFour");
        System.out.println(studentFour);

        // Student{id=1004, name='赵六', age=26, sex='女', clazz=Clazz{clazzId=3333, clazzName='最强王者班'}}
        // 这里出现还是最强王者班是因为出现了后面的赋值导致的

    }


    @Test
    public void testGetStudentFive() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentFive = (Student) ac.getBean("studentFive");
        System.out.println(studentFive);

    }


    @Test
    public void testGetStudentSix() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentSix = (Student) ac.getBean("studentSix");
        System.out.println(studentSix);

    }

    @Test
    public void testGetStudentSeven() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentSeven = (Student) ac.getBean("studentSeven");
        System.out.println(studentSeven);
    }


    @Test
    public void testGetStudentEight() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentEight = (Student) ac.getBean("studentEight");
        System.out.println(studentEight);
    }

    @Test
    public void testGetStudentBeanByStudentFactoryBean() {
        // setter进行注入
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student studentNine = (Student) ac.getBean("studentNine");
        System.out.println(studentNine);
    }







}
