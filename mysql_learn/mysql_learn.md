### 回答问题
- mysql的逻辑体系结构 3层是哪三层
- 一条mysql查询语句的执行过程（基于上面的三层结构）
- hash 模式和Btree模式的区别和优点缺点
- B树和B+树的区别？
- B+树是如何支持索引的？从查询到单条结果返回的命中的过程（画图->目录页之间的查找过程->数据页内二分、槽、分组内的二分）
- 页中的页目录的作用？
- 索引失效的几个case？
- join的底层原理？驱动表 被驱动表？
- 通过explain判定使用了联合索引中的几个字段的索引？（key_len）
- 覆盖索引？
- 索引下推？
- 事务中隔离级别？幻读着重看下
- 范式：第一范式 第二范式 第三范式
- Redo/Undo log 如何保证ACD？
- mysql是如何实现可重复读的？--mvcc

----
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
  - insert into emp (emp_id,first_name) values(11,'admin');
  - insert into emp select id,name from table2;
- 更新
  - update table_name set col1=12 where id = 1;
- 删除
  - delete from table_name where id = 1;
- 创建表
  - create table table_name();
  - 关于建表语句中的字段长度
    - 基本的字段的长度和存储范围 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201343380.png)
    - varchar 和 char 里面指定都是字符数，底层用啥样的编码占用多少字节不一定
    - varchar(N) 最大写65532，标志变长字段的实际长度用两个字节，null值的标志一个字节
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
- 事务的属性ACID
  - A 原子性(Atomicity)  原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生，mysql 通过undo 和 redo日志实现
  - C 一致性(Consistency) 事务必须使数据库从一个一致性状态变换到另外一个一致性状态，一致性的约束都没有被破坏 mysql 通过undo 和 redo日志实现
  - I 事务的隔离性(isolation)是指一个事务的执行不能被其他事务干扰，即一个 事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。 ---并发的事务互不影响，mysql 通过锁机制来实现
  - D 持久性(durability)是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来的其他操作和数据库故障不应该对其有任何影响 mysql 通过undo 和 redo日志实现
- 脏读，幻读，可重复读等并发问题
  - 脏写：对于两个事务 Session A、Session B，如果事务Session A 修改了 另一个 未提交 事务Session B 修改过的数据，那就意味着发生了脏写
  - 脏读：对于两个事务 T1, T2, T1 读取了已经被 T2 更新但还没有被提交的字段. 之后, 若 T2 回滚, T1读取的内容就是临时且无效的.
  - 幻读：对于两个事务T1, T2, T1 从一个表中读取了一个字段, 然后 T2 在该表中插 入了一些新的行. 之后, 如果 T1 再次读取同一个表, 就会多出几行.
  - 可重复读取：对于两个事务T1, T2, T1 读取了一个字段, 然后 T2更新了该字段. 之后, T1再次读取同一个字段, 值就不同了.
- 事务隔离级别：
  - 未提交读(READ UNCOMMITTED)
  - 提交读(READ COMMITTED) 也叫不可重复读，事务开启前后的读取值可能不一致
  - 可重复读(REPEATABLE READ) 这个是mysql的默认的支持的
  - 可串行(SERIALIZABLE)
  - 以上四种都是不允许脏写
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201425280.png)
- 设置不同的隔离级别：
  - 每启动一个 mysql 程序, 就会获得一个单独的数据库连接. 每个数据库连接都有一个全局变量 @@tx_isolation, 表示当前的事务隔离级别.
  - 查看当前的隔离级别: SELECT @@tx_isolation;
  - 设置当前 mySQL 连接的隔离级别:  set transaction isolation level read committed;
  - 设置数据库系统的全局的隔离级别: set global transaction isolation level read committed;
- 事务的状态
  - 正在执行（active）
  - 部分提交的(partially committed)
  - 失败的(failed)
  - 中止的(aborted)
  - 提交的(committed)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231557267.png)

### mysql中的sql使用事务
- 整体流程：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302201431040.png)
- 开始事务 开启事务
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

```
# 完整的事务sql例子
BEGIN;
INSERT INTO user SELECT '张三'; COMMIT;
BEGIN;
INSERT INTO user SELECT '李四'; INSERT INTO user SELECT '李四'; ROLLBACK;
````

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
在MySQL 8.0版本之前，默认字符集为 latin1 ，utf8字符集指向的是 utf8mb3 。网站开发人员在数据库 设计的时候往往会将编码修改为utf8字符集。如果遗忘修改默认的编码，就会出现乱码的问题。从MySQL 8.0开始，数据库的默认编码将改为 utf8mb4 ，从而避免上述乱码的问题；

统一使用utf8mb4( 5.5.3版本以上支持)兼容性更好，统一字符集可以避免由于字符集转换产生的乱码。不同的 字符集 进行比较前需要进行 转换 会造成索引失效。

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
- 列名字是不区分大小写的
- 数据库名 表名 表别名 都是严格区分大小写的
- 数据内容区分大小写看行格式 COLLATE _ci还是_cs

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
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302271342847.png)


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
- BlackHole
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

### 关于hash和二叉树的对比，b树和b+树的对比
- hash模式的话是可以很好的支持点查的，但是对于范围查询不是很友好
- hash 对于联合索引的话是一起算的hash值，所以不支持最左侧原则
- 二叉树的话可以很好的支持范围查询
- b树存储数据的位置在叶子节点和非叶子节点 m叉，效率不稳定，
- b+树存储数据的位置只在叶子节点，查询效率更稳定，都是在叶子节点命中；比B树更好的支持往大了查找
- InnoDb 和 MyISAM是不支持hash索引的，只有Memory是支持Hash的

### 面试题：mysql索引树会一次性的加载到内存中吗？
- 不会
- 可能很大，只会注意逐一加载

### 面试题： B+树的查询能力如何？大致需要几次io呢？
- 这个是和层高相关的
- 单个也节点的能力如果是可以存储1000个目录项的话
- 三层就是 1000^3 = 1000000000 个数据行记录

### 面试题：b树和b+树的对比？为啥B+树更适合作为索引
- B+树效率稳定，命中总在叶子节点=>IO次数稳定，查找范围时候也更方便
- B+磁盘效率更高，因为一个页内存储的记录信息更多，次数就更少

### 面试题：Hash索引和B+树索引的区别？
- hash的效率高，一般认为是O1的复杂度，但是对于范围查找的支持不是很好
- hash 不支持最左侧原则，尤其是联合索引
- hash 索引不支持order by ，不支持 模糊查找
- B+树的很好支持范围查找，order by，模糊查找

### InnoDb的数据存储结构-页和页的内部结构
- 页： 数据存储的基本单位；磁盘和内存交互的基本单位；IO操作的基本单位；
- 常见的页有：数据页（存储数据或者目录项就是BTree中的叶子和飞叶子节点）、系统页、UNDO页、事务数据页等等
```
# 查看页的size大小
show variables like '%innodb_page_size%';

# 设置innodb_page_size的大小

```
- 页面的内部结构：
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221029885.png)
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221032007.png)
1. 第一部分文件头：File Header信息，这里记住如下几个比较重要的信息：
   -  FIL_PAGE_OFFSET：当前页的页号，每个页都有一个唯一编号
   -  FIL_PAGE_PREV：双向链表中指向当前页的上一个页
   -  FIL_PAGE_NEXT：双向链表中指向当前页的下一个页
   -  FIL_PAGE_TYPE：页的类型，索引和数据都是存放在FIL_PAGE_INDEX（0x45BF）这种类型的页中，就是数据页。
   - 数据页之间组成了双向链表 : ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221037528.png)
2. 第二部分 表示页内的空闲空间，最大最小记录，用户记录
3. 第三部分 页目录 页头部
   - 页目录 是用来支持二分查找的，如果用户数据有1000条，会把数据分组（比如下面的 3 7 9 、、、），页目录对每个组存一个组内的最大或者最小值（下面演示的是存储的最大值），然后查询时候就是在页目录中进行二分查找就可定位到组；定位到组之后在组内也可以进行二分查找，查找到用户数据
   - 页目录中实际存储是槽slot，是支持二分查找
   - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221021379.png)
   - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221039851.png)

### InnoDb中的行格式
行格式就是规定底层使用存储行记录时候的格式，实际到底层存储的格式信息

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221054681.png)
``` 
# 查询默认的行格式 mysql 8 和 mysql 5.7 默认的都是dynamic
SELECT @@innodb_default_row_format;
mysql> SELECT @@innodb_default_row_format;
+-----------------------------+
| @@innodb_default_row_format |
+-----------------------------+
| dynamic                     |
+-----------------------------+
1 row in set (0.01 sec)

# 查看某个表的行格式
show table status like "XXX"\G；

