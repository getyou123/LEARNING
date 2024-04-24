### hive是什么
hive是一个数仓工具。
Hive可以帮助开发人员来做完成这些苦活（将SQL语句转化为MapReduce在yarn上跑），如此开发人员就可以更加专注于业务需求了。

### hive的优点和缺点：
- 优点：处理大量数据，SQL表达
- 缺点：时延较大，SQL的迭代不好表达
 
### hive的本质
- 转化sql为具体的spark或者mr任务，提交到集群上执行
- 前提是文件是结构化的，通过元数据进行映射为一张表

### hive的基本的数据类型
- INT 等
- STRUCT MAP ARRAY
- DECIMAL(10,2)表示存储总共10位数字，其中2位是小数部分。如果小数点前面的数据超过了8为的话，会变为null
- 大于bigint的数字需要使用DECIMAL(38,0)来表示

### hive的架构
- @TODO

### hive和普通的数据库的区别
- @TODO

### hive的两种访问模式
- 使用命令行模式
- 使用hiveserver2，客户端使用jdbc的方式使用hive


### hive的内部表和外部表
- 内部表完全是hive来管理的，会移动到具体的数据仓库地址位置，数据随着表一起删除
- 外部表可以在任何位置，建表时候只是记录了地址，删除的时候也是不删除的，只删除了元数据

### hive的分区和分桶
- hive 分区底层是分文件夹
- hive 分桶底层是文件夹内的文件进一步的分片

1.引言：
  分区提供一个隔离数据和优化查询的便利方式。
  不过，并非所有的数据集都可形成合理的分区。  
2.分桶表：
  对于一张表或者分区，Hive 可以进一步组织成桶，也就是更为细粒度的数据范围划分。
  分桶是将数据集分解成更容易管理的若干部分的另一个技术。
  分区针对的是数据的存储路径（细分文件夹）；分桶针对的是数据文件（按规则多文件放一起）。
- @TODO

### hive参数的配置方式：
- 配置文件 xml文件
- 命令行参数 启动时候指定 --hiveconf  只对本次启动有效
- 启动seesion之后手动的set
- 参数的优先级别 顺序
- @TODO

### hive中的动态分区
- 动态分区就是说可以在指定分区的时候，按照结果中的分区字段的值动态的更新对应的分区
```
（1）开启动态分区功能（默认true，开启）
hive (default)> set hive.exec.dynamic.partition=true;
（2）设置为非严格模式（动态分区的模式，默认strict，表示必须指定至少一个分区为静态分区，nonstrict模式表示允许所有的分区字段都可以使用动态分区。）
hive (default)> set hive.exec.dynamic.partition.mode=nonstrict
（3）在所有执行MR的节点上，最大一共可以创建多少个动态分区。默认1000
hive (default)> set hive.exec.max.dynamic.partitions=1000;
（4）在每个执行MR的节点上，最大可以创建多少个动态分区。
该参数需要根据实际的数据来设定。比如：源数据中包含了一年的数据，即day字段有365个值，那么该参数就需要设置成大于365，如果使用默认值100，则会报错。
hive (default)> set hive.exec.max.dynamic.partitions.pernode=100;
（5）整个MR Job中，最大可以创建多少个HDFS文件。默认100000
hive (default)> set hive.exec.max.created.files=100000;
（6）当有空分区生成时，是否抛出异常。一般不需要设置。默认false
hive (default)> set hive.error.on.empty.partition=false;
```

## hive常见的函数

### 聚合函数
count(),max(),min(),sum(),avg()

### 关系运算
支持：等值(=)、不等值(!= 或 <>)、小于(<)、小于等于(<=)、大于(>)、大于等于(>=)

空值判断(is null)、非空判断(is not null)

LIKE 比较: LIKE

JAVA 的 LIKE 操作: RLIKE

REGEXP 操作: REGEXP

### 数学运算
支持所有数值类型：加(+)、减(-)、乘(*)、除(/)、取余(%)、位与(&)、位或(|)、位异或(^)、位取反(~)

