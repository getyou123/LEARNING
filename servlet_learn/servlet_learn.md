### servlet_learn 的相关记录

- 第一个servlet
- 如何配置web.xml中的数值给到程序来使用
- servlet的工作流程
- HttpServletRequest实例中获取信息
- 获取HttpServletRequest中的表单信息
- tomcat的目录和请求url的对应关系
todo
- servlet的请求转发
- response接口
- cookie
- session
- filter
- servlet监听器




### 什么是servlet
Server Applet的简称，也就是服务程序；Servlet是对请求作出处理的一种组件；
servlet 其实是在传统的b/s 或者c/s架构中实现提供服务功能的程序，即：

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201609824.png)

本质上是sun公司提供的一个servlet接口，要求java程序遵循其中的开发规范，帮助我们处理http请求并返回响应

***其主要任务就是***：
- 读取客户端发送来的数据，如表单数据
- 读取client发送来的请求数据
- 生成结果
- 向客户端返回文档
- 发送http响应数据

### servlet的生命周期

- 加载和实例化
- init初始化
- 处理请求service
- destroy

### 实现servlet的三种方式

- javax.servlet.Servlet
- javax.servlet.GenericServlet
- javax.servlet.http.HttpServlet

这三个是依次继承过来的

### HttpServlet的主要实现步骤
- 一类：主要是需要编写相应的类，实现HttpServlet中的两个主要的方法doGet 和 doPost针对前端的get 和 post方法来做出响应[MyFirstServlet.java](src%2Fmain%2Fjava%2FMyFirstServlet.java)
- 二配：就是在项目下的web.xml 文件中配置Servlet。配置好这个servlet的name和url地址（这个就是配置了url和对应的servlet的映射关系）[web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml)
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

note:这个参数是给到具体的servlet的，所以是在servlet这个配置项下面的
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201351858.png)

经过上面的配置，在servlet中能够调用getServletConfig().getInitParameter("url")获得参数名对应的值

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201605766.png)


### web.xml配置中的细节
- 支持多个url映射同一个servlet
- 支持通配符，单纯的 *
- 支持*.do 类似的模式url

### servlet的线程安全问题
servlet本身是单例的，web服务器会为每个请求开一个线程，这个线程会去调用service方法，如果service访问了同一个资源，可能存在线程安全问题；

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


### 关于重定向（Redirect）
什么是重定向呢