# 修改表的行格式
alter table XXX row format=compact;

# 创建表时候指定行格式
 CREATE TABLE record_test_table (
col1 VARCHAR(8),
col2 VARCHAR(8) NOT NULL,
col3 CHAR(8),
col4 VARCHAR(8)
) CHARSET=ascii ROW_FORMAT=COMPACT;
```
- 行格式的分类
  - COMPACT
  - dynamic 
  - compressed
- 行溢出
  - 本来一个行是要放在底层的页内的，但是varchar或者其他字段的最大长度是可以超过一个页的，这种现象就是行溢出
  - 行溢出的处理：只存一部分，数据被分页存储，指明在哪些页存储
    - 即：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221106986.png)
    - COMPACT 下 只存前768个
    - dynamic 对于BLOB完全的off page 溢出页来存储
    - compressed 对于BLOB完全的off page 溢出页来存储 并进行压缩，这种行格式存储 BLOB TEXT VARCHAR 的效率比较高

### 页Page 区extent 段segment 表空间Tablespace
- 页Page 区extent 段segment 表空间Tablespace 关系 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221034510.png)
- 为啥有区的概念？主要是用来贴合顺序存储，这样的访问速度较快
- 为啥要有段的概念？主要是为了让目录项的页和非目录项的页分开存储
- 表空间：
  - @TODO

### 索引
1. 索引的分类
   - 普通索引
   - 唯一性索引
   - 主键索引
   - 单列索引
   - 多列(组合、联合)索引 
   - 全文索引：分词
2. 不同的引擎支持不同的索引
   - InnoDB：支持 B-tree、Full-text 等索引，不支持 Hash 索引;
   - MyISAM : 支持 B-tree、Full-text 等索引，不支持 Hash 索引; 
   - Memory :支持 B-tree、Hash 等 索引，不支持 Full-text 索引; 
   - NDB :支持 Hash 索引，不支持 B-tree、Full-text 等索引; 
   - Archive :不支 持 B-tree、Hash、Full-text 等索引;
3. 创建索引,删除索引
``` 
CREATE TABLE table_name [col_name data_type]
[UNIQUE | FULLTEXT | SPATIAL] [INDEX | KEY] [index_name] (col_name [length]) [ASC |
DESC]

# eg
PRIMARY KEY (`id`),
UNIQUE KEY `uniq_uid_inner_id` (`uid`, `inner_id`),
KEY `idx_jigou_id` (`jigou_id`),
FULLTEXT INDEX futxt_idx_info(info),
SPATIAL INDEX spa_idx_geo(geo)
ALTER TABLE table_name ADD UNIQUE KEY uniq_title(title)
ALTER TABLE table_name DROP INDEX index_name;
```

### mysql8的索引新特性
- 支持降序索引
``` 
CREATE TABLE ts1(a int,b int,index idx_a_b(a,b desc));
```
- 隐藏索引  
  只需要将待删除的索引设置为隐藏索引，使查询优化器不再使用这个索引(即使使用force index(强制使用索引)，优化器也不会使用该索引)， 确认将索引设置为隐藏索引后系统不受任何响应，就可以彻底删除索引。
  但是在更新数据的时候也是会更新隐藏的索引的
```
 #切换成隐藏索引 
 ALTER TABLE tablename ALTER INDEX index_name INVISIBLE; 
 
 #切换成非隐藏索引
 ALTER TABLE tablename ALTER INDEX index_name VISIBLE; 

 #修改为优化器可见
 select @@optimizer_switch \G
 set session optimizer_switch="use_invisible_indexes=on";
```

### 索引的设计原则
- 字段的数值有唯一性的限制
- 频繁作为 WHERE 查询条件的字段
- 经常GROUP BY和ORDER BY的列
- UPDATE、DELETE 的 WHERE 条件列
- DISTINCT 字段需要创建索引
- 多表 JOIN 连接操作时，创建索引注意事项
  - 首先， 连接表的数量尽量不要超过 3 张 ，因为每增加一张表就相当于增加了一次嵌套的循环，数量级增
    长会非常快，严重影响查询的效率。
    其次，对 WHERE 条件创建索引，因为WHERE才是对数据条件的过滤。如果在数据量非常大的情况下，
    没有 WHERE 条件过滤是非常可怕的。
    最后， 对用于连接的字段创建索引 ，并且该字段在多张表中的 类型必须一致 。比如 course_id 在
    student_info 表和 course 表中都为 int(11) 类型，而不能一个为 int 另一个为 varchar 类型。
- 使用最频繁的列放到联合索引的左侧 最左前缀原则
- 在多个字段都要创建索引的情况下，联合索引优于单值索引
- 需要避免重复索引，冗余索引
```
    # 重复
    KEY idx_name_birthday_phone_number (name(10), birthday, phone_number),
    KEY idx_name (name(10))
    
    # 冗余
    UNIQUE uk_idx_c1 (col1),
    INDEX idx_c1 (col1)
```

### 查看系统性能参数
``` 
# 查询连接MySQL服务器的次数
show STATUS LIKE 'Connections';

# 查询MySQL服务器的上 线时间
show STATUS LIKE 'Uptime';

# 查询MySQL服务器的慢查询的次数
show STATUS LIKE 'Slow_queries';

# 查询Select查询返回的行数
show STATUS LIKE 'Innodb_rows_read';

# 查询执行INSERT操作插入的行数
show STATUS LIKE 'Innodb_rows_inserted';

# Innodb_rows_deleted 执行DELETE操作删除的行数
# Com_update 更新操作 的次数
# Com_delete 删除操作的次数
# Com_insert 插入操作的次数
# Com_select 查询操作的次数

# 最近一次的查询的查询了多少数据页
SHOW STATUS LIKE 'last_query_cost';

```

### mysql定位执行慢的SQL：慢查询日志
``` 
# 开启slow_query_log，开启之后才会记录日志，这也只是临时的，重启mysql失效
set global slow_query_log='ON';

# 查看是否是开启了慢日志记录和慢日志位置
mysql> show variables like '%slow_query_log%';
+---------------------+--------------------------------------+
| Variable_name       | Value                                |
+---------------------+--------------------------------------+
| slow_query_log      | OFF                                  |
| slow_query_log_file | /var/lib/mysql/d70a5deda3eb-slow.log |
+---------------------+--------------------------------------+

# 查看并修改long_query_time阈值
mysql>  show variables like '%long_query_time%';
+-----------------+-----------+
| Variable_name   | Value     |
+-----------------+-----------+
| long_query_time | 10.000000 |
+-----------------+-----------+

set global long_query_time = 1;

# 查看慢查询数目
SHOW GLOBAL STATUS LIKE '%Slow_queries%';

# 查看mysqldumpslow --help的使用情况
mysqldumpslow --help

#得到返回记录集最多的10个SQL
mysqldumpslow -s r -t 10 /var/lib/mysql/atguigu-slow.log

#得到访问次数最多的10个SQL
mysqldumpslow -s c -t 10 /var/lib/mysql/atguigu-slow.log

#得到按照时间排序的前10条里面含有左连接的查询语句
mysqldumpslow -s t -t 10 -g "left join" /var/lib/mysql/atguigu-slow.log

#另外建议在使用这些命令时结合 | 和more 使用 ，否则有可能出现爆屏情况 
mysqldumpslow -s r -t 10 /var/lib/mysql/atguigu-slow.log | more

# 永久关闭慢查日志记录功能之后重启mysql
 [mysqld]
slow_query_log=OFF

# 临时性方式之后重启mysql
SET GLOBAL slow_query_log=off;

```

### 查看 SQL 执行成本 profiling
```

# 查看是否开启profiling
mysql> show variables like 'profiling';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| profiling     | OFF   |
+---------------+-------+

# 开启profiling
set profiling = 'ON';

# 查看所有的profiles
show profiles;

# 查看指定的query的profile
show profile cpu,block io for query 2;

# show profile的常用查询参数:
1 ALL:显示所有的开销信息。 
2 BLOCK IO:显示块IO开销。 
3 CONTEXT SWITCHES:上下文切换开销。 
4 CPU:显示CPU开销信息。 
5 IPC:显示发送和接收开销信息。 
6 MEMORY:显示内存开销信息。 
7 PAGE FAULTS:显示页面错误开销信息。 
8 SOURCE:显示和Source_function，Source_file， Source_line相关的开销信息。 
9 SWAPS:显示交换次数开销信息。
```

### 分析查询语句:EXPLAIN
如果sql的执行成本在executing的时候耗时比较大的话，那就按照EXPLAIN查询执行计划并优化是否走索引
``` 
EXPLAIN select * From table where id = 1;

#查询的每一行记录都对应着一个单表
EXPLAIN SELECT * FROM s1;

