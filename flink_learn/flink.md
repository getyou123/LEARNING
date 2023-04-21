
https://github.com/flink-china/flink-training-course/tree/master

----
## talk1 https://files.alicdn.com/tpsservice/53de65050b468fc6d338fbaff799828a.pdf

### flink 简介

- 处理有界无界的数据
- 数据处理框架，我们只需要专注自己的业务逻辑
- 分布式
- 有状态

### 如何理解数据流Stream

- 不断地产出
- 动态的
- 分为有界的和无界的，是不是有始有终的来区分是不是有界的，一般来说Stream unbound是有始无终的

### 如何理解有状态的计算和无状态的计算

- 如果数据来了直接map转换，然后流转到下游，这种就是无状态的
- 数据有状态是指：比如计算一小时的pv，count等等，包括 Exactly-once Semantics 精确一次语义
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171639249.png)

### flink中的时间的概念

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171640327.png)
- 事件时间
- 处理时间
- 进入时间

### flink的api的层级

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171642615.png)
- 越往上抽象的更高
- 越往下可控的性更好，表达能力更强

### flink状态的原理

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171645422.png)
- 每个task把计算的状态存在自己的本地，这样是保证了处理速度快
- 定期的存到远程，来应对failover

### flink的应用场景，三大场景

- Data Pipeline
  类似数据的搬运和复杂的数据ETL（比如构建实时的索引，构建实时数仓） ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171649804.png)
- Data Analytics
  数据分析，离线的or实时的 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171650528.png)
- Data Driven 数据驱动型的，例如风控等场景

--- 

## talk2 https://files.alicdn.com/tpsservice/b55f732fbc32522ca5394544f3834530.pdf

### Flink Application 的相关概念

- Streams 分为有限数据流与无限数据流
- State 状态是计算过程中的数据信息，在容错恢复和 Checkpoint 中有重要的作用，流计算在本质上是 Incremental
  Processing，因此需要不断查询保持状态；另外，为了确保 Exactly- once 语义，需要数据能够写入到状态中；而持久化存储，能够保证在整个分布式系统运行失败或者挂掉的情况下做到
  Exactly- once，这是状态的另外一个价值。
- Time
- API

### 有状态的流式处理中的概念

- 传统批处理方法是持续收取数据，以时间作为划分多个批次的依据，再周期性地执行批次运算。 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171708562.png)
    - 缺点： 事件跨越划分点；事件顺序颠倒情况都会放到了下一个运算周期
- 理想状态：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171711533.png)
    - 就是流的转态流过算子
- 分布式流式处理->有状态分布式流式处理
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171715053.png) 分布式流式处理
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171716884.png) 有状态分布式流式处理

### 有状态的流式处理中的挑战

- 如何保证精确一次性的处理，状态容错机制
    - 简单场景下的状态容错 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171727865.png)
      核心就是记录位置，多次的记录位置，出错了之后进行回滚
    - 分布式计算下的状态容错 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171729587.png)
    - 分布式计算系统下的状态恢复 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171729369.png)
    - flink中的checkpoint机制 ：
        - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171730285.png)
        - jb按照需求插入 checkpoint barrier，所有算子遇到这个barrier之后，把自己的状态存到远端
        - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171730285.png)
        - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171732012.png)
        - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171733176.png)
        - 直至完成 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171733269.png)

### 状态维护和使用

- 本地的状态 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171735308.png)
- 要么存在JVM中，要么存储量大的话存储在DFS中（Distributed Snapshots）
- jvm：用 Java object read / writes 进行读或写，不会产生较大代价，只适合很小的状态 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171742322.png)
- RocksDB：在 Runtime
  的本地状态后端让使用者去读取状态的时候会经过磁盘，相当于将状态维护在磁盘里，与之对应的代价可能就是每次读取状态时，都需要经过序列化和反序列化的过程。当需要进行快照时只将应用序列化即可，序列化后的数据直接传输到中央的共享
  DFS 中。![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171742362.png)

