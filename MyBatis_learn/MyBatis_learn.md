### 回答这些问题
- MyBatis整体的需要的配置文件
- MyBatis查询功能的实现（特殊需要注明查询出来类对象）
- 核心配置文件的具体的标签 note: 包的形式引入mappers接口文件
- 拷贝注释配置mapper.xml
- 如何使用resultMap解决字段对应关系的情况
- 多种查询结果接收
- resultType还是resultMap


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
  - 引入properties文件  ```<properties resource="jdbc.ssm.properties"/>```
  - ${VAL_NAME} 来使用  ```<property name="url" value="${jdbc.ssm.url}"/> ```
- typeAliases 标签 注意顺序
  - 在[mybatis-config.xml](src%2Fmain%2Fresources%2Fmybatis-config.xml)核心配置文件中声明别名 ```<typeAlias type="org.example.pojo.User" alias="self_user_df"></typeAlias>``` 不写的也是存在别名的，类的名不区分大小写
  - 在mapper文件中[UserMapper.xml](src%2Fmain%2Fresources%2Fmappers%2FUserMapper.xml) 可以使用    ```<select id="getAllUser" resultType="self_user_df"> ```
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
- 实体类参数
- 单个参数 使用注解
- 多个参数 使用注解

1. 单个参数的情况
- mapper接口中写[UserMapper.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Fmapper%2FUserMapper.java) ```   User getUserByName(String username); ```
- 使用${}获取变量 在maper.xml中写啥名字都可，但是最好是见名知意
- ```#{}```  和 ```${}``` 的区别：
  - ```#{}```  实际是占位符？+''，变量替换的是 ？一些特殊的比如in (#{val})的话会出现错误
  - ```${}```  实际是直接的拼接到，所以in的话不会出错
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
5. 通过注解的方式获取参数
- mapper中设置变量名字 ``` User getUserByPara(@Param("username") String username, @Param("password") String password); ```
- mapper.xml 中配置
``` 
    <!--void getUserByPara(@Param("username") String username, @Param("password") String password);-->
    <select id="getUserByPara" resultType="User">
        select *
        from t_user
        where username = #{username}
          and password = #{password}
    </select>
```

### mybatis查询数据的方式
以下代码展示的是mapper和mapper.xml的关键写法：
1. 根据id查询单个的User getUserById 
  - 如果出现查询出来多个但是方法只给外面吐一个的话就会出现 too many args
``` 
   User getUserByPara(@Param("username") String username, @Param("password") String password);
   
   <!--void getUserByPara(@Param("username") String username, @Param("password") String password);-->
    <select id="getUserByPara" resultType="User">
        select *
        from t_user
        where username = #{username}
          and password = #{password}
    </select> 
```
2. 查询总count或者某行的某个列 
```
    Integer getAllCount();

    <!--   Integer getAllCount();-->
    <select id="getAllCount" resultType="Integer">
        select count(1)
        from t_user
    </select>
```
3. 查出来的单条数据没有实体类来对应，直接作为map存储，即使有对应实体类也是可以查询为map
``` 
   Map<String,Object> getMapFromTable();

    <!--    Map<String,Object> getMapFromTable();-->
    <select id="getMapFromTable" resultType="map">
        select *
        from t_user
        where id = 2
    </select>
```
4. 查询多条数据为map 特殊需要@MapKey("id")指明key为哪个字段
```
    @MapKey("id")
    Map<String, Object> getAllMapAsMapFromTable();
    
    <!--    Map<String, Object> getAllMapAsMapFromTable();-->
    <select id="getAllMapAsMapFromTable" resultType="map">
        select *
        from t_user
    </select>
```
5. 模糊查询 注意 % 的写法
``` 
   List<User> getUserByLikeName(@Param("username") String username);

    <!--    User getUserByLikeName();-->
    <select id="getUserByLikeName" resultType="User">
        select *
        from t_user
        where username like "%"#{username}"%"
         <!--select * from t_user where username like '%${mohu}%'-->
         <!--select * from t_user where username like concat('%',#{mohu},'%')-->
    </select> 
```

### mybatis批量删除
- 这个最好是使用${} 直接拼写
- 当然动态sql也是可以的
- #{}存在问题
```
    void deleteMultiUser(@Param("ids") String ids);
    
    <!--void DeleteMultiUser(@Param("ids") String ids);-->
    <delete id="deleteMultiUser">
        delete
        from t_user
        where id in (${ids})
    </delete>
```

### 动态表名,表名字作为参数进行查询
- 这个也是使用 #{}会出现问题，因为mysql中的表名不要使用'' 包括
``` 
    List<User> getUserList(@Param("tableName") String tableName);

    <!-- List<User>  getUserList(@Param("tableName") String tableName);-->
    <select id="getUserList" resultType="User">
        select *
        From ${tableName}
    </select>
```


### 插入后获取自增主键
- 这个插入之后获取到主键id是jdbc中的功能，不是mybatis的特有的
- Preparedstatement.getGeneratedkeys()； 这个就可以获取到生成的key
- [GetAutoId.java](..%2Fjdbc_learn%2Fsrc%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FGetAutoId.java) 这个是一个示例
- mybatis中
``` 
    int getIdAfterInsert(User user);
    <!-- int getIdAfterInsert(User user);-->
    <insert id="getIdAfterInsert" useGeneratedKeys="true" keyProperty="id">
        insert into t_user
        values (null, #{username}, #{password}, #{age}, #{sex}, #{email})
    </insert>
```
- 上面配置中获取到的id，其实是写回去了用于插入的user对象中 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302151145818.png)
- 否则会报错信息 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302151145012.png)

