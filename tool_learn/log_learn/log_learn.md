## 回答问题list
- 如何配置一个写滚动日志文件的类
- 如何搭配lombok使用Slf4j
- [log4j2.xml](src%2Fmain%2Fresources%2Flog4j2.xml)中的标签
  - Appenders
  - Loggers
  - 如何配置最低的日志级别
- 日志等级
- 桥接器
- 绑定器
- 日志门面


### 文件说明：
[App1.java](src%2Fmain%2Fjava%2Forg%2Fexample%2FApp1.java) 核心的示例代码
[DemoLog.java](src%2Fmain%2Fjava%2Forg%2Fexample%2FDemoLog.java) 被调用方
[Test01.java](src%2Fmain%2Fjava%2Forg%2Fexample%2FTest01.java) 简单的实例，不和其他有关系


### 历史关系
* System.out 这个是最基本的机器的控制数据，对于Java日志打印最开始只有大家熟悉的以System开头如System.out.println("hello world")这样的写法，
  * 默认的控制台日志打印方式需要有IO操作,
  * 性能极其低效(慎用)，
  * 功能也太过单一只能简简单单的输出日志。
* log4j
  * 再后来就有了软件开发者Ceki Gulcu设计出了一套日志库也就是log4j并捐献给了Apache帮助简化日志打印。
  * 相关的依赖包是log4j和适配log4j2的桥接包log4j-1.2-api。
* JUL （Java Util Logging）
  * Java毕竟还是sun公司(后被oracle收购)的Java,sun公司并没有采纳Log4j提供的标准库，而是在jdk1.4自立一套日志标准JUL（Java Util Logging) JUL并不算强大也没得到普及所以现在大家也很少听说了。相关的依赖是jdk和适配log4j2的桥接包log4j-jul
* JCL （Jakarta Commons Logging）
* slf4j
  * 前面的竞争促进了日志组件的发展但也逐渐导致日志依赖与配置越来越复杂，2006年Log4j的作者Ceki Gulcu离开了Apache组织后觉得JCL门面不好用，于是自己开发了一版和其功能相似的Slf4j（Simple Logging Facade for Java）这个也是大家所熟悉的日志门面。
  * 相关的依赖是slf4j-api和适配log4j2的桥接包og4j-slf4j-impl或者log4j-slf4j2-impl。
* logback
  * 后来Slf4j作者又写出了Logback日志标准库作为Slf4j接口的默认实现。
* log4j2  log4j的升级版本，不再更新log4j
  * 到了2012年，Apache可能看到这样下去要被反超了，于是就推出了新项目Log4j2并且不兼容Log4j，全面借鉴Slf4j+Logback。Apache Log4j 2是对Log4j的升级，它比其前身Log4j 1.x提供了显著的改进，并提供了Logback中可用的许多改进，同时修复了Logback体系结构中的一些固有问题。


### 官网地址&定位&特性
Log4j 2 旨在用作审计日志记录，被设计为可靠、快速和可扩展，易于理解和使用的框架。简单的来说Log4j2就是一个日志框架，用来管理日志的

特性：
* 异常处理机制
* 性能提升
* 自动重新配置，无需重启
* 无侵入性：通过扩展机制自动加载，无需与代码完全耦合，代码中可以使用SLF4J门面

### 什么是日志门面
SLF4J（Simple Logging Facade for Java）用作各种日志框架（java.util.logging，logback，log4j，log4j2）的简单外观或抽象，
允许最终用户在部署时插入所需的日志框架，
是一款Java程序编写的日志门面框架，
其本身定义了统一的日志接口，
且对不同的日志实现框架进行抽象化，
***我们的应用只需要跟SLF4J进行沟通***，
而不需要跟具体实现框架直接沟通，
从而调用具体实现框架的相关方法进行日志记录。
这样我们可以方便的切换日志的实现框架，且无需改动我们的应用，这也是门面模式的优点。

### 绑定器和桥接器
- Slf4j绑定器 （SLF4J adaptor）
- Slf4j桥接器（SLF4J bridge）
- 区别：
  - 因为我们只和门面打交道了
  - 所以要么实现门面中的接口，实现了的就是绑定器
  - 要么需要把实现的接口转为门面中的接口样式，这种的就是桥接器

### 关于日志门面Slf4j+log4j2
Simple Logging Facade for Java（SLF4J）用作各种日志框架（例如 java.util.logging，logback，log4j）的简单外观或抽象，允许最终用户在部署时插入所需的日志框架。
slf4j 就是众多日志接口的集合，他不负责具体的日志实现，只在编译时负责寻找合适的日志系统进行绑定
Log4j2 和logbook是具体的日志框架的实现。

