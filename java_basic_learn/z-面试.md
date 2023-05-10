### 关于hashcode
1. question1
```
System.out.println(new Object());
```
这个后面的产出的类似 ``` java.lang.Object@6bc7c054 ```
其中的 6bc7c054是 @符号后面的是什么？是 hashcode 还是对象的内存地址？还是其他的什么值？

其实是hashcode值，16进制的hashcode值

基于hotspot jvm来看是按照
```
product(intx, hashCode, 5, "(Unstable) select hashCode generation algorithm" ) 
```
定义的hashcode值来定义的hashcode生成策略 共计五种算法，不过需要注意的是jvm不保证对应的hashcode是绝对的不一致的

2. question2：
   https://mp.weixin.qq.com/s/GfKUn-QbwL-CO5WionF17w 
``` 为什么重写 equals 时必须重写 hashCode 方法 ```

   hashCode其实是作为是识别唯一对象来的，如果你逻辑上认为了一个学生age+name相同就是逻辑上的一个同学的话，那么为了防止在hashtable中他们获取数据不一致的情况，是需要把hashcode进行重写的
```java
public class Test {
    public static void main(String[] args) {
        Student s1 = new Student(18, "张三");
        Map<Student, Integer> scores = new HashMap<>();
        scores.put(s1, 98);

        Student s2 = new Student(18, "张三");
        System.out.println(scores.get(s2));
    }
}

class Student { private int age;private String name;
    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(name, student.name);
    }
}
```
上面的只是重写了equals方法，所以产出的数据为null，因为两个对象的hashcode是不一样的，他们两个是不同的对象，hashcode产出和本地方法有关，因为不一样所以查出的为null

底层实际上是 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302081148839.png)

必须要重写hashcode方法：
```
@Override
 public int hashCode() {
     return Objects.hash(age, name);
 }
```
这样他们的hashcode值就是一样的![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302081149961.png)
System.out.println(scores.get(s2));就能获取到正常的数据


note ： 综上，一个map中的student对象的相等是指，hashcode等，且equals也得相等，这两个缺一不可

3.  重写 hashCode() 方法的基本原则
- 在程序运行时，同一个对象多次调用 hashCode() 方法应该返回相同的值。
- 当两个对象的 equals() 方法比较返回 true 时，这两个对象的 hashCode() 方法的返回值也应相等。
- 对象中用作 equals() 方法比较的 Field，都应该用来计算 hashCode 值。


4. 面试题：Hashtable和HashMap的区别

```
HashMap:底层是一个哈希表（jdk7:数组+链表;jdk8:数组+链表+红黑树）,是一个线程不安全的集合,执行效率高
Hashtable:底层也是一个哈希表（数组+链表）,是一个线程安全的集合,执行效率低

HashMap集合:可以存储null的键、null的值
Hashtable集合,不能存储null的键、null的值

Hashtable的子类Properties（配置文件）依然活跃在历史舞台
Properties集合是一个唯一和IO流相结合的集合
```

--- 
5. ArrayList与Vector的区别
它们的底层物理结构都是数组，我们称为动态数组。
* ArrayList是新版的动态数组，线程不安全，效率高，Vector是旧版的动态数组，线程安全，效率低。
* 动态数组的扩容机制不同，ArrayList默认扩容为原来的1.5倍，Vector默认扩容增加为原来的2倍。
--- 

6. Entry中的hash属性为什么不直接使用key的hashCode()返回值呢？
JDK1.7和JDK1.8关于hash()的实现代码不一样，
但是不管怎么样都是为了提高hash code值与 (table.length-1)的按位与完的结果，尽量的均匀分布。
所以核心就是为了减少碰撞；
因为一个HashMap的table数组一般不会特别大，至少在不断扩容之前，那么table.length-1的大部分高位都是0，直接用hashCode和table.length-1进行&运算的话，就会导致总是只有最低的几位是有效的，那么就算你的hashCode()实现的再好也难以避免发生碰撞，这时让高位参与进来的意义就体现出来了。它对hashcode的低位添加了随机性并且混合了高位的部分特征，显著减少了碰撞冲突的发生。

7. HashMap是如何决定某个key-value存在哪个桶的呢？
①hash 值 % table.length会得到一个[0,table.length-1]范围的值，正好是下标范围，但是用%运算效率没有位运算符&高。
②hash 值 & (table.length-1)，任何数 & (table.length-1)的结果也一定在[0, table.length-1]范围。

8. HashMap解决[index]冲突问题
- JDK1.8之间使用：数组+链表的结构。
- JDK1.8之后使用：数组+链表/红黑树的结构。

9. HashMap 什么时候树化？什么时候反树化？
- 哈希表的链表树化成红黑树有 两个条件（且的关系）：
   static final int TREEIFY_THRESHOLD = 8;//树化阈值 链表长度
   static final int MIN_TREEIFY_CAPACITY = 64;//最小树化容量 数组长度
- 反树化：
  static final int UNTREEIFY_THRESHOLD = 6;//反树化阈值

10. JDK1.7中HashMap的循环链表是怎么回事？如何解决？
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303031553589.png)
避免HashMap发生死循环的常用解决方案：

- 多线程环境下，使用线程安全的ConcurrentHashMap替代HashMap，推荐
- 多线程环境下，使用synchronized或Lock加锁，但会影响性能，不推荐
- 多线程环境下，使用线程安全的Hashtable替代，性能低，不推荐

HashMap死循环只会发生在JDK1.7版本中，主要原因：头插法+链表+多线程并发+扩容。

在JDK1.8中，HashMap改用尾插法，解决了链表死循环的问题。