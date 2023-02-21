### 回答问题
- mysql的逻辑体系结构 3层是哪三层
- 一条mysql查询语句的执行过程






### mysql5.7 和 mysql8
- 驱动类不同 
  - 5.7 com.mysql.jdbc.Driver
  - 8 com.mysql.cj.jdbc.Driver
- 连接地址
  - 5 jdbc:mysql://localhost:3306/ssm
  - 8 jdbc:mysql://localhost:3306/ssm?serverTimezone=UTC 
    - 需要设置时区否则报错 java.sql.SQLException: The server time zone value 'ÖÐ¹ú±ê×¼Ê±¼ä' is unrecognized 
- 设置相应数据编码

### 数据库的基本概念
- 数据库 本质就是文件的集合
- DBMS 软件层名的概念 管理软件
- sql 结构化查询语言



### select的执行的顺序
```sql
(8)SELECT (9)DISTINCT (11)<Top Num> <select list>
(1)FROM [left_table] 
(3)<join_type> JOIN <right_table> (2) ON <join_condition> (4)WHERE <where_condition> (5)GROUP BY <group_by_list> (6)WITH <CUBE | RollUP> 
(7)HAVING <having_condition> (10)ORDER BY <order_by_list>
```

### select中的distinct的作用范围
是全部字段的，所有的字段的，不是单个的字段的

### LIKE的用法，上次就是这里出现了错误
```
%  百分号表示零个，一个或多个字符
_ 下划线表示单个字符
```

### mysql的终端的登录
mysql 【-h主机名 -P端口号 】-u用户名 -p密码

### sql语言的分类
``` 
DQL（Data Query Language）：数据查询语言
  select
DML(Data Manipulate Language):数据操作语言
  insert 、update、delete
DDL（Data Define Languge）：数据定义语言
  create、drop、alter
TCL（Transaction Control Language）：事务控制语言
  commit、rollback
```

### 常见的数据库对象
- 表
- 视图
- 触发器
- 函数
- 事件

### mysql的各种函数
#### 数学函数
- ABS(x)   返回x的绝对值
- BIN(x)   返回x的二进制（OCT返回八进制，HEX返回十六进制）
- CEILING(x)   返回大于x的最小整数值
- EXP(x)   返回值e（自然对数的底）的x次方
- FLOOR(x)   返回小于x的最大整数值
- GREATEST(x1,x2,...,xn)返回集合中最大的值
- LEAST(x1,x2,...,xn)      返回集合中最小的值
- LN(x)                    返回x的自然对数
- LOG(x,y)返回x的以y为底的对数
- MOD(x,y)                 返回x/y的模（余数）
- PI()返回pi的值（圆周率）
- RAND()返回０到１内的随机值,可以通过提供一个参数(种子)使RAND()随机数生成器生成一个指定的值。
- ROUND(x,y)返回参数x的四舍五入的有y位小数的值
- SIGN(x) 返回代表数字x的符号的值
- SQRT(x) 返回一个数的平方根
- TRUNCATE(x,y)  返回数字x截短为y位小数的结果

#### 聚合函数
- AVG(col)返回指定列的平均值
- COUNT(col)返回指定列中非NULL值的个数
- MIN(col)返回指定列的最小值
- MAX(col)返回指定列的最大值
- SUM(col)返回指定列的所有值之和
- GROUP_CONCAT(col) 返回由属于一组的列值连接组合而成的结果
``` 
select * from aa;  
+------+------+
| id| name |
+------+------+
|1 | 10|
|1 | 20|
|1 | 20|
|2 | 20|
|3 | 200 |
|3 | 500 |

select id,group_concat(name) from aa group by id;  
+------+--------------------+
| id| group_concat(name) |
+------+--------------------+
|1 | 10,20,20|
|2 | 20 |
|3 | 200,500|
+------+--------------------+
3 rows in set (0.00 sec)
```

#### 字符串函数
- ASCII(char)返回字符的ASCII码值
- BIT_LENGTH(str)返回字符串的比特长度
- CONCAT(s1,s2...,sn)将s1,s2...,sn连接成字符串
- CONCAT_WS(sep,s1,s2...,sn)将s1,s2...,sn连接成字符串，并用sep字符间隔
- INSERT(str,x,y,instr) 将字符串str从第x位置开始，y个字符长的子串替换为字符串instr，返回结果
- FIND_IN_SET(str,list)分析逗号分隔的list列表，如果发现str，返回str在list中的位置
- LCASE(str)或LOWER(str) 返回将字符串str中所有字符改变为小写后的结果
- LEFT(str,x)返回字符串str中最左边的x个字符
- LENGTH(s)返回字符串str中的字符数
- LTRIM(str) 从字符串str中切掉开头的空格
- POSITION(substr,str) 返回子串substr在字符串str中第一次出现的位置
- QUOTE(str) 用反斜杠转义str中的单引号
- REPEAT(str,srchstr,rplcstr)返回字符串str重复x次的结果
- REVERSE(str) 返回颠倒字符串str的结果
- RIGHT(str,x) 返回字符串str中最右边的x个字符
- RTRIM(str) 返回字符串str尾部的空格
- STRCMP(s1,s2)比较字符串s1和s2
- TRIM(str)去除字符串首部和尾部的所有空格
- UCASE(str)或UPPER(str) 返回将字符串str中所有字符转变为大写后的结果

