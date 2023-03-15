package org.getyou123.congfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.getyou123.pojo.Clazz;
import org.getyou123.pojo.HelloSpring;
import org.getyou123.pojo.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource("classpath:applicationContext.xml") // 引入其他配置文件
public class ASpringConfig {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.user}")
    private String jdbcUser;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean
    public HelloSpring helloSpring() {
        return new HelloSpring();
    }

    @Bean
    public Student studentOne() {
        Student student = new Student();
        student.setId(1001);
        student.setName("张三");
        student.setAge(23);
        student.setSex("男");
        return student;
    }

    @Bean
    public Student studentTwo() {
        return new Student(1002, "李四", 33, "女");
    }

    @Bean
    public Clazz clazzOne() {
        Clazz clazz = new Clazz();
        clazz.setClazzId(1111);
        clazz.setClazzName("财源滚滚班");
        return clazz;
    }

    @Bean
    public Student studentFour() {
        Student student = new Student();
        student.setId(1004);
        student.setName("赵六");
        student.setAge(26);
        student.setSex("女");
        student.setClazz(clazzOne());
        return student;
    }

    @Bean
    public Student studentThree() {
        Student student = new Student(1002, "李四", 33, "女");
        student.setClazz(clazzOne());
        student.getClazz().setClazzId(3333);
        student.getClazz().setClazzName("最强王者班");
        return student;
    }

    @Bean
    public Student studentFive() {
        Student student = new Student();
        student.setId(1004);
        student.setName("赵六");
        student.setAge(26);
        student.setSex("女");
        Clazz clazz = new Clazz();
        clazz.setClazzId(2222);
        clazz.setClazzName("远大前程班");
        student.setClazz(clazz);
        return student;
    }

    @Bean
    public Student studentSix() {
        Student student = new Student();
        student.setId(1004);
        student.setName("赵六");
        student.setAge(26);
        student.setSex("女");
        student.setClazz(clazzOne());
        String[] hobbies = {"抽烟", "喝酒", "烫头"};
        return student;
    }


    @Bean
    public DruidDataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUsername(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

}


