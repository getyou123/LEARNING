package com.getyou123;

import com.getyou123.bean.Person;
import com.getyou123.bean.ImportDemo;
import com.getyou123.bean.Pet;
import com.getyou123.bean.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        // SpringApplication.run(MainApplication.class, args); 正常执行这句话即可


        // 这里返回了IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
        // IOC容器中的组件的list
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }


        System.out.println("==============");
        Pet dog = run.getBean("Dog", Pet.class);
        System.out.println(dog);

        System.out.println("==============");
        ImportDemo bean = run.getBean(ImportDemo.class);
        bean.doSomething();


        System.out.println("==============");
        Car Byd = run.getBean(Car.class);
        System.out.println(Byd);

        System.out.println("==============");
        Person p1 = run.getBean(Person.class);
        System.out.println(p1);

    }
}
