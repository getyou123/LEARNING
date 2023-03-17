### mvc 的简介

mvc本身是一种思想，模型（数据模型），视图，控制器

- M:Model，模型层，指工程中的JavaBean，作用是处理数据 JavaBean分为两类:
  一类称为实体类Bean:专门存储业务数据的，如 Student、User 等
  一类称为业务处理 Bean:指 Service 或 Dao 对象，专门用于处理业务逻辑和数据访问。
- V:View，视图层，指工程中的html或jsp等页面，作用是与用户进行交互，展示数据
- C:Controller，控制层，指工程中的servlet，作用是接收请求和响应浏览器

### spring mvc的简介

三层架构：
三层架构分为表述层(或表示层)、业务逻辑层、数据访问层，表述层表示前台页面和后台 servlet

- SpringMVC 作为 Java EE 项目 表述层开发的首选方案
- SpringMVC 其实就是 封装了servlet

### 配置IDEA中的TOMCAT搭配spring mvc

详见[docker_learn.md](..%2F..%2Fdocker_learn%2Fdocker_learn.md)

### HelloWorld

- 配置pom [pom.xml](pom.xml)
- 配置web.xml [web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml)
- 编写Controller [HelloController.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FHelloController.java)
- 配置spring mvc的配置文件 [springMVC.xml](src%2Fmain%2Fresources%2FspringMVC.xml)
- 配置index.html [index.html](src%2Fmain%2Fwebapp%2FWEB-INF%2Ftemplates%2Findex.html)

### spring mvc的实现过程

浏览器发送请求，若请求地址符合前端控制器的url-pattern，该请求就会被前端控制器 DispatcherServlet处理。
前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，
将请求地址和控制器中@RequestMapping注解的value属性值进行匹配，若匹配成功，该注解所标识的 控制器方法就是处理请求的方法。
处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会 被视图解析器解析，加上前缀和后缀组成视图的路径，
通过Thymeleaf对视图进行渲染，最终转发到视图所对应页面

### RequestMapping注解

- @RequestMapping注解:处理请求和控制器方法之间的映射关系
- @RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径
- 如果作用在了类上，那么就是整个类的都是有一个基础的前缀，然后方法中的都是这个前缀+方法的匹配org.getyou123.RequestMappingLearn.hello
- 配置Method方法：@RequestMapping注解的method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配 多种请求方式的请求 `405:Request method 'POST' not supported`
  - 默认是get，只有表单 几个特殊的为post
  - ``


