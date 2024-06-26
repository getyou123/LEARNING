
## help的命令：
```shell
# hdfs dfs -help  ：
* hdfs dfs -help mv
  -mv <src> ... <dst> :
  Move files that match the specified file pattern <src> to a destination <dst>.
  When moving multiple files, the destination must be a directory.

* hdfs dfs -ls

* hdfs dfs -mkdir

* hdfs dfs -moveFromLocal ./local.txt /user/remote/test/

* hdfs dfs -appendToFile ./local.txt /user/remote/test/1.txt

* hdfs dfs -cat /user/remote/test/1.txt

* hdfs dfs -copyFromLocal ./local.txt /user/remote/test/

* hdfs dfs -coptToLocal /user/remote/test/1.txt ./

* hdfs dfs -cp /user/dir1/test/1.txt /user/dir2/test/

* hdfs dfs -get 同copyToLocal

* hdfs dfs -getmerge /user/test/*.txt ./jiu.txt 合并之后下载

* hdfs dfs -put 同copyFromLocal

* hdfs dfs -tail

* hdfs dfs -rm

* hdfs dfs -rmdir

* hdfs dfs -du

* hdfs dfs -setrep 10 /user/text.txt 设置副本数
```


* 小文件归档：
  （2）归档文件
  把/user/XX/input目录里面的所有文件归档成一个叫input.har的归档文件，并把归档后文件存储到/user/XX/output路径下。

[XXX@hadoop01 hadoop-2.7.7]$ bin/hadoop archive -archiveName input.har –p /user/XXX/input /user/XXX/output
（3）查看归档

[XXX@hadoop01 hadoop-2.7.7]$ hadoop fs -lsr /user/XX/output/input.har
[XXX@hadoop01 hadoop-2.7.7]$ hadoop fs -lsr har:///user/XX/output/input.har
（4）解归档文件

[XXX@hadoop01 hadoop-2.7.7]$ hadoop fs -cp har:///user/XXutput/input.har/* /user/X

## hadoop的三大发行版本：
- apache hadoop
- cdh
- hdp
- 合一的cdp 收费比较贵

## hadoop 从 1.x 到 2.x 到 3.x的变化：
- 1->2 增加了yarn
- 2->3 NN不止一个

##  hdfs的架构：
- NN（active standby）
- DN
- QJM集群
- zkfc
- client

## hdfs的HA是如何实现的：
- zkfc用作选举 监控 故障转移
- 元数据的共享 QJM集群 JNS

## hdfs的读写流程：
- 读流程
- 写流程
