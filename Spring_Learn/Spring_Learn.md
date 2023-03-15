####

### spring是什么

- 官网地址:https://spring.io/
- Spring 是轻量级的框架，其基础版本只有 2 MB 左右的大小。
- Spring 框架的核心特性是可以用于开发任何 Java 应用程序，但是在 Java EE 平台上构建 web 应 用程序是需要扩展的。
- Spring 框架的目标是使 J2EE 开发变得更容易使用，通过启用基于 POJO 编程模型来促进良好的编程实践。
- Spring 性能好
- IOC对象的控制权反转给spring，对象都是spring中来获取
- 易于测试整合了JUNIT
- 提高重用性 AOP 切面的概念

### Spring家族

- Spring Framework 基础
    - 基础的，其他的都是按照这个作为基础的
- Spring Boot
- Spring Cloud

### Spring Framework五大功能模块

* Core Container 核心容器，在 Spring 环境下使用任何功能都必须基于 IOC 容器。
* AOP&Aspects 面向切面编程
* Testing 提供了对 junit 或 TestNG 测试框架的整合。
* Data Access/Integration 提供了对数据访问/集成的功能
* Spring MVC 提供了面向Web应用程序的集成功能
* ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303071432332.png)

### Spring Framework特性

- 非侵入式:使用 Spring Framework 开发应用程序时，Spring 对应用程序本身的结构影响非常小。对领域模型可以做到零污染;对功能性组件也只需要使用几个简单的注解进行标记，完全不会
  破坏原有结构，反而能将组件结构进一步简化。这就使得基于 Spring Framework 开发应用程序 时结构清晰、简洁优雅。
- 控制反转:IOC——Inversion of Control，翻转资源获取方向。把自己创建资源、向环境索取资源 变成环境将资源准备好，我们享受资源注入。
- 面向切面编程:AOP——Aspect Oriented Programming，在不修改源代码的基础上增强代码功能。
    - 是对于面向对象的进一步扩展，用于进一步增强
- 容器:Spring IOC 是一个容器，因为它包含并且管理组件对象的生命周期。组件享受到了容器化
  的管理，替程序员屏蔽了组件创建过程中的大量细节，极大的降低了使用门槛，大幅度提高了开发 效率。
- 组件化:Spring 实现了使用简单的组件配置组合成一个复杂的应用。在 Spring 中可以使用 XML 和 Java
  注解组合这些对象。这使得我们可以基于一个个功能明确、边界清晰的组件有条不紊的搭 建超大型复杂应用系统。
- 声明式:很多以前需要编写代码才能实现的功能，现在只需要声明需求即可由框架代为实现。
    - 区分于编程式
- 一站式:在 IOC 和 AOP 的基础上可以整合各种企业应用的开源框架和优秀的第三方类库。而且 Spring 旗下的项目已经覆盖了广泛领域，很多方面的功能性需求可以在
  Spring Framework 的基 础上全部使用 Spring 来实现。

### IOC思想

- IOC:Inversion of Control，翻译过来是反转控制。
- IOC是一种思想：
    - 以前是需要手动创建 new的，现在把对象的管理交给IOC容器
    - IOC容器就是存储对象的容器
    - 正转：程序new object，然后使用
    - 反转：由容器来帮忙创建及注入依赖对象，使用方是被动接受这个对象
- DI:DI:Dependency Injection，翻译过来是依赖注入。
    - 把有依赖关系的类放到容器中，解析出这些类的实例，然后从使用关系中来完成A对于B对象的引用（就是成员变量赋值）
    - Class A中用到了Class B的对象b，一般情况下，需要在A的代码中显式的new一个B的对象。
    - 采用依赖注入技术之后，A的代码只需要定义一个私有的B对象，不需要直接new来获得这个对象，而是通过相关的容器控制程序来将B对象在外部new出来并注入到A类里的引用中。

``` 
class Player{  
    Weapon weapon;  

    Player(){  
        // 与 Sword类紧密耦合
        this.weapon = new Sword();  

    }  

    public void attack() {
        weapon.attack();
    }
}
```