### 逻辑运算
逻辑与(and)、逻辑或(or)、逻辑非(not)

### 数值运算
#### 取整函数: round
```
语法: round(double a)
返回值: BIGINT
说明: 返回 double 类型的整数值部分 （遵循四舍五入）
示例：```select round(3.1415926) from tableName;```
结果：3
```

```
语法: round(double a, int d)
返回值: DOUBLE
说明: 返回指定精度 d 的 double 类型
hive> select round(3.1415926,4) from tableName;
3.1416
```


```
向下取整函数: floor
语法: floor(double a)
返回值: BIGINT
说明: 返回等于或者小于该 double 变量的最大的整数
hive> select floor(3.641) from tableName;
3
```

```
向上取整函数: ceil
语法: ceil(double a)
返回值: BIGINT
说明: 返回等于或者大于该 double 变量的最小的整数
hive> select ceil(3.1415926) from tableName;
4
```


```
取随机数函数: rand
语法: rand(),rand(int seed)
返回值: double
说明: 返回一个 0 到 1 范围内的随机数。如果指定种子 seed，则会等到一个稳定的随机数序列
hive> select rand() from tableName; -- 每次执行此语句得到的结果都不同
0.5577432776034763
hive> select rand(100) ; -- 只要指定种子，每次执行此语句得到的结果一样的
0.7220096548596434
```

```
自然指数函数: exp
语法: exp(double a)
返回值: double
说明: 返回自然对数 e 的 a 次方
hive> select exp(2) ;
7.38905609893065
以 10 为底对数函数: log10,以 2 为底对数函数: log2()、对数函数: log()
语法: log10(double a)
返回值: double
说明: 返回以 10 为底的 a 的对数
hive> select log10(100) ;
2.0
```

```
幂运算函数: pow
语法: pow(double a, double p)
返回值: double
说明: 返回 a 的 p 次幂
hive> select pow(2,4) ;
16.0
```

```
开平方函数: sqrt
语法: sqrt(double a)
返回值: double
说明: 返回 a 的平方根
hive> select sqrt(16) ;
4.0
```

```
二进制函数: bin,十六进制函数: hex()、将十六进制转化为字符串函数: unhex()
语法: bin(BIGINT a)
返回值: string
说明: 返回 a 的二进制代码表示
hive> select bin(7) ;
111
```

```
进制转换函数: conv(bigint num, int from_base, int to_base)
说明: 将数值 num 从 from base 进制转化到 tobase 进制
select conv(8,10,2);
```

```
绝对值函数: abs()
正取余函数: pmod()
正弦函数:sin()
反正弦函数: asin()
余弦函数: cos()
反余弦函数: acos()
positive函数: positive()
negative 函数: negative()
```

```
条件函数
If 函数: if
语法: if(boolean testCondition, T valueTrue, T valueFalseOrNull)
返回值: T
说明: 当条件 testCondition 为 TRUE 时，返回 valueTrue；否则返回 valueFalseOrNull
hive> select if(1=2,100,200) ;
200
hive> select if(1=1,100,200) ;
100
```

```
非空查找函数: coalesce
语法: coalesce(T v1, T v2, …)
返回值: T
说明: 返回参数中的第一个非空值；如果所有值都为 NULL，那么返回 NULL
hive> select coalesce(null,'100','50') ;
100
```

```
条件判断函数：case when (两种写法，其一)
语法: case when a then b [when c then d]* [else e] end
返回值: T
说明：如果 a 为 TRUE,则返回 b；如果 c 为 TRUE，则返回 d；否则返回 e
hive> select case when 1=2 then 'tom' when 2=2 then 'mary' else 'tim' end from tableName;


条件判断函数：case when (两种写法，其二)
语法: case a when b then c [when d then e]* [else f] end
返回值: T
说明：如果 a 等于 b，那么返回 c；如果 a 等于 d，那么返回 e；否则返回 f
hive> Select case 100 when 50 then 'tom' when 100 then 'mary' else 'tim' end from tableName;

hive> select 	case t_company_index.company_type
	when '1' then '1'
	when '2' then '3'
	when '3' then '3'
	end as company_type
from tableName;
```