#s1:驱动表  s2:被驱动表
EXPLAIN SELECT * FROM s1 INNER JOIN s2;

#2. id：在一个大的查询语句中每个SELECT关键字都对应一个唯一的id，每个select对应一个id，其实更好的理解是把id作为趟
 SELECT * FROM s1 WHERE key1 = 'a';

# 一个id
 SELECT * FROM s1 INNER JOIN s2
 ON s1.key1 = s2.key1
 WHERE s1.common_field = 'a';

# 两个id 
 SELECT * FROM s1 
 WHERE key1 IN (SELECT key3 FROM s2);

# 两个id
 SELECT * FROM s1 UNION SELECT * FROM s2;

# 一个id
 EXPLAIN SELECT * FROM s1 WHERE key1 = 'a';
 
# 一个id 
 EXPLAIN SELECT * FROM s1 INNER JOIN s2;
 
# 两个id
 EXPLAIN SELECT * FROM s1 WHERE key1 IN (SELECT key1 FROM s2) OR key3 = 'a';

```

1. explain select 语句的返回结果说明
   ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221405251.png)

2. 可以查看是不是走了索引 
   ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221140670.png)

3. 针对单表查询的type字段： 

CONST,SYSTEM->EQ_REF->REF->RANGE->INDEX->ALL

  - system 最高的o1拿到
  - CONST 走索引直接拿到的性能也是很好，当我们根据主键或者唯一二级索引列与常数进行等值匹配时，对单表的访问方法就是 const
``` 
mysql> explain select * From t_emp where emp_id =1;
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref   | rows | filtered | Extra |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
|  1 | SIMPLE      | t_emp | NULL       | const | PRIMARY       | PRIMARY | 4       | const |    1 |   100.00 | NULL  |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
1 row in set, 1 warning (0.00 sec)
```
  - EQ_REF 在连接查询时，如果被驱动表是通过主键或者唯一二级索引列等值匹配的方式进行访问的，对该被驱动表的访问方法就是`eq_ref`
``` 
mysql> explain SELECT * FROM s1 INNER JOIN s2 ON s1.id = s2.id;
+----+-------------+-------+------------+--------+---------------+---------+---------+-------------------+------+----------+-------+
| id | select_type | table | partitions | type   | possible_keys | key     | key_len | ref               | rows | filtered | Extra |
+----+-------------+-------+------------+--------+---------------+---------+---------+-------------------+------+----------+-------+
|  1 | SIMPLE      | s1    | NULL       | ALL    | PRIMARY       | NULL    | NULL    | NULL              |    1 |   100.00 | NULL  |
|  1 | SIMPLE      | s2    | NULL       | eq_ref | PRIMARY       | PRIMARY | 4       | mysql_learn.s1.id |    1 |   100.00 | NULL  |
+----+-------------+-------+------------+--------+---------------+---------+---------+-------------------+------+----------+-------+
2 rows in set, 1 warning (0.03 sec)
```
- ref  #当通过普通的二级索引列与常量进行等值匹配时来查询某个表，那么对该表的访问方法就可能是`ref`
``` 
mysql>  EXPLAIN SELECT * FROM s1 WHERE key1 = 'a';
+----+-------------+-------+------------+------+---------------+----------+---------+-------+------+----------+-------+
| id | select_type | table | partitions | type | possible_keys | key      | key_len | ref   | rows | filtered | Extra |
+----+-------------+-------+------------+------+---------------+----------+---------+-------+------+----------+-------+
|  1 | SIMPLE      | s1    | NULL       | ref  | idx_key1      | idx_key1 | 303     | const |    1 |   100.00 | NULL  |
+----+-------------+-------+------------+------+---------------+----------+---------+-------+------+----------+-------+
1 row in set, 1 warning (0.03 sec)
```
- ref_or_null 当对普通二级索引进行等值匹配查询，该索引列的值也可以是`NULL`值时，那么对该表的访问方法
- range 如果使用索引获取某些`范围区间`的记录，那么就可能使用到`range`访问方法
- INDEX 索引全扫描，MYSQL遍历整个索引来查找匹配的行。
- ALL   全表扫描，MYSQL扫描全表来找到匹配的行

4. 关于explain中的key_len的计算方法：
- 表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。在不损失精确性的情况下，长度越短越好；
- key_len显示的值为索引字段的最大可能长度，并非实际使用长度，即key_len是根据表定义计算而得，不是通过表内检索出的
- 所以我们可以从key_len来判断使用联合索引时候，索引用到了哪几个字段
- 计算方法如下：
  - 注意索引字段的附加信息，下面两种
    - 可以分为变长和定长数据类型，当索引字段为定长数据类型时，如char、int、datetime，需要有是否为空的标记，这个标记占用1个字节（对于not null的字段来说，则不需要这1个字节）
    - 对于变长数据类型，比如varchar，除了是否为空的标记之外，还需要有长度信息，需要占用2个字节
  - char、varchar、blob、text等字符集来说，key_len的长度还和字符集有关
    - 在latin1字符集中，一个字符占用1个字节 
    - 在GBK字符集中，一个字符占用2个字节 
    - 在utf8字符集中，一个字符占用3个字节
  - 整数类型、浮点数类型、时间类型的索引长度
    - NOT NULL=字段本身的字段长度
    - NULL=字段本身的字段长度+1，因为需要有是否为空的标记，这个标记需要占1个字节
- key_len案例：
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302230927094.png)
### trace 工具 @TODO

### 主键插入顺序和性能的关系
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221531062.png)
- 所以是可能会引起页分裂
- 最好把主键设置为 AUTO_INCREMENT 
- 然后不在指定主键
- 顺序写入 减少分裂

### 索引失效的情况
1. 最左匹配原则：
   如果一个索引有多个字段col1，col2，col3 这样的，查询是时候where col1=2 and col3=4 这种只能使用部分索引col3用不到
   如果一个索引有多个字段col1，col2，col3 这样的，查询是时候where col1=2 and col2>2 and col3=4 这种只能使用部分索引col3用不到，第二是非等值的
2. 计算、函数、类型转换(自动或手动)导致索引失效
``` 
# 生效 type = range
EXPLAIN SELECT SQL_NO_CACHE * FROM student WHERE student.name LIKE 'abc%';
# 失效 type = ALL
EXPLAIN SELECT SQL_NO_CACHE * FROM student WHERE LEFT(student.name,3) = 'abc';

# 生效 type = ref
explain select * From XX where graph_id = XXXX
# 失效 type = ALL
explain select * From XX where graph_id + 1 = XXX

# 失效 type = ALL
explain select * From xx where substr(company_name,1,3)='深圳市'

# 失效 type = ALL ddl中定义为char查询没加引号
explain select * From xx where company_name=123
# 生效 type = const
explain select * From xx where company_name='123'