如果出现了升级weapon的时候就出现了更新大量的代码了，这就是耦合性太强了，使用IOC容器来管理之后，通过配置容器来是实现weapon
具体引用哪个对象，然后在此基础上更改就很容易

- IOC 就是一种反转控制的思想， 而 DI 是对 IOC 的一种具体实现。

### IOC容器在spring中的实现

- 如何在spring中获取IOC容器
    - BeanFactory： 内部接口不对开发者开放
    - ApplicationContext：BeanFactory 的子接口，提供了更多高级特性。面向 Spring 的使用者，几乎所有场合都使用
      ApplicationContext 而不是底层的 BeanFactory。
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303071451715.png)

### 如何把Bean交给IOC容器来管理：

- 通过xml中的bean标签来配置
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303071457853.png)
    - 注意id唯一
- 通过注解来配置

### 如何获取IOC容器&如何从IOC容器中获取Bean

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303071506428.png)

```
public class HelloSpringTest {
    @Test
    public void test(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloSpring helloSpring = (HelloSpring) ac.getBean("hellospring");
        helloSpring.sayHello();
    }
}
```

- IOC容器中默认是单例，需要提供是类的无参构造函数
- 获取Bean的方式中有多种方式
    - 通过id获取 `HelloSpring helloSpring = (HelloSpring) ac.getBean("hellospring");`
    - 通过类型获取 `HelloWorld bean = ac.getBean(HelloWorld.class);`
    - 通过根据id和类型获取 `HelloWorld bean = ac.getBean("helloworld", HelloWorld.class);`

- 如果组件类实现了接口，根据接口类型可以获取 bean 吗?

> 可以，前提是bean唯一

- 如果一个接口有多个实现类，这些实现类都配置了 bean，根据接口类型可以获取 bean 吗?

> 不行，因为bean不唯一

### 依赖注入的实现

1.

通过xml通过Setter方法注入到对象中 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303071516778.png)

- xml 配置

```
    <bean id="studentOne" class="org.getyou123.pojo.Student">
        <!-- property标签:通过组件类的setXxx()方法给组件对象设置属性 -->
        <!-- name属性:指定属性名(这个属性名是getXxx()、setXxx()方法定义的，和成员变量无关)
        -->
        <!-- value属性:指定属性值 -->
        <property name="id" value="1001"></property>
        <property name="name" value="张三"></property>
        <property name="age" value="23"></property>
        <property name="sex" value="男"></property>
    </bean>
```

2. 构造器注入

- xml 配置

```
    <bean id="studentTwo" class="org.getyou123.pojo.Student">
        <constructor-arg value="1002"></constructor-arg>
        <constructor-arg value="李四"></constructor-arg>
        <constructor-arg value="33"></constructor-arg>
        <constructor-arg value="女"></constructor-arg>
    </bean>
    
constructor-arg标签还有两个属性可以进一步描述构造器参数:
  index属性:指定参数所在位置的索引(从0开始) 
  name属性:指定参数名
```

3. 一些特殊值的处理

- 字面量直接写 `<property name="name" value="张三"/>`
- null值 `<property name="name"> <null /> </property>`
- xml实体 `<property name="expression" value="a &lt; b"/>`
- CDATA节
- 为类类型属性赋值

4. 为类类型属性赋值：这里的场景是学生类中包含Classzz对象，即学生所属的班级的信息

第一种方式： 使用级联的方式，org.getyou123.StudentTest.testGetStudentThree

``` 
    <bean id="studentThree" class="org.getyou123.pojo.Student">
        <constructor-arg value="1002"></constructor-arg>
        <!-- 指定属性的对应关系 -->
        <constructor-arg value="李四" name="name"></constructor-arg>
        <constructor-arg value="33"></constructor-arg>
        <constructor-arg value="女"></constructor-arg>
        <!-- 一定先引用某个bean为属性赋值，才可以使用级联方式更新属性 -->
        <property name="clazz" ref="clazzOne"></property>
        <property name="clazz.clazzId" value="3333"></property>
        <property name="clazz.clazzName" value="最强王者班"></property>
    </bean>
```

