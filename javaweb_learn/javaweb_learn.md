## 一个简单实现web的项目

持久化层
表述层

三个层次 
- 自己使用java web 底层的数据打包war包进行运行展示页面，页面使用thymeleaf，自己封装jdbc工具类
- 使用SSM框架
-  使用springboot

回答这些问题：
- 如何控制请求到响应返回都是在一个事务控制下呢？
- 如何搭配每个request来使用连接池呢？
- [TransactionFilter.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Ffilter%2FTransactionFilter.java) 的作用
- 对于每个请求，如何在filter中排除静态资源的请求呢？
- 封装工具类 [JdbcUtilImperialCourt.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Futil%2FJdbcUtilImperialCourt.java)中的TreadLocal是怎样工作的？
- 如何组织各个模块，比如登录，注册，登出等呢？
- 显示首页（这个就是登录页面）
- 一个功能的开发的基本步骤，比如是登录的流程和基本步骤是咋样的


### 这个项目里的总结一个功能的开发和基本流转的情况：
- 页面这个不用多说 提交收集相关数据封装到请求里，这之后经过txFilter的过滤，到达指定的servlet上
- 这个项目只配置了一个filter，这个txFilter的功能主要是获取数据库连接然后实现事务操作，之后的请求会打在指定的servlet上
- servlet的层次
  - [ViewBaseServlet.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservlet%2Fbase%2FViewBaseServlet.java) 继承这个类，所有的servlet类具有处理视图信息的能力
  - [ModelBaseServlet.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservlet%2Fbase%2FModelBaseServlet.java) 进一步继承 ViewBaseServlet，其实是接收到请求，然后按照method去调用servlet的指定方法
  - 其他模块的servlet的开发
    - 第一，要继承ModelBaseServlet，然后开发自己的逻辑，这样servlet就具有页面展示和按照method方法是去调用指定的方法的能力
    - 第二，开发自己的逻辑，主要是去调用[service](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservice)里面的服务
  - service的开发：这个是服务于servlet的
    - 进一步划分为api 和 impl ，impl 持有对应的 Dao的实现
    - 主要的逻辑控制在
  - DAO的开发： 这个是服务于service的
    - BaseDao具有数据的数据库sql的增删改查的基础功能
    - 每个Dao继承BaseDao实现每个Dao api的接口


### 包结构介绍
- [dao](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fdao) 这个是实现数据库操作的，也是需要一个总的basedao封装需要的基本的操作
- [entity](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fentity) 定义需要的实体类
- [exception](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fexception) 异常的定义
- [filter](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Ffilter)  实现filter的操作
- [service](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservice) service配置
- [servlet](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservlet) servlet
- [util](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Futil) 项目中使用的一些工具的集合


### 关于dao包的结构说明
[BaseDao.java](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fdao%2FBaseDao.java) 这个是所有的dao的基类，其中通过模板类定义了对于数据的基础操作，主要包括：
- 基础的增删改查
- 查询返回单个bean对象
- 查询返回多个bean对象，返回一个list
- 但是实际操作返回的数据类型是不定的
- 持有一个queryRunner，接受参数等执行sql

***注意这里需要所有的实体类必须要有一个无参的构造函数***![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301161348879.png)https://blog.csdn.net/weixin_42422429/article/details/81671648

dao包中baseDao声明和实现了基础的查询操作，其中的类型目前是保留了模板的类， 其他的子dao需要：
- 继承BaseDao，指明其中的模板类为子类的对象，类似于 ``` public class EmpDaoImpl extends BaseDao<Emp> { }```
- 实现具体的业务代码，即实现对应的接口  ``` implements EmpDao  ```

### 关于filter的说明
整体的filter的工作模式![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171021864.png)

[TransactionFilter.java](src%2Fmain%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Ffilter%2FTransactionFilter.java) 这个文件的说明
- 这个文件主要是在filter这层增加，获取数据连接和执行servlet时候都需要的事务操作的封装，这个搭配后面的所有的内容都要出错就抛出runTimeException
- 这个其中包含了对于所有的请求request的filter，因为在[web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml)中配置了所有的都需要过 TransactionFilter这个filter![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171018938.png)
- 基于上面一点，需要在TransactionFilter中配置排除请求静态资源的逻辑，因为访问静态资源不需要访问数据库![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171019302.png)

