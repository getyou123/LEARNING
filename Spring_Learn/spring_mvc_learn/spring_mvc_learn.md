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
- 配置spring mvc的配置文件
- 配置index.html

### RequestMapping注解

- @RequestMapping注解:处理请求和控制器方法之间的映射关系
- @RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径



