### 教程资料

- https://www.bilibili.com/video/BV1Kw411Z7dF?spm_id_from=333.788.videopod.episodes&vd_source=d63883aafa94bea28d7963f231cca55b&p=2
- https://www.bilibili.com/video/BV1ar4y1x727/?spm_id_from=333.337.search-card.all.click&vd_source=d63883aafa94bea28d7963f231cca55b

### 教程和线程的基本概念

- 进程：运行的程序，是资源最小的
- 线程：程序执行的最小单元

### juc 简介

- java.util.concurrent 的简称 #2
- java底层对应的native方法都是调用的C #3
    - 线程的状态：Thread.State
        - NEW,(新建)
        - RUNNABLE,(准备就绪)
        - BLOCKED,(阻塞)
        - WAITING,(不见不散)
        - TIMED_WAITING,(过时不候)
        - TERMINATED;(终结)

### wait/sleep 方法的区别

- wait是object的方法，sleep是thread中的静态方法
- sleep拿着锁睡觉，wait是释放锁睡觉
- 都会被interept

### 共享

- 独占式 一个时间段内只能一个进程访问
- 共享式 一个时间内多个进程访问

### 并行和并发

- 并发
- 并行

### 管程

- 就是monitor
- 编程语言提供的一种同步锁机制，用来保证同一个时刻 只有一个线 程访问
- java中每个对象实例都有一个Monitor对象，是C来实现的
- 更常见的说法直接把这个叫做锁
- 进入Monitor对象和退出Monitor来实现同步锁的，这是java的锁实现的原理

### 用户线程和守护线程的的特点

- 用户自定义线程：时间上完成可以超过主线程
- 守护线程 通过setDomain来设置线程是不是守护线程，然后用户线程结束则守护线程结束，周期同jvm一起结束
- 守护线程服务于用户线程
    - 运行在后台的，比如垃圾回收线程
    - 如果jvm中只有守护线程的话，这个jvm就结束了
- 判断线程是不是守护线程呢？
- [Thread01.java](main%2Fjava%2Forg%2Fgetyou123%2FThread01.java)

### LOCK接口

* Synchronized关键字
    * 可以用来修饰代码块，修改类 ，修饰方法，这个是一个同步锁，上锁和释放锁都是自动的

* LOCK关键字
    * 比Synchronized关键字功能更加强大，LOCK是一种接口，lock.lock 和 lock.unlock
    * LOCK实现卖票[Thread02Lock.java](main%2Fjava%2Forg%2Fgetyou123%2FThread02Lock.java)
    * 这是个接口，有各种的实现类：比如是可重入锁


* lock和Synchronized关键字对比：
    * 一个自动的上锁自动解锁，一个手动
    * Synchronized异常自动释放锁，但是lock不会，可能死锁，所以要写在finally比如下面的
    * ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202309172217301.png)
    * 可重入锁：上锁持有对象，解锁释放对象，同一个线程可以在持有锁之后可以再次持有锁
    * Lock的性能比Synchronized好，尤其是在大量的竞争的线程
    * Lock可以让等待锁的线程响应中断，而Synchronized却不行
    * Synchronized只支持公平锁，Lock可以配置公平锁

### Lock可以让等待锁的线程响应中断，而Synchronized却不行

- 这种是因为底层的实现不同，可以通过一个案例来了解这个：
- 两个人买奶茶：
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503221852793.png)
- 这里的就说明了不同，使用Synchronized那么就只能一直在锁的位置等下去

### 线程间的通信

* 即使是你在代码中先后启动start线程，他们也不一定都是按照这个顺序启动，因为是交给了os 的native方法
* 线程之间的通信主要是实现线程之间的同步和互斥
* 这里说明下多线程编程的主要模式
    * 定义资源类
    * 定义资源类中的方法： 判断 干活 通知
    * new 多个线程进行操作资源类
