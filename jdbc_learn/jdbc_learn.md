### 几个文件说明
- [JDBCDemo1.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FJDBCDemo1.java) 简单的demo，注意这里使用的Statement
- [JDBCUtil1.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FJDBCUtil1.java) 简单封装的获取connection和释放资源
- [PrepareStatementLearn.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FPrepareStatementLearn.java) 使用prepareStatement
- [ResultSetsGet.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FResultSetsGet.java) 结果集操作
- [BatchProcess.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FBatchProcess.java)  批处理例子
- [AppDruidL.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FAppDruidL.java) 基础的从druid获取jdbc connect
- [JDBCUtilDruid.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FJDBCUtilDruid.java) 简单封装的一个druid连接池util
- [ApacheDBUtil.java](src%2Fmain%2Fjava%2Forg%2Fjdbc_basic%2FApacheDBUtil.java) 一个使用apache DBUtil + druid连接池的例子,把返回结果封装为Bean


#### 主要内容总结
- 如何防止sql注入
- 如何实现事务
- 如何进行批处理
- 使用druid连接池关联连接
- 自己封装的一个jdbcUtil[JdbcUtilImperialCourt.java](..%2Fmaven_learn%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgetyou123%2Fimperial%2Fcourt%2Futil%2FJdbcUtilImperialCourt.java)



#### jdbc的主要作用
- 就是访问各种数据的统一一个规范入口，比如mysql oracle db2等
- jdbc可以访问所有提供了jdbc接口的数据库
- 不一致就加一层 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301071136983.png)

### jdbc主要步骤
- 获取链接
- preStatement准备
- 执行sql
- 关闭释放资源

### sql注入和通过prepareStatement避免sql注入
SQL 注入就是在用户输入的字符串中加入 SQL 语句，如果在设计不良的程序中忽略了检查，那么这些注入进去的 SQL 语句就会被数据库服务器误认为是正常的 SQL 语句而运行，攻击者就可以执行计划外的命令或访问未被授权的数据。
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301072140923.png)

### 关于mysql的事务的操作
事务是为了绑定操作序列为原子操作，不存在完成一部分而没有完成另外的部分的情况，要么都成功，要么都失败
- 关闭自动提交
- try catch 
- 自己控制提交

### jdbc的批处理应用
- 前提是对于url增加参数：要批量执行的话，JDBC连接URL字符串中需要新增一个参数：rewriteBatchedStatements=true


#### 为啥要有数据库连接池
- 一个池子管理着多个数据库连接，要用即拿，不用放回，效率高
- 省略了底层的现用现去连接
- 注意conn.close其实是把还回去到连接池中，下次获取时候会被重复使用
  即：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301071139986.png)

#### 常见的数据库连接器
主要有
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301071114662.png)
还是使用druid最好,druid的配置文件的几个参数


#### druid的配置文件的说明
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301081956757.png)
 其中包含了使用连接池的注意点
- 设置初始的池子的大小
- 设置最大池子的大小
- 设置最小的池子的大小
- 获取等待超时的时间
即： 
```shell # druid的使用
  driverClassName=com.mysql.jdbc.Driver
  url=jdbc:mysql://localhost:3306/db_imperial_court
  username=root
  password=123456
  # 初始化连接池的大小
  initialSize=10
  # 最大连接池的数量
  maxActive=20
  # 最小的空闲连接数
  minIdle=5
  #最大的等待获取链接的时间
  maxWait=10000
```

### Apache DBUtils 这个是封装的更好的一个工具类
这个工具类实现的是：
- 实现查询结果到类对象的转化，转为一个list ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301090002627.png)
- 其实重复操作就是： 遍历结果集合封装为一个list，其中的主要操作包括：
  - ArrayHandler:把结果集中的第一行数据转成对象数组。
  - ArrayListHandler:把结果集中的每一行数据都转成一个数组，再存放到List中。
  - BeanHandler:将结果集中的第一行数据封装到一个对应的JavaBean实例中。
  - BeanListHandler:将结果集中的每一行数据都封装到一 个对应的JavaBean实例中，存放到List里。
  - ColumnListHandler:将结果集中某一列的数据存放到List中。
  - KeyedHandler(name):将结果集中的每行数据都封装到Map里，再把这些map再存到一个map里，其key为指定的key.
  - MapHandler:将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。
  - MapListHandler:将结果集中的每一行数据都封装到一个Map里，然后再存放到List
可以看下底层源码情况