```
3. 不等于(!= 或者<>)索引失效
``` 
# 生效
select * From xx where graph_id = 41526137
# 失效
select * From xx where graph_id <> 41526137
# 失效
select * From xx where graph_id != 41526137
```
4. is null可以使用索引，is not null无法使用索引
```
# 生效
EXPLAIN SELECT SQL_NO_CACHE * FROM student WHERE age IS NULL;
# 失效
EXPLAIN SELECT SQL_NO_CACHE * FROM student WHERE age IS NOT NULL;
```
5. like以通配符%开头索引失效，%放在后面的生效；严禁左模糊或者全模糊
6. OR 前后存在非索引的列，索引失效
``` 
# 失效因为classid没有索引
EXPLAIN SELECT SQL_NO_CACHE * FROM student WHERE age = 10 OR classid = 100;
```

### 关联查询的sql优化
join中的驱动表和被驱动表，先了解在join连接时哪个表是驱动表，哪个表是被驱动表：
1. 当使用left join时，左表是驱动表，右表是被驱动表
2. 当使用right join时，右表是驱动表，左表是被驱动表
3. 当使用join时，mysql会选择数据量比较小的表作为驱动表，大表作为被驱动表
4. join的底层原理：join情况下，驱动表有索引不会使用到索引，被驱动表建立索引会使用到索引，在以小表驱动大表的情况下，再给大表建立索引会大大提高执行速度

- MySQL 表关联的算法是 Nest Loop Join，是通过驱动表的结果集作为循环基础数据，然后一条一条地通过该结果集中的数据作为过滤条件到下一个表中查询数据，然后合并结果。
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302221628745.png)
```
EXPLAIN SELECT SQL_NO_CACHE FROM type LEFT JOIN book ON type.card = book.card;
```
上面写法中type就是驱动表book就是被驱动表，被驱动表一定要有索引否则会引起全表扫描，驱动表一定是会进行全表扫描了

### 子查询的优化
- 子查询的效率不高，MySQL需要为内层查询语句的查询结果建立一个临时表
- 子查询的结果可能很大，还没有索引
- 最好是改为join连接起来
- 尽量不要使用NOT IN 或者 NOT EXISTS，用LEFT JOIN xxx ON xx WHERE xx IS NULL替代

### 排序优化
1. 在 WHERE 条件字段上加索引，但是为什么在ORDER BY 字段上还要加索引呢?
- order by 建索引是为了避免使用FileSort方式排序。
2. 使用哪些索引其实要看数据量和索引情况，按照explain来key_len来分析具体使用了哪几个字段的索引
3. fileSort两种算法：
   - 双路排序 (慢)；先读 order by列 ，然后去读实际数据
   - 单路排序 (快)：直接去取所有的字段的数据

### group by的优化
- 当无法使用索引列，增大 max_length_for_sort_data 和 sort_buffer_size 参数的设置
- group by 使用索引的原则几乎跟order by一致 ，group by 即使没有过滤条件用到索引，也可以直接使用索引。
- group by 先排序再分组，遵照索引建的最佳左前缀法则
- 包含了order by、group by、distinct这些查询的语句，where条件过滤出来的结果集请保持在1000行 以内，否则SQL会很慢。
- group by没有过滤条件，也可以用上索引。Order By 必须有过滤条件才能使用上索引

### 分页优化
select * From table limit 20000,10,这种方式是排序前面的20010条数据，然后返回20000~20010条
1. 优化方式一：
   EXPLAIN SELECT * FROM student t,(SELECT id FROM student ORDER BY id LIMIT 2000000,10) a  WHERE t.id = a.id;
2. 优化方式二：不断中间的id
   SELECT * FROM student WHERE id > 2000000 LIMIT 10;

### 覆盖索引
一个索引包含了满足查询结果的数据就叫做覆盖索引。
比如： 如果表中是name,graph_id 是联合索引，那么 
  select name from table where graph_id = 1；
  select name, graph_id from table where graph_id = 1； 
  select name, graph_id, id from table where graph_id = 1；
这三个都是可以使用到覆盖索引的，简单说就是 索引列+主键 包含 SELECT 到 FROM之间查询的列 。
可以通过 explain 来区分

1. 覆盖索引的好处：
   不用再回表，避免Innodb表进行索引的二次查询(回表)
   可以把随机IO变成顺序IO加快查询效率
2. 覆盖索引的缺点：
   索引字段的维护

### 前缀索引
给字符串加索引的时候，是可以指定给字符串的前面部分字符加索引的
``` 
# 默认指定所有的字符
alter table teacher add index index1(email);

# 指定前面的几个字符
alter table teacher add index index1(email(6));

```

使用前缀索引，定义好长度，就可以做到既节省空间，又不用额外增加太多的查询成本；
前缀索引对覆盖索引的影响：使用前缀索引就用不上覆盖索引对查询性能的优化了，这也是你在选择是否使用前缀索引时需要考 虑的一个因素

### 索引下推
1. 什么是索引下推
Index Condition PushDown(ICP)，是一种在存储引擎层使用索引过滤数据的一种优化方式。
ICP可以减少存储引擎访问基表的次数以及MySQL服务器访问存储引擎的次数。

``` 
# 案例一：在name有单独索引的情况下
select * From table where name > 'aaa' and name like '%aaaa就是' 

# 案例二：在zipcode，lastname 为联合索引的情况下
SELECT *
FROM people
WHERE zipcode='000001'
AND lastname LIKE “%张%”
AND address LIKE “%北京%”
```

上面的两个案例都是只能用到部分的索引，
但是后面的过滤字段可以继续将过滤条件用到索引上：
- 案例一中：like虽然不能走索引，但是可以在查询时候进行判断，减少回表的数量；
- 案例二中：lastname 本来用不到索引，但是也可以通过索引下推ICP，在第一次查询就过滤，减少回表量

ICP的 加速效果 取决于在存储引擎内通过 ICP筛选 掉的数据的比例。

2. 索引下推的条件
- 只能用于二级索引(secondary index)
- explain显示的执行计划中type值(join 类型)为 range 、 ref 、 eq_ref 或者 ref_or_null 。
- 并非全部where条件都可以用ICP筛选，如果where条件的字段不在索引列中，还是要读取整表的记录 到server端做where过滤。
- ICP可以用于MyISAM和InnoDB存储引擎
- MySQL 5.6版本的不支持分区表的ICP功能，5.7版本的开始支持。 6 当SQL使用覆盖索引时，不支持ICP优化方法。

3. 索引下推功能的开启和关闭：默认是关闭的
``` 
# 关闭索引下推
SET optimizer_switch ='index-condition_pushdown=off’；
# 打开索引下推
SET optimizer_switch ='index_condition_pushdown=on’:
```

4. 从explain判定是不是使用了索引下推
explain中的会注明是 using index condition


### 索引的其他优化策略
1. in 和 exists 核心也是小表驱动大表
2. count(*) 和 count(1)的区别？ --在 innodb中都一样
3. count(*) 在不同的搜索引擎的区别？ innodb中是O(n)，在MYISAM中是O(1)
4. count(具体字段) 时候会走二级索引，会走最小的二级索引，这样成本是最低的，尽量不会使用聚簇索引
5. limit 1 找到一条就停止，唯一索引不用，因为就一条

### 主键的设计原则
1. 简单自增id的问题
- 可靠性不高，存在ID回溯问题（mysql 8修复了）
- 安全性不高，接口中可以去猜比如user_id=5
- 性能差
- 局部唯一，只是当前数据库唯一，全局数据不是唯一的
2. 从业务出发来生成id的问题
- 手机号？ 不是很好，手机号可以复用
- 卡号？卡号也存在重复
- 身份证号？个人隐私，不一定能拿到
3. 核心业务使用 特殊的uuid，保证存储耗费不大的情况下，唯一且自增
   非核心业务使用 自增的，这个没啥影响，比如 日志等
4. 关于uuid：
其实是有不同的生成版本，mysql这一版本是 时间戳+UUID版本+MAC地址，是全局唯一的 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231034201.png)
这里是无序的，不适合做主键-因为有页面分裂问题
4. 如何正确生成uuid作为业务主键（唯一+自增+压缩）
- mysql8中 uuid_to_bin(UUID(),TRUE); 这个实现了 存储不高+自增+唯一+压力在客户端
``` 
# UUID_TO_BIN 实现对 UUID 字符串进行二进制压缩，32字符-->16bit
# BIN_TO_UUID 是相应的解压操作，16bit-->32字符
# IS_UUID 可以帮助我们验证传递过来的参数是否为有效的 UUID，合法的 UUID 是由 32个十六进制字符与几个可选字符（'{', '-', '}'）构成
   
# 建表
CREATE TABLE t (id binary(16) PRIMARY KEY); 