#### 时间和日期函数
- CURDATE()或CURRENT_DATE() 返回当前的日期
- CURTIME()或CURRENT_TIME() 返回当前的时间
- DATE_ADD(date,INTERVAL int keyword)返回日期date加上间隔时间int的结果(int必须按照关键字进行格式化),如：SELECTDATE_ADD(CURRENT_DATE,INTERVAL 6 MONTH);
- DATE_FORMAT(date,fmt)  依照指定的fmt格式格式化日期date值
- DATE_SUB(date,INTERVAL int keyword)返回日期date加上间隔时间int的结果(int必须按照关键字进行格式化),如：SELECTDATE_SUB(CURRENT_DATE,INTERVAL 6 MONTH);
- DAYOFWEEK(date)   返回date所代表的一星期中的第几天(1~7)
- DAYOFMONTH(date)  返回date是一个月的第几天(1~31)
- DAYOFYEAR(date)   返回date是一年的第几天(1~366)
- DAYNAME(date)   返回date的星期名，如：SELECT DAYNAME(CURRENT_DATE);
- FROM_UNIXTIME(ts,fmt)  根据指定的fmt格式，格式化UNIX时间戳ts
- HOUR(time)   返回time的小时值(0~23)
- MINUTE(time)   返回time的分钟值(0~59)
- MONTH(date)   返回date的月份值(1~12)
- MONTHNAME(date)   返回date的月份名，如：SELECT MONTHNAME(CURRENT_DATE);
- NOW()    返回当前的日期和时间
- QUARTER(date)   返回date在一年中的季度(1~4)，如SELECT QUARTER(CURRENT_DATE);
- WEEK(date)   返回日期date为一年中第几周(0~53)
- YEAR(date)   返回日期date的年份(1000~9999)
- now:获取当前日期
- str_to_date:将日期格式的字符转换成指定格式的日期 
  - STR_TO_DATE('9-13-1999','%m-%d-%Y') 1999-09-13
- date_format:将日期转换成字符 DATE_FORMAT(‘2018/6/6’,‘%Y年%m月%d日’) 2018年06月06日
- DATE_FORMAT(date,fmt)  依照字符串fmt格式化日期date值
- FORMAT(x,y)   把x格式化为以逗号隔开的数字序列，y是结果的小数位数
- INET_ATON(ip)   返回IP地址的数字表示
- INET_NTOA(num)   返回数字所代表的IP地址
- TIME_FORMAT(time,fmt)  依照字符串fmt格式化时间time值


### mysql常见的增删改查询
- 插入
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201341225.png)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201341550.png)
- 更新
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201341687.png)
- 删除
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201342068.png)
- 创建表
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201342127.png)
  - 关于建表语句中的字段长度
    - 基本的字段的长度和存储范围 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201343380.png)
    - Text字段类型的进一步划分： ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201345750.png)
    - 其他各种字段类型长度：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201346211.png)
    - datetime和timestamp的区别
      - 1、Timestamp支持的时间范围较小，取值范围：19700101080001——2038年的某个时间 Datetime的取值范围：1000-1-1  9999-12-31
      - 2、timestamp和实际时区有关，更能反映实际的日期，而datetime则只能反映出插入时的当地时区
      - 3、timestamp的属性受Mysql版本和SQLMode的影响很大
      可以按照测试结果得知：
      首先当前时刻下 东八区的8点 东九区是9点
      timestamp存储的是一个时间戳数字（表征时刻），然后再根据所处的时区转化（如果当前是东八区就是8点，如果是东九区就是9点）
      datetime可以理解为存储的是字面类的时间串
- 修改表
  - 加一个列： alter table dept add jpb_id varchar(19) COMMENT "jod id";
  - 修改一个列： alter table dept modify (job_id varchar(1000))  注意这个修改只对后面的数据生效
  - 删除一个列： alter table dept drop  column job_id;
  - 重命名一个列： alter table change column job_id job_id varchar(10);
- 删除一个表
  - drop table dept；
  - drop table 不能回滚
- 清空表数据
  - truncate table dept；
  - truncate 不能回滚
- 重命名表：
  - alter table dept rename to dept_bak;

### mysql的约束
约束的分类
- NOT NULL 非空约束，规定某个字段不能为空
- UNIQUE 唯一约束，规定某个字段在整个表中是唯一的
- PRIMARY KEY 主键(非空且唯一)
- FOREIGN KEY 外键
- CHECK 检查约束 -- 几乎不用
- DEFAULT 默认值
- 外键
  - 从表的外键值必须在主表中能找到或者为空。当主表的记录被从表参照时，主表的记录将不允许删除，如果要删除数据，需要先删除从表中依赖该记录的数据，然后才可以删除主表的数据
  - 外键的参照列
``` 
CREATE TABLE student(
id INT AUTO INCREMENT PRIMARY KEY,
NAME VARCHAR (20),
classes name VARCHAR (20)，
classes number INT,
/*表级别联合外键*/
FOREIGN KEY (classes name, classes number)
REFERENCES classes (NAME, number) ON DELETE CASCADE);

其中的两个行为 
1.ON DELETE CASCADE(级联删除)：当父表中的列被删除时，子表中相对应的列也被删除
2.ON DELETE SET NULL(级联置空)：子表中相应的列置空
```

### mysql的分页sql
其实就是limit start_index，page_num
```
前10条记录:SELECT * FROM table LIMIT 0,10; 
第11至20条记录:SELECT * FROM table LIMIT 10,10;
第21至30条记录: SELECT * FROM table LIMIT 20,10; 
注意这里是从0开始的下标
```

