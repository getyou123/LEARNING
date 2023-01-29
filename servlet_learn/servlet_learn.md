### servlet_learn 的相关记录

- 第一个servlet[MyFirstServlet.java](src%2Fmain%2Fjava%2FMyFirstServlet.java)
- 如何配置web.xml中的数值给到程序来使用[MyFirstServlet.java](src%2Fmain%2Fjava%2FMyFirstServlet.java)
- servlet的工作流程
- HttpServletRequest实例中获取信息
- 获取HttpServletRequest中的表单信息
- tomcat的目录和请求url的对应关系[web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml)
- 使用注解的方式配置servlet[WebServletTest.java](src%2Fmain%2Fjava%2FWebServletTest.java)
- 自定义错误的跳转的特定页面[web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml)[NullToHtml.java](src%2Fmain%2Fjava%2FNullToHtml.java)
- servlet的请求转发[DispatcherServlet.java](src%2Fmain%2Fjava%2FDispatcherServlet.java)
- servlet重定向[RedirectDestination.java](src%2Fmain%2Fjava%2FRedirectDestination.java)
- cookie的使用[CookieUtil.java](src%2Fmain%2Fjava%2FCookieUtil.java)[CookieSet.java](src%2Fmain%2Fjava%2FCookieSet.java)[CookieGet.java](src%2Fmain%2Fjava%2FCookieGet.java)
- session的使用[SessionAdd1.java](src%2Fmain%2Fjava%2FSessionAdd1.java)[SessionAdd2.java](src%2Fmain%2Fjava%2FSessionAdd2.java)
- filter 过滤器 和 过滤过滤器链
- 监听器 @TODO


### 什么是servlet
Server Applet的简称，也就是服务程序；Servlet是对请求作出处理的一种组件；
servlet 其实是在传统的b/s 或者c/s架构中实现提供服务功能的程序，即：

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201609824.png)

本质上是sun公司提供的一个servlet接口，要求java程序遵循其中的开发规范，帮助我们处理http请求并返回响应,本身就是java编写的

***其主要任务就是***：
- 读取客户端发送来的数据，如表单数据
- 读取client发送来的请求数据
- 生成结果
- 向客户端返回文档
- 发送http响应数据


### servlet的生命周期

- 加载和实例化（默认的servlet类是单例的，懒加载的，可以通过指定load-on-start标签来指定web容器启动时候加载）
- init初始化（调用servlet对象的init方法）
- 处理请求service
- destroy

### 实现servlet的三种方式

- javax.servlet.Servlet
- javax.servlet.GenericServlet
- javax.servlet.http.HttpServlet

这三个是依次继承过来的

### ***HttpServlet的主要实现步骤***
- 一类：主要是需要编写相应的类，实现HttpServlet中的两个主要的方法doGet 和 doPost针对前端的get 和 post方法来做出响应[MyFirstServlet.java](src%2Fmain%2Fjava%2FMyFirstServlet.java)
- 二配：就是在项目下的web.xml 文件中配置Servlet。配置好这个servlet的name和url地址（这个就是配置了url和对应的servlet的映射关系，支持正则）[web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml)
- 三编：编写具体的业务[MyFirstServlet.java](src%2Fmain%2Fjava%2FMyFirstServlet.java)
- 四运行：就是在创建项目时，生成我们项目的上下文路径

### web.xml和ServletConfig
web容器（Tomcat）在创建servlet对象的时候会使用到web.xml来创建ServletConfig对象，所以我们配置在web.xml中的配置信息会生效，调用servlet的init方法时候会将 ServletConfig 传递给 Servlet

ServletConfig的主要作用是：
- 获取servlet的别名servlet-name值
- 初始化参数init-param
- 获取ServletContext对象


### ServletContext对象
通过在web.xml中配置传递给程序的参数值是通过这个ServletContext对象获取到的，例如配置某个servlet使用到的库连接地址，用户名和密码等

- 一个tomcat中可以运行多个项目
- 多个项目中的每个项目都有一个ServletContext对象，这个是个全局对象，伴随整个项目的声明周期
- 功能作用主要有：
  - 实现servlet之间的数据共享，可以存一个变量记录访问的用户总数
  - 实现读取配置文件，这里需要特殊注意下路径的写法

实例程序：[ServletContextLearn.java](src%2Fmain%2Fjava%2FServletContextLearn.java)

note:这个参数是给到具体的servlet的，所以是在servlet这个配置项下面的
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201351858.png)

