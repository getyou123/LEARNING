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
 class Student {
    private int age;
    private String name;

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
上面的只是重写了equals方法，所以产出的数据为null，因为两个对象的hashcode是不一样的，他们两个是不同的对象，hashscode产出和本地方法有关，因为不一样所以查出的为null

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

3.  重写 hashCode() 方法的基本原则
- 在程序运行时，同一个对象多次调用 hashCode() 方法应该返回相同的值。
- 当两个对象的 equals() 方法比较返回 true 时，这两个对象的 hashCode() 方法的返回值也应相等。
- 对象中用作 equals() 方法比较的 Field，都应该用来计算 hashCode 值。