### mysql中的事务（事务只对DML有关，只有DML语句才有事务）
- 事务的定义：
  事务由单独单元的一个或多个SQL语句组成，在这 个单元中，每个MySQL语句是相互依赖的。而整个单独单 元作为一个不可分割的整体，如果单元中某条SQL语句一 旦执行失败或产生错误，整个单元将会回滚。所有受到影 响的数据将返回到事物开始以前的状态;如果单元中的所 有SQL语句均执行成功，则事物被顺利执行。
- 事务需要的引擎支持
  常见的现在的引擎是innodb
- 事务的属性
  - A 原子性(Atomicity)  原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生
  - C 一致性(Consistency) 事务必须使数据库从一个一致性状态变换到另外一个一致性状态，一致性的约束都没有被破坏
  - I 事务的隔离性是指一个事务的执行不能被其他事务干扰，即一个 事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。 ---并发的事务互不影响
  - D 持久性是指一个事务一旦被提交，它对数据库中数据的改变就是 永久性的，接下来的其他操作和数据库故障不应该对其有任何影响
- 脏读，幻读，可重复读
  - 脏读：对于两个事务 T1, T2, T1 读取了已经被 T2 更新但还没有被提交的字段. 之后, 若 T2 回滚, T1读取的内容就是临时且无效的.
  - 幻读：对于两个事务T1, T2, T1 从一个表中读取了一个字段, 然后 T2 在该表中插 入了一些新的行. 之后, 如果 T1 再次读取同一个表, 就会多出几行.
  - 可重复读取：对于两个事务T1, T2, T1 读取了一个字段, 然后 T2更新了该字段. 之后, T1再次读取同一个字段, 值就不同了.
- 事务隔离级别：
  - 未提交读(READ UNCOMMITTED)
  - 提交读(READ COMMITTED) 也叫不可重复读，事务开启前后的读取值可能不一致
  - 可重复读(REPEATABLE READ)
  - 可串行(SERIALIZABLE)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201425280.png)
- 设置不同的隔离级别：
  - 每启动一个 mysql 程序, 就会获得一个单独的数据库连接. 每个数据库连接都有一个全局变量 @@tx_isolation, 表示当前的事务隔离级别.
  - 查看当前的隔离级别: SELECT @@tx_isolation;
  - 设置当前 mySQL 连接的隔离级别:  set transaction isolation level read committed;
  - 设置数据库系统的全局的隔离级别: set global transaction isolation level read committed;

### mysql中的sql使用事务
- 整体流程：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201431040.png)
- 开始事务 
```
set autocommit=0;
start transaction;
```
- 做一个保存点
``` 
savepoint b；
```
- 回滚
``` 
rollback to 断点
```
- 提交commit
``` 
commit
```

### jdbc中的事务
```
try {
            conn = JdbcUtils.getConnection(); //建立连接
            //关闭自动提交，自动开启事务conn.setAutoCommit(false);  //开启事务
            String sql1 = "update company set money = money - 100 where name = 'A'";
            st = conn.prepareStatement(sql1);
            st.executeUpdate();
//int x = 1/0; //报错
            String sql2 = "update company set money = money + 100 where name = 'B'";
            st = conn.prepareStatement(sql2);
            st.executeUpdate();
            //业务完毕，提交事务            conn.commit();
            System.out.println("成功");
} catch (SQLException throwables) {
            //如果失败则自动回滚
//            try {
                conn.rollback(); //如果失败则回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            }            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(conn,st,rs);
        }
```

### mysql中的视图
- 视图的定义：
  一种虚拟存在的表，行和列的数据来自定义视图的查询中使用的表 ，并且是在使用视图时动态生成的，只保存了sql逻辑，不保存查询结果，所以实际上几乎不会占用存储空间
- 应用场景：
  重用sql语句；简化复杂的sql操作，不必知道它的查询细节 ；保护数据，提高安全性
- 语法：
``` 
  create [or replace] view view_name As select_statement [with|cascaded|local|check option]
  drop view [if exists] view_name,view_name ...[restrict|cascade]
  show create view view_name
```
- 不可更新视图：
  1. 包含：分组函数，distinct，group by, having, union， union all
  2. 常量视图：看下面例子， myv2 不能更新
```
create or replace view myv2
as 
select 'john' name;
```
  3. select 中包含子查询
```
create or replace view myv3
as 
select (select MAX(salary) from employees) 最高工资;
select * from  myv3;
```
  4. join
  5. from一个不能更新的视图
  6. where子句的子查询引用了from子句中的表

### mysql中的存储过程和存储函数
- 什么是存储过程
  （PROCEDURE）是事先经过编译并存储在数据库中的一段SQL语句的集合。调用存储过程可以简化应用开发人员的很多工作，减少数据在数据库和应用服务器之间的传输，对于提高数据处理的效率是很有好处的
   其实核心就是数据库 SQL 语言层面的代码封装与重用。
- 创建和使用存储过程和函数
```   
创建存储过程:
  create procedure 存储过程名 ([proc_parameter[,...]]) [characteristic...]routine_body
创建函数:
  create function 函数名([func_parameter[,...]])
  returns type [characteristic...]routine_body
调用存储过程:
  call 存储过程名(参数列表)
调用函数:
  Select 函数名(参数列表)
```
- 调用和使用存储过程
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201453388.png)
- 函数和存储过程的对比
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201454992.png)

### mysql中的流程控制
MySQL 中流程控制语句有：IF 语句、CASE 语句、LOOP 语句、LEAVE 语句、ITERATE 语句、REPEAT 语句和 WHILE 语句等。


### mysql中的全局变量和会话变量

