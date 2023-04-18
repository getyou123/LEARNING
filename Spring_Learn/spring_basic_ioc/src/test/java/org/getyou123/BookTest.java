package org.getyou123;


import org.getyou123.controller.BookController;
import org.getyou123.controller.StudentController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-trans-config.xml"})
public class BookTest {
    @Autowired
    private BookController bookController;

    /*
    这个测试是注定不过的
     */
    @Test
    public void testBookController() {
        try {
            System.out.println(bookController);
            bookController.buyBook(1, 1);
        } catch (Exception e) {
            // 这里按照异常的方式
            System.out.println(e);
        }
    }

}
