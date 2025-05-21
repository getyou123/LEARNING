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


### 可重入锁案例
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504110841128.png)
- 这里获取的是同一个对象的锁
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504112224116.png)

### 锁案例总结
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504120907670.png)

### 什么是线程中断机制？
- 首先线程不能被其他的线程中断，应该是其自己来中断或者停止
- 线程状态都有哪些？
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504121732075.png)
  - NEW
  - RUNNABLE
  - BLOCKED
  - WAITING
  - TIMED_WAITING
  - TERMINATED
- 不能强制中断，只能进行协商中断，中断线程只能手动调用interrupt，把线程中断设置为中断。
- 自己线程如何感知别的线程让自己中断呢？ 自己线程手动的检测标志。
- 这种别人设置，自己检测，然后才能主动中断的机制就叫做协商中断机制。

### 中断的三个重要的API：
- interrupt：请求线程中断，仅仅只是将线程的中断标志设置为true，发起一个协商但并不是立即停止线程。
- interrupted态方法，检查当前线程的中断状态，并清除中断标志。第二次调用该方法会返回 false，因为中断标志已经被清除。
- isInterrupted ：检查当前线程是否被中断。返回 true 表示线程已经被请求中断，但不会清除中断状态。
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504121743652.png)

### 线程中断示例：
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504121757590.png)
- [Thread23.java](main%2Fjava%2Forg%2Fgetyou123%2FThread23.java)
- 通过volatile实现
- 通过autoBoolean实现
- 这两种都是不建议使用的，因为都是线程之外的数据，有更好的
- [Thread24.java] (main %2Fjava%2Forg%2Fgetyou123%2FThread24.java)

### java中interrupt方法的特殊说明：
- 如果线程是正常活动状态，将该线程的中断标志设置为true，仅此而已，线程响应不响应中断自己说了算
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504122049612.png)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504122054864.png)
- 如果线程是被阻塞的状态，别的线程调用当前线程对象的interrupt方法，那么线程立即退出被阻塞状态，并抛出异常InterruptException
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504122039086.png)
- 不活跃线程去调用他自己的interrupt的话啥影响没有，比如线程已经销毁之后就没有意义
- 都在 [Thread25.java](main%2Fjava%2Forg%2Fgetyou123%2FThread25.java) 有演示

### java中如何实现线程阻塞？
- 两种存在缺点的方式：
  - object中的wait和notify
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504150730008.png)
  - Lock中的await和signal方式
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504150755564.png)
- 这两种方式都必须要满足先获取，之后还需要保证顺序才能唤醒，否则就一直AA处于阻塞状态
- 通过LockSupport方式可以避免以上两种的缺点：（类似ETC直接通过 ）
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504150804344.png)
- [Thread26.java](main%2Fjava%2Forg%2Fgetyou123%2FThread26.java)
- LockSupport只能对于一个进程发一次，unpark多次也是只发一个证 

### JMM定义和作用：
- JMM是一种抽象的概念，他只是对于硬件平台和操作系统的内存访问差异，他只是一组规定或规范，通过这种规范
定义了程序中各个变量的读写方式，并决定一个线程对于共享变量的写入何时以及如何变成另外一个线程可见，
关键技术是围绕多线程的原子性、可见性和有序性。
- JMM的三大特性：
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504170832975.png)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504170838530.png)
  - 可见性：内存条中的共享数据，线程私有空间操作（数据是复制到这里的），其他线程要从主内存得知更新并更新自己的内存空间=>可见
  - 原子性：并发情况下，协作时候，比如两个线程对共享变量各自+1，最终得到2，所以(读取到本地内存+操作+回写主内存+通知其他线程主内存变更)是原子操作
  - 有序性：最终结果不变情况下，编译器、指令并行优化、内存系统重排 ，会对指令进行重排，以优化执行速度，但是需要保证数据之间的依赖关系没变

### JMM中线程对于变量的读写过程

### JMM happends-before原则
- A happends-before B，意味着A发生的事情对于B来说是可见的，无论两者是不是发生在一个线程中
- 这个原则使得我们很多时候没有必要对好多变量加volatile关键字