``` 
查看所有全局变量
SHOW GLOBAL VARIABLES;
查看满足条件的部分系统变量
SHOW GLOBAL VARIABLES LIKE '%char%';
查看指定的系统变量的值
SELECT @@global.autocommit;
为某个系统变量赋值
SET @@global.autocommit=0;
SET GLOBAL autocommit=0;

查看所有会话变量
SHOW SESSION VARIABLES;
查看满足条件的部分会话变量
SHOW SESSION VARIABLES LIKE '%char%';
查看指定的会话变量的值
SELECT @@autocommit;
SELECT @@session.tx_isolation;
为某个会话变量赋值
SET @@session.tx_isolation='read-uncommitted';
SET SESSION tx_isolation='read-committed';
```

### mysql中的游标
个人理解游标就是一个标识，用来标识数据取到了什么地方，如果你了解编程语言，可以把他理解成数组中的下标。
不像多数 DBMS，MySQL 游标只能用于存储过程和函数。

### mysql中的触发器
触发器（Trigger）是 MySQL 中非常实用的一个功能，它可以在操作者对表进行「增删改」 之前（或之后）被触发，自动执行一段事先写好的 SQL 代码。

### mysql8的窗口函数
- 几个概念：
  - 函数名
  - 静态窗口函数： rank（）、dense_rank()、row_number()；
  - 滑动窗口函数 sum、 avg、percent_rank() first_value()、last_value()、nth_value()、lag()、lead()、ntile()
  - 分区 partition by 指定字段
  - 排序 order by 指定字段
  - frame_definition 窗口子句
    - CURRENT ROW 边界是当前行，一般和其他范围关键字一起使用
    - UNBOUNDED PRECEDING 边界是分区中的第一行
    - UNBOUNDED FOLLOWING 边界是分区中的最后一行
    - expr PRECEDING 当前行之前的expr(数字或表达式)行
    - expr FOLLOWING 当前行之后的expr(数字或表达式)行
- 主要的窗口的函数
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201500651.png)
- rank() dense_rank() row_number()的区别
  - row_number：1 2 3 这样的
  - rank：使用RANK()函数能够对序号进行并列排序，并且会跳过重复的序号，比如55，56，56，57序号为1、2、2、4。 
  - dense_rank：DENSE_RANK()函数对序号进行并列排序，并且不会跳过重复的序号，比如55，56，56，57序号为1、2、2、3。


### mysql8的新特性
- cte 公共表达式
``` 
with table_name as (

)
```

### mysql中的字符集设置
在MySQL 8.0版本之前，默认字符集为 latin1 ，utf8字符集指向的是 utf8mb3 。网站开发人员在数据库 设计的时候往往会将编码修改为utf8字符集。如果遗忘修改默认的编码，就会出现乱码的问题。从MySQL 8.0开始，数据库的默认编码将改为 utf8mb4 ，从而避免上述乱码的问题

查看默认的字符集：
``` 
show variables like 'character%'
```
- 字符集的级别&可以设置的变量
``` 
| character_set_client | utf8 |
| character_set_connection | utf8 |
| character_set_database | latin1                       |
| character_set_filesystem | binary |
| character_set_results | utf8 |
| character_set_server | utf8 |
| character_set_system | utf8 |
| character_sets_dir | /usr/share/mysql/charsets/ |
```
#### mysql 5.7中字符集的设置
最终合理的设置：
```
show variables like 'character%'
```
结果如下

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201522171.png)

note ： 可以实现操作之后的都生效但是之前的数据不生效的

修改的主要步骤：
1. vim /etc/my.cnf
2. 文件最后加上 character_set_server=utf8
3. 重启mysql systemctl restart mysqld

对于已有的数据库的更新字符集操作：
1. 修改数据库字符集 alter database dbtest1 character set 'utf8';
2. 修改表的字符集 alter table t_emp convert to character set 'utf8';


#### mysql 8中的字符集
默认就是utf8-mb4，字符集是可以设置的
``` 
# 修改具体数据库的字符集和比较规则
ALTER DATABASE dbtest1 DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
```

#### 请求过程中的字符集转化
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201722400.png)

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201526306.png)

有几个地方需要保持一致，比如发送方的编码二进制，要和 character_set_client 一致；返回数据的编码character_set_results方式要能被 client解析

#### mysql字符集与比较规则
- 关于utf8的编码长度
utf8 这是最多使用三个字符来编码字符的
utf8mb4 这个是是用1~4个字节表示字符。
- 关于比较规则（主要关注下大小写是否敏感 _ci _cs）
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201719587.png)
  - 例子 比如 utf8_polish_ci 表示以波兰语的规则，utf8_spanish_ci 是以西班牙语的规则比较，utf8_general-ci 是一种通用的比较规则。
``` 
# 查看支持的字符集
show charset
# 查看支持的比较规则
show collation
# 修改具体数据库的字符集和比较规则
ALTER DATABASE dbtest1 DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
```
note :更新是向后生效的

### mysql大小写敏感吗
- 关键字和函数不区分大小写
- 数据库名 表名 表别名 都是严格区分大小写的
- 列名字是不区分大小写的

note：
1. 关键字和函数名全部大写；
2. 数据库名、表名、表别名、字段名、字段别名等全部小写；

### mysql中的sql_mode
- 严格模式  对数据进行很严格的校验，错误数据不能插入，会报错，进行回滚
- 非严格模式 如果数据错误可能插入成功
- 查看方式  show variables like 'sql_mode'
- 临时设置sql_mode  
``` 
SET GLOBAL sql_mode = 'modes...'; #全局
SET SESSION sql_mode = 'modes...'; #当前会话
```
- 永久设置方式:在/etc/my.cnf中配置sql_mode
``` 
# 查看会话配置的sql_mode
SELECT @@session.sql_mode;

# 查看全局的sql_mode
SELECT @@global.sql_mode;
```