经过上面的配置，在servlet中能够调用getServletConfig().getInitParameter("url")获得参数名对应的值

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201605766.png)


### web.xml配置中的细节
- 支持多个url映射同一个servlet
- 支持通配符，单纯的 *
- 支持*.do 类似的模式url

### servlet的线程安全问题
servlet本身是单例的，而且是懒加载的（也可以使用load-on-start标签来让容器启动时候就加载）。web服务器会为每个请求开一个线程，这个线程会去调用service方法，如果service访问了同一个资源，可能存在线程安全问题；

如何解决servlet的线程安全问题：
- 如果确定是存在线程安全问题的话使用synchronized关键字同步锁（）

考虑下面的场景：
- 每个客户端一个变量，记录在这个客户端内被点击的次数，这种就不能在servlet类中定义这个变量，因为每个线程共享会互相干扰，只能每个线程独一份，可以通过把变量定义在方法内，或者是用ThreadLocal


### 关于tomcat war包的位置和请求url地址的关系
- 我们把war包放在tomcat的/usr/local/tomcat/webapps/ 路径下
- /usr/local/tomcat/webapps/ 下的内容类似，存储的是war和解压路径 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201533469.png)
- 可以看到这个war包是可以进行解压的，并且是tomcat自动解压的，servlet_learn_1.war 会被解压为 servlet_learn_1
- 解压出来的路径servlet_learn_1下面的内容为![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201535700.png)
- 其中的WEB-INF 中包含我们的web.xml
- 下面的url-pattern和浏览器中的地址关系为![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201538131.png)
- 如果这里使用的是软链的话，不会进行解压 ![宿主机卷](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201541168.png) ![docker中的卷](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201541114.png)


### HttpServletRequest 和 HttpServletResponse
先说下整体流程：http://c.biancheng.net/servlet2/httpservletrequest.html
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201632732.png)
HttpServletRequest 代表着客户端的请求，服务器把所有的请求头信息都放在了这里，然后再把这个对象传给Servlet的service方法（doGet 或者 doPost，从里面可以获取到的一些信息
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201635621.png)
- 获取其中的信息的实例代码 [RequestLine.java](src%2Fmain%2Fjava%2FRequestLine.java)

从HttpServletRequest中获取表单信息：
- 首先编写html表单用来收集信息：[form.html](src%2Fmain%2Fwebapp%2Fhtml%2Fform.html)
- 注意文件文件中的  action="/servlet_learn/form" method="post" 这个是因为在用一个项目中，直接指向servlet即可
- 编写servlet [RequestParam.java](src%2Fmain%2Fjava%2FRequestParam.java) 实现数据数据收集之后的展示


### 请求转发（Forward）
请求转发的行为发生在服务器端，客户端并不知道其中的工作的流转情况，容器接收请求后，Servlet 会先对请求做一些预处理，然后将请求传递给其他 Web 资源，来完成包括生成响应在内的后续工作。实际项目中基于servlet的分工明确来做：

请求转发的主要的工作原理是：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301251048662.png)

可以看到从第一个servlet到第二个servlet的转发动作就是所谓的请求转发，核心就是一个RequestDispatcher，其中的核心的代码就是
```java
   class main{
    // 转发 这里转发的时候增加了三个属性，转发到另外的servlet了
    request.getRequestDispatcher("/DoServletTest").forward(request, response);
}
```
note: 代码中的转发，因为是在一个项目中的，所以直接写servlet的名字就可，不用项目名servlet_learn;但是html文件中是需要写servlet_learn的=> 链接，表单，重定向需要写绝对路径，就是从各个项目名字开始写，转发的话从相对路径，就是不写应用名的

### 关于重定向（Redirect）
什么是重定向呢？大致的请求过程是这样的：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301252204372.png)

可以看出来，实际就是让client在去请求指定的servlet

示例：[Redirect.java](src%2Fmain%2Fjava%2FRedirect.java) + [RedirectDestination.java](src%2Fmain%2Fjava%2FRedirectDestination.java)
最终的效果类似：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301252217865.png)

### 关于 @WebServlet()注解
这个注解实现就是当前类作为servlet对于url-pattern的相应,从servlet 3.0可以使用，实际上效果是等同于：
- 在web.xml中配置servlet名字
- 在web.xml中配置servlet-mapping
- 需要在web.xml中指明版本信息

  ```shell 
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
    http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" id="WebApp_ID" version="4.0">
  </web-app>
  ```

