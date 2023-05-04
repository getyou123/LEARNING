https://github.com/flink-china/flink-training-course/tree/master

----

## talk1 https://files.alicdn.com/tpsservice/53de65050b468fc6d338fbaff799828a.pdf

### flink 简介

- 处理有界无界的数据
- 数据处理框架，我们只需要专注自己的业务逻辑
- 分布式
- 有状态
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304241015356.png)
- flink 是事件驱动型的，区别于spark streaming的微批次
    - ![spark的微批次](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304241016731.png)
    - ![flink的事件驱动](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304241017206.png)

### 如何理解流和批呢

- 一切都是流
- 离线数据就是有界的流
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304241021687.png)

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
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304241022424.png)
    - 最底层的 有状态的流 + process function
    - data stream api 这层是核心，常见的算子在这
    - table api ： 声明式 + select / join 等，代码量显著减低
    - flink sql ： 几乎标准的sql语句

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

### flink的运行架构

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304241556466.png)
- 主要角色说明：
    - 客户端：实现用于准备和发送dataflow到JobManager
    - JobManager:

``` 
控制一个应用程序执行的主进程，也就是说，每个应用程序都会被一个的JobManager所控制执行。

JobManager会先接收到要执行的应用程序，这个应用程序会包括：作业图（JobGraph）、逻辑数据流图（logical dataflow graph）和打包了所有的类、库和其它资源的JAR包。

JobManager会把JobGraph转换成一个物理层面的数据流图，这个图被叫做“执行图”（ExecutionGraph），包含了所有可以并发执行的任务。JobManager会向资源管理器（ResourceManager）请求执行任务必要的资源，也就是任务管理器（TaskManager）上的插槽（slot）。一旦它获取到了足够的资源，就会将执行图分发到真正运行它们的TaskManager上。

而在运行过程中，JobManager会负责所有需要中央协调的操作，比如说检查点（checkpoints）的协调。

```

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

-

传统批处理方法是持续收取数据，以时间作为划分多个批次的依据，再周期性地执行批次运算。 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171708562.png)

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
    -
  分布式计算系统下的状态恢复 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171729369.png)
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
- jvm：用 Java object read / writes
  进行读或写，不会产生较大代价，只适合很小的状态 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304171742322.png)
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

- 可以在 flink-dist/target/目录中看到产出的文件

### flink 程序提交到yarn，单个job的模式 @TODO

- HA的保证
    - yarn-site.xml配置 `yarn.resourcemanager.am.max-attempts`
    - flink-conf.yaml
      ` yarn.application-attempts: 3
      high-availability: zookeeper
      high-availability.storageDir: hdfs://xx:8020/flink/yarn/ha
      high-availability.zookeeper.quorum: xx:2181,xx:2181,xx:2181
      high-availability.zookeeper.path.root: /flink-yarn
      `
- flink yarn 的两种模式
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304181728564.png)
    - 推荐使用第二种 `bin/flink run -m yarn-cluster -yn 2 -yjm 1024 -ytm 1024 ./examples/batch/WordCount.jar`
    - 注意：Client端(提交Flink任务的机器)
      必须要设置YARN_CONF_DIR、HADOOP_CONF_DIR或者HADOOP_HOME环境变量，Flink会通过这个环境变量来读取YARN和HDFS的配置信息，否则启动会失败。

### flink几个层次的图

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304181731459.png)

--- 

## talk4  https://files.alicdn.com/tpsservice/38bf5c75c7491323b4b99101a2fab65c.pdf

### 分布式流处理的基本模型

