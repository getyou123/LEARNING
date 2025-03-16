### juc 简介
- java.util.concurrent 的简称 #2
- java底层对应的native方法都是调用的C #3
- 进程和线程的概念 #3
  - 进程&线程 ： 一对多
    - 进程是指在系统中正在运行的一个应用程序;程序一旦运行就是进程;进程— —资源分配的最小单位
    - 线程是系统分配处理器时间资源的基本单元，或者说进程之内独立执行的一个 单元执行流。线程——程序执行的最小单位
  - 线程的状态：Thread.State
    - NEW,(新建)
    - RUNNABLE,(准备就绪)
    - BLOCKED,(阻塞)
    - WAITING,(不见不散)
    - TIMED_WAITING,(过时不候)
    - TERMINATED;(终结)
    
### wait/sleep 的区别
  - wait是object的方法，sleep是thread中的静态方法
  - sleep拿着锁睡觉，wait是释放锁睡觉

### 共享
  - 独占式 一个时间段内只能一个进程访问
  - 共享式 一个时间内多个进程访问

### 并行和并发
  - 并发
  - 并行

### 管程
  - monitor
  - 编程语言提供的一种同步锁机制
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
  * Synchronized异常自动释放锁，但是lock不会，可能死锁，所以要写在finally
  * ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202309172217301.png)
  * 可重入锁：上锁持有对象，解锁释放对象
  * Lock的性能比Synchronized好

### 线程间的通信

* 线程之间的通信主要是实现线程之间的同步和互斥
* 这里说明下多线程编程的主要模式
  * 定义资源类
  * 定义资源类中的方法： 判断 干活 通知
  * new 多个线程进行操作资源类
* 示例如下：
  * 要求： 两个线程，分别 +1 -1 然后共享值在 0 1 之间固定跳转

   