### mysql中的一些重要目录
- 数据存储目录 show variables like "%datadir%"
``` 
mysql> show variables like 'datadir';
+---------------+-----------------+
| Variable_name | Value           |
+---------------+-----------------+
| datadir       | /var/lib/mysql/ |
+---------------+-----------------+
1 row in set (0.04 sec)
```
- 命令目录  /usr/bin和/usr/sbin
- 配置文件目录 /usr/share/mysql-8.0和/etc/my.cnf

### mysql中的几个默认的数据库
- mysql mysql系统自带的核心数据库，存储了mysql的用户账户和权限信息，一些存储过程、事件的定义信息，一些运行过程中产生的日志信息，一些帮助信息以及时区信息等
- information_schema 这个数据库保存着mysql服务器所有其他数据库的信息。比如有哪些表、哪些视图、哪些触发器、哪些列、哪些索引。这些数据并不是真实的用户数据，而是描述性信息，有时候也称为元数据。在该库中提供了一些以innodb_sys开头的表，用于表示内部系统表。
- performation_schema 主要保存mysql服务器运行过程中的一些状态信息，可以用来监控mysql服务的各类指标。包括统计最近执行了那些语句，在执行过程的各个阶段都花费了多长时间，内存的使用情况等信息
- sys 该数据库主要通过视图的形式把information_schema和 performation_schema结合起来，帮助开发人员监控mysql的技术性能。

### mysql库表在底层文件系统中的组织方式(Innodb)
- 数据库的组织方式-文件夹（5.7的话会有一个db.opt文件）
- 表的组织方式
  - 如果是5.7的话 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201603929.png)，每个表对应两个文件 .frm（数据的结构）  和 .ibd（可以用来放置表中数据 当然也可以放在系统表空间）这两个文件
  - 如果是8的话 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201605723.png) 并没有frm文件，文件的数据结构都是合一存在 .ibd文件中了，可以通过ibd2sdi的转化为txt文件来看，json格式的


### mysql的用户管理
- 创建用户
```
创建用户在任何地方都可登录
CREATE USER 'neil'@'%' IDENTIFIED BY '123456';

创建用户在指定的机器上登录
CREATE USER 'neil'@'192.168.1.10' IDENTIFIED BY '123456';

创建用户允许在任何的机器上登录
CREATE USER 'neil'
```

- 删除用户
``` 
DROP USER <用户1> [ , <用户2> ]…
```

- 修改用户密码（设置密码）
```
update user set authentication_string='' where user='root';--将字段置为空
ALTER user 'root'@'localhost' IDENTIFIED BY 'root';--修改密码为root
```

### mysql的权限管理
``` 
# 查看权限-支持哪些权限
show privileges;
```
- 给用户赋权
1. 查看用户权限
权限主要是有 select  insert update delete alter create drop grant
``` 
# 查看用户权限
show grants;
show grants for dba@localhost;
SHOW GRANTS FOR CURRENT_USER;
```
2. 授权语句
``` 
grant select, insert, update, delete on testdb.* to common_user@'%'
grant create on testdb.* to developer@'192.168.0.%';
grant index on testdb.* to developer@'192.168.0.%';
grant create view on testdb.* to developer@'192.168.0.%';
grant show view on testdb.* to developer@'192.168.0.%';
grant create routine on testdb.* to developer@'192.168.0.%'; -- now, can show procedure status
grant alter routine on testdb.* to developer@'192.168.0.%'; -- now, you can drop a procedure
grant execute on testdb.* to developer@'192.168.0.%';
grant all privileges on testdb to dba@'localhost'
```
3. 刷新数据库
``` 
flush privileges;
```
4. 撤销权限，回收权限
``` 
revoke all on *.* from dba@localhost;
note： 须用户重新登录后才能生效
```

### mysql角色管理
角色是8.0之后引入的概念，本质上是一堆权限的集合，用户通过获得某个角色而得到某些权限，以此来到达控制用户的权限
1. 创建角色  
```
create role 'role_name'[@'host_name'];
```
2. 角色授权  
```
grant privileges on table_name to 'role_name'[@'host_name']
```
3. 查看角色权限  
```
show grants for 'role_name'[@'host_name'];
```
4. 角色撤权  
```
Revoke privileges on table_name from'role_name';
#例
Revoke insert,update,delete on school.*from'school_write';
```
5. 删除角色  
```
drop role 'role_name';
```
6. 用户授予角色  
```
grant 'role_name'[@ 'host_name'] to 'user_name'[@ 'host_name']
#授予权限后需要激活才生效
set default 'role_name'[@ 'host_name'] all to 'user_name'[@ 'host_name'];
#或者设置默认系统参数
show variables like 'activate_all_roles_on_login';#查看
set global activate_all_roles_on_login = ON;#设置状态为开启
```
7. 撤销用户角色  
```
revoke 'role_name'[@ 'host_name'] from 'user_name'[@ 'host_name']
```
8. 激活角色
``` 
SET DEFAULT ROLE ALL TO 'kangshifu'@'localhost';
```

### mysql权限相关表
- user表 多个列的，描述用户的信息
- db表 库的权限控制
- tables_priv表 表的权限控制
- columns_priv表 列的权限控制
- procs_priv表 函数和存储过程的权限控制