- 逻辑模型：DAG 点（每一个点就代表一个基本的逻辑单元，也就是前面说的算子） 有向边 的含义
- 物理模型：从DAG点搞出不同的实例，然后这个点实例放到不同的SLOT上去跑
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304181718461.png)
    - 如图 ： 作为 Source 的 A 算子有两个实例，中间算子 C 也有两个实例。在逻辑模型中，A 和 B 是 C 的上游节点，而在对应的物理逻辑中，C
      的所有实例和 A、B
      的所有实例之间可能都存在数据交换。在物理模型中，我们会根据计算逻辑，采用系统自动优化或人为指定的方式将计算工作分布到不同的实例中。只有当算子实例分布到不同进程上时，才需要通过网络进行数据传输，而同一进程中的多个实例之间的数据传输通常是不需要通过网络的。

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
    - 其实就是分区,主要是需要 key的数据远大于分区数，底层是一个KeySelector

``` 
KeyedStream<TaxiRide, Long> keyedRides = rides
        .keyBy(new KeySelector<TaxiRide, Long>() {
            @Override
            public Long getKey(TaxiRide ride) throws Exception {
                return ride.getRideId();
            }
        });
```

### 关于物理分组

- 就是按照DAG上的节点对应的实例是多个的，如何定义实例发数据到下游的哪些个实例呢？
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304182014485.png)

### 类型系统

---

## talk5 https://files.alicdn.com/tpsservice/a8d224d6a3b8b82d03aa84e370c008cc.pdf

### 客户端的操作用于提交任务

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304271820867.png)

### flink命令行

``` 
bin/flink run -d examples/streaming/TopSpeedWindowing.jar
bin/flink list -m 127.0.0.1:8081
bin/flink stop -m 127.0.0.1:8081 d67420e52bd051fae2fddbaa79e046bb # 一个 job 能够被 stop 要求所有的 source 都是可以 stoppable 的，即实现了 StoppableFunction 接口。
bin/flink cancel -m 127.0.0.1:8081 5e20cb6b0f357591171dfcca2eea09de # 取消任务。如果在 conf/flink-conf.yaml 里面配置了 state.savepoints.dir ，会保存savepoint，否则不会保存 savepoint。
bin/flink cancel -m 127.0.0.1:8081 -s /tmp/savepoint 29da945b99dea6547c3fbafd57ed8759 # 指定savepoint
bin/flink run -d -s /tmp/savepoint/savepoint-f049ff-24ec0d3e0dc7 ./examples/streaming/TopSpeedWindowing.jar # 从savePoint开始消费
bin/flink modify -p 4 790d7b98db6f6af55d04aec1d77385 # 修改任务的并行度
bin/flink run -m yarn-cluster ./examples/streaming/TopSpeedWindowing.jar # 提交而且不退出client
bin/flink run -yd -m yarn-cluster ./examples/streaming/TopSpeedWindowing.jar # 提交到yarn 且提交完成就退出
```

### flink中的取消和停止（流作业）的区别如下：

- cancel() 调用，立即调用作业算子的 cancel() 方法，以尽快取消它们。如果算子在接 到 cancel() 调用后没有停止，Flink
  将开始定期中断算子线程的执行，直到所有算子停 止为止。
- stop() 调用，是更优雅的停止正在运行流作业的方式。stop() 仅适用于 source 实现了 StoppableFunction 接口的作业。当用户请求停止作业时，作业的所有
  source 都将接 收 stop() 方法调用。直到所有 source 正常关闭时，作业才会正常结束。这种方式，使作业正常处理完所有作业。

### savepoint 和 checkpoint 的区别

- checkpoint 是增量做的，每次的时间较短，数据量较小，只要在程序里面启用后会自动触发，用户无须感知；savepoint
  是全量做的，每次的时间较长，数据量较大，需要用户主动去触发。
- checkpoint 是作业 failover 的时候自动使用，不需要用户指定。savepoint 一般用于程序的版本更新，bug修复，A/B Test等场景，需要用户指定。

---
## talk6 https://files.alicdn.com/tpsservice/a8d224d6a3b8b82d03aa84e370c008cc.pdf
主要包括：主要包括为什么要有 Window；
Window 中的三个核心组件：WindowAssigner、Trigger 和 Evictor；
Window 中怎么处理乱序数据，乱序数据是否允许延迟，以及怎么处理迟到的数据；
最后我们梳理整个 Window 的数据流程，以及 Window 中怎么保证 Exactly Once 语义。
### window概念