### 什么是指令重排？
- 编译器和处理器为了高效执行，对指令序列进行重排
- 只能对不存在数据依赖的语句进行重排，有数据依赖的不能重排

### volatile关键字的两大特性（可见性，禁重排序）
- 可见性：线程的操作会立即刷新到主内存，使其他线程可见；其他线程的失效会立马去重新读取，但是已经发生了计算不会重来，这是不满足原子性的
- 有序性：
- 无原子性

### 内存屏障是volatile关键字的两大特性的基础
- 本质是JVM的指令，编译器生成JVM指令时候插入的内存屏障
- 内存屏障之前的所有写都要写回到主存
- 内存屏障之后的所有读操作都能获取屏障之前的写操作的结果
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504242201657.png)
- 四种屏障的含义：
  - LoadLoad
  - LoadStore
  - StoreStore
  - StoreLoad
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504242210768.png)

### volatile关键字的可见性案例：
- 可见性保证‌
  - 普通变量‌：  
    每个线程有自己的工作内存（缓存），对变量的修改可能仅停留在工作内存中，其他线程无法立即看到修改后的值，导致‌可见性问题‌。
  - volatile变量‌：   
    当线程写入volatile变量时，修改会‌立即刷新到主内存‌；当读取时，会‌直接从主内存获取最新值‌，而非工作内存中的副本。这确保了多线程间的可见性。
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504250835880.png)
- [Thread27.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread27.java)

### volatile关键字的无原子性的案例：
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504250902178.png)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504250905991.png)
- 没有原子性发生了（+1和写回）不是原子的，所以即使发生了主内存变更，但是没有重算，而且还有覆盖写
- [Thread28.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread28.java)

### volatile的日常使用场景：
- 最好用在两个线程协作的时候，感知flag的变更
- 一个一直在检测，另外的一个置位

### cas原理：（compare and swap/set）
- 如果我要实现i++，作为一个原子操作的话，之前只能加锁，这个锁比较重
- 使用原子类的话，就可以替换这个加锁操作，直接调用相关的原子类
- 这种思想类似乐观锁：cas底层操作，如果内存位置和预期值相同的话，设置为更新值，不一样的话不做任何操作
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504262052947.png)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504262058707.png)
- 这里引出自旋的概念：线程更新不了内存的值，然后一直在这里循环，一直无法达成更新的最终行为，这种浪费CPU的行为就是自旋。自旋锁 

### cas使用案例：
- 正常的比较&set
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504262107147.png)
- 底层使用的操作系统的指令这种是底层是对总线加锁，这是CPU底层原语实现的，硬件级别的
- 这种比synchronized要性能更好
- unsafe类是CAS的思想实现，但是不要轻易使用unsafe类
- [Thread29.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread29.java)

### atomicReference
- juc中自带了很多的基础类型的实现好的atomic类，如何自己实现一个atomicStudent呢
- [Thread30.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread30.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504290835160.png)

### atomic手写自旋锁
- 就是cas的过程中，有比较和设置，这里通过原子引用类来实现
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504292206372.png)
- 思路上就是AA和BB获取之后把自己放在原子引用类中，虽然这个案例是AA先的，但是不耽误演示BB处于自旋状态
- [Thread31.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread31.java)

### cas的缺点：
- 避开重量加锁，实现比较轻的加锁效果，因为这是os层面的支持
- 自旋浪费CPU空转
- ABA问题：本质是：变量的值从 A 改为 B，又改回 A，但 CAS 无法感知中间的变化。
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504292222629.png)
- 解决方式是：AtomicStampReference就是给最开始的A加上版本号，那么后续的A的版本号就不一致了
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202504300840595.png)
- 代码在这里 [Thread32.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread32.java)

### 原子类的基础操作
- [Thread33.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread33.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505021610343.png)
- countDownLatch使用来保证所有的线程都结束之后才执行main线程

### 数组类型原子类
- 常见的不同的线程操作不同的索引下标
- [Thread34.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread34.java) 这里使用join