### 日期函数
```
获取当前 UNIX 时间戳函数: unix_timestamp
语法: unix_timestamp()
返回值: bigint
说明: 获得当前时区的 UNIX 时间戳
hive> select unix_timestamp() from tableName;
1616906976
```

```
UNIX 时间戳转日期函数: from_unixtime
语法: from_unixtime(bigint unixtime[, string format])
返回值: string
说明: 转化 UNIX 时间戳（从 1970-01-01 00:00:00 UTC 到指定时间的秒数）到当前时区的时间格式
hive> select from_unixtime(1616906976,'yyyyMMdd') from tableName;
20210328
```

```
日期转 UNIX 时间戳函数: unix_timestamp
语法: unix_timestamp(string date)
返回值: bigint
说明: 转换格式为"yyyy-MM-dd HH:mm:ss"的日期到 UNIX 时间戳。如果转化失败，则返回 0。
hive> select unix_timestamp('2021-03-08 14:21:15') from tableName;
1615184475
```

```
指定格式日期转 UNIX 时间戳函数: unix_timestamp
语法: unix_timestamp(string date, string pattern)
返回值: bigint
说明: 转换 pattern 格式的日期到 UNIX 时间戳。如果转化失败，则返回 0。
hive> select unix_timestamp('2021-03-08 14:21:15','yyyyMMdd HH:mm:ss') from tableN
ame;
1615184475
```

```
日期时间转日期函数: to_date
语法: to_date(string timestamp)
返回值: string
说明: 返回日期时间字段中的日期部分。
hive> select to_date('2021-03-28 14:03:01') from tableName;
2021-03-28
```

```
日期转年函数: year
语法: year(string date)
返回值: int
说明: 返回日期中的年。
hive> select year('2021-03-28 10:03:01') from tableName;
2021
hive> select year('2021-03-28') from tableName;
2021
```

```
日期转月函数: month
语法: month (string date)
返回值: int
说明: 返回日期中的月份。
hive> select month('2020-12-28 12:03:01') from tableName;
12
hive> select month('2021-03-08') from tableName;
8
```

```
日期转天函数: day
语法: day (string date)
返回值: int
说明: 返回日期中的天。
hive> select day('2020-12-08 10:03:01') from tableName;
8
hive> select day('2020-12-24') from tableName;
24
```

```
日期转小时函数: hour
语法: hour (string date)
返回值: int
说明: 返回日期中的小时。
hive> select hour('2020-12-08 10:03:01') from tableName;
10
```

```
日期转分钟函数: minute
语法: second (string date)
返回值: int
说明: 返回日期中的秒。
hive> select second('2020-12-08 10:03:01') from tableName;
1
```

```
日期转秒函数: second
语法: second (string date)
返回值: int
说明: 返回日期中的秒。
hive> select second('2020-12-08 10:03:01') from tableName;
1
```

```
日期转周函数: weekofyear
语法: weekofyear (string date)
返回值: int
说明: 返回日期在当前的周数。
hive> select weekofyear('2020-12-08 10:03:01') from tableName;
49
```

```
日期比较函数: datediff
语法: datediff(string enddate, string startdate)
返回值: int
说明: 返回结束日期减去开始日期的天数。
hive> select datediff('2020-12-08','2020-05-09') from tableName;
213
```

```
日期增加函数: date_add
语法: date_add(string startdate, int days)
返回值: string
说明: 返回开始日期 startdate 增加 days 天后的日期。
hive> select date_add('2020-12-08',10) from tableName;
2020-12-18
```

```
日期减少函数: date_sub
语法: date_sub (string startdate, int days)
返回值: string
说明: 返回开始日期 startdate 减少 days 天后的日期。
hive> select date_sub('2020-12-08',10) from tableName;
2020-11-28
```