- 无限流中的使用的，收集一些数据放在某个bucket中，然后按照一定的条件，定时or定量触发 对于bucket进行计算
- 根据上游的数据是不是keyed stream，窗口划分为Keyed Window和Non-Keyed Windows
- 两者的区别在于KeyStream调用相应的window()方法来指定window类型，数据会根据Key在不同的Task中并行计算，而Non-Keyed
  Stream需要调用WindowsAll()方法来指定window类型，所有的数据都会在一个Task进行计算，相当于没有并行

``` 
-- keyedStream
stream
       .keyBy(...)               <-  keyed versus non-keyed windows
       .window(...)              <-  required: "assigner"
      [.trigger(...)]            <-  optional: "trigger" (else default trigger)
      [.evictor(...)]            <-  optional: "evictor" (else no evictor)
      [.allowedLateness(...)]    <-  optional: "lateness" (else zero)
      [.sideOutputLateData(...)] <-  optional: "output tag" (else no side output for late data)
       .reduce/aggregate/fold/apply()      <-  required: "function"
      [.getSideOutput(...)]      <-  optional: "output tag"
   
-- Non-Keyed Windows 这里可以理解keyBy了个固定值，所有的数据都是放在一个task中了
stream
       .windowAll(...)           <-  required: "assigner"
      [.trigger(...)]            <-  optional: "trigger" (else default trigger)
      [.evictor(...)]            <-  optional: "evictor" (else no evictor)
      [.allowedLateness(...)]    <-  optional: "lateness" (else zero)
      [.sideOutputLateData(...)] <-  optional: "output tag" (else no side output for late data)
       .reduce/aggregate/fold/apply()      <-  required: "function"
      [.getSideOutput(...)]      <-  optional: "output tag"
```

- WindowAssigner 这个方法被window接收，负责把数据发送到正确的窗口(bucket)，一条数据可以被发送到多个bucket中
- Flink 提供了几种通用的 WindowAssigner：
    - tumbling window(
      窗口间的元素无重复)， ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281022596.png)
    - sliding window(
      窗口间的元素可能重复)，![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281022223.png)
    - session window
      基于时间的 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281022603.png)
    - global window
      某个key下的所有的数据在一个bucket中，需要搭配自己的触发器，这个和keyed之后直接计算的触发时间时机是不同的 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281023009.png)
    - 如果需要自己定制数据分发策略，则可以实现一个 class，继承自 WindowAssigner。

- trigger 如何理解：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281028876.png)
- evictor 如何理解：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281027976.png)

### flink中的window的生命周期
- 每条数据过来之后，会由 WindowAssigner 分配到对应的 Window，
- 当 Window 被触发之后，如何控制触发是 trigger
- 会交给 Evictor(如果没有设置 Evictor 则跳过)，
- 然后处理 UserFunction：用户自己的处理逻辑


### flink中的时间：
需要注意的是，WatermarkGenerator 只适用于 EventTime 时间特征。在使用 ProcessingTime 或者 IngestionTime 时，不需要产生 Watermark。

- Event-Time，Processing-Time 以及 Ingestion-Time 这三种时间
    - Event-Time：表示事件发生的时间
    - Processing-Time：表示处理消息的时间(墙上时间)
    - Ingestion-Time：表示进入到系统的时间。
- 三种时间的区别 ： ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281029764.png)
- 在1.12之前默认的时间语义是处理时间, 从1.12开始, Flink内部已经把默认的语义改成了事件时间

### flink中的watermark？

- 支持event time的流式处理框架需要一种能够测量event time 进度的方式；
- Flink中去测量事件时间的进度的机制就是 watermark(水印). watermark作为数据流的一部分在流动, 并且携带一个时间戳t.
- 一个Watermark(t)表示在这个流里面事件时间已经到了时间t, 意味着此时, 流中不应该存在这样的数据: 他的时间戳t2<=t (
  时间比较旧或者等于时间戳)
