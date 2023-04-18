### juc 简介
- java.util.concurrent 的简称
- 进程和线程的概念
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

### 并行和并发
### 管程

### 用户线程和守护线程的的特点
- 用户自定义线程：时间上完成可以超过主线程
- 守护线程 通过setDomain来设置线程是不是守护线程，然后用户线程结束则守护线程结束，周期同jvm一起结束
- [Thread01.java](main%2Fjava%2Forg%2Fgetyou123%2FThread01.java)


### LOCK接口
