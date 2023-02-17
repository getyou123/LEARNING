### 回答这些问题
- MyBatis整体的需要的配置文件
- MyBatis查询功能的实现（特殊需要注明查询出来类对象）
- 核心配置文件的具体的标签 note: 包的形式引入mappers接口文件
- 拷贝注释配置mapper.xml
- 如何使用resultMap解决字段对应关系的情况
- 多种查询结果接收
- resultType还是resultMap
- 级联查询的返回和接收
- mybatis的逆向工程
- 如何实现分页和返回页面的更多的信息


### 依赖数据的sql
``` 
库的话ssm mysql版本8

CREATE  table ssm.t_emp(
emp_id bigint COMMENT "自增id",
emp_name varchar(20) COMMENT "用户名",
sex  varchar(20) COMMENT "性别",
dept_id bigint COMMENT "部门id"
);

INSERT INTO ssm.t_emp (emp_id,emp_name,sex,dept_id) VALUES
	 (1,'admin1','男',1),
	 (2,'columns_priv','男',1),
	 (3,'huizi','男',2);
	 
CREATE  table ssm.t_dept(
dept_id bigint COMMENT "自增id",
dept_name varchar(20) COMMENT "部门名"
);

INSERT INTO ssm.t_dept (dept_id,dept_name) VALUES
	 (1,'数据智能部');

```


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
- 表所对应的实体类的类名+Mapper.xml [UserMapper.java](mybatis_basic%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Fmapper%2FUserMapper.java)
- mapper接口和映射文件的对应关系要求：
  - mapper接口的全类名和映射文件的命名空间（namespace）保持一致
  - mapper接口中方法的方法名和映射文件中编写SQL的标签的id属性保持一致
  - 即 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302101822589.png)

### 面向接口编程
- 接口本来是不能进行实例实例化

### 封装工具类[SqlSessionUtil.java](mybatis_basic%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Futils%2FSqlSessionUtil.java)
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
- [mybatis-config.xml](mybatis_basic%2Fsrc%2Fmain%2Fresources%2Fmybatis-config.xml)
- 事务管理器选项
- dataSource标签
- 如何使用properties文件
  - 引入properties文件  ```<properties resource="jdbc.ssm.properties"/>```
  - ${VAL_NAME} 来使用  ```<property name="url" value="${jdbc.ssm.url}"/> ```
- typeAliases 标签 注意顺序
  - 在[mybatis-config.xml](mybatis_basic%2Fsrc%2Fmain%2Fresources%2Fmybatis-config.xml)核心配置文件中声明别名 ```<typeAlias type="org.example.pojo.User" alias="self_user_df"></typeAlias>``` 不写的也是存在别名的，类的名不区分大小写
  - 在mapper文件中 [UserMapper.java](mybatis_basic%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Fmapper%2FUserMapper.java) 可以使用    ```<select id="getAllUser" resultType="self_user_df"> ```
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
- mapper接口中写[UserMapper.java](mybatis_basic%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Fmapper%2FUserMapper.java)  ```   User getUserByName(String username); ```
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
- 在mapper.xml中设置直接使用username,password的 [UserMapper.xml](mybatis_basic%2Fsrc%2Fmain%2Fresources%2Forg%2Fexample%2Fmapper%2FUserMapper.xml)
4. 传入的数据是单个类对象 User
- [UserMapper.java](mybatis_basic%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Fmapper%2FUserMapper.java) 中配置 void insertUserByUserObj(User user);
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
比如[Emp.java](mybatis_basic%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Fpojo%2FEmp.java) 中定义的字段是按照驼峰命名的，而在mysql中是按照_ 来作为区分的：
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