# 插入
INSERT INTO t VALUES(UUID_TO_BIN(UUID(), true));
```
- mysql 5中需要手动赋值字段做主键
- 总部 MySQL 数据库中，有一个管理信息表，在这个表中添加一个字段，专门用来记录当前会员编号的最大值。

### 为啥需要进行数据库设计
- 方便使用
- 降低冗余
- 保证正确性
- 规范化约束

### 范式
在关系型数据库中，关于数据表设计的基本原则、规则就称为范式
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231048345.png)

1.  键和相关属性的概念
    这里有两个表:
    球员表(player) :球员编号 | 姓名 | 身份证号 | 年龄 | 球队编号 球队表(team) :球队编号 | 主教练 | 球队所在地
    - 超键 :对于球员表来说，超键就是包括球员编号或者身份证号的任意组合，比如(球员编号) (球员编号，姓名)(身份证号，年龄)等。
    - 候选键 :就是最小的超键，对于球员表来说，候选键就是(球员编号)或者(身份证号)。 主键 :我们自己选定，也就是从候选键中选择一个，比如(球员编号)。
    - 外键 :球员表中的球队编号。
    - 主属性 、 非主属性 :在球员表中，主属性是(球员编号)(身份证号)，其他的属性(姓名)(年龄)(球队编号)都是非主属性。
2. 
- 第一范式  每个字段不能再继续拆分（其实很主观，比如是地址=省市区）
- 第二范式  
  - 每个表必须有主关键字(Primary key)
  - 其他数据元素与主关键字一一对应
  - 它的规则是要求数据表里的所有非主属性都要和该数据表的主键有完全依赖关系；就是主键定了则其他字段都定了，然后其他字段也是由主键唯一定义；
  - 如果有哪些非主属性只和主键的一部份有关的话，它就不符合第二范式；
  - 比如 表的决定关系是 (球员编号, 比赛编号) → (姓名, 年龄, 比赛时间, 比赛场地，得分)，但是 比赛时间和场地这俩属性可以从比赛编号单独决定，所以不满足第二范式
  - 需要拆分为 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231124357.png) 三个表
  - 否则会产生很大的数据冗余，删除问题，插入问题等
- 第三范式  
  - 不存在传递依赖
- 反范式化：
  - 范式的话，join比较多
  - 按照业务来说，最好是冗余些好
  - 冗余程度自己来控制
  - 空间换时间
  - 冗余的数据需要大量的修改
- 巴斯范式
  - 若一个关系达到了第三范式，并且它只有一个候选键，或者它的每个候选键都是单属性，则该关系自然达到Bc范式。
- 第四范式
- 第五范式


### ER模型和数据表
- ER 模型中有三个要素，分别是实体、属性和关系。实体用矩形表示，属性用椭圆，关系用菱形表示 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231338135.png)
- ER模型转为实体表：
  - (1)一个 实体 通常转换成一个 数据表 ;
  - (2)一个 多对多的关系 ，通常也转换成一个 数据表 ;
  - (3)一个 1 对 1 ，或者 1 对多 的关系，往往通过表的 外键 来表达，而不是设计一个新的数据表; 
  - (4) 属性 转换成表的 字段 。


### 数据库的规范
- 关于库和账号的规范：
1. 表尽量的少 
2. 数据表中联合主键的字段个数越少越好 
3. 库名都是小写，单词按照_区分开来，需要指明字符集并且字符集只能是utf8或者utf8mb4
``` 
CREATE DATABASE crm_fund DEFAULT CHARACTER SET 'utf8mb4' ;
```
4. 账号创建，对于程序连接数据库账号，遵循权限最小原则 
5. 临时库以 tmp_ 为前缀，并以日期为后缀 
6. 备份库以 bak_ 为前缀，并以日期为后缀


- 关于表的规范：
1. 表名、列名一律小写 ，不同单词采用下划线分割
1. 表名要求有模块名强相关，同一模块的表名尽量使用 统一前缀
2. 创建表时必须 显式指定字符集 为utf8或utf8mb4。
3. 表名、列名禁止使用关键字(如type,order等)。
4. 创建表时必须 显式指定表存储引擎 类型。如无特殊需求，一律为InnoDB。
5. 字段命名应尽可能使用表达实际含义的英文单词或 缩写 。如:公司 ID，不要使用 corporation_id, 而用corp_id 即可。
6. 布尔值类型的字段命名为 is_描述 。如member表上表示是否为enabled的会员的字段命 名为 is_enabled。
7. 禁止在数据库中存储图片、文件等大的二进制数据，存储放在obs oss等外接存储介质url地址
8. 建表时关于主键: 
  - 表必须有主键 (1)强制要求主键为id，类型为int或bigint，且为 auto_increment 建议使用unsigned无符号型。 
  - (2)标识表里每一行主体的字段不要设为主键，建议设为其他字段如user_id，order_id等，并建立unique key索引。
  - 因为如果设为主键且主键值为随机 插入，则会导致innodb内部页分裂和大量随机I/O，性能下降
9. 核心表(如用户表)必须有行数据的 创建时间字段 (create_time)和 最后更新时间字段 (update_time)，便于查问题。
10. 所有存储相同数据的 列名和列类型必须一致 (一般作为关联列，如果查询时关联列类型 不一致会自动进行数据类型隐式转换，会造成列上的索引失效，导致查询效率降低)。
11. 中间表 tmp_ 开头
``` sql
CREATE TABLE user_info (
`id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
`user_id` bigint(11) NOT NULL COMMENT '用户id',
`username` varchar(45) NOT NULL COMMENT '真实姓名',
`email` varchar(30) NOT NULL COMMENT '用户邮箱',
`nickname` varchar(45) NOT NULL COMMENT '昵称',
`birthday` date NOT NULL COMMENT '生日',
`sex` tinyint(4) DEFAULT '0' COMMENT '性别',
`short_introduce` varchar(150) DEFAULT NULL COMMENT '一句话介绍自己，最多50个汉字', `user_resume` varchar(300) NOT NULL COMMENT '用户提交的简历存放地址', `user_register_ip` int NOT NULL COMMENT '用户注册时的源ip',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
CURRENT_TIMESTAMP COMMENT '修改时间',
`user_review_status` tinyint NOT NULL COMMENT '用户资料审核状态，1为通过，2为审核中，3为未通过，4为还未提交审核',
PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`),
  KEY `idx_username`(`username`),
  KEY `idx_create_time_status`(`create_time`,`user_review_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网站用户基本信息'
```

- 关于索引的规范：
1. 【强制】InnoDB表必须主键为id int/bigint auto_increment，且主键值 禁止被更新 ，核心表用改进的uuid。
2. 【强制】InnoDB和MyISAM存储引擎表，索引类型必须为 BTREE 。
3. 【建议】主键的名称以 pk_ 开头，唯一键以 uni_ 或 uk_ 开头，普通索引以 idx_ 开头，一律使用小写格式，以字段的名称或缩写作为后缀。
4. 【建议】多单词组成的column name，取前几个单词首字母，加末单词组成column_name。如:sample 表 member_id 上的索引:idx_sample_mid。
5. 【建议】单个表上的索引个数 。
6. 【建议】在建立索引时，多考虑建立   ，并把区分度最高的字段放在最前面。
7. 【建议】在多表 JOIN 的SQL里，保证被驱动表的连接列上有索引，这样JOIN 执行效率最高。
8. 【建议】建表或加索引时，保证表里互相不存在 冗余索引 。 比如:如果表里已经存在key(a,b)，则key(a)为冗余索引，需要删除。

- 关于SQL编写：
1. 【强制】程序端SELECT语句必须指定具体字段名称，禁止写成 *。
2. 【建议】程序端insert语句指定具体字段名称，不要写成INSERT INTO t1 VALUES(...)。
3. 【建议】除静态表或小表(100行以内)，DML语句必须有WHERE条件，且使用索引查找。
4. 【建议】INSERT INTO...VALUES(XX),(XX),(XX).. 这里XX的值不要超过5000个。 值过多虽然上线很 快，但会引起主从同步延迟。
5. 【建议】SELECT语句不要使用UNION，推荐使用UNION ALL，并且UNION子句个数限制在5个以 内。
6. 【建议】线上环境，多表 JOIN 不要超过5个表。
7. 【建议】减少使用ORDER BY，和业务沟通能不排序就不排序，或将排序放到程序端去做。ORDER BY、GROUP BY、DISTINCT 这些语句较为耗费CPU，数据库的CPU资源是极其宝贵的。
8. 【建议】包含了ORDER BY、GROUP BY、DISTINCT 这些查询的语句，WHERE 条件过滤出来的结果集请保持在1000行以内，否则SQL会很慢。
9. 【建议】对单表的多次alter操作必须合并为一次，对于超过100W行的大表进行alter table，必须经过DBA审核，并在业务低峰期执行，多个alter需整 合在一起。 因为alter table会产生 表锁 ，期间阻塞对于该表的所有写入，对于业务可能会产生极 大影响。
10. 【建议】批量操作数据时，需要控制事务处理间隔时间，进行必要的sleep。
11. 【建议】事务里包含SQL不超过5个。 因为过长的事务会导致锁数据较久，MySQL内部缓存、连接消耗过多等问题。
12. 【建议】事务里更新语句尽量基于主键或UNIQUE KEY，如UPDATE... WHERE id=XX;否则会产生间隙锁，内部扩大锁定范围，导致系统性能下降，产生死锁。

### 数据库建模设计工具 @TODO
- PDMan


### 宏观的mysql调优方向
出发点：
  * 用户的反馈(主要)
  * 日志分析(主要)
  * 服务器资源使用监控
  * 数据库内部状况监控
  * 事务的监控
  * 锁等待的监控
主要步骤方向：
  * 优化表设计
  * 优化逻辑查询
  * 优化物理查询：主要是对于索引的正确使用
  * 使用 Redis 或 Memcached 作为缓存
  * 库级优化： 读写分离 数据分片

### 优化MySQL服务器（配置mysql的配置文件）
- 硬件最有效，但是花钱
- 其他配置参数（区分下生效的引擎）：
  - innodb_buffer_pool_size
  - key_buffer_size
  - table_cache
  - query_cache_size
  - sort_buffer_size
  - wait_timeout
  - interactive_timeout

### 优化数据库结构
  - 拆分表:冷热数据分离 比如会员表，不常用字段单独转为member_detail 和 常用 member 两个表
  - 增加中间表:
  - 增加冗余字段:反范式化
  - 优化数据类型
    - 对整数类型数据进行优化 能不能加UNSIGNED
    - 既可以使用文本类型也可以使用整数类型的字段，要选择使用整数类型 。
    - 避免使用TEXT、BLOB数据类型
    - 避免使用ENUM类型
    - 使用TIMESTAMP存储时间
    - 用DECIMAL代替FLOAT和DOUBLE存储精确浮点数
  - 优化插入速度
    - 禁用索引
    - 批量插入

### 分析表
ANALYZE [LOCAL | NO_WRITE_TO_BINLOG] TABLE tbl_name[,tbl_name]...
使用 ANALYZE TABLE 分析表的过程中，数据库系统会自动对表加一个 只读锁 。在分析期间，只能读取 表中的记录，不能更新和插入记录。
ANALYZE TABLE分析后的统计结果会反应到   的值，该值统计了表中某一键所在的列不重复 的值的个数。 该值越接近表中的总行数，则在表连接查询或者索引查询时，就越优先被优化器选择使 用。

### 检查表 @TODO

### 优化表 @TODO


### 读写分离&双主双从
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231534965.png)

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231534660.png)