### 当从mysql中查出的字段名和POJO的命名是对应不上的时候
比如[Emp.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Fpojo%2FEmp.java)中定义的字段是按照驼峰命名的，而在mysql中是按照_ 来作为区分的：
- 处理方法一：在mapper.xml中配置字段的别名为POJO的字段名字
``` 
select emp_name empName from t_emp 其他字段类似
```
- 处理方法二：在mybatis核心配置文件中直接写明为所有的mysql字段和POJO的映射规则，这个是要求了所有使用情况
``` 
<!-- 将下划线映射为驼峰 -->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
```
否则是出现查询的错误的:
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302151428173.png)
---
这里只是sex这个字段单独映射成功了
- 处理方式三：自定义映射 resultMap，这个都是在mapper.xml中配置
``` 
    <!-- 自定义的对应关系名称 映射结果为 Emp类对象-->
    <resultMap id="userMap" type="Emp">
        <!-- id设置的是主键的 -->
        <id property="empId" column="emp_id"></id>
        <!-- result设置普通字段的映射关系 mysql中_ java 对象的是驼峰 -->
        <result property="empName" column="emp_name"></result>
        <result property="sex" column="sex"></result>
    </resultMap>

    <!--Emp getEmpByEmpId(@Param("empId") String empId);-->
    <!--这里的resultMap指明使用resultMap中的Emp 这个type-->
    <select id="getEmpByEmpId" resultMap="userMap">
        select *
        from t_emp
        where emp_id = #{empId}
    </select>
```


### 配置查询结果中持有其他对象的情况（持有一个对象）
- 这种对应的是sql中的join操作的结果，比如 一个国家里有一个首都，都是类对象哈
- 这里演示的是一个emp对象中有其对应的dept
``` java
public class Emp {
    private Integer empId;
    private String empName;
    private String sex;
    private Dept dept;
}
```
1. 级联查询
``` 
    Emp getEmpByEmpId1(@Param("empId") Integer empId);

    <resultMap id="userMap1" type="Emp">
        <!-- id设置的是主键的 -->
        <id property="empId" column="emp_id"></id>
        <!-- result设置普通字段的映射关系 mysql中_ java 对象的是驼峰 -->
        <result property="empName" column="emp_name"></result>
        <result property="sex" column="sex"></result>
        <result property="dept.deptId" column="dept_id"></result>
        <result property="dept.deptName" column="dept_name"></result>
    </resultMap>

    <!--Emp getEmpByEmpId1(@Param("empId") String empId);-->
    <select id="getEmpByEmpId1" resultMap="userMap1">
        select t_emp.*,
               t_dept.*
        from t_dept
                 join t_emp
                      on t_dept.dept_id = t_emp.dept_id
        where t_emp.emp_id = #{empId} and emp_name = 'admin1'
    </select>

```
@TODO 这里的mysql字段的是重复的也没有影响吗？
2. 使用association来处理级联关系
``` 
    Emp getEmpByEmpId2(@Param("empId") Integer empId);

     <resultMap id="userMap2" type="Emp">
        <!-- id设置的是主键的 -->
        <id property="empId" column="emp_id"></id>
        <!-- result设置普通字段的映射关系 mysql中_ java 对象的是驼峰 -->
        <result property="empName" column="emp_name"></result>
        <result property="sex" column="sex"></result>
        <association property="dept" javaType="Dept">
            <id property="deptId" column="dept_id"></id>
            <result property="deptName" column="dept_name"></result>
        </association>
    </resultMap>
    <!-- Emp getEmpByEmpId2(@Param("empId") Integer empId);-->
    <select id="getEmpByEmpId2" resultMap="userMap2">
        select t_emp.*,
               t_dept.*
        from t_dept
                 join t_emp
                      on t_dept.dept_id = t_emp.dept_id
        where t_emp.emp_id = #{empId} and emp_name = 'admin1'
    </select>
```
3. 分步查询 
- 分布查询需要使用到另外的mapper的查询功能情况
- 需要编写另外的类的mapper
- 是懒加载的；延迟加载的
``` 
   Emp getEmpByEmpIdByStep(@Param("empId") Integer empId)

   <!--Emp getEmpByEmpIdByStep(@Param("empId") Integer empId);-->
    <resultMap id="userMapStep" type="Emp">
        <!-- id设置的是主键的 -->
        <id property="empId" column="emp_id"></id>
        <!-- result设置普通字段的映射关系 mysql中_ java 对象的是驼峰 -->
        <result property="empName" column="emp_name"></result>
        <result property="sex" column="sex"></result>
        <association property="dept"
                     select="org.example.mapper.DeptMapper.getDeptByDeptId" column="dept_id">
        </association>
    </resultMap>
    <select id="getEmpByEmpIdByStep" resultMap="userMapStep">
        select t_emp.*,
               t_dept.*
        from t_dept
                 join t_emp
                      on t_dept.dept_id = t_emp.dept_id
        where t_emp.emp_id = #{empId} and emp_name = 'admin1'
    </select>

```

### 分布查询-延迟加载
- 懒加载，即在分步查询的时候，在多个步骤的查询过程中，如果只用了步骤一的查询信息的话，那么就不回去触发后面的查询
- 只有在用到后面的查询的过程中才会触发接下里的查询
- 懒加载通过全局配置"lazyLoadingEnabled"和<association/>的fetchType属性来配置。
- ``` <setting name="lazyLoadingEnabled" value="true" />  ``` 这个是总开关
- 每个关联关系中可单独设置 fetchType="lazy(延迟加载)|eager(立即加载)"


### 一个对象持有多个另外的对象
- 这里演示一个部门中有多个员工
1. 级联方式
2. 分步查询
 
    