### 一个对象持有多个另外的对象（一对多的情况）
- 这里演示一个部门中有多个员工
``` 
类定义：
@Data
public class Dept {
    private Integer deptId;
    private String deptName;
    private List<Emp> emps;
}
```
1. 级联方式
```
mapper.xml中配置：
    <!--  Dept getDeptAndAllEmpByDeptId(@Param("dept_id") Integer deptId);-->
    <resultMap id="getDeptByDeptIdMap" type="Dept">
        <!-- id设置的是主键的 -->
        <id property="deptId" column="dept_id"></id>
        <result property="deptName" column="dept_name"></result>
          <!--
            ofType:设置collection标签所处理的集合属性中存储数据的类型 
          -->
        <collection property="emps" ofType="Emp">
            <id property="empId" column="emp_id"></id>
            <result property="empName" column="emp_name"></result>
            <result property="sex" column="sex"></result>
        </collection>
    </resultMap>
    <select id="getDeptAndAllEmpByDeptId" resultMap="getDeptByDeptIdMap">
        select t_emp.*,
               t_dept.*
        from t_dept
                 join t_emp
                      on t_dept.dept_id = t_emp.dept_id
        where t_dept.dept_id = #{dept_id}
    </select>

```
2. 分步查询
- 先查询dept获取部门信息 getDeptByDeptId
- 然后再查询员工表emp rg.example.mapper.EmpMapper.getEmpListByStep封装到emps属性中
```
    <!-- Dept getDeptAndAllEmpByDeptIdStep(@Param("dept_id") Integer deptId);-->
    <resultMap id="getDeptByDeptIdMapStep" type="Dept">
        <!-- id设置的是主键的 -->
        <id property="deptId" column="dept_id"></id>
        <result property="deptName" column="dept_name"></result>
        <collection property="emps"
                    fetchType="eager"
                    select="org.example.mapper.EmpMapper.getEmpListByStep" column="dept_id">
        </collection>
    </resultMap>
    <select id="getDeptAndAllEmpByDeptIdStep" resultMap="getDeptByDeptIdMapStep">
        select *
        from t_dept
        where dept_id = #{dept_id}
    </select>
```


### 动态sql
Mybatis框架的动态SQL技术是一种根据特定条件动态拼装SQL语句的功能，它存在的意义是为了解决拼接SQL语句字符串时的痛点问题
- 比如页面上的多个筛选条件
- 注意临界的默认值
- 比如dataBank上的筛选条件
- if
- where
- choose when otherwise
- sql include
- trim
- foreach
### if 标签
```XMl
    <!--List<Emp> getEmpListByCondition(Emp emp);-->
    <select id="getEmpListByCondition" resultType="Emp">
        select * from t_emp where 1=1
        <if test="empName != '' and empName != null">
            and emp_name = #{empName}
        </if>
        <if test="sex != '' and sex != null">
            and sex = #{sex}
        </if>
    </select>
```
这里是为了防止sql的拼接错误，防止出现 select * from t_emp where and XXX


### where + if
```xml
    <!--List<Emp> getEmpListByCondition(Emp emp);-->
    <select id="getEmpListByCondition" resultType="Emp">
        select * from t_emp
        <where>
            <if test="empName != '' and empName != null">
                and emp_name = #{empName}
            </if>
            <if test="sex != '' and sex != null">
                and sex = #{sex}
            </if>
        </where>
    </select>
```
最好所有if标签都要加上 and 或者 or，原因如下：
- 第一，只有if标签有内容的情况下才会插入where子句；
- 第二，若子句的开头为 “AND” 或 “OR”，where标签会将它替换去除；
- 第三，where 中的if不能加注释 
``` 
if test="idNo != null and idNo != ''">
        /* and id_no = #{idNo}*/
        and id_no = #{idNo}
      </if>
```
是不允许的会出现错误

#### trim标签
trim用于去掉或添加标签中的内容，这个是替换trim标签的位置的，常用属性: 
- prefix:在trim标签中的内容的前面添加某些内容 
- prefixOverrides:在trim标签中的内容的前面去掉某些内容 
- suffix:在trim标签中的内容的后面添加某些内容 
- suffixOverrides:在trim标签中的内容的后面去掉某些内容
- trim实现where标签的功能 
```
    <!--List<Emp> getEmpListByCondition(Emp emp);-->
    <select id="getEmpListByCondition" resultType="Emp">
        select * from t_emp
        <trim prefix="where" prefixOverrides="and|or">
            <if test="empName != '' and empName != null">
                and emp_name = #{empName}
            </if>
            <if test="sex != '' and sex != null">
                and sex = #{sex}
            </if>
        </trim>
    </select>
```
#### choose when otherwise
- choose是父标签
- 这三个标签功能上是 if ... elif .. else
- 生效的只有一个when标签，生效存在先后顺序
``` 
    <select id="getEmpListByCondition" resultType="Emp">
        select * from t_emp
        <where>
            <choose>
                <when test="empName != '' and empName != null">
                    and emp_name = #{empName}
                </when>
                <when test="sex != '' and sex != null">
                    and sex = #{sex}
                </when>
            </choose>
        </where>
    </select>
```