示例文件:[WebServletTest.java](src%2Fmain%2Fjava%2FWebServletTest.java)

#### WebServlet属性中到底value和urlPatterns有什么区别？
其实是value等价于 urlPatterns但是value和urlPattern不要一起使用


### 指定异常的跳转页面
一些常见的异常配置：
- 自定义404 error html标签
- 自定义的错误
- 空指针错误

配置自定义异常跳转页面的过程：
- web.xml中配置
```xml
  <!-- 配置指定异常跳转页面 -->
  <error-page>
    <!-- 捕捉到这个异常 -->
  <exception-type>java.lang.NullPointerException</exception-type>
    <!-- 跳转到这个页面 -->
  <location>/html/nullException.html</location>
  </error-page> 
```

示例：[NullToHtml.java](src%2Fmain%2Fjava%2FNullToHtml.java) + [web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml) + [nullException.html](src%2Fmain%2Fwebapp%2Fhtml%2FnullException.html)
效果类似：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301252200016.png)


### 状态管理
首先是这个状态的概念，理解为客户端和服务器的各种交互中的数据中间状态；这个数据记录（状态）在客户端就是cookie，记录在服务器端就是Session；


#### cookie相关
- cookie中都是string类型的key-value对，添加cookie的方式主要是在servlet中产出，然后添加到response中
- cookie在client的是一个网站一个cookie的
- cookie的编码方式设置 URlEncoder，尤其是中文的需要设置
- cookie的路径设置 cookie.setPath()
  - 默认的是设置cookie的servlet所在的上一层，比如：
    - 设置cookie的servlet是/demo01,对应的上一层是/ 根目录，也就是 /servlet_learn 这个，所以在请求/demo02时候是可以拿到cookie的
    - 但是如果设置cookie的路径是 /path1/demo01,则在 /demo02中是无法访问到的，因为设置cookie的上一级的是/path1,这个和/demo02的父以及下不一致
    - cookie.setPath() 可以指明cookie的路径
- cookie的存活时间设置
  - 如果maxAge属性为正数，则表示该Cookie会在maxAge秒之后自动失效。浏览器会将maxAge为正数的Cookie持久化，即写到对应的Cookie文件中。无论客户关闭了浏览器还是电脑，只要还在maxAge秒之前，登录网站时该Cookie仍然有效。 
  - 如果maxAge为负数，则表示该Cookie仅在本浏览器窗口以及本窗口打开的子窗口内有效，关闭窗口后该Cookie即失效。maxAge为负 数的Cookie，为临时性Cookie，不会被持久化，不会被写到Cookie文件中。Cookie信息保存在浏览器内存中，因此关闭浏览器该 Cookie就消失了。Cookie默认的maxAge值为-1。 
  - ‍如果maxAge为0，则表示删除该Cookie。Cookie机制没有提供删除Cookie的方法，因此通过设置该Cookie即时失效实现删除Cookie的效果。失效的Cookie会被浏览器从Cookie文件或者内存中删除
- cookie的限制：
  - 可以被用户禁止
  - 数据安全问题，明文的数据
  - 数据的只能保存4k左右

示例：
- 向客户端中增加cookie并在浏览器中检查并展示 [CookieSet.java](src%2Fmain%2Fjava%2FCookieSet.java)
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301261040217.png)

- servlet获取cookie然后给到client进行展示 [CookieGet.java](src%2Fmain%2Fjava%2FCookieGet.java)
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301261046743.png)

- 可以自己封装一个cookie的工具类 [CookieUtil.java](src%2Fmain%2Fjava%2FCookieUtil.java)

### session相关
cookie可以在客户端存数据，但是安全性和数据量有限制，这个是缺陷的。session基于cookie，在cookie中记录着session id，然后通信过程中还会把这个session id传递给server 即：

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301281355308.png)
- 如何获取session对象：
  - 直接从请求头中获取即可，本来是区分false和true的，但是最好都是直接使用 request.getSession()来获取这里默认就是true
- 重启服务器不会使session失效（存疑docker看起来没有失效）
- 手动设置session失效 session.invalidate();
- 设置session的过期时间：
  - web.xml中的 <session-config> 标签，默认值是30，分钟单位的，全局默认的,
  - 单个session的 setMaxInactiveInterval() 方法
  - 以上两者中的0或者负数都是表示永不过期
