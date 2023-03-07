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
- 非侵入式:使用 Spring Framework 开发应用程序时，Spring 对应用程序本身的结构影响非常小。对领域模型可以做到零污染;对功能性组件也只需要使用几个简单的注解进行标记，完全不会 破坏原有结构，反而能将组件结构进一步简化。这就使得基于 Spring Framework 开发应用程序 时结构清晰、简洁优雅。
- 控制反转:IOC——Inversion of Control，翻转资源获取方向。把自己创建资源、向环境索取资源 变成环境将资源准备好，我们享受资源注入。
- 面向切面编程:AOP——Aspect Oriented Programming，在不修改源代码的基础上增强代码功能。
  - 是对于面向对象的进一步扩展，用于进一步增强
- 容器:Spring IOC 是一个容器，因为它包含并且管理组件对象的生命周期。组件享受到了容器化 的管理，替程序员屏蔽了组件创建过程中的大量细节，极大的降低了使用门槛，大幅度提高了开发 效率。
- 组件化:Spring 实现了使用简单的组件配置组合成一个复杂的应用。在 Spring 中可以使用 XML 和 Java 注解组合这些对象。这使得我们可以基于一个个功能明确、边界清晰的组件有条不紊的搭 建超大型复杂应用系统。 
- 声明式:很多以前需要编写代码才能实现的功能，现在只需要声明需求即可由框架代为实现。 
  - 区分于编程式
- 一站式:在 IOC 和 AOP 的基础上可以整合各种企业应用的开源框架和优秀的第三方类库。而且 Spring 旗下的项目已经覆盖了广泛领域，很多方面的功能性需求可以在 Spring Framework 的基 础上全部使用 Spring 来实现。


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
如果出现了升级weapon的时候就出现了更新大量的代码了，这就是耦合性太强了，使用IOC容器来管理之后，通过配置容器来是实现weapon 具体引用哪个对象，然后在此基础上更改就很容易
- IOC 就是一种反转控制的思想， 而 DI 是对 IOC 的一种具体实现。

### IOC容器在spring中的实现
- 如何在spring中获取IOC容器
  - BeanFactory： 内部接口不对开发者开放
  - ApplicationContext：BeanFactory 的子接口，提供了更多高级特性。面向 Spring 的使用者，几乎所有场合都使用 ApplicationContext 而不是底层的 BeanFactory。
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
- 通过xml通过Setter方法注入到对象中 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303071516778.png)
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

- 构造器注入
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
- 一些特殊值的处理
  - 字面量直接写 `<property name="name" value="张三"/>`
  - null值 `<property name="name"> <null /> </property>`
  - xml实体 `<property name="expression" value="a &lt; b"/>`
  - CDATA节
  - 为类类型属性赋值