### mysql的体系结构（逻辑结构）
逻辑结构如图： ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201826535.png)

这里展示的是mysql 8之前的，mysql8干掉了***查询缓存***，因为查询缓存要求 sql是一模一样，显得很鸡肋

上图进一步细化为：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201827363.png)

这是一种类似的C/s架构

1. 第一层：连接层
   - 底层是tcp连接
   - 身份认证，权限认证
   - 从后台的线程池中申请一个线程去相应这个请求
2. 第二层：服务层
   - SQL Interface: SQL接口
   - Parser: 解析器 词法分析，语法分析
   - Optimizer: 查询优化器
   - Caches & Buffers: 查询缓存组件
3. 第三层：引擎层
   - 真正的负责了MySQL中数据的存储和提取，对物理服务器级别 维护的底层数据执行操作
4. 第四层：存储层

总的类似这样的三层结构：

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302210934638.png)


### mysql的执行流程
依据上面的体系结构，一条sql的执行流程是![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302210937395.png)
- 客户端网络通信
- 查询缓存是否命中（很鸡肋 ，mysql8 抛弃了这个特性）
- 解析器：主要经过 词法分析（识别词） 语法分析（识别语法） 语义分析（识别干啥） 语法树
- 优化器：逻辑查询优化 物理查询优化 到此生成了执行计划
- 执行器：按照执行计划下放到存储引擎去执行
- 是否需要写到缓存
- 返回结果集


### mysql分析指定语句的执行耗时
``` 
# 确认profiling 是否开启
select @@profiling;
show variables like 'profiling';

# 开启profiling
set profiling=1;
mysql> select @@profiling;
+-------------+
| @@profiling |
+-------------+
|           1 |
+-------------+

# 查看最近的几次查询
show profils;
mysql> show profiles;
+----------+------------+---------------------------------+
| Query_ID | Duration   | Query                           |
+----------+------------+---------------------------------+
|        1 | 0.00232225 | select @@profiling              |
|        2 | 0.02331050 | show variables like 'profiling' |
|        3 | 0.09385625 | show databases                  |
|        4 | 0.01302050 | SELECT DATABASE()               |
|        5 | 0.00949500 | show databases                  |
|        6 | 0.00317200 | show tables                     |
|        7 | 0.00964175 | show tables                     |
|        8 | 0.05024050 | select * From t_emp             |
+----------+------------+---------------------------------+


# 查看单次查询的耗时细节
show profile;
mysql> show profile;
+----------------------+----------+
| Status               | Duration |
+----------------------+----------+
| starting             | 0.001866 |
| checking permissions | 0.000100 | 权限检查
| Opening tables       | 0.000426 | 打开表
| init                 | 0.000251 | 初始化
| System lock          | 0.000450 | 锁系统
| optimizing           | 0.000027 | 优化查询
| statistics           | 0.000290 |
| preparing            | 0.000118 | 准备
| executing            | 0.000012 | 执行
| Sending data         | 0.046382 |
| end                  | 0.000049 |
| query end            | 0.000061 |
| closing tables       | 0.000040 |
| freeing items        | 0.000053 |
| cleaning up          | 0.000117 |
+----------------------+----------+
15 rows in set, 1 warning (0.02 sec)

# 指定查看某个QUERY_ID的profile来看
show profile for query 8;
mysql> show profile for query 8;
+----------------------+----------+
| Status               | Duration |
+----------------------+----------+
| starting             | 0.001866 |
| checking permissions | 0.000100 |
| Opening tables       | 0.000426 |
| init                 | 0.000251 |
| System lock          | 0.000450 |
| optimizing           | 0.000027 |
| statistics           | 0.000290 |
| preparing            | 0.000118 |
| executing            | 0.000012 |
| Sending data         | 0.046382 |
| end                  | 0.000049 |
| query end            | 0.000061 |
| closing tables       | 0.000040 |
| freeing items        | 0.000053 |
| cleaning up          | 0.000117 |
+----------------------+----------+
15 rows in set, 1 warning (0.01 sec)
```


### 关于mysql的查询缓存的情况（只针对mysql5.7，mysql 8之后移除了）
- 即使在mysql 5.7中也是需要手动开启
- 这个功能很鸡肋
- 一般不需要开启
- %query_cache_type% 这个变量在mysql的配置文件中可以显式的指明的
  - query_cache_type=0 表示OFF
  - query_cache_type=1 表示ON
  - query_cache_type=2 表示 DEMAND，只缓存select语句中通过SQL_CACHE指定需要缓存的查询

``` 
# 查询是否开启了缓存
select @@query_cache_type;
+--------------------+
| @@query_cache_type |
+--------------------+
| ON |
+--------------------+

# have_query_cache 设置查询缓存是否可用
+------------------+-------+
| Variable_name    | Value |
+------------------+-------+
| have_query_cache | YES   |
+------------------+-------+

# 查看查询缓存的工作情况
show status like "%Qcache%"
+-------------------------+---------+
| Variable_name           | Value   |
+-------------------------+---------+
| Qcache_free_blocks      | 1       | 目前还处于空闲状态的 Query Cache 中内存 Block 数目
| Qcache_free_memory      | 1031832 | 查询缓存目前剩余空间大小
| Qcache_hits             | 0       | 查询缓存的命中次数
| Qcache_inserts          | 0       | 查询缓存插入的次数。
| Qcache_lowmem_prunes    | 0       | 当 Query Cache 内存容量不够，需要从中删除老的 Query Cache 以给新的 Cache 对象使用的次数
| Qcache_not_cached       | 1       | 没有被 Cache 的 SQL 数，包括无法被 Cache 的 SQL 以及由于 query_cache_type 设置的不会被 Cache 的 SQL
| Qcache_queries_in_cache | 0       | 目前在 Query Cache 中的 SQL 数量
| Qcache_total_blocks     | 1       | Query Cache 中总的 Block 数量  
+-------------------------+---------+
8 rows in set (0.05 sec)

# 在开启缓存情况下使用缓存
SELECT SQL_CACHE id,field FROM table WHERE 1

# 强制不使用缓存
SELECT SQL_NO_CACHE id,field FROM table WHERE 1

# 整理查询缓存碎片
FLUSH QUERY CACHE
```

