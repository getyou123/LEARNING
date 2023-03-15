package org.getyou123;

import com.sun.tracing.dtrace.Attributes;
import org.getyou123.pojo.Clazz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringJunitTest {

    @Autowired
    private Clazz clazzTwo;
    @Test
    public void testGetClassTwo() {
        System.out.println(clazzTwo);
    }



}