### foreach标签
- 注意separator 这个字段是配置元素之间的分隔符，批量添加中是逗号，但是批量删除中是 or
- 批量添加
``` 
    <!--void insertMultiEmps(List<Emp> emps);-->
    <insert id="insertMultiEmps">
        insert into t_emp values
        <foreach collection="emps" item="emp" separator=",">
            (#{emp.empId},#{emp.empName},#{emp.sex},null)
        </foreach>
    </insert>
```
- 批量删除 两种方式
```
   <!-- void deleteMultiEmps(@Param("emps") int[] emps);-->
    <delete id="deleteMultiEmps">
        delete from t_emp where
        <foreach collection="emps" item="empId" separator="or">
                emp_id = #{empId}
        </foreach>
    </delete>
    
    ======== 或者 指定自定义的开始和结束字符
    
     <delete id="deleteMultiEmps">
        delete from t_emp where emp_id in
        <foreach collection="emps" item="empId" separator="," open="(" close=")">
                #{empId}
        </foreach>
    </delete> 
```

### sql片段
- sql片段，可以记录一段公共sql片段，在使用的地方通过include标签进行引入
```
<sql id="empColumns">
    eid,ename,age,sex,did
</sql>

  select <include refid="empColumns"></include> from t_emp
```

### mybatis一级缓存
- 默认开启的，直接使用
- 一级缓存是SqlSession级别的，通过同一个SqlSession查询的数据会被缓存，
- 下次查询相同的数据，就会从缓存中直接获取，不会从数据库重新访问
``` 
下面的两次查询中的第二次查询实际就是从sqlSession获取的而不是从数据库中获取的
 User User1 = userMapper.getUserByName("admin1");
 System.out.println(User1);
 User User2 = userMapper.getUserByName("admin1");
 System.out.println(User2);    
```
- 一级缓存失效的情况：即不从sqlSession缓存中获取
  - 不同的SqlSession对应不同的一级缓存
  - 同一个SqlSession但是查询条件不同
  - 同一个SqlSession两次查询期间执行了任何一次增删改操作 （会清空缓存）
  - 同一个SqlSession两次查询期间手动清空了缓存

### mybatis二级缓存
- 二级缓存是SqlSessionFactory级别，通过同一个SqlSessionFactory创建的SqlSession查询的结果会被缓存;
- 此后若再次执行相同的查询语句，结果就会从缓存中获取
- 默认的是不开启
- 需要检查下自己的使用的sqlSessionUtil是不是存在使用不同的sqlSessionFactory对象
- 开启条件：
  - 在核心配置文件中，设置全局配置属性cacheEnabled="true"，默认为true，不需要设置 
  - 在映射文件中设置标签<cache/>
  - 二级缓存必须在SqlSession关闭或提交之后有效 
  - 查询的数据所转换的实体类类型必须实现序列化的接口 
- 使二级缓存失效的情况: 
  - 两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效
- 表现为：
  - 需要关闭SqlSession关闭或者提交才能在二级缓存中进行缓存
  - 可以减少数据库的压力
- 二级缓存的设置参数：
- eviction属性:缓存回收策略，默认的是 LRU。
  - LRU(Least Recently Used) – 最近最少使用的:移除最长时间不被使用的对象。 
  - FIFO(First in First out) – 先进先出:按对象进入缓存的顺序来移除它们。 
  - SOFT – 软引用:移除基于垃圾回收器状态和软引用规则的对象。
  - WEAK – 弱引用:更积极地移除基于垃圾收集器状态和弱引用规则的对象。
- flushInterval属性:刷新间隔，单位毫秒
  - 默认情况是不设置，也就是没有刷新间隔，缓存仅仅调用语句时刷新
