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
- 配置Method方法：@RequestMapping注解的method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配
  多种请求方式的请求 `405:Request method 'POST' not supported`
    - 默认是get，只有表单 几个特殊的为post
    - 组合注解： GetMapping = @RequestMapping 中设置method为Get
    - 处理post请求的映射-->@PostMapping
    - 处理put请求的映射-->@PutMapping
    - 处理delete请求的映射-->@DeleteMapping
- @RequestMapping注解的params属性，请求方法时候的参数

```
     @RequestMapping(
     value = {"/testRequestMapping", "/test"}
     ,method = {RequestMethod.GET, RequestMethod.POST}
     ,params = {"username","password!=123456"}
     )
```

- 这里的params之间是且的关系
- "param=value":要求请求映射所匹配的请求必须携带param请求参数且param=value
- "param!=value":要求请求映射所匹配的请求必须携带param请求参数但是param!=value
- 不满足的话会报错 400 `:Parameter conditions "username, password!=123456" not met for actual request parameters`

### SpringMVC支持ant风格的路径

``` 
  ?:表示任意的单个字符 
  *:表示任意的0个或多个字符 
  **:表示任意层数的任意目录 
  注意:在使用**时，只能使用/**/xxx的方式
```

### spring mvc中的占位符

SpringMVC路径中的占位符常用于RESTful风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，
就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，在通过@PathVariable注解，
将占位符所表示的数据赋值给控制器方法的形参

``` 
  原始方式:/deleteUser?id=1 
  rest方式:/user/delete/1
```

### spring mvc获取请求参数

1. 通过ServletAPI获取参数 org.getyou123.RequestMappingLearn.testGetParaByServlet
2. 通过控制器方法的形参获取请求参数 org.getyou123.RequestMappingLearn.testGetParaBYController
3. 实现请求参数和方法的形参关系对应，配置对是否是必须参数，默认值等 org.getyou123.RequestMappingLearn.testGe
4. 请求头信息和方法的形参绑定注解 @RequestHeader
5. 从cookie中获取参数绑定到方法的形参的 @CookieValue
6. 通过pojo获取参数，即方法的形参是个类对象
   一般来说让方法的形参和传输的参数是一致的

### 解决spring mvc中的请求参数的乱码问题

``` 
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

note:SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效

### 如何理解域对象呢？

用于处理共享数据：

1. request域（一次请求）：用于将数据存储在当前请求中，在请求处理期间共享数据。
   可以通过HttpServletRequest对象的setAttribute()方法将数据存储在request域中，-- 这个很少使用了
   也可以通过ModelAndView对象的addObject()方法将数据添加到request域中。 -- 最好是使用这个

```
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request){  // 这里演示了获取HttpServletRequest的方式
        request.setAttribute("testScope", "hello,servletAPI");
        return "success";
    }


    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        /**
         * ModelAndView有Model和View的功能
         * Model主要用于向请求域共享数据
         * View主要用于设置视图，实现页面跳转
         */
        ModelAndView mav = new ModelAndView(); //向请求域共享数据
        mav.addObject("testScope", "hello,ModelAndView"); //设置视图，实现页面跳转
        mav.setViewName("successFromModelAndView");
        return mav;
    }
    <P th:text="${testScope}"></p>
```

2. session域（一个会话）：用于将数据存储在当前会话中，在会话期间共享数据。可以通过HttpSession对象的setAttribute()
   方法将数据存储在session域中。

3.

application域（整个应用）：用于将数据存储在全局上下文中，在应用程序期间共享数据。可以通过ServletContext对象的setAttribute()
方法将数据存储在application域中。
通过使用这些域对象，可以在请求处理期间将数据从一个控制器传递到另一个控制器，或者在多个请求之间共享数据。例如，在用户登录后，可以将用户信息存储在session域中，在整个会话期间共享数据，以便在多个请求中访问该信息。又如，可以将查询结果存储在request域中，在JSP页面中显示查询结果。
总之，域对象是Spring MVC框架中的重要机制，可以方便地在请求处理期间共享数据，使得程序开发更加方便和灵活。

### ModelAndView

Model主要用于向请求域共享数据
View主要用于设置视图，实现页面跳转

### SpringMVC的视图

1.

ThymeleafView：当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图在配置文件中配置了前缀和后缀的，就直接是使用ThymeleafView进行解析；

``` 
@RequestMapping("/testHello")
public String testHello(){
    return "hello";
}
```

2. 转发视图 SpringMVC中默认的转发视图是InternalResourceView，forward前缀的不会被ThymeleafView解析

``` 
@RequestMapping("/testForward")
public String testForward(){
    return "forward:/testHello";
}
```

3. 重定向视图：RedirectView redirect作为前缀

```
@RequestMapping("/testRedirect")
public String testRedirect(){
    return "redirect:/testHello";
}
```

### 视图控制器view-controller

不再写controller 直接写配置文件来实现跳转

``` 
<!--
path:设置处理的请求地址 
view-name:设置请求地址所对应的视图名称
-->
<mvc:view-controller path="/testView" view-name="success"></mvc:view-controller>
```

### RESTful风格

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303241751762.png)

1. 代码中如何区分各种请求方式呢？

``` 
@RequestMapping(value = "/employee", method = RequestMethod.GET)
public String getEmployeeList(Model model){
    Collection<Employee> employeeList = employeeDao.getAll();
    model.addAttribute("employeeList", employeeList);
    return "employee_list";
}

或者是GetMapping PostMapping PutMapping  DelteMapping
```

2. 浏览器中只支持 post 和 get两个方法如何发送DELETE 和 PUT呢
   通过 HiddenHttpMethodFilter 进行转化
   HiddenHttpMethodFilter 处理put和delete请求的条件:
   a>当前请求的请求方式必须为post
   b>当前请求必须传输请求参数_method
``` 
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-
class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>

```

3. 开发一个CURD的场景