### 字符串函数
```
字符串长度函数：length
语法: length(string A)
返回值: int
说明：返回字符串 A 的长度
hive> select length('abcedfg') from tableName;
7
```

```
字符串反转函数：reverse
语法: reverse(string A)
返回值: string
说明：返回字符串 A 的反转结果
hive> select reverse('abcedfg') from tableName;
gfdecba
```

```
字符串连接函数：concat
语法: concat(string A, string B…)
返回值: string
说明：返回输入字符串连接后的结果，支持任意个输入字符串
hive> select concat('abc','def’,'gh')from tableName;
abcdefgh
```

```
带分隔符字符串连接函数：concat_ws
语法: concat_ws(string SEP, string A, string B…)
返回值: string
说明：返回输入字符串连接后的结果，SEP 表示各个字符串间的分隔符
hive> select concat_ws(',','abc','def','gh')from tableName;
abc,def,gh
```

```
字符串截取函数：substr,substring
语法: substr(string A, int start),substring(string A, int start)
返回值: string
说明：返回字符串 A 从 start 位置到结尾的字符串
hive> select substr('abcde',3) from tableName;
cde
hive> select substring('abcde',3) from tableName;
cde
hive> select substr('abcde',-1) from tableName; （和 ORACLE 相同）
e

语法: substr(string A, int start, int len),substring(string A, int start, int len)
返回值: string
说明：返回字符串 A 从 start 位置开始，长度为 len 的字符串
hive> select substr('abcde',3,2) from tableName;
cd
hive> select substring('abcde',3,2) from tableName;
cd
hive>select substring('abcde',-2,2) from tableName;
de
```

```
字符串转大写函数：upper,ucase
语法: upper(string A) ucase(string A)
返回值: string
说明：返回字符串 A 的大写格式
hive> select upper('abSEd') from tableName;
ABSED
hive> select ucase('abSEd') from tableName;
ABSED
```

```
字符串转小写函数：lower,lcase
语法: lower(string A) lcase(string A)
返回值: string
说明：返回字符串 A 的小写格式
hive> select lower('abSEd') from tableName;
absed
hive> select lcase('abSEd') from tableName;
absed
```

```
去空格函数：trim,ltirm,rtrim
语法: trim(string A)
返回值: string
说明：去除字符串两边的空格
hive> select trim(' abc ') from tableName;
abc
```

```
正则表达式替换函数：regexp_replace
语法: regexp_replace(string A, string B, string C)
返回值: string
说明：将字符串 A 中的符合 java 正则表达式 B 的部分替换为 C。注意，在有些情况下要使用转义字符,类
似 oracle 中的 regexp_replace 函数。
hive> select regexp_replace('foobar', 'oo|ar', '') from tableName;
fb
```

```
正则表达式解析函数：regexp_extract
语法: regexp_extract(string subject, string pattern, int index)
返回值: string
说明：将字符串 subject 按照 pattern 正则表达式的规则拆分，返回 index 指定的字符。
hive> select regexp_extract('foothebar', 'foo(.?)(bar)', 1) from tableName;
the
hive> select regexp_extract('foothebar', 'foo(.?)(bar)', 2) from tableName;
bar
hive> select regexp_extract('foothebar', 'foo(.?)(bar)', 0) from tableName;
foothebar
```

```
URL 解析函数：parse_url
语法: parse_url(string urlString, string partToExtract [, string keyToExtract])
返回值: string
说明：返回 URL 中指定的部分。partToExtract 的有效值为：
HOST, PATH, QUERY, REF, PROTOCOL, AUTHORITY, FILE, and USERINFO.
hive> select parse_url
('https://www.tableName.com/path1/p.php?k1=v1&k2=v2#Ref1', 'HOST')
from tableName;
www.tableName.com
hive> select parse_url
('https://www.tableName.com/path1/p.php?k1=v1&k2=v2#Ref1', 'QUERY', 'k1')
from tableName;
v1
```