* 示例如下：
    * 要求： 两个线程，分别 +1 -1 然后共享值在 0 1 之间固定跳转
    * 通过Synchronized+wait来实现[Thread04.java](main%2Fjava%2Forg%2Fgetyou123%2FThread04.java)
    * 通过Lock+Condition来实现[Thread5.java](main%2Fjava%2Forg%2Fgetyou123%2FThread5.java)

### while不包含wait的时候的虚假唤醒

- 线程start的顺序不等同于他们的执行顺序，wait在哪里睡，就在哪里醒
- 虚假唤醒的情况：目的就是让+1 -1
  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503222056270.png)

### 如何实现定制流程呢/同步？

- 让线程A执行完之后->B->C
- 思路上：单标志，然后按照顺序唤醒
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503231114548.png)
- [Thread6.java](main%2Fjava%2Forg%2Fgetyou123%2FThread6.java)

### 集合的线程安全问题（Arraylist）

- 比如list的并发写和访问异常

```
Exception in thread "10" Exception in thread "36" Exception in thread "40" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:911)
	at java.util.ArrayList$Itr.next(ArrayList.java:861)
	at java.util.AbstractCollection.toString(AbstractCollection.java:461)
	at java.lang.String.valueOf(String.java:2994)
	at java.io.PrintStream.println(PrintStream.java:821)
	at org.getyou123.Thread07.lambda$unSafeAdd$0(Thread07.java:11)
	at java.lang.Thread.run(Thread.java:750)
java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:911)
```

- [Thread07.java](main%2Fjava%2Forg%2Fgetyou123%2FThread07.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503231131257.png)
- 可以使用线程安全的版本的Vector（但是效率比较低，不建议使用）
- 在这里有展示[Thread07.java](main%2Fjava%2Forg%2Fgetyou123%2FThread07.java)
- 使用CopyOnWrite机制
- 在这里有展示![CopyOnWrite](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503231550734.png)
- 可以明显的看到代码里的是先复制然后在改回去

### HashMap、hashSet的线程安全解决方式：

- hashSet也是线程不安全的
- 可以使用CopyOnWriteArraySet来替换
- [Thread08.java](main%2Fjava%2Forg%2Fgetyou123%2FThread08.java)
- 也看下底层的核心实现：
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503231603033.png)
- hashMap也是不安全的使用ConcurrentHashMap

### 八种锁：

- https://www.bilibili.com/video/BV1R5NreGEsk/?spm_id_from=333.1391.0.0&vd_source=d63883aafa94bea28d7963f231cca55b
- 乐观锁、悲观锁:每次使用都加锁
- 公平锁、非公平锁：竞争调度时候是否公平
- 共享锁、排它锁：读锁还是写锁
- sychronized锁升级：无锁->

### 公平锁和非公平锁

- 非公平锁（线程饿死、效率高） ReentrantLook默认就是非公平锁=false构造参数（首次获取之后一直是这个线程占用）
- 公平锁（公平竞争，效率低） ReentrantLook中true构造参数（首次获取之后再重新公平竞争）
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503251500391.png)
- [Thread11.java](main%2Fjava%2Forg%2Fgetyou123%2FThread11.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503251503693.png)

### 可重入锁

- Synchronized和Lock都是可重入锁，获取到了锁之后还可以获取到这个锁，这就是可重入锁

### 死锁

- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503231635170.png)
- 四个条件：
- 判定死锁：
- 死锁示例：[Thread09.java](main%2Fjava%2Forg%2Fgetyou123%2FThread09.java)
- 查看是否有死锁：
    - jstack <进程号>
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503231645547.png)

### callable接口

- 可以抛出异常，可以获取到线程的执行结果
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503251515946.png)

### 三个常用的辅助类

- countDownLatch 主要用来主从结构中，master得知所有的worker完成了任务
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503251420528.png)
- [Thread10.java](main%2Fjava%2Forg%2Fgetyou123%2FThread10.java)

- cyclicBarrier主要用在同executors中，用来完成一个阶段的执行之后同样的都进入到下一个阶段
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503251430228.png)
- [Thread10.java](main%2Fjava%2Forg%2Fgetyou123%2FThread10.java)