第二种方式： 直接引用配置文件中已经声明的另外的bean org.getyou123.StudentTest.testGetStudentFour
注意不要把ref写为了value

``` 
    <bean id="studentFour" class="org.getyou123.pojo.Student">
        <property name="id" value="1004"></property>
        <property name="name" value="赵六"></property>
        <property name="age" value="26"></property>
        <property name="sex" value="女"></property>
        <!-- ref属性:引用IOC容器中某个bean的id，将所对应的bean为属性赋值 -->
        <property name="clazz" ref="clazzOne"></property>
    </bean>
```

第三种方式：内部bean方式

``` 
    <bean id="studentFive" class="org.getyou123.pojo.Student">
        <property name="id" value="1004"></property>
        <property name="name" value="赵六"></property>
        <property name="age" value="26"></property>
        <property name="sex" value="女"></property>
        <property name="clazz">
            <!-- 在一个bean中再声明一个bean就是内部bean -->
            <!-- 内部bean只能用于给属性赋值，不能在外部通过IOC容器获取，因此可以省略id属性 -->
            <bean id="clazzInner" class="org.getyou123.pojo.Clazz">
                <property name="clazzId" value="2222"></property>
                <property name="clazzName" value="远大前程班"></property>
            </bean>
        </property>
    </bean>
```

5. 为数组类型的成员变量赋值:student有多个爱好
   如果是字符串数组：这里使用value
   如果是引用的数组，那就使用ref

``` 
    <bean id="studentSix" class="org.getyou123.pojo.Student">
        <property name="id" value="1004"></property>
        <property name="name" value="赵六"></property>
        <property name="age" value="26"></property>
        <property name="sex" value="女"></property>
        <!-- ref属性:引用IOC容器中某个bean的id，将所对应的bean为属性赋值 -->
        <property name="clazz" ref="clazzOne"></property>
        <!-- 未数组进行赋值 -->
        <property name="hobbies">
            <array>
                <value>抽烟</value>
                <value>喝酒</value>
                <value>烫头</value>
            </array>
        </property>
    </bean>
```

6. 为集合赋值 ： 班级中存储多个学生
   第一种：为List集合类型属性赋值,如果是set的话把list标签换为set

``` 
    <bean id="clazzTwo" class="org.getyou123.pojo.Clazz">
        <property name="clazzId" value="4444"></property>
        <property name="clazzName" value="Javaee0222"></property>
        <property name="students">
            <list>
                <ref bean="studentOne"></ref>
                <ref bean="studentTwo"></ref>
                <ref bean="studentThree"></ref>
                <ref bean="studentSix"></ref>
            </list>
        </property>
    </bean>
```

第二种：为map集合赋值：学生有多个老师,下面两个都可

``` 
<bean id="studentSeven" class="org.getyou123.pojo.Student">
        <property name="id" value="1004"></property>
        <property name="name" value="赵六"></property>
        <property name="age" value="26"></property>
        <property name="sex" value="女"></property>
        <!-- ref属性:引用IOC容器中某个bean的id，将所对应的bean为属性赋值 -->
        <property name="clazz" ref="clazzOne"></property>
        <!-- 未数组进行赋值 -->
        <property name="hobbies">
            <array>
                <value>抽烟</value>
                <value>喝酒</value>
                <value>烫头</value>
            </array>
        </property>
        <property name="teacherMap">
            <map>
                <entry>
                    <key>
                        <value>10010</value>
                    </key>
                    <ref bean="teacherOne"></ref>
                </entry>
                <entry>
                    <key>
                        <value>10086</value>
                    </key>
                    <ref bean="teacherTwo"></ref>
                </entry>
            </map>
        </property>
    </bean>


    <bean id="studentEight" class="org.getyou123.pojo.Student">
    <property name="id" value="1004"></property>
    <property name="name" value="赵六"></property>
    <property name="age" value="26"></property>
    <property name="sex" value="女"></property>
    <!-- ref属性:引用IOC容器中某个bean的id，将所对应的bean为属性赋值 -->
    <property name="clazz" ref="clazzOne"></property>
    <!-- 未数组进行赋值 -->
    <property name="hobbies">
        <array>
            <value>抽烟</value>
            <value>喝酒</value>
            <value>烫头</value>
        </array>
    </property>
        <property name="teacherMap">
            <map>
                <entry key="10010" value-ref="teacherTwo"></entry>
                <entry key="10086" value-ref="teacherOne"></entry>
            </map>
        </property>
    </bean>
```