- window 处理数据时候需要来关窗口，只能拿到当前的数据，怎么保证是现在可以关窗口了？ 其实就是如何感知时间的进度呢，尤其是乱序的时间呢
- 有序流中的水印（数据到达时候自身带的时间戳转为事件时间就是有标志的，且是递增的有序的）
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281040560.png)
    - 这种有序的其实就是一个周期的时间点
- 乱序流中的水印（数据到达时候自己带着时间戳，但是是乱序的）
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202304281042951.png)
    - wm的传播，会更新算子subtask内的时钟：水印是一种标记, 是流中的一个点, 所有在这个时间戳(水印中的时间戳)前的数据应该已经全部到达。
      一旦水印到达了算子, 则这个算子会提高他内部的时钟的值为这个水印的值。
- 处理时间没有使用水印的必要，水印只是针对事件时间来说的一种计量机制
- 需要注意的是，WatermarkGenerator 只适用于 EventTime 时间特征。在使用 ProcessingTime 或者 IngestionTime 时，不需要产生 Watermark。

1. flink中的watermark的生成和time的提取，一般来说是一起生成的
- 最最核心是 WatermarkGenerator + TimestampAssigner ，flink期望提供得到 一个 WatermarkStrategy
- 实际就是如何往数据流中插入wm，可以定时周期来生成，可以按照数据特定类型来生成
- [WaterMarkLearnTest.java](src%2Ftest%2Fjava%2Forg%2Fexample%2FWaterMarkLearnTest.java)
``` 
    public interface WatermarkStrategy<T> 
        extends TimestampAssignerSupplier<T>,
                WatermarkGeneratorSupplier<T>{
    
        /**
         * Instantiates a {@link TimestampAssigner} for assigning timestamps according to this
         * strategy.
         */
        @Override
        TimestampAssigner<T> createTimestampAssigner(TimestampAssignerSupplier.Context context);
    
        /**
         * Instantiates a WatermarkGenerator that generates watermarks according to this strategy.
         */
        @Override
        WatermarkGenerator<T> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context);
    }
```
2. 如何处理迟到数据呢？
- 即使定义了watermark但是可能还是存在比最大乱序时间慢的事件到来，flink允许时间更晚的到来，window暂时不关闭
- window的关闭时间是 watermark  = 窗口关闭时间 + 超过时间 ，这个关闭时间 
- 迟到的数据也可以可以触发计算的
``` 
.window(TumblingEventTimeWindows.of(Time.seconds(5)))
.allowedLateness(Time.seconds(3)) // 这里设置关闭时间
```

---
## talk7 https://files.alicdn.com/tpsservice/1b9f5f0bda10883dce78496e6a5d648a.pdf
### 关于flink中的状态？
- 什么是状态？-- 计算过程中的使用到的，需要保证其正确性和可恢复性的一些变量，区分于计算中的局部变量
- 如果计算中无需记录中间的需要容错的变量的话就是无状态的计算
- 有状态的场景：
  - 去重
  - 窗口
  - 访问历史数据
  - 机器学习的参数

#### 状态的分类？
- flink管理否？ managed-state 和 Raw-state（实际存的是字节数组）
- 作用在keyed stream上，每个key存一个状态？ Keyed State 
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202305021952117.png)
- 一个operator 实例存储 一个的状态变量 Operator State
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202305021952724.png)
- Keyed State 使用示例 
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202305021952633.png)
  - 区分下 AggregatingState 和 ListState ： 比如ListState窗口计算中是全都暂存，AggregatingState 可以按照reduce那样定义然后还支持输入数据和输出的数据的格式不一致
  - 区分下 AggregatingState 和 ReducingState ： AggregatingState 不要求输入和输出一致，ReducingState 要求必须一致