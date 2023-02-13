### 回答这些问题
- MyBatis整体的需要的配置文件
- MyBatis查询功能的实现（特殊需要注明查询出来类对象）
- 核心配置文件的具体的标签 note: 包的形式引入mappers


### MyBatis 简介
- 基于java的持久层框架
- 提供的持久层框架包括SQL Maps和Dat a Access Objects（DAO）

### MyBatis特性
- 支持定制化sql，存储过程以及高级映射的优秀的持久层框架
- 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集
- 可以使用简单的XML或注解用于配置和原始映射，将接口和Java的POJO（Plain Old Java Objects，普通的Java对象）映射成数据库中的记录
- 是一个半自动的ORM框架

### 和其他的持久层技术的对比
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302101704345.png)

### 核心文件
- mybatis-config.xml 放置在 src/main/resources目录下
- mybatis使用核心的文件，这个核心文件不需要固定命名，整合spring时候是可以省略的
- 这个文件用于配置连接的数据库环境和全局配置 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302101823977.png)
- 本次使用的mysql8

### 关于mapper接口和映射文件
- MyBatis中的mapper接口相当于以前的dao。但是区别在于，mapper仅仅是接口
- 一张表对应一个实体类，对应一个mapper接口，对应一个映射文件，mapper接口中的一个方法，对应映射文件中的一个sql，id理应是对齐的
- 表中的字段对应类中的属性
- 一个类对象对应一个记录
- MyBatis映射文件用于编写SQL，访问以及操作表中的数据
- MyBatis映射文件存放的位置是src/main/resources/mappers目录下
- 表所对应的实体类的类名+Mapper.xml[UserMapper.xml](src%2Fmain%2Fresources%2Fmappers%2FUserMapper.xml)
- mapper接口和映射文件的对应关系要求：
  - mapper接口的全类名和映射文件的命名空间（namespace）保持一致
  - mapper接口中方法的方法名和映射文件中编写SQL的标签的id属性保持一致
  - 即 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302101822589.png)

### 面向接口编程
- 接口本来是不能进行实例实例化

### 封装工具类[SqlSessionUtil.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Futils%2FSqlSessionUtil.java)
- 因为去读核心配置文件是共同的，所以可以封装为工具类
- 流程是固定的


### 使用Mybatis的主要流程是
- 定义XXXmapper接口
- 定义XXXMapper.xml
- 在主配置文件中配置开启 XXXMapper.xml
- 在mapper中写好接口方法 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302121134121.png)
- 在XXXMapper.xml配置和这个方法对应sql，最好在标签之前配置下对应的类名和返回值 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302121135889.png)


### 关于查询结果映射的情况
- 如果是查询出来的是单的已经定义好的对象的话就是 使用 select 标签中的resultType对象 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302121246645.png)


### 核心配置文件的结构
- [mybatis-config.xml](src%2Fmain%2Fresources%2Fmybatis-config.xml)
- 事务管理器选项
- dataSource标签
- 如何使用properties文件
  - 引入文件    <properties resource="jdbc.ssm.properties"/>
  - ${VAL_NAME}来使用  <property name="url" value="${jdbc.ssm.url}"/> 
- typeAliases 标签 注意顺序
  - 在[mybatis-config.xml](src%2Fmain%2Fresources%2Fmybatis-config.xml)核心配置文件中声明别名 <typeAlias type="org.example.pojo.User" alias="self_user_df"></typeAlias> 不写的也是存在别名的，类的名不区分大小写
  - 在mapper文件中[UserMapper.xml](src%2Fmain%2Fresources%2Fmappers%2FUserMapper.xml) 可以使用     <select id="getAllUser" resultType="self_user_df">
  - 上面的方式是单个的类的对应关系
  - 下面的是按照包来定义别名，指定了包下的所有的类都有了自己的别名
  - <package name="org.example.pojo"/>
- mappers 标签
  - ```<mapper>``` 标签定义单个的mapper.xml 和 mapper接口的对应关系
  - 使用包的形式建立一个路径专门存储mapper.xml
    - ```<package name='org.example.mapper'> ``` 然后还需要在resources下面建立的这个路径
    - 保持UserMapper.xml 和 UserMapper 这个java文件同名
    - 底层可以从target下面看到类似的结构：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302122144175.png)
    - 以包的方式引入映射文件，但是必须满足两个条件：
    - 1、mapper接口和映射文件所在的包必须一致
    - 2，mapper接口的名字和映射文伴的名字必须一致


### mybatis获取参数的方式
1. 单个参数的情况
- mapper接口中写[UserMapper.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Fmapper%2FUserMapper.java) ```   User getUserByName(String username); ```
- 使用${}获取变量 在maper.xml中写啥名字都可，但是最好是见名知意
``` 
    <!--User getUserByName();-->
    <select id="getUserByName" resultType="User">
        select * from t_user where username = #{username}
    </select>
```
2. 多个参数的情况
- 如果是多个的话 比如实现验证登录的时候mapper文件的接口，User checkLogin(String username,String password);
- mapper.xml中的写法 
```
    <select id="checkLogin" resultType="User">
        select * from t_user where username = #{param1} and password = #{param2}
    </select>
     或者是
         <select id="checkLogin" resultType="User">
        select * from t_user where username = #{arg0} and password = #{arg1}
    </select>
```
- 因为默认情况下多个参数的时候会放在一个map集合中，然后使用 param1 或者 arg0 来作为key，然后实际数据作为value
3. 自己封装一个map作为参数
- mapper接口中写一个接受map的方法 ``` User checkLoginMap(Map<String,String> M); ```
- 在mapper.xml中设置直接使用username,password的 [UserMapper.xml](src%2Fmain%2Fresources%2Forg%2Fexample%2Fmapper%2FUserMapper.xml)
4. 传入的数据是单个类对象 User
- [UserMapper.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Fmapper%2FUserMapper.java) 中配置 void insertUserByUserObj(User user);
- 然后在mapper.xml中配置
``` 
    <!--void insertUserByUserObj(User user);-->
    <insert id="insertUserByUserObj">
        insert into t_user
        values (null, #{username}, #{password}, #{age}, #{sex}, #{email})
    </insert>
```

    