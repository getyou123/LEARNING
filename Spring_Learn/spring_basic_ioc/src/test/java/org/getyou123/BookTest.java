package org.getyou123;


import org.getyou123.controller.BookController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-trans-config.xml"})
public class BookTest {
    @Autowired
    private BookController bookController;

    @Test
    public void testBookController() {
        bookController.buyBook(1, 1);
    }

}