### 垂直分库 & 垂直分表 & 水平分表
库中的表分别存在不同服务器上 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231535196.png)

一个表的不同列放在不同的服务器上 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231536185.png)

表进行分片

### 事务中的UNDO日志和REDO日志
- undo 和 redo日志是ACD（原子性、一致性和持久性）实现的机制原理
- 都是存储引擎生成的日志，区分于binlog日志是数据层的
1. redo日志：
  - 缓冲池的存在意义：缓解磁盘和CPU的速度鸿沟；checkpoint机制按照一定的时间规律刷盘（定期）；
  - 问题点：看是不是宕机发生在commit写入没写入磁盘 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231710199.png)
  - 不能实时修改内存就同步磁盘的原因：效率，io时间耗时是按照页为单位的
  - WAL机制：
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231743845.png)
    - 上面漏了要修改的表data从磁盘加载到内存
    - 重点：redo日志没生成完成，不会告知client commit成功；redo log buffer
    - 理解下 过程2 '遇到commit刷盘断电了如何恢复'
  - redo日志顺序写盘（比写data页要快），redo日志占用的空间非常小（比写data页要快），可以进一步降低刷盘的频率
  - 查看redo log buffer大小
```
mysql> show variables like '%innodb_log_buffer_size%';
+------------------------+----------+
| Variable_name          | Value    |
+------------------------+----------+
| innodb_log_buffer_size | 16777216 |
+------------------------+----------+
```
  - 查看磁盘上的redo log ： ib_logfile0 和 ib_logfile1
  - 引入page_cache：innodb_flush_log_at_trx_commit 内存和磁盘还是有一个page_cache，通过这个参数来控制
    - 设置为0 :表示每次事务提交时不进行刷盘操作。(系统默认master thread每隔1s进行一次重做日 志的同步)
    - 设置为1 :表示每次事务提交时都将进行同步，刷盘操作(默认值) ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302231756141.png)
    - 设置为2 :表示每次事务提交时都只把 redo log buffer 内容写入 page cache，不进行同步。由os自己决定什么时候同步到磁盘文件。
2. Undo日志：
   - 其实就是对于事务对于数据的操作记录
   - 会构成一个版本链
   - update & insert
   - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302252120122.png)


###  MySQL并发事务访问相同记录的问题
如果两个事务对于同一个数据的操作是这样的：
1. 读-读 两个都是读取相同的数据，不需要锁来介入
2. 写-写 每个事务产生描述这个事务的锁，在锁结构中表征是否有可操作记录的权限表示是不是等待
   - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302240926320.png)
   - is_waiting字段标志 true作为等待标志
3. 读-写
   - 脏读 read uncommited
   - 不可重复读取 两次读不一致
   - 幻读 前面条数和后面的条数不一样
   - 关于读写事务的上述问题呢？
     - 方案一：读操作采用mvcc，写操作采用锁机制
     - 方案二：读和写都采用锁机制
     - 读写都是锁的话会有排队执行，效率没有mvcc读和加锁的写效率的方案高

### mvcc是如何解决读过程中的脏读，不可重复读呢？读-mvcc
- mvcc下每个select操作都生成一个ReadView，当然这个ReadView是只有针对已经COMMITED的记录才会生成，否则不会生成，读也读不到，即
  本身ReadView存在就说明是读取的是已经提交的了，这个存在就保证了 READ COMMITTED 隔离级别 这个隔离级别的实现
- 进一步的，在上面的基础上的，对于REPEATABLE READ 隔离级别 ，这个级别的实现原理是：每个首次select的数据都存储到ReadView，这个ReadView中的数据服务于后面的select，那么
  除了首次的select，后面的select都是复用ReadView，所以无论如何重复读都是这个数，这也就实现了可重复读

### mysql的锁的分类
1. 对数据的操作类型划分：
  - 共享锁/读锁  S锁 事务不必等着前面
  - 排它锁/写锁  X锁 事务需要排队，如果前面的X或者S事务都没有结束，那么就会卡住或者（ERROR 1205 (HY000): Lock wait timeout exceeded; try restarting transaction）
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241013579.png)
  - 如何实现排他读，共享读，即对读操作上锁：
    - select * From table FOR UPDATE; -x锁
    - select * From table FOR SHARE; - s锁
    - select * From table LOCK IN SHARE MODE; - s锁
    - 排它锁的排他性体现：
      - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241035464.png)
      - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241036426.png)
      - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241037769.png)
      - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241037452.png)
  - 平常的UPDATE DELETE INSERT是如何加锁的？
    - 一定会加X锁的操作 UPDATE DELETE 一定有
    - INSERT呢因为本来没有这条数据没法加锁
2. 锁粒度：
  - 表锁之：S锁 X锁，粒度比较大的
    1. 普通的表锁 S锁 X锁
    - 对整个表都加锁，开销小，但是并发性不高
    - MYISAM只支持到表锁，InnoDB的话是可以支持更细的粒度的
    - 主要发生在DDL和其他的DML语句
    - 在对某个表执行一些诸如 ALTER TABLE 、 DROP TABLE 这类的 DDL 语句时，其他事务对这个表并发执行诸如SELECT、INSERT、DELETE、UPDATE的语句会发生阻塞。
    - 同理，某个事务 中对某个表执行SELECT、INSERT、DELETE、UPDATE语句时，在其他会话中对这个表执行 DDL 语句也会 发生阻塞。这个过程其实是通过在 server层 使用一种称之为 元数据锁
    - 手动获取表t 的 S锁 或者 X锁 可以这么写:
      - LOCK tables t READ :InnoDB存储引擎会对表 t 加表级别的 S锁
      - LOCK tables t WRITE :InnoDB存储引擎会对表 t 加表级别的 X锁
      - show open tables where in_use>0;  查看表锁
    2. 表锁之-意向锁
      - 意向锁本身是一种表锁
      - 是一种由行锁引起的表锁
      - 场景就是： 行上加锁了，然后表上也要加锁的时候不用再一行一行的遍历是不是存在行锁
      - 存在意向共享锁，意向排它锁
    3. 表锁之-自增锁 （AUTO-INC锁)
      - 这个锁在Auto INCREMENT 的主键插入数据时候会用到，每条insert申请一次
      - innodb_autoinc_lock_mode = 0(“传统”锁定模式)
      - innodb_autoinc_lock_mode = 1(“连续”锁定模式) 简单插入模式不用，批量的话需要申请锁
      - innodb_autoinc_lock_mode = 2(“交错”锁定模式) 这种不保证是不是连续的
    4. 表锁-元数据锁(MDL锁)
      - 增删改查 + 改DDL结构 的这两个操作
      - 当对一个表做增删改查操作的时候，加 MDL读锁;
      - 当要对表做结构变更操作的时候，加 MDL 写 锁。
  - 行锁
    - 在存储引擎层实现的，开销大，但是并发高
    1. 记录锁：X记录锁 S记录锁 
    2. 间隙锁：避免一个在读另外一个写入满足条件的更多的记录，实际就是说，如果我想查询id =5的那条不存在的数据的话，
       其实是给最近的（2，8） 这个间隙上了个锁，此时如果事务往id=6插入是卡住的
    3. 临键锁(Next-Key Locks)
       有时候我们既想  锁住某条记录，又想阻止其他事务在该记录前边的插入
    4. 插入意向锁(Insert Intention Locks)
  - 页锁
    - 介于行锁和表锁之间
