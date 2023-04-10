package com.getyou123.configuration;


import com.getyou123.bean.ImportDemo;
import com.getyou123.bean.Pet;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration  // 这是一个配置类，相当于一个xml文件
@Import(ImportDemo.class) // 实现往ioc中引入一个bean
@EnableConfigurationProperties
public class UserConfiguration {
    @Bean // 向IOC容器中增加组件，方法名字作为id,
    public Pet Dog() {
        return new Pet("dog jerry", 12.0);
    }


}