第三： 可以先声明 map 和 list集合作为bean，然后对象直接使用ref指向

``` 
<!--list集合类型的bean--> <util:list id="students">
    <ref bean="studentOne"></ref>
    <ref bean="studentTwo"></ref>
    <ref bean="studentThree"></ref>
</util:list>
<!--map集合类型的bean--> <util:map id="teacherMap">
    <entry>
        <key>
            <value>10010</value>
        </key>
        <ref bean="teacherOne"></ref>
    </entry>
    <entry>
        <key>
            <value>10086</value>
        </key>
        <ref bean="teacherTwo"></ref>
    </entry>
</util:map>
<bean id="clazzTwo" class="org.getyou123.pojo.Clazz">
    <property name="clazzId" value="4444"></property>
    <property name="clazzName" value="Javaee0222"></property>
    <property name="students" ref="students"></property>
</bean>
<bean id="studentFour" class="org.getyou123.pojo.Student">
<property name="id" value="1004"></property>
<property name="name" value="赵六"></property>
<property name="age" value="26"></property>
<property name="sex" value="女"></property>
<!-- ref属性:引用IOC容器中某个bean的id，将所对应的bean为属性赋值 --> <property name="clazz" ref="clazzOne"></property>
    <property name="hobbies">
        <array>
<value>抽烟</value> <value>喝酒</value> <value>烫头</value>
        </array>
    </property>
    <property name="teacherMap" ref="teacherMap"></property>
</bean>
```

### 创建外部属性文件

<!-- 引入外部属性文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>

配置文件：

``` 
<bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
<property name="url" value="${jdbc.url}"/>
<property name="driverClassName" value="${jdbc.driver}"/>
<property name="username" value="${jdbc.user}"/>
<property name="password" value="${jdbc.password}"/>
</bean>
```

测试代码：

``` 
 @Test
    public void testDataSource() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        DataSource dataSource = ac.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
```

### bean的作用域

- 通过在配置文件中配置scope来判定
- 默认是单例 singleton 整个容器中就一个bean，在构建IOC容器时候初始化
- 多例 prototype ，容器中多个示例，在获取bean时候创建，而不是在获取IOC容器时候就执行，同时这个对象的销毁也不是有IOC容器来管理的了

### bean的生命周期

- 实例化
- 依赖注入
- 初始化
  初始化时候可以指定调用的方法  `<bean id="studentEight" class="org.getyou123.pojo.Student" scope="singleton" init-method="inited">`
    - 其实这也是bean的作用域有关的 如果是多例的话，在获取的时候才会初始化
- 关闭销毁： 多例的话ioc容器不管销毁

### FactoryBean-工厂Bean

- FactoryBean是Spring提供的一种整合第三方框架的常用机制
- 作为一个从Spring容器中获取bean的工具类
- FactoryBean是Spring框架中的一个接口
    - 主要是要实现getObject 方法
    - 也支持 InitializingBean方法
    - DisposableBean 方法
- FactoryBean 中getObject返回的bean，就可以交给IOC容器来管理了

```
把 FactoryBean 配置到 配置文件中
  <bean id="studentNine" class="org.getyou123.factory.StudentFactoryBean"></bean>
 
但是实际是其中返回的student对象被放到了IOC容器来管理，是FactoryBean中的getObject方法的返回值给到了IOC容器来管理
```

### 基于XML中的自动装配