- 设置session中的数据 session.setAttribute("count", count);
- 获取session中的数据 session.getAttribute("count");
- 服务器将 SessionID 以 Cookie（Cookie 名称为：“JSESSIONID”，值为 SessionID 的值）的形式发送给客户端浏览器；
- session存在服务器端的数据oom风险，但是安全性高点


```shell
 ~/Documents/Docker_use/servlet_learn  rm -rf servlet_learn*                                                                                                                  ok  base py
 ~/Documents/Docker_use/servlet_learn  cp /Users/XX/IdeaProjects/LEARNING/servlet_learn/target/servlet_learn.war ./                                                   ok  base py
 ~/Documents/Docker_use/servlet_learn  docker restart 0ec7e79b9916
 # 重启之后示例还是正常加数的，然后add2加入了超过20之后归零,add1加入了超过20使失效  -- @TODO存疑
```

示例：写两个servlet，第一个和第二个分别对于session内的数值进行加数+1 和 +2 ，第一个中超过20会使得session失效，第二种超过20会使得计数从0重新开始

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301281504825.png)

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301281505025.png)

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301281505830.png)

注意add1中session id会变化


### filter 过滤器
filter对于response和request两者进行拦截，然后再给到相应的servlet，对两者进行过滤

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301281650959.png)

- 这个filter并不是servlet，只能提供一些过滤的功能
- 过滤器并不是必须要将请求传递到下一个过滤器或目标资源，它可以自行对请求进行处理，并发送响应给客户端，也可以将请求转发或重定向到其他的 Web 资源
- 主要用在用户的权限控制、过滤敏感词、设置统一编码格式
- 开发过滤器要实现 javax.servlet.Filter 接口，并提供一个公开的不带参的构造方法
- 主要的开发过程（web.xml）：
  - 写好filter实现filter接口
  - 重写doFilter方法：注意这里是一个方法被调用两次的，response调用，request也会去调用
  - 在web.xml中注册 name url_pattern mapping等
- filter的声明周期
  - 初始化（随同容器一起被初始化）
  - 拦截（理解进入servlet之前和servlet返回response之后）
  - 销毁（容器停止）
  - 分别对应三个方法
- filter的放行
  - 直接调用filterChain.doFilter(servletRequest, servletResponse)
- 多个filter组成filter chain的和执行的先后顺序（不使用注解的方式）
  - filter chain是个接口
  - 每个filter中的入口中有个filter chain
  - 返回的话按照调用栈的出栈顺序来
  - ***filter的顺序通过web.xml filter-mapping的声明顺序来指定*** 其余的filter都是正常写，然后按照web.xml中的顺序来，<filter-mapping> 靠前，则 Filter 先执行，靠后则后执行
- 多个filter组成filter chain的和执行的先后顺序（使用注解的方式）
  - 这里因为不存在对应的web.xml 所以也不存在声明的顺序
  - 这种情况下，想要控制filer的执行顺序可以通过控制filter的文件名来控制
  - 底层通过HashMap存储，key值即filterName值 
  - 示例程序是： [MyFirstFilter.java](src%2Fmain%2Fjava%2FMyFirstFilter.java) [MyFirstFilter2.java](src%2Fmain%2Fjava%2FMyFirstFilter2.java)
  - 两个都是对于一个urlPattern的过滤器，然后顺序上按照filterName的来定义执行的顺序

示例：
[MyFirstFilter.java](src%2Fmain%2Fjava%2FMyFirstFilter.java) [MyFirstFilterServlet.java](src%2Fmain%2Fjava%2FMyFirstFilterServlet.java)

filter中设置response中的编码为utf-8，设置之前![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301281711887.png)

设置之后可以正常显示中文字符

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301281823908.png)


### WebFilter 注解
可以看下源代码,value、urlPatterns、servletNames 三者必需至少包含一个，且 value 和 urlPatterns 不能共存，如果同时指定，通常忽略 value 的取值 ),一般来说指定一个就可，其实就是本质上指定需要作用在那个servlet上

note：实现Filter接口时候，不能直接使用快速生成代码的重写方法，要把init和destroy() 里面的super都删掉，当然不删destroy中的依旧可以启动；否则会报错 启动 java.lang.AbstractMethodError:javax.servlet.Filter.init 错误

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20230128182753.png)


### 监听器 listener
用于监听另外一个java对象被创建，方法被调用，属性发生改变，然后调用监听器的指定方法

- 