使用 Slf4j 非常简单，只需要在项目中加入以下依赖：
```xml
<!-- slfg4j的使用-->
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j-impl</artifactId>
      <version>2.12.1</version>
  </dependency>
```

代码中
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Slf4jDemo {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Slf4jDemo.class);
        logger.info("Slf4j log info");
    }
}
```

配合lombok的使用：
上面的基础上加入：
```xml
<!--lombok的使用-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.16</version>
        </dependency>
```

示例代码：

```java
import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class Test01 {
  public static void main(String[] args) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //在类中引入注解@Slf4j后就可以使用，使用时固定的记录器是log
    log.error("Hello World --by Test01， Test01：测试日志1  ");

    new Timer("timer").schedule(new TimerTask() {
      @Override
      public void run() {
        log.error("Hello World --by Test01， Test01：测试日志1 ,系统时间 " + sdf.format(System.currentTimeMillis()));
        log.error("参数1:{},参数2：{}", "lombok时间", sdf.format(System.currentTimeMillis()));
      }
    }, 1000, 1000);
  }
}
```

### 关于log4j的实现和其中的主要对象
- 第一个主要对象 Loggers 记录器，这个用于记录各个类的日志生成情况（日志的同步or日志异步）
  - 每条日志语句都要设置一个等级 (DEBUG、 INFO、 WARN. ERROR和FATAL)
  - FATAL是最高的
  - 设置一个日志级别就可以实现到一个级别才会输出日志
  
- 第二个主要对象 Appenders 输出源
  - 禁用和使用日志请求只是Log4的基本功能，Log4日志系统还提供许多强大的功能，比如允许把日志输出到不同的地方，如控制台(Console）、文件（Files)等，可以根据天数或者文件大小产生新的文件，可以以流的形式发送到其它地方等等。
  - 常使用的类如下：
    - org.apache.log4j.ConsoleAppender（控制台）
    - org.apache.log4j.FileAppender（文件）
    - org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）
    - org.apache.log4.RolingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）
    - org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方)

     基本上可以满足我们的日常需求，当然如果你的需求比较特殊，可以自己实现Appender输出路径。只需要定义一个类，实现Appender接口就可以了。Appender接口中定义了一系列记录日志的方法，按照自己的规则实现这些方法即可
- 第三个主要对象 Layouts 布局
  - 这个主要是规定了日志输出的格式
  - 用户可以根据自己的喜好格式科自己的日志输出
  - Layouts提供四种日志输出样式，如根据HTML样式、自由指定样式、包含日志级别与信息的样式和包含日志时间、线程、类别等信息的样式。 常使用的类如下：
    - org.apache.log4.HTMLLayout（以HTML表格形式布局）
    - org.apache.log4i.PatternLayout (可以灵活地指定布局模式）
    - org,apache.log4j simpleLayout （包含日志信息的级别和信息字符串）
    - org-apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等信息）

三者的关系
- 日志收集
- 日志格式转化
- 日志发送

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302091829632.png)

### log4j的使用
- 声明配置文件 xml和properties都可，但是后面的高版本是不支持 properties的了
  - 最好还是配置logback.xml
  - 不过log4j.properties也可
  - maven项目中需要放在[resources](src%2Fmain%2Fresources)下

- 导包 pom 直接直接参考 [pom.xml](pom.xml)
- 代码 直接参考这个 [App1.java](src%2Fmain%2Fjava%2Forg%2Fexample%2FApp1.java)



### 首先定义Appenders 标签
[log4j2.xml](src%2Fmain%2Fresources%2Flog4j2.xml) 这里写了两个输出一个是控制台一个是滚动文件
当然也是支持写到jdbc等

### 关于Logger日志生成的配置即 ```<Logger>``` 标签
默认情况下 Logger 都是同步的，但是也有异步的实现，
- Root 的异步版本是 AsyncRoot，
- Logger 的异步版本是 AsyncLogger，异步 Logger 依赖外部队列 LMAX Disruptor。使用异步 Logger 需要加上外部依赖:
```xml
<!-- 使用异步日志增加如下的配置-->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.2</version>
</dependency>
```

使用异步的Logger：

```xml
<!-- 这里引用上面定义的输出端，需要指明输出的Appender的名字。 -->
<!-- 配置指定的类的收集的日志级别 -->
<AsyncLogger name="org.example.App1" level="${LOG_LEVEL}" additivity="false" >
  <AppenderRef ref="RollingFile"/>
</AsyncLogger>
```
additivity=false 用于防止信息被打印两遍