- 自动装配（autowiring）机制
- 可以无需手动将依赖注入到类中，而是通过配置Spring容器，让Spring自动完成依赖注入
- 这里controller持有service，service持有dao
- 都是认为能且只能找到一个bean的，如果容器中有多个的话，会报错不是唯一的bean结果
- 自动装配: 其实是指定了策略，然后实现注入
    - byName 按照名字去匹配
    - byType 按照type去匹配
    - no
    - default
-

示例程序 [AutoWireTest.java](spring_basic_ioc%2Fsrc%2Ftest%2Fjava%2Forg%2Fgetyou123%2FAutoWireTest.java) + [spring-autowire.xml](spring_basic%2Fsrc%2Fmain%2Fresources%2Fspring-autowire.xml)

1. 如果不使用自动装配的话

```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="StudentController" class="org.getyou123.controller.StudentController">
        <property name="studentService" ref="studentService"></property>
    </bean>
    <bean id="studentService" class="org.getyou123.service.impl.StudentServiceImpl">
        <property name="studentDao" ref="studentDao"></property>
    </bean>
    <bean id="studentDao" class="org.getyou123.dao.impl.StudentImpl"></bean>
</beans>
```

2. 使用自动装配采用byType-能匹配到就匹配到，匹配不到就是null这个默认值，

``` 
    <bean id="StudentController" class="org.getyou123.controller.StudentController" autowire="byType">
<!--        <property name="studentService" ref="studentService"></property>-->
    </bean>
    <bean id="studentService" class="org.getyou123.service.impl.StudentServiceImpl" autowire="byType">
<!--        <property name="studentDao" ref="studentDao"></property>-->
    </bean>
    <bean id="studentDao" class="org.getyou123.dao.impl.StudentImpl"></bean>
</beans>
```

3. 使用自动装配采用byName-很少用到，也是能用到就用到然后用不上的话就用默认值
   bean 的id 和 当前的属性名字一致，那么就可以在当前类上使用byName

``` 
    <bean id="StudentController" class="org.getyou123.controller.StudentController" autowire="byName">
<!--        <property name="studentService" ref="studentService"></property>-->
    </bean>
    <bean id="studentService" class="org.getyou123.service.impl.StudentServiceImpl" autowire="byName">
<!--        <property name="studentDao" ref="studentDao"></property>-->
    </bean>
    <bean id="studentDao" class="org.getyou123.dao.impl.StudentImpl"></bean>
</beans>

```

### 使用注解控制IOC容器中的Bean

扫描 + 注解 才能生效； 并不是所有的类对象都要交给IOC来管理

1. 常见的组件的注解 其实都是从Component 演变来的，都是一样的，这些注解只能加载实现类上不能加在接口上
   @Component:将类标识为普通组件
   @Controller:将类标识为控制层组件
   @Service:将类标 识为业务层组件
   @Repository:将类标识为持久层组件
2. 配置扫描：

- 基本的扫描 `<context:component-scan base-package="org.getyou123"></context:component-scan>`
- 排除某个注解 这里是Controller这个注解

```
    <context:component-scan base-package="org.getyou123">
        <!-- 实现按照注解进行过滤 -->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        <!-- 实现按照类型进行过滤 -->
        <context:exclude-filter type="assignable" expression="com.getyou123.controller.StudentController"/>
    </context:component-scan>
```

3. 配置注解过过程中的id

- id 默认是类的小驼峰名字，StudentController->studentController,交给IOC管理的是StudentDaoImpl->studentDaoImpl
- 自定义id @Controller("studentDaoImpl") 在这里进行配置id

### 基于注解实现自动装配

- @Autowired 可以标志属性，这个是不依赖类的setter方法上
- 也可以在setter方法上
- 也可以在构造函数上的参数上
- 默认是通过byType的方式进行注入，byType不能确定的话就会按照byName方式，如果不能的话就是byType报错多个非唯一（注意下这个情况）
    - @Qualifier("studentDaoImpl")可以在byType失效的情况下，指定bean的id
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303131851932.png)

### Autowired 和 Resource注解的区别