### 计算引擎如何判断这个 bucket可以出发计算了呢
- watermark的是一种特殊事件
- 常见的场景是：知道一个数据流中的最大的乱序程度，然后按照source中的当前的已经收到的最大的时间戳+最大的delay时间作为watermark，插入到数据流中
- 可以周期性的生成，也可以基于指定的事件来生成
  - [WaterMarkLearn01.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Fhelloworld%2FWaterMarkLearn%2FWaterMarkLearn01.java)
  - [WaterMarkLearn02.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Fhelloworld%2FWaterMarkLearn%2FWaterMarkLearn02.java)
  - [WaterMarkLearn03.java](src%2Fmain%2Fjava%2Forg%2Fexample%2Fhelloworld%2FWaterMarkLearn%2FWaterMarkLearn03.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171825207.png)
- 上面图演示了无乱序的行为和乱序行为下的watermark生成（周期性的插入目前最大的时间戳+delay时间长度 作为 watermark）
- 下游算子接收到这个watermark之后就认定bucket可以触发计算了
- 最好来使用 WatermarkStrategy 
``` 
KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
    .setBootstrapServers(brokers)
    .setTopics("my-topic")
    .setGroupId("my-group")
    .setStartingOffsets(OffsetsInitializer.earliest())
    .setValueOnlyDeserializer(new SimpleStringSchema())
    .build();

DataStream<String> stream = env.fromSource(
    kafkaSource, WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(20)), "mySource");
```


### flink中的savepoint
- 可以想象手动生成的一个checkpoint
- 也是记录状态然后用于修bug，进行重新消费等
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171829609.png)

---

## talk3 https://files.alicdn.com/tpsservice/4824447b829149c86bedd19424d05915.pdf
### flink开发环境和部署
- 单机
- standalone
- yarn


### 开发环境搭建

```
# 删除已有的 build，编译 flink binary
mvn clean package -DskipTests
# 另一种编译命令，相对于上面这个命令，主要的确保是：
# 不编译 tests、QA plugins 和 JavaDocs，因此编译要更快一些
mvn clean package -DskipTests -Dfast
# 如果需要指定hadoop的版本
mvn clean install -DskipTests -Dhadoop.version=2.6.1
# 或者
mvn clean package -DskipTests -Dhadoop.version=2.6.1
# 最好使用自带的mvn编译
./mvnw clean package -DskipTests 
```
-  可以在 flink-dist/target/目录中看到产出的文件


### flink 程序提交到yarn，单个job的模式 @TODO
- HA的保证
  - yarn-site.xml配置 `yarn.resourcemanager.am.max-attempts`
- flink yarn 的两种模式
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304181728564.png)
  - 推荐使用第二种 `bin/flink run -m yarn-cluster -yn 2 -yjm 1024 -ytm 1024 ./examples/batch/WordCount.jar`
  -  注意：Client端(提交Flink任务的机器)必须要设置YARN_CONF_DIR、HADOOP_CONF_DIR或者HADOOP_HOME环境变量，Flink会通过这个环境变量来读取YARN和HDFS的配置信息，否则启动会失败。

### flink几个层次的图
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304181731459.png)

## talk4  https://files.alicdn.com/tpsservice/38bf5c75c7491323b4b99101a2fab65c.pdf
### 分布式流处理的基本模型
- 逻辑模型：DAG 点（每一个点就代表一个基本的逻辑单元，也就是前面说的算子） 有向边 的含义
- 物理模型：从DAG点搞出不同的实例，然后这个点实例放到不同的SLOT上去跑 
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304181718461.png)
  - 如图 ： 作为 Source 的 A 算子有两个实例，中间算子 C 也有两个实例。在逻辑模型中，A 和 B 是 C 的上游节点，而在对应的物理逻辑中，C 的所有实例和 A、B 的所有实例之间可能都存在数据交换。在物理模型中，我们会根据计算逻辑，采用系统自动优化或人为指定的方式将计算工作分布到不同的实例中。只有当算子实例分布到不同进程上时，才需要通过网络进行数据传输，而同一进程中的多个实例之间的数据传输通常是不需要通过网络的。

### FLINK程序的结构
- 固定的创建环境env
- source
- transform
- sink
- 执行

---
### DataStream 的转化
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304181734452.png)
- 如何理解 KeyedStream : ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304182004619.png)
  - 其实就是分区,主要是需要 key的数据远大于分区数

### 关于物理分组
- 就是按照DAG上的节点对应的实例是多个的，如何定义实例发数据到下游的哪些个实例呢？
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304182014485.png)

### 类型系统