### mysql5.7开启缓存前后的执行细节对比
不命中cache之前：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211009142.png)

命中cache时候：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211010738.png)

可以看出命中时候查询加快的速度很快，当然完全是看命中率的，最好是把较小的静态表作cache。

### mysql数据库缓冲池
1. 缓冲池的工作原理
   - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211119275.png)
   - InnoDB 缓冲池包括了数据页、索引页、插入缓冲、锁信息、自适应 Hash 和数据字典 信息等。
   - 主要按照数据的冷热程度来进行预加载
   - 在数据库进行页面读操作的时候，首先会判断该页面是否在缓冲池中，如果存在就直接读取，如果不存在，就会通过内存或磁盘将页面存放到缓冲池中再进行读取。
2. 查询缓存和缓冲池的区别
   - 查询缓存存储的是查询的结果
   - 缓冲池缓存的是mysql的自身需要的一些数据信息，索引信息等
3. 查看/设置缓冲池的大小
``` 
# 查看大小 单位B 字节 /1024/1024
show variables like 'innodb_buffer_pool_size';

# 更改缓冲池大小 256MB
set global innodb_buffer_pool_size = 268435456;

# 多个Buffer Pool实例
 [server]
innodb_buffer_pool_instances = 2
```

### mysql存储引擎
- 插件式的存储引擎，实际指明是的表的存储结构（以前叫表处理器）
- 主要是接受执行计划，处理相应的请求
- 主要的存储引擎
  - innodb
  - myIsam
``` 
# 查看mysql提供什么存储引擎:
show engines;
mysql> show engines;
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| Engine             | Support | Comment                                                        | Transactions | XA   | Savepoints |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| InnoDB             | DEFAULT | Supports transactions, row-level locking, and foreign keys     | YES          | YES  | YES        |
| MRG_MYISAM         | YES     | Collection of identical MyISAM tables                          | NO           | NO   | NO         |
| MEMORY             | YES     | Hash based, stored in memory, useful for temporary tables      | NO           | NO   | NO         |
| BLACKHOLE          | YES     | /dev/null storage engine (anything you write to it disappears) | NO           | NO   | NO         |
| MyISAM             | YES     | MyISAM storage engine                                          | NO           | NO   | NO         |
| CSV                | YES     | CSV storage engine                                             | NO           | NO   | NO         |
| ARCHIVE            | YES     | Archive storage engine                                         | NO           | NO   | NO         |
| PERFORMANCE_SCHEMA | YES     | Performance Schema                                             | NO           | NO   | NO         |
| FEDERATED          | NO      | Federated MySQL storage engine                                 | NULL         | NULL | NULL       |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
9 rows in set (0.05 sec)
上面的展示信息
- 默认是哪个引擎
- 是否支持事务
- 是否支持分布式
- 支持savePoint

# 查看默认的存储引擎
show variables like '%storage_engine%';
或者
SELECT @@default_storage_engine;
mysql> SELECT @@default_storage_engine;
+--------------------------+
| @@default_storage_engine |
+--------------------------+
| InnoDB                   |
+--------------------------+
1 row in set (0.02 sec)

# 修改默认的存储引擎
临时修改 SET DEFAULT_STORAGE_ENGINE=MyISAM;
永久修改 my.cnf 文件: default-storage-engine=MyISAM 后重启


# 创建表时指定存储引擎
CREATE TABLE 表名( 
建表语句;
)  ENGINE=InnoDB;

# 修改表的存储引擎
ALTER TABLE engine_demo_table ENGINE = InnoDB;
```

#### mysql中引擎对比

- innodb 和 MyISAM的对比

| 引擎   |  innodb   | MyISAM |
|------|:---------:|:------:|
| 事务   |    支持     |  不支持   |
| 锁机制  |   最小行锁    |  最小表锁  |
| 缓存   | 数据和索引都缓存  | 只缓存索引  |
| 内存要求 |     低     |   高    |
| 外键   |    支持     |  不支持   |

- archive 用于数据存档
- Blackhole
- CSV 
``` 
CREATE TABLE test (
i INT NOT NULL, 
c CHAR(10) NOT NULL
) ENGINE = CSV;
落到文件系统的后台中是csv文件，直接使用wps就可打开
```
- Memory 置于内存的表 掉电遗失
- Federated 访问远程表
- Merge 引擎
- NDB 引擎

### 为啥要使用索引（索引的优点缺点）
- 索引是用于快速找到数据的一种数据结构，回忆下基础的几个数据结构（尤其是节点如何放在其中）
  - 二叉树
  - BST 搜索二叉树
  - AVL 平衡二叉树
    - 红黑树
  - B树 数据在树内
  - B+树 数据节点在叶子
- 优点：
  - 提高IO速度
  - 保证数据的唯一性，唯一键
  - 提高分组的速度
