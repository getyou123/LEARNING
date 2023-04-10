### springboot中就一个配置文件 [application.properties](src%2Fmain%2Fresources%2Fapplication.properties)

- tomcat
- 上传下载的默认文件大下值

### spring的包结构

- 主程序所在的包及其下面的包都是默认扫描的
- 或者指定@ComponentScan() 他是 SpringBootApplication这个注解的一部分内容，指定了去哪些包中获取相应的Bean

### 如何给IOC容器中增加组件

- 之前是写xml文件，里面写beans标签下写不同的bean
- 现在是构造一个配置类，使用@Configration标签，然后使用@Bean注解
- @Configration 标签就相当于一个配置文件
- 组件的id是方法名字，或者使用@Bean("Tom")来指定id
- 组件的类型就是组件的类型
- 组件非单例如何实现呢

### import标签

-

作用到普通的类，实现普通的Bean放到IOC中，![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304041724927.png)

- 编写一个普通的类
- 在配置类上Import这个普通类，这就是放在了相应的IOC容器中了
- 然后从容器中获取Bean，调用方法

### 加载Xml文件，原始配置文件的引入

- @ImportResource("classpath:beans.xml")

### @Condition注解 条件装配

- @Conditional注解可以用于在满足某些条件时才进行Bean的创建
- @ConditionalOnMissingBean(name = "tom") tom不在才进行装配
- Spring Boot 包含多个 @Conditional 注释，可以在@Configuration注解的类和@Bean注解方法中使用。

```
public class MyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MyService myService() {  }

}
```

- 常用的条件注解有：

@ConditionalOnBean: 当容器中存在指定Bean时，才会实例化当前Bean。
@ConditionalOnMissingBean: 当容器中不存在指定Bean时，才会实例化当前Bean。
@ConditionalOnClass: 当指定的class位于类路径上时，才会实例化当前Bean。
@ConditionalOnMissingClass: 当指定的class位于类路径上时，不会实例化当前Bean。
@ConditionalOnProperty: 当指定的属性值存在时，才会实例化当前Bean。
@ConditionalOnExpression: 当指定的SpEL表达式结果为true时，才会实例化当前Bean。

### 配置绑定

- 以前的逻辑

``` 
public class getProperties {
     public static void main(String[] args) throws FileNotFoundException, IOException {
         Properties pps = new Properties();
         pps.load(new FileInputStream("a.properties"));
         Enumeration enum1 = pps.propertyNames();//得到配置文件的名字
         while(enum1.hasMoreElements()) {
             String strKey = (String) enum1.nextElement();
             String strValue = pps.getProperty(strKey);
             System.out.println(strKey + "=" + strValue);
             //封装到JavaBean。
         }
     }
 }

```

- 现在逻辑 方法一 这种方式是需要能拿到源码的
    - 定义对应的bean类型[Car.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2Fbean%2FCar.java)

```java
  package org.getyou123.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 实现从配置文件中读取数据实例化就一个bean
 */

@Component
@ConfigurationProperties(prefix = "mycar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String brand;
    private Integer price;
}
```

- 更新配置文件 [application.properties](src%2Fmain%2Fresources%2Fapplication.properties)

- 现在逻辑 方法二 无需要再bean的定义源码上操作
    - @EnableConfigurationProperties + @ConfigurationProperties

### springbootApplication

- 相当于三个注解 @SpringBootConfiguration 表示这个是个配置类
- @ComponentScan 指定扫描的包
- @EnableAutoConfiguration
    - @AutoConfigurationPackage 导入main程序所在的包到IOC容器中
    - @Import(AutoConfigurationImportSelector.class) 获取所有的自动配置类，默认要加载的100多个组件，这些组件啥时候回生效呢，按照组件的Conditional注解来生效

### springboot中的都有哪些配置项

- https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#common-application-properties

### spring热部署dev-tools

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

### 使用spring的向导开发

### 核心配置文件

- properties
- yaml非常适合用来做以数据为中心的配置文件,相同的处于同一个缩进上
    - `#` 作为注释
- 示例程序：
    - [application.yaml](src%2Fmain%2Fresources%2Fapplication.yaml)
    - [Person.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2Fbean%2FPerson.java)

### 实现配置yaml文件时候的提示功能

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304061452693.png)

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>


<!-- 打包时候不打包进去这个配置处理器 -->
 <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-configuration-processor</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304061456423.png)

### spring 进行web开发-静态资源访问

- 只要静态资源放在类路径下： called /static (or /public or /resources or /META-INF/resources 访问 ： 当前项目根路径/ +
  静态资源名 `http://localhost:8888/gzh.png`
- 请求进来，先去找Controller看能不能处理。不能处理的所有请求又都交给静态资源处理器。静态资源也找不到则响应404页面，请求的优先级别
- 如果要给静态资源加上前缀的话

``` 
spring:
  mvc:
    static-path-pattern: /res/**
```

- 引入webjar

```
<dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>3.5.1</version>
</dependency>
http://localhost:8080/webjars/jquery/3.5.1/jquery.js  
```

### 使用自定义欢迎页

直接写一个index.html，放在静态资源文件夹中

### 自定义favicon

### 静态资源相关的配置原理


### restfull风格的开启
- 页面传入一个_method参数 如何修改这个参数呢
- 必须原来是post方式

### 获取请求参数
- 基本注解获取
  @PathVariable、@RequestHeader、@ModelAttribute、@RequestParam、@MatrixVariable、@CookieValue、@RequestBody

- 通过servletApi获取
  WebRequest、ServletRequest、MultipartRequest、 HttpSession、javax.servlet.http.PushBuilder、Principal、InputStream、Reader、HttpMethod、Locale、TimeZone、ZoneId
``` 
public String getUser(HttpServletRequest request)
```
- 获取自定义类型对象的参数[ParameterTestController.java](springboot_basic%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fcontroller%2FParameterTestController.java)

### 自定义的converter


### 数据响应
- 响应json数据 直接使用@ResponseBody
- 