- 两个都是用来完成，依赖注入的，但是Autowired是spring提供的注解，Resource是JDK中的提供的注解
-
作用对象不同：@Autowired注解可以对类的成员变量、方法、构造函数进行标注，标注在字段上表示自动注入该类型的对象，标注在方法或构造函数上表示该方法或构造函数需要注入这个类型的对象；而@Resource注解主要是标注在字段上面，用于注入指定名称的Bean。
- 装配的策略不同： Autowired 是默认按照类型，之后才按照name进行装配，Resource则是先按照name来先进行装配的

### 啥时用xml，啥时用注解来管理bean呢

- 能拿到源码的话使用注解方便，且精确
- 不能拿到源码的，第三方的就用xml

### 使用配置类来代替xml文件配置

- 通过@Configuration注解表示这是一个配置类
- 通过@ComponentScan注解指定要扫描的包或类路径；
- @ImportResource("classpath:applicationContext.xml") // 引入其他配置文件
- @Value("${jdbc.url}") 在属性上配置配置文件中的数值
- @Bean 将方法的返回值作为Bean交给IOC容器
- 所以一个配置xml文件和spring的一个配置类是等效的

### 代理对象

- 其实服务于对象的功能增强，对目标对象的访问转为访问代理对象
- 被代理对象的代码没有发生变化
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303131958180.png)
- 把核心功能作为被代理对象，把非核心功能作为包围在外层作为一个代理对象

1. 静态代理
2. 动态代理
    - jdk
    - cglib

### AOP的思想

- 切入点
- 通知方法
- 切面 （通知方法的集合）
- 连接点
- 连接点
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303140932266.png)

### 基于注解实现AOP

- 给[Calculator.java](spring_basic_aop%2Fsrc%2Fmain%2Fjava%2Forg%2Fgetyou123%2FCalculator.java)
  的实现类 [CalculatorImpl.java](spring_basic_aop%2Fsrc%2Fmain%2Fjava%2Forg%2Fgetyou123%2FCalculatorImpl.java)
  加上相应的日志打印功能
- 切面类和目标类都要交给IOC容器：切面类加上 @Aspect注解标注
- 开启基于注解的aop功能 [spring-anno-aop.xml](spring_basic_aop%2Fsrc%2Fmain%2Fresources%2Fspring-anno-aop.xml)
  中的 `<aop:aspectj-autoproxy/>`
- 编写切面类 ： [LogAspect.java](spring_basic_aop%2Fsrc%2Fmain%2Fjava%2Forg%2Fgetyou123%2FLogAspect.java)
    - `@Before("execution(public int org.getyou123.CalculatorImpl.* (..))") ` 通知访问是Before，需要配置 切入表达式

### 关于通知方法的执行位置

前置通知:使用@Before注解标识，在被代理的目标方法前执行
返回通知:使用@AfterReturning注解标识，在被代理的目标方法成功结束后执行(寿终正寝) ，能回去到返回值
异常通知:使用@AfterThrowing注解标识，在被代理的目标方法异常结束后执行(死于非命)
后置通知:使用@After注解标识，在被代理的目标方法最终结束后执行(盖棺定论)，无论正常结束还是异常结束都是会执行
环绕通知:使用@Around注解标识，使用try...catch...finally结构围绕整个被代理的目标方法，包 括上面四种通知对应的所有位置

通知的顺序：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303141726443.png)

### 切入点重用

```  
# 声明一个切入点表达式
@Pointcut("execution(* com.xxx.aop.annotation.*.*(..))")
    public void pointCut(){}

# 在同一个切面中使用
@Before("pointCut()")
public void beforeMethod(JoinPoint joinPoint){
String methodName = joinPoint.getSignature().getName();
String args = Arrays.toString(joinPoint.getArgs()); System.out.println("Logger-->前置通知，方法名:"+methodName+"，参数:"+args);
}

# 不同的切面中使用
@Before("com.xxx.aop.CommonPointCut.pointCut()")
public void beforeMethod(JoinPoint joinPoint){
String methodName = joinPoint.getSignature().getName();
String args = Arrays.toString(joinPoint.getArgs()); System.out.println("Logger-->前置通知，方法名:"+methodName+"，参数:"+args);
}
```