3. 对待锁的态度： 是一种思想
  - 悲观锁 直接使用表锁 页锁 行锁，并发低
    悲观锁总是假设最坏的情况，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上 锁，这样别人想拿这个数据就会 阻塞 直到它拿到锁(共享资源每次只给一个线程使用，其它线程阻塞， 用完后再把资源转让给其它线程 )。比如行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁，当 其他线程想要访问数据时，都需要阻塞挂起。Java中 synchronized 和 ReentrantLock 等独占锁就是 悲观锁思想的实现。
    比如在秒杀场景下使用悲观锁，select * From FOR UPDATE;
  - 乐观锁 程序来实现，并发高
    乐观锁认为对同一数据的并发操作不会总发生，属于小概率事件，不用每次都对数据上锁，但是在更新 的时候会判断一下在此期间别人有没有去更新这个数据，也就是 不采用数据库自身的锁机制，而是通过 程序来实现 。在程序上，我们可以采用 版本号控制  或者 CAS机制  实现。
    乐观锁可以通过版本号来实现，秒杀时候查询version版本号，写回去的时候也是查询版本号是还是和自己开始时候的一样；
    乐观锁页可以通过数时间戳来实现，同上。
    
4. 加锁方式：
   - 显示加锁：通过明确的sql来加锁
   - 隐式加锁：@TODO


### mysql的全局锁和死锁
- 全局锁 ：全部备份时候的
- 死锁：两个时候等待对方释放锁，但是都不释放自己的锁，![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241434258.png)
  - 当出现死锁以后，有 两种策略 :
    1. innodb_lock_wait_timeout 来设置等待时间；
    2. 另一种策略是，发起死锁检测，发现死锁后，主动回滚死锁链条中的某一个事务(将持有最少行级 排他锁的事务进行回滚)，让其他事务得以继续执行。将参数 innodb_deadlock_detect 设置为 on ，表示开启这个逻辑。
  - mysql中如何避免和解除死锁：
    - 如果你能确保这个业务一定不会出现死锁，可以临时把死锁检测关掉
    - 控制并发度

### 锁结构
结构如图：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241438343.png)
1. 锁监控：
```
# 查看分析系统上的行锁的争夺情况
mysql> show status like 'innodb_row_lock%';
+-------------------------------+--------+
| Variable_name                 | Value  |
+-------------------------------+--------+
| Innodb_row_lock_current_waits | 0      | # Innodb_row_lock_current_waits:当前正在等待锁定的数量;
| Innodb_row_lock_time          | 109549 | # Innodb_row_lock_time:从系统启动到现在锁定总时间长度;(等待总时长)
| Innodb_row_lock_time_avg      | 36516  | # 每次等待所花平均时间;(等待平均时长)
| Innodb_row_lock_time_max      | 50127  | # Innodb_row_lock_time_max:从系统启动到现在等待最常的一次所花的时间;
| Innodb_row_lock_waits         | 3      | # 系统启动后到现在总共等待的次数;(等待总次数)
+-------------------------------+--------+
5 rows in set (0.04 sec)
```
2. 与事务和锁相关的几个表：
事务表 锁表 等待表 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302241501976.png)
```
# 查看事务
select * From  information_schema.INNODB_TRX;
mysql> select * From  information_schema.INNODB_TRX\G;
*************************** 1. row ***************************
                    trx_id: 562947782847704
                 trx_state: RUNNING # 事务状态  LOCK WAIT；RUNNIG
               trx_started: 2023-02-24 07:04:22
     trx_requested_lock_id: NULL
          trx_wait_started: NULL
                trx_weight: 2
       trx_mysql_thread_id: 8
                 trx_query: trx_query: select * From t_emp where emp_id =1 for update #  实际WATI的 sql
       trx_operation_state: NULL
         trx_tables_in_use: 0
         trx_tables_locked: 1
          trx_lock_structs: 2
     trx_lock_memory_bytes: 1128
           trx_rows_locked: 6
         trx_rows_modified: 0
   trx_concurrency_tickets: 0
       trx_isolation_level: REPEATABLE READ # 事务隔离级别
         trx_unique_checks: 1
    trx_foreign_key_checks: 1
trx_last_foreign_key_error: NULL
 trx_adaptive_hash_latched: 0
 trx_adaptive_hash_timeout: 0
          trx_is_read_only: 0
trx_autocommit_non_locking: 0
       trx_schedule_weight: NULL
1 row in set (0.00 sec)

mysql 5.7：
# 查看事务的锁情况，只能看到阻塞的
information_schema.INNODB_LOCKS
# 等待锁的情况
information_schema.INNODB_LOCK_WAITS

mysql 8：
# 查看事务锁的情况，阻塞和非阻塞的都可以看到
performance_schema.data_locks 
mysql> SELECT * from performance_schema.data_locks\G;
*************************** 1. row ***************************
               ENGINE: INNODB
       ENGINE_LOCK_ID: 281472806138664:1075:281472695075616
ENGINE_TRANSACTION_ID: 3862
            THREAD_ID: 51
             EVENT_ID: 15
        OBJECT_SCHEMA: ssm
          OBJECT_NAME: t_emp
       PARTITION_NAME: NULL
    SUBPARTITION_NAME: NULL
           INDEX_NAME: NULL
OBJECT_INSTANCE_BEGIN: 281472695075616
            LOCK_TYPE: TABLE
            LOCK_MODE: IX
          LOCK_STATUS: GRANTED
            LOCK_DATA: NULL

# 等待锁的情况
performance_schema.data_lock_waits 
mysql> SELECT * FROM performance_schema.data_lock_waits\G;
*************************** 1. row ***************************
                          ENGINE: INNODB
       REQUESTING_ENGINE_LOCK_ID: 281472806138664:10:4:11:281472695072704
REQUESTING_ENGINE_TRANSACTION_ID: 3863 # 被阻塞的TRX_ID
            REQUESTING_THREAD_ID: 51
             REQUESTING_EVENT_ID: 19
REQUESTING_OBJECT_INSTANCE_BEGIN: 281472695072704
         BLOCKING_ENGINE_LOCK_ID: 281472806137048:10:4:11:281472695060480
  BLOCKING_ENGINE_TRANSACTION_ID: 562947782847704 # 正在执行的事务ID，也就是那个事务阻塞了当前的事务
              BLOCKING_THREAD_ID: 49
               BLOCKING_EVENT_ID: 38
  BLOCKING_OBJECT_INSTANCE_BEGIN: 281472695060480
1 row in set (0.01 sec)
```

### MVCC (Multi version Concurrency Control)
- 加锁来控制读-写安全性问题的话，因为都是排队，效率不是很高
- mvcc 可以更好的实现读写并发，效率更高，空间换时间，和加锁是两个方式
- 核心是 Undo Log 和 Read View
- 快照读和当前读，这两个概念和undo 日志 对应下
- mvcc主要是解决 使用 READ COMMITTED 和 REPEATABLE READ 隔离级别的事务，MVCC只能在这两个隔离级别下工作，下面是如何工作的
  - UNDO LOG 是记录了数据的变化的链，然后以此来支持快照读和当前读，事务会生成对应的REDA VIEW，然后按照是去快照读还是当前读
  - READ  VIEW 针对每个select生成
  - trx_ids 记录目前活跃的事务id，up_limit_id 为最大事务id，low_limit_id最小的事务id，creator_trx_id 为创建的事务id
  - 然后根据事务隔离级别，去读取undo log 链，选择指定的版本
  - READ COMMITTED 级别下：READ VIEW 只能读已经提交的数据才会生成
  - REPEATABLE READ：READ VIEW 的可重复读的隔离级别实现是只在首次读取才会生成，后续都是是有第一次的READ VIEW
- mvcc 如何解决幻读：
  - 也是根据当前事务id，和 活跃事务id比较
  - 不读取比当前事务id更大的事务id就可实现不幻读

### 通用查询日志(所有的查询日志)-文本的vi可看
1. 查看通用查询日志状态
``` 
mysql> SHOW VARIABLES LIKE '%general%';
+------------------+---------------------------------+
| Variable_name    | Value                           |
+------------------+---------------------------------+
| general_log      | OFF                             |
| general_log_file | /var/lib/mysql/2cf944a72e26.log |
+------------------+---------------------------------+
2 rows in set (0.02 sec)
```

2. 开启通用查询日志
``` 
# 在配置文件中永久开启
[mysqld]
general_log=ON
general_log_file=[path[filename]]

# 或者临时开启
SET GLOBAL general_log=on; # 开启通用查询日志 
SET GLOBAL general_log_file=’path/filename’; # 设置日志文件保存位置
SET GLOBAL general_log=off; # 关闭通用查询日志
SHOW VARIABLES LIKE 'general_log%';
```
3. 通用日志内容：
``` 
/usr/sbin/mysqld, Version: 8.0.30 (MySQL Community Server - GPL). started with:
Tcp port: 3306  Unix socket: /var/run/mysqld/mysqld.sock
Time                 Id Command    Argument
2023-02-25T15:20:25.116516Z	   11 Query	select * From t_emp where emp_id =1
2023-02-25T15:20:37.022286Z	   11 Query	select * From t_emp
```