- semaphore 主要用在多个线程竞争有限的资源的
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503251433061.png)
- [Thread10.java](main%2Fjava%2Forg%2Fgetyou123%2FThread10.java)

### volatile 关键字

- 主要用于多个线程中间的保证数据的可见性
- 禁止指令重排
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503251625366.png)
- [Thread12.java](main%2Fjava%2Forg%2Fgetyou123%2FThread12.java)

### 可重入读写锁

- 主要用在对于数据读多写少的情况下的提升效率
- [Thread13.java](main%2Fjava%2Forg%2Fgetyou123%2FThread13.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503260831864.png)

### 阻塞队列
- BlockingQueue 用于线程之间有依赖的数据交互，常见于生产者和消费者
- 多个线程共享一个队列，如果没有队列中没有数据的话就消费者阻塞，如果队列满了的话就把生产者阻塞
- [Thread14.java](main%2Fjava%2Forg%2Fgetyou123%2FThread14.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503280829773.png)

### 线程池
- 一池多个线程，一池一个线程
- 使用最多的是自定义线程池
- [Thread15.java](main%2Fjava%2Forg%2Fgetyou123%2FThread15.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202503280842454.png)

### 多线程和多线程同步
- https://tech.meituan.com/2024/07/19/multi-threading-and-multi-thread-synchronization.html
- 线程是一个执行的上下文，包含诸多的状态数据，每个线程有自己的执行流、调用站、错误码、信号掩码、私有数据。

- 执行流：

### 总结下FutureTask的缺点：
- get是同步阻塞的，轮询的话费CPU（isDone）
- 在这里有体现[Thread16.java](main%2Fjava%2Forg%2Fgetyou123%2FThread16.java)
- 后面jDk改造这部分内容，提供了CompletableFuture接口
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504041746291.png)

### CompletableFuture接口基础用法
- 同时处理多个耗时任务，为了不阻塞线程等待结果
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504041942209.png)
- [Thread17.java](main%2Fjava%2Forg%2Fgetyou123%2FThread17.java)
- （有返回值、无返回值）*（自定义线程池、默认的线程池）共计四种静态方法
- [Thread17.java](main%2Fjava%2Forg%2Fgetyou123%2FThread17.java)

### join和get的区别：
- join在编译时候不会报错，不需要处理异常，而是在执行时候才会产出异常
- get需要在编译时候处理异常

### CompletableFuture接口非阻塞处理任务结果和异常
- thenApply 和 thenAccept的区别：链式的处理返回值，无返回值
- exceptionally 捕获异常
- 任务编排：allOf anyOf
- [Thread20.java](main%2Fjava%2Forg%2Fgetyou123%2FThread20.java)

### 使用CompletableFuture加速任务的执行
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504092116000.png)
- [Thread19.java](main%2Fjava%2Forg%2Fgetyou123%2FThread19.java)

### 多个任务执行选取最新执行完成的
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504092146301.png)
- [Thread21.java](main%2Fjava%2Forg%2Fgetyou123%2FThread21.java)

### combine合并两个阶段的结果
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504092156302.png)
- [Thread22.java](main%2Fjava%2Forg%2Fgetyou123%2FThread22.java)
- 可以多个组合的，这里只是展示了两个的

### 乐观锁和悲观锁
- 悲观锁在多写操作的情况下：lock和Synchronized
- 乐观锁不加锁：判断是否被更新过，通过版本号和CAS算法（适用于读多的场景）

### 类锁和对象锁
Synchronized 中对象锁：
``` 
class phone{
public Synchronized void run()
}
```
Synchronized 中类锁：
``` 
class phone{
public static Synchronized void run()
}
```

Synchronized 中对象锁,写明了是对象锁：
``` 
Synchronized(obj) {
String yui = "Stri";
}
```

那么，两个线程争抢对象锁的时候，争抢类锁的时候。

### Synchronized 对应的JVM底层命令：
- 其实是两个jvm指令
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504102156463.png)