```
json 解析函数：get_json_object
语法: get_json_object(string json_string, string path)
返回值: string
说明：解析 json 的字符串 json_string,返回 path 指定的内容。如果输入的 json 字符串无效，那么返
回 NULL。
hive> select get_json_object('{"store":{"fruit":\[{"weight":8,"type":"apple"},{"we
ight":9,"type":"pear"}], "bicycle":{"price":19.95,"color":"red"} },"email":"amy@onl
y_for_json_udf_test.net","owner":"amy"}','$.owner') from tableName;
```

```
空格字符串函数：space
语法: space(int n)
返回值: string
说明：返回长度为 n 的字符串
hive> select space(10) from tableName;
hive> select length(space(10)) from tableName;
10
```

```
重复字符串函数：repeat
语法: repeat(string str, int n)
返回值: string
说明：返回重复 n 次后的 str 字符串
hive> select repeat('abc',5) from tableName;
abcabcabcabcabc
```

```
首字符 ascii 函数：ascii
语法: ascii(string str)
返回值: int
说明：返回字符串 str 第一个字符的 ascii 码
hive> select ascii('abcde') from tableName;
97
```

```
补足函数：lpad,rpad
语法: lpad(string str, int len, string pad)
返回值: string
说明：将 str 进行用 pad 进行左补足到 len 位
hive> select lpad('abc',10,'td') from tableName;
tdtdtdtabc
注意：与 GP，ORACLE 不同，pad 不能默认

语法: rpad(string str, int len, string pad)
返回值: string
说明：将 str 进行用 pad 进行右补足到 len 位
hive> select rpad('abc',10,'td') from tableName;
abctdtdtdt
```

```
分割字符串函数: split
语法: split(string str, string pat)
返回值: array
说明: 按照 pat 字符串分割 str，会返回分割后的字符串数组
hive> select split('abtcdtef','t') from tableName;
["ab","cd","ef"]
```

```
集合查找函数: find_in_set
语法: find_in_set(string str, string strList)
返回值: int  
说明: 返回 str 在 strlist 第一次出现的位置，strlist 是用逗号分割的字符串。如果没有找该 str 字
符，则返回 0
```



### hive sql总结案例总结使用
```
建表
create table XXX(

)
partitoined by (pt string)
stored as orc
```

```
hive表删除分区
alter table XXX drop if exists partition(pt='${pt}')
```

```
hive表增加字段
alter table dwd_offline.XXX add columns(device_uuid string COMMENT 'device_uuid')
增加字段默认是在表的最后的，然后如果更新完sql的话还是最好的先把分区drop掉，然后再insert
```

```
hive表更新列
alter table XXX change age age int comment 'XXX'
alter table XXX change age age int comment 'XXX' after name_col
```


```
hive修改表名字
alter table table_name rename to table_name2;
```

```
hive表限制不可被drop
ALTER TABLE table_name PARTITION(dt='2020-01-01') ENABLE NO_DROP;
```

```
hive表限制不可被查询
ALTER TABLE table_name PARTITION(dt='2020-01-01') ENABLE OFFLINE;
```



```
hive 设置队列
set mapreduce.job.queuename=offline;
```


```
hive 设置executor
set spark.executor.instances=100;
set spark.executor.memory=5G;
set spark.executor.cores=2;
```


```
hive 设置diver
set spark.driver.cores=4;
set spark.driver.memory=8G;
```

```shell
hive使用skewjoin

```


``` 
hive实现解析json数组并进行行转列

SELECT explode(split(regexp_replace(regexp_replace('[{"website":"baidu.com","name":"百度"},{"website":"google.com","name":"谷歌"}]', '\\[|\\]',''),'\\}\\,\\{','\\}\\;\\{'),'\\;'));
{"website":"baidu.com","name":"百度"}
{"website":"google.com","name":"谷歌"}
```

``` 
hive 重新计算某个分区信息
analyze table ods.XXXX partition(pt='${pt}') compute statistics;
```


```
hive 设置任务名称
set mapred.job.name = my_job_name
```

``` 
hive 创建数据库
create database test
```