- 缺点：
  - 创建和维护索引需要耗时耗费资源
  - 索引需要耗费磁盘
  - 更新表需要耗费维护索引的时间，插入，删除

### 索引查询的原理说明
1. 如果不引入索引：
   表的数据量很少，如果只占用了一个页的话，
     - 如果是按照主键关键字来查找的话，可以考虑使用二分查找
     - 如果是按照非叶子节点来查的话，那就直接在当前页中沿着链表逐一查
   表的数据量很大，占用了多个页的话，需要顺序的一个一个的查找
2. 引入索引来解决上面的问题：
3. 索引原理说明：
- 先建立一个测试表
``` 
# 建立一个测试表
CREATE TABLE index_demo(
   c1 INT,
   c2 INT,
   c3 CHAR(1),
   PRIMARY KEY(c1)
) ROW_FORMAT = Compact;
```
- 单条记录的样式
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211427372.png)

  里面最重要的一个字段是指向下一条记录的next_record，
  然后还有一个record_type :记录头信息的一项属性，表示记录的类型， 0-表示普通记录、 2-表示最小记录、 3-表示最大记录、 1 表示这个是索引项的（下面的目录项的标志）

- 内存的页内是如何存储上面的单条记录的呢
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211429776.png)

- 如果数据是超过一个页呢
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211502018.png)

  这里展示对于四个页进行索引的情况

- Innodb 中的目录项也存在了页中，整体结构如下（下面没细分目录页 数据页）
  单个页内就能存储所有的目录项的情况： ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211504486.png)

  需要两个顶层目录项页的情况（注意顶层一定是一个页不存在两个的情况）：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211506695.png)

  为存储目录项的页增加目录项：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211507314.png)

  对整体的存储结构进行进一步的抽象： ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211509069.png)

  叶子节点所在的页之间是双向链表的连接，从顶层的页节点到叶子节点是单向的连接，叶子节点内的数据是单链表的连接方式

- 基于上面的innodb的目录项，如何实现快速的查找呢？
  - 首先需要知道的是整个B+ tree的结构，从顶层开始查找
  - 对于一个页内的查找，如果是定长的排序记录（已经是排序）直接使用二分查找就可（虽然是链表但是记录了额外的数据结构）
  - 经历了几次io呢，按照层次来算的话其实就是层高
  - 估算树高度和能支持的数据量的关系 如果一个页比如存储100个项，那么4层的话 100^4= 100000000个数据项
  - 其实也是就是生产中最多4次io，存储4层

  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211509069.png)

4. 索引的相关概念
- 什么是聚簇索引？
  - 很简单记住一句话：找到了索引就找到了需要的数据，那么这个索引就是聚簇索引，所以主键就是聚簇索引，修改聚簇索引其实就是修改主键。
  - 叶节点都是数据。
  - 一个表中只能有一个聚簇索引。
  - 优点：主键的排序查找和范围查找很快，数据访问比非聚簇索引快
  - 缺点：插入速度严重依赖于插入顺序，主键的插入是最快的
- 什么是非聚簇索引（二级索引）？
  - 索引的存储和数据的存储是分离的，也就是说找到了索引但没找到数据，需要根据索引上的值(主键)再次回表查询,非聚簇索引也叫做辅助索引。
  - 叶子结点都是id，查询真实的数据需要再次按照id去查聚簇索引，这个操作就是回表。
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211613090.png)
  - 一个表中可以有多个非聚簇索引。
  - 二级索引为啥不存储完整的数据--因为占用的空间太大了
- 联合索引（一种非聚簇索引）
  - 让B+树先按照某个列，然后再按照某个列进行排序
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211611910.png)
  - 这里涉及到一个最左匹配的规则
5. InnoDB的索引的注意事项
- 根节点位置不变
- 内节点中目录项记录的唯一性
  - 这里需要考虑下索引列发生了重复的情况
- 一个页面最少存储2条记录

### MYISAM中的索引 @TODO
- 这里的索引都是二级索引
- 叶子节点存储的是节点的地址信息
- 然后查找之后直接按照地址信息去查找
- 没有聚簇索引

### MyISAM 与 InnoDB对比
1. 在InnoDB存储引擎中，我们只需要根据主键值对 聚簇索引 进行一次查找就能找到对应的记录，而在 MyISAM 中却需要进行一次 回表 操作，意味着MyISAM中建立的索引相当于全部都是 二级索引 。
2. InnoDB的数据文件本身就是索引文件，而MyISAM索引文件和数据文件是 分离的 ，索引文件仅保存数 据记录的地址。
3. InnoDB的非聚簇索引data域存储相应记录 主键的值 ，而MyISAM索引记录的是 地址 。换句话说， InnoDB的所有非聚簇索引都引用主键作为data域。
4. MyISAM的回表操作是十分 快速 的，因为是拿着地址偏移量直接到文件中取数据的，反观InnoDB是通 过获取主键之后再去聚簇索引里找记录，虽然说也不慢，但还是比不上直接用地址去访问。
5. InnoDB要求表 必须有主键 ( MyISAM可以没有 )。如果没有显式指定，则MySQL系统会自动选择一个 可以非空且唯一标识数据记录的列作为主键。如果不存在这种列，则MySQL自动为InnoDB表生成一个隐 含字段作为主键，这个字段长度为6个字节，类型为长整型。

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302211821372.png)

### 索引的代价
- 时间上
  - 增，删的调整
  - B+ 树的目录页的开销 也不是很小
- 空间上
  - 增删改上的时间开销包含对于索引的修改
  - 页面分裂，页面回收

### 