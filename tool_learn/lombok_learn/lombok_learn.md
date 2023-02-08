## 注解
- @Data 用在类上，@Data相当于@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解的合集
- @Setter 生成所以得setter方法
- @Getter 生成所有的getter方法
- @ToString 生成toString方法
- @ToString中可以排除某些字段 @ToString(exclude={"column1","column2"})
- @ToString中可以指明某些字段 @ToString(of={"column1","column2"}) 或者 @ToString(of="column")
- @AllArgsConstructor 全参数的构造函数，这个单独的话会只有全参数的，一般也需要生成下面的无参数的构造
- @NoArgsConstructor 无参数的构造函数
- 要是想生成部分的构造的函数的话，只能自己写了
- @EqualsAndHashCode 自动生成hashcode和equals方法
  - 它默认使用非静态，非瞬态的属性
  - 可通过参数exclude排除一些属性
  - 可通过参数of指定仅使用哪些属性
- @RequiredArgsConstructor 用在类上，要求所有的@NonNull作为参数的构造函数
- @Value 用在类上，是@Data的不可变形式，相当于为属性添加final声明，只提供getter方法，而不提供setter方法
- @Accessors(chain = true) 可以实现 new User().setAge(12).setName("小明");
- @Synchronized 用在方法上，将方法声明为同步的，并自动加锁
- @slf4j 用来代替 为每个类生成static的Logger，然后调用log.info() 这个可以直接使用 log对象
  - 注意配置 [log4j.properties](src%2Fmain%2Fresources%2Flog4j.properties) 
  - 注意pom中增加
```
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
</dependency>

<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
</dependency>
```
- 原理：注解是在编译过程中生效的

### idea中使用lombok
- 就是在插件市场进行安装
- 配置pom

### lombok原理
jsr269 这个规范的实现，可以实现对于语法树的修改（是对编译过程的干预）这个修改是在编译器完成的