### 错误日志
``` 
mysql> SHOW VARIABLES LIKE 'log_err%';
+----------------------------+----------------------------------------+
| Variable_name              | Value                                  |
+----------------------------+----------------------------------------+
| log_error                  |  /var/log/mysqld.log                   |
| log_error_services         | log_filter_internal; log_sink_internal |
| log_error_suppression_list |                                        |
| log_error_verbosity        | 2                                      |
+----------------------------+----------------------------------------+
4 rows in set (0.05 sec)
```

### 二进制文件
1. binlog包括的是DML和DDL，主要用于数据恢复和数据同步 
2. 二进制的，事件性质,binlog日志位置
``` 
show variables like '%log_bin%';
mysql> show variables like '%log_bin%';
+---------------------------------+-----------------------------+
| Variable_name                   | Value                       |
+---------------------------------+-----------------------------+
| log_bin                         | ON                          | # binlog的日志位置
| log_bin_basename                | /var/lib/mysql/binlog       |
| log_bin_index                   | /var/lib/mysql/binlog.index | # 也建立了index
| log_bin_trust_function_creators | OFF                         | # 不同步函数，因为比如说now()
| log_bin_use_v1_row_events       | OFF                         |
| sql_log_bin                     | ON                          |
+---------------------------------+-----------------------------+
6 rows in set (0.07 sec)
```
3. binlog的配置-数据日志和数据库文件最好不要放在一个路径上
```
############### 永久性的
[mysqld]
#启用二进制日志 
log-bin=mysql-bin  # 日志名称
binlog_expire_logs_seconds=600 # 存留时间单位s，默认2592000 30天；14400 4小时；86400 1天；259200 3天；
max_binlog_size=100M # 日志文件大小，单个日志文件的大小， 最大和默认值是1GB
# 需要重启mysql服务

################ session级别的
# global 级别
mysql> set global sql_log_bin=0;
ERROR 1228 (HY000): Variable 'sql_log_bin' is a SESSION variable and can`t be used with SET GLOBAL
# session级别
mysql> SET sql_log_bin=0;
Query OK, 0 rows affected (0.01 秒)
```
4. 查看binlog日志：
重启一次就会写一个新的日志文件；日志大小到了指定的也会产出一个新的日志文件
``` 
SHOW BINARY LOGS; 
mysql> SHOW BINARY LOGS;
+---------------+-----------+-----------+
| Log_name      | File_size | Encrypted |
+---------------+-----------+-----------+
| binlog.000003 |       580 | No        |
| binlog.000004 |     15980 | No        |
| binlog.000005 |     14955 | No        |
| binlog.000006 |       157 | No        |
+---------------+-----------+-----------+
4 rows in set (0.07 sec)

# 查看具体的日志情况
mysqlbinlog -v "binlog.000006"

# 从mysql交互端查看
mysql> show binlog events in "binlog.000006" from 4;
+---------------+-----+----------------+-----------+-------------+--------------------------------------+
| Log_name      | Pos | Event_type     | Server_id | End_log_pos | Info                                 |
+---------------+-----+----------------+-----------+-------------+--------------------------------------+
| binlog.000006 |   4 | Format_desc    |         1 |         126 | Server ver: 8.0.30, Binlog ver: 4    |
| binlog.000006 | 126 | Previous_gtids |         1 |         157 |                                      |
| binlog.000006 | 157 | Anonymous_Gtid |         1 |         236 | SET @@SESSION.GTID_NEXT= 'ANONYMOUS' |
| binlog.000006 | 236 | Query          |         1 |         319 | BEGIN                                |
| binlog.000006 | 319 | Table_map      |         1 |         381 | table_id: 93 (ssm.t_emp)             |
| binlog.000006 | 381 | Update_rows    |         1 |         459 | table_id: 93 flags: STMT_END_F       |
| binlog.000006 | 459 | Xid            |         1 |         490 | COMMIT /* xid=96 */                  |
+---------------+-----+----------------+-----------+-------------+--------------------------------------+

# binlog的格式
mysql> show variables like 'binlog_format';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| binlog_format | ROW   | # Statement-记录sql而非数据  Row-row level 的日志内容会非常清楚的记录下每一行数据修改的细节 Mixed-实际上就是Statement与Row的结合
+---------------+-------+
1 row in set (0.04 sec)

# 使用binlog实现数据恢复 @TODO
指定使用pos进行恢复
指定按照时间进行恢复

# 删除binlog日志
PURGE {MASTER | BINARY} LOGS TO ‘指定日志文件名’;  这个是按照名字删除
  mysql> show binary log;
  ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'log' at line 1
  mysql> show binary logs;
  +---------------+-----------+-----------+
  | Log_name      | File_size | Encrypted |
  +---------------+-----------+-----------+
  | binlog.000003 |       580 | No        |
  | binlog.000004 |     15980 | No        |
  | binlog.000005 |     14955 | No        |
  | binlog.000006 |       490 | No        |
  +---------------+-----------+-----------+
  4 rows in set (0.03 sec)
  
  mysql> PURGE master LOGS To 'binlog.000004';
  Query OK, 0 rows affected (0.07 sec)
  
  mysql> show binary logs;
  +---------------+-----------+-----------+
  | Log_name      | File_size | Encrypted |
  +---------------+-----------+-----------+
  | binlog.000004 |     15980 | No        |
  | binlog.000005 |     14955 | No        |
  | binlog.000006 |       490 | No        |
  +---------------+-----------+-----------+
  3 rows in set (0.03 sec)

PURGE {MASTER | BINARY} LOGS BEFORE ‘指定日期’ 按照时间来进行删除
```
5. binlog 和 redo 日志对比
- binlog用于恢复，主从
- redo log 用于崩溃恢复
- redo log 开始和commit中间加上了binlog的日志成功写入，这样保证了主从数据一致性
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302260007002.png)

### 中继日志
- 只在从服务器上
- 位置
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302260008049.png)

### 主从复制
- 用于提升数据库的并发能力
  - 实现读写分离：一台作为写，其他同步
   - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302260014054.png)
   - 主从同步中的三个线程:
     - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302260019581.png)
  - 实现数据热备
  - 实现HA
1. 主从复制的配置：
``` 
###### 主机配置
#主服务器唯一ID 
server-id=1
#启用二进制日志,指名路径。比如:自己本地的路径/log/mysqlbin 
log-bin=mysql-bin
#[可选] 0(默认)表示读写(主机)，1表示只读(从机) 
read-only=0
#设置日志文件保留的时长，单位是秒 
binlog_expire_logs_seconds=6000
#控制单个二进制日志大小。此参数的最大和默认值是1GB 
max_binlog_size=200M
#[可选]设置不要复制的数据库 
binlog-ignore-db=test
#[可选]设置需要复制的数据库,默认全部记录。比如:binlog-do-db=ssm 
binlog-do-db=需要复制的主数据库名字
#[可选]设置binlog格式 
binlog_format=STATEMENT

###### 从机配置
#[必须]从服务器唯一ID
server-id=2
#[可选]启用中继日志 
relay-log=mysql-relay

##### 主机:建立账户并授权
#5.5,5.7的话
GRANT REPLICATION SLAVE ON *.* TO 'slave1'@'从机器数据库IP' IDENTIFIED BY 'abc123'; 

#8.0的话
CREATE USER 'slave1'@'%' IDENTIFIED BY '123456';
GRANT REPLICATION SLAVE ON *.* TO 'slave1'@'%';
#此语句必须执行。否则见下面。
ALTER USER 'slave1'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
flush privileges;
# 查询master的状态 记录下File和Position的值。
show master status;

##### 从机
# 从机上复制主机的命令
mysql> CHANGE MASTER TO
MASTER_HOST='192.168.1.150',MASTER_USER='slave1',MASTER_PASSWORD='123456',MASTER_LOG_FILE='mysql-bin.000007',MASTER_LOG_POS=154;
# 启动slave同步 
START SLAVE;
# SHOW SLAVE STATUS\G;
Slave IO Running: Yes
Slave SQL Running: Yes
则为成功
```
2. binlog的format格式：
- Statement-记录sql而非数据  
- Row-row level 的日志内容会非常清楚的记录下每一行数据修改的细节 
- Mixed-实际上就是Statement与Row的结合，在Mixed模式下，一般的语句修改使用Statement格式保存binlog。如一些函数，statement无法完成主从复制的操作，则采用row格式保存binlog。
  - 会根据具体的sql选择使用Statement 还是 Row 选择一个

### 主从同步的数据一致性问题
- 异步复制
  - 主响应，从不管
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302260038658.png)
- 半同步复制
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302260038885.png) 
  - 指定一个从同步完成后返回
- 组复制

### mysqldump 的使用


### 分库分表


### 读写分离