```
hive切换数据库 删除数据库
use database;
drop database hive;
 
```


``` 
hive 内部表外部表和转换
内部表也加管理表：HIVE同时管理元数据和实际数据的表，删除之后数据也被删
外部表：HIVE只是管理元数据，删除之后只是删除了元数据，实际数据还在那里
EXTERNAL 外部表 实际数据不用移动到指定的路径，删除时候不会涉及到数据删除，只是简单的删除了元数据

desc formatted emppart
alter table emppart tblproperties('EXTERNAL'='TRUE');注意是大写，否则不生效
```

``` 
hive加载数据到表中
load数据时候需要加上load data local inpath '/data/test.txt' into table emppart partition(date="20200504")
注意csv文件导入hdfs并映射为为hive表的ddl写法
row format delimited fields terminated by ','
```

``` 
hive 查看分区的情况和总数：
show partitioins emppart;

hive 删除分区
alter table emppart drop partition(data="20200521");

hive 增加分区
alter table emppart add partition(date="20200521")
```

``` 
hive修改表名
alter table emmpart rename to emmpart1;
```

``` 
hive select 排除某个字段
set hive.support.quoted.identifiers=none
SELECT `(pt|user_name)?+.+` FROM <table>;
```


```
hive 四个by的区别
order by 全局一个reducer，数据量大有风险，生产环境不用这个
sort by 每个reducer或者说是分区内有序，依赖map.reduce.tasks 一般来说和distrbute by一起使用
distrbute by 指定分区的key，需要结合，DISTRIBUTE BY 语句要写在 SORT BY 语句之前
cluster by = distribute by +sort by，只能是升序排列。
```


``` 
hive 实现全局有序
1. 如果是使用order by 的话全局数据过多的时候会存在执行慢，甚至失败的情况，因为只有一个reducer
2. 如果是需要全局数据有序的话，可以使用udf进行分区，按照排序字段的key的范围进行分区，也就是cluster by，但是要自己写分区的类
```

``` 
hive求top N的优化
1. 可以使用子查询进行优化：
--从表中获取name长度为TOP10的数据
select a.id,a.name from 
(
 select id,name  from <table_name>  
 distribute by length(name)  sort by length(name) desc limit 10
 ) a 
 order by length(a.user_name) desc limit 10;
```

### hive udf的分类


### beeline执行sql
beeline -u "jdbc:hive2://XXX3:2181,10.XXX.XXX.86:2181/ods;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2" -n "username" -e "";
beeline -u "jdbc:hive2://1XXX3:2181,XXX:2181/ods;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2" -n "username" -f dwd_XXX_di_tmp.sql
beeline -u jdbc:hive2://<hive-server-hostname>:<port>/<database> -n <username> -p <password> -e "";


### hive udf注册
```
ADD JAR hdfs:///user/XXX/jars/StaffPositionClean.jar;
DROP FUNCTION if  exists default.StaffPositionClean;
CREATE FUNCTION default.StaffPositionClean AS 'com.bigdata.udf.StaffPositionClean' USING JAR 'hdfs:///user/xxx/jars/StaffPositionClean.jar';
```
note ： 更新jar逻辑之后需要关闭cli，然后重新drop和注册


### hive行转列

```
user_id  browsing_history
-------  --------------------------
1        ["www.google.com", "www.yahoo.com"]
2        ["www.facebook.com", "www.amazon.com", "www.netflix.com"]
    
SELECT user_id, website
FROM user_activity 
LATERAL VIEW EXPLODE(browsing_history) browsing AS website;

user_id website
------- ----------------
1       www.google.com
1       www.yahoo.com
2       www.facebook.com
2       www.amazon.com
2       www.netflix.com

```


### 通过mysql的表结构产出hive表ddl
- [CreateHiveTableByMysql.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2FCreateHiveTableByMysql.java)
- 这里可能需要修改下具体的mysql类型和hive字段类型的映射

### hive窗口函数
一个窗口函数的三个组成部分， function + over + window expression（window子句）
- @TODO