- size属性:引用数目，正整数
  - 代表缓存最多可以存储多少个对象，太大容易导致内存溢出
- readOnly属性:只读， true/false
  - true:只读缓存;会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了很重要的性能优势。
  - false:读写缓存;会返回缓存对象的拷贝(通过序列化)。这会慢一些，但是安全，因此默认是false。


### mybatis缓存的生效顺序
- 首先是从二级缓存开始查询是否命中
- 然后是从一级缓存查询是否命中
- 最后是去数据库查询

### mybatis整合第三方缓存
- 使用第三方缓存作为二级缓存
- maven配置
``` 
<!-- Mybatis EHCache整合包 --> <dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.2.1</version>
</dependency>
```
- ehcache.xml配置文件配置 可配置选项 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302170916650.png)
```xml
<?xml version="1.0" encoding="utf-8" ?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:noNamespaceSchemaLocation="../config/ehcache.xsd">
<!-- 磁盘保存路径 -->
<diskStore path="XXXXX"/> <defaultCache
        maxElementsInMemory="1000"
        maxElementsOnDisk="10000000"
        eternal="false"
        overflowToDisk="true"
        timeToIdleSeconds="120"
        timeToLiveSeconds="120"
        diskExpiryThreadIntervalSeconds="120"
        memoryStoreEvictionPolicy="LRU">
    </defaultCache>
</ehcache>
```
- mybatis全局配置文件
``` xml
<cache type="org-mybatis.caches.ehcarhe.EhcacheCachel />
```

### mybatis的逆向工程
正向工程和逆向工程
- 正向工程:java类->数据库表
- 逆向工程:数据库表->java类、mapper接口、mapper.xml配置文件
- 创建逆向工程的步骤：
  - 配置maven [pom.xml](mybatis_reverse_engineer_simple%2Fpom.xml)
  - 创建MyBatis的核心配置文件 [mybatis-config.xml](mybatis_reverse_engineer_simple%2Fsrc%2Fmain%2Fresources%2Fmybatis-config.xml)
  - 创建逆向工程的配置文件 [generatorConfig.xml](mybatis_reverse_engineer_simple%2Fsrc%2Fmain%2Fresources%2FgeneratorConfig.xml)
  - 执行MBG插件的generate目标 
- 关于QBC
  - QBC(Query By Criteria)是一种面向对象的查询方式，这种查询方式以函数API的方式动态地设置查询条件，组成查询语句
```
        EmpExample empExample = new EmpExample();
        EmpExample.Criteria admin1 = empExample.createCriteria().andEmpNameEqualTo("admin1");
        System.out.println("结果：" + admin1);
```
  - 其实就是构造查询的sql然后执行，虽然在我看来这个QBC难用极了
  - 多数都是and语法，也有or的语法

### mybatis 分页功能 
- 分页在sql中对应的就是limit index,pageSize
- 计算起始页面面收第一条数据 index=(pageNum-1)*pageSize
- mybatis使用分页插件：
  - pom配置
``` 
   
```
  - mybatis的核心配置文件
``` 
  <plugins>
  <!--设置分页插件-->
      <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
  </plugins>
```
  - 代码实现
``` 
        // 开启分页
        PageHelper.startPage(1, 2);
        List<Emp> emp1 = mapper.selectByExample(null);
        System.out.println("分页结果：" + emp1);
        // 生成当前页的前面后面3个页面的信息
        PageInfo<Emp> empPageInfo = new PageInfo<>(emp1, 3);
        System.out.println("当前页面大小：" + empPageInfo.getSize());
        System.out.println("当前页面页码：" + empPageInfo.getPageNum());
        // PageInfo还有其他属性 可以通过getXX获取
        /**
         pageNum:当前页的页码
         pageSize:每页显示的条数
         size:当前页显示的真实条数
         total:总记录数
         pages:总页数
         prePage:上一页的页码
         nextPage:下一页的页码
         isFirstPage/isLastPage:是否为第一页/最后一页 hasPreviousPage/hasNextPage:是否存在上一页/下一页 navigatePages:导航分页的页码数
         navigatepageNums:导航分页的页码，[1,2,3,4,5]
         */
```



    