### 引用类型原子类
- 基础的实现自旋锁
- 带版本号原子类-使用时间戳解决ABA问题，多次修改是按照时间戳来表示
- AtomicMarkableReference：不同于ABA，这是标记是不是一次性的被修改过(true false两种值)；[Thread35.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread35.java)

### 对象属性的修改原子类（FieldUpdater）
- 本质就是原子操作更新：类对象的某个属性字段
- 一种线程安全的方式操作非线程安全的对象中的某个字段
- 基于反射的，对类的指定的volatile 字段进行原子更新 （必选满足 public volatile） 
- [Thread36.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread36.java)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505050918183.png)
- 如果字段是其他的class对象的话，比如在多线程环境下抢夺执行资源的初始化

### 原子类型增强类
- 以下四个是从1.8开始的，优化了cas的乐观锁的重试次数，一般用于AtomicXX
  - DoubleAccumulator
  - DoubleAdder
  - LongAccumulator
  - LongAdder

### 原子类实现点赞累加
- [Thread38.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread38.java) 演示基础操作
- [Thread39.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread39.java) 演示点赞累加的操作，longAdder 加速效果明显

### ThreadLocal简介
- 就是让每个线程持有自己的本地变量，因为每个线程持有自己的副本所以不存在线程安全问题
- 类比就是：公共电话亭和私人手机
- [Thread40.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread40.java)
- 自定义的比如要回收，避免造成内存泄漏和程序错误，尤其是在线程池的过程中，没做remove的话会有线程的复用
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505180834119.png)
- ![](https:// raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505180844347.png)
- ThreadLocal 最好是static的这样的话不会随着对象多次初始化，他是属于线程的而非业务对象的 
- 必须强制remove 

### ThreadLocal代码源码
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505180855917.png)
- Thread中持有ThreadLocal类对象，然后每个ThreadLocalMap中存着对get 和set的对象
- Thread、ThreadLocal和ThreadLocalMap的关系：
- ThreadLocal的实现依赖于Thread和ThreadLocalMap 
  - 每个Thread对象持有一个ThreadLocalMap 
  - ThreadLocalMap中存储的是以ThreadLocal为key的键值对 
  - 这种设计实现了线程隔离的数据存储
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505190744727.png)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505190746162.png)

### ThreadLocal为啥是弱引用呢？
- Object的四个引用层级：强软弱虚
- Object的finalize方法，根可达
  - 强引用：即使OOM也不回收
  - 软引用：内存不够就回收
  - 弱引用：GC就回收
  - 虚引用：任何时候
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505190805988.png)
- 为啥用弱引用呢？
  - 因为业务代码把ThreadLocal设置为null，不在使用的时候，防止ThreadLocalMap一直引用导致无法回收内存泄漏
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505200759887.png)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505200810120.png)

### ThreadLocal清理脏entry
- get set remove 都会清理map中key 为null entry对应的value 直接置为null

### 一个对象的空间分布
- 整体来看三部分：对象头、实例数据、对齐填充
- 对象头：Mark Word；类元信息；
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505202156197.png)
- MarkWord（8个字节）：哈希码、GC标记、GC次数、锁标记
- 类元信息（8个字节）：表明是哪个类的实例
- JOL看类对象的内存分布：
  - 加入GAV
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505210749146.png)
  - [Thread42.java](src%2Fmain%2Fjava%2Forg%2Fgetyou123%2FThread42.java)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202505210759512.png)

### 为啥对象的最大的年龄是15呢？
- 因为对象头中的布局限制了4bit来标志GC年龄次数 最大15

### 压缩指针
- 在64位JVM中，普通对象指针（OOP）占用8字节，
而指针压缩通过将64位地址映射到32位偏移量（配合3位左移运算），使指针降为4字节，节省堆内存约20-30%
- ‌压缩过程‌：实际内存地址 = 32位偏移量 << 3 + 堆基地址
- ‌寻址范围‌：最大支持32GB堆内存（2^32 * 8字节）
- 如果不压缩：对象头：16字节（8 MarkWord + 8 类型指针）
- 压缩的话：对象头：12字节（8 MarkWord + 4 类型指针）