### 如何实现事务控制呢
实现事务的代码主要有
```java
class c {
try
    {
        // 1、获取数据库连接
        connection = JdbcUtilImperialCourt.getConnection();
        // 重要操作：关闭自动提交功能
        connection.setAutoCommit(false);
        // 2、核心操作
        filterChain.doFilter(servletRequest, servletResponse);
        // 3、提交事务
        connection.commit();
    } catch(Exception e){
        try {
            // 4、回滚事务
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // 页面显示：将这里捕获到的异常发送到指定页面显示
        // 获取异常信息
        String message = e.getMessage();

        // 将异常信息存入请求域
        request.setAttribute("systemMessage", message);

        // 将请求转发到指定页面
        request.getRequestDispatcher("/").forward(request, servletResponse);
    } finally{

        // 5、释放数据库连接
        JdbcUtilImperialCourt.releaseConnection(connection);

    }
}
```
可以看出需要保持是同一个的事务的连接，实现方法上可以将数据库连接绑定到当前线程，思路上就是先从本地线程获取connection然后再去datasource中获取；

还有一个重要的点：必须控制事务的范围，事务过程中的任务异常都要直接抛出，不要做提前的catch![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171051934.png)


### 关于ThreadLocal 
当你在一个类中使用 static 成员变量时，一定要问自己这 个 static 成员变量需要考虑“线程安全吗？”（也就是说多个 线程需要自己独立的 static 成员变量吗？）如果需要那就需 要使用 ThreadLocal。
https://www.cnblogs.com/fsmly/p/11020641.html
其实就是每个线程会维护自己的变量副本，服务于线程的安全问题
https://zhuanlan.zhihu.com/p/102744180
其中的主要方法 https://www.jianshu.com/p/6fc3bba12f38
其实就是一个ThreadLocalMap然后每个进程作为key然后获取其中的数据，键值为当前ThreadLocal变量，value为变量副本（即T类型的变量）
ThreadLocal在数据库连接池获取中的使用方式：
在dbutil中，获取链接的时候，先获取当前的线程作为key，然后再从TreadLocal中获取，不行的话再从datasource中获取
[为啥需要绑定到线程上](#jump)

```java
   class c {
    // 1、尝试从当前线程检查是否存在已经绑定的 Connection 对象
    connection = threadLocal.get();
    // 2、检查 Connection 对象是否为 null
    if(connection == null) {
        // 3、如果为 null，则从数据源获取数据库连接
        connection = dataSource.getConnection();
        // 4、获取到数据库连接后绑定到当前线程
        threadLocal.set(connection);
    }
}
```

### 什么是前后端分离
***服务器端渲染***：
熟悉的JSP，还有Velocity、Freemarker、Thymeleaf等视图模板技术。虽然具体语法各不相同，但是它们都有一个共通的特点，就是在固定内容中可以穿插表达式等形式的动态内容。将视图模板中的动态内容转换为对应的Java代码并执行，然后使用计算得到的具体数据替换原来的动态部分。这样整个文件的动态内容就可以作为确定的响应结果返回给浏览器。在这种模式下，前端工程师将前端页面全部开发完成，交给后端程序员加入到项目中。此时不可避免的需要后端程序员根据需要对前端代码进行补充和调整。
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171058510.png)
note：这里是前端开发完了，然后再让后端介入，后端是在前端工程师的基础上进行加工的;总结来说就是在页面上的指定位置的数据进行替换即可

***前后端分离***：
前端程序和后端程序使用JSON格式进行交互，所以项目启动时前端工程和后端工程师需要坐在一起开会，商量确定JSON格式的具体细节。然后分头开发。后端工程师在把后端的代码发布到测试服务器前，前端工程师无法调用后端程序拿到真实数据，所以使用Mock.js生成假数据。直到后端工程师开发完成，后端程序发布到了测试服务器上，前端工程师再从Mock.js切换到实际后端代码。![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171100952.png)
- 前端专注于展示后端给到的数据，做好填充，和后端只是约定格式的数据（一般是json）交互；
- 好处在于解耦，减少后端的开发工作量，不然后端还得继续渲染好页面给前端

### 关于视图层中的thymeleaf的使用
这是一个服务器渲染的引擎；这块的代码都是公用的，主要说明下其实就是把需要填空的地方使用这个模板进行填空


### 关于逻辑视图和物理视图
- 逻辑视图是指访问地址中除了前缀和后缀之外的东西 地址 = 前缀 + 逻辑视图 + 后缀
- 物理视图是指具体的页面的各个html页面或者jsp等页面
- javaWeb这个实际构建的就是从逻辑地址返回指定的物理地址的这个映射

note:放在WEB-INF这个是为了不让直接使用浏览器访问，只能通过java代码访问，这个是个最佳实践，经验总结之处

### 关于servlet
[servlet_learn.md](..%2Fservlet_learn%2Fservlet_learn.md)着重理解下其中的servlet的线程安全问题，就能理解数据库连接绑定到当前线程， <span id="jump">为啥需要绑定到线程上</span>

### web.xml元素的顺序需要注意下
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171122968.png)

### ModelBaseServlet文件说明：
- 这个所有的登录 注册模块的基类的servlet，这类继承了 ViewBaseServlet
- 他们的关系如图：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301291402568.png)
- 这个类扩展了 httpServlet 本来只是能使用doGet 和 doPost：
  - 每个请求附带一个请求参数，表明自己要调用的目标方法
  - Servlet 根据目标方法名通过反射调用目标方法

### 关于首页
- 首页只是用来展示固定的页面就可
- 只需要继承[ViewBaseServlet.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservlet%2Fbase%2FViewBaseServlet.java) 而不需要继承[ModelBaseServlet.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservlet%2Fbase%2FModelBaseServlet.java)
- 当前项目首页html 位置放在 [index.html](src%2Fmain%2Fwebapp%2FWEB-INF%2Fpages%2Findex.html) ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301291613544.png) 这个index.html中增加了method的参数，写明了提交到哪个servlet(auth)，写明了method(login)的标签
- 首页的配置和流转过程中 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301291653718.png)
- 更详细的调用过程 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301291709793.png)

### 登录功能
登录功能的流程：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301312027906.png)
这个流程细节为
- [index.html](src%2Fmain%2Fwebapp%2FWEB-INF%2Fpages%2Findex.html)
- 按照 [web.xml](src%2Fmain%2Fwebapp%2FWEB-INF%2Fweb.xml) 跳转到[TransactionFilter.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Ffilter%2FTransactionFilter.java) 进一步到[AuthServlet.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservlet%2Fmodule%2FAuthServlet.java)
- 其实登录的最终结果就是在session中的存储了emp对象

### 退出功能
- 也是[AuthServlet.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Fservlet%2Fmodule%2FAuthServlet.java)中实现sesion失效
- 然后转到首页
- 开发过程也是：
  - 页面 指定好参数
  - servlet中完成逻辑
  - 写好接下来的跳转的方向

### 显示列表


### 显示某个数据详情


### 更新数据的已读和未读状态



### 回复信息



### 登录检测




### maven 命令总结
```shell
mvn clean package -DskipTests # 跳过测试



```


## 大数据应用常用打包方式
通过maven将项目打成 JAR 包的常用打包方式如下：
- 不在pom中加任何插件，直接使用 mvn package 打包；这个存在局限性，只有不使用任何的第三方的jar才可，不过可以通过 spark 提交时候的--jars 来补充，不过这存在版本不一致风险；所以最好是把所有的依赖都打包到一个jar中，直接提交这个jar是最好的，即 ALL IN ONE
- 使用 maven-assembly-plugin 插件：
- 使用 maven-shade-plugin 插件；
- 使用 maven-jar-plugin 和 maven-dependency-plugin 插件；

### 使用 maven-assembly-plugin 插件
https://github.com/heibaiying/BigData-Notes/blob/master/notes/%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%BA%94%E7%94%A8%E5%B8%B8%E7%94%A8%E6%89%93%E5%8C%85%E6%96%B9%E5%BC%8F.md


### maven在pom中配置跳过测试
```xml
    <build>
        <finalName>maven_learn</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

### 
todo ： https://zhuanlan.zhihu.com/p/119306367