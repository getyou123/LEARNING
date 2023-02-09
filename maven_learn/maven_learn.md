### maven和构建
***构建的定义***
* 开发源码
* 清理
* 编译
* 单元测试
* 生成文档
* 打包
* 部署等

所以，其实构建是整个的产出过程，maven的出发点是：是否能从工作流中抽象出我们的重复工作，然后流程化，通过简单的命令实现构建；

当然，Maven 还能帮助我们管理原本分散在项目中各个角落的项目信息，包括项目描述、开发者列表、版本控制系统地址、许可证、缺陷管理系统地址等。这些微小的变化看起来很 琐碎，并不起眼，但却在不知不觉中为我们节省了大量寻找信息的时间。除了直接的项目信息，通过 Maven 自动生成的站点，以及一些已有的插件，我们还能够轻松获得项目文档、测试报告、静态分析报告、源码版本日志报告等非常具有价值的项目信息。
总结主要功能为
- 解决版本冲突
- 实施自动化构建
- 解决版本不一致
- 解决依赖臃肿

### maven的坐标
每个构件都是需要一个GAV来进行定位，这个就被称为是坐标，坐标和路径存在相应的对应关系，一般来说groupId就作为了项目的id
```xml
    <dependency>
      <groupId>org.apache.flink</groupId>
      <artifactId>flink-table-common</artifactId>
      <version>${flink.version}</version>
      <scope>${flink.scope}</scope>
    </dependency>
```

### maven中的依赖
maven的一个大功能就是规范需要的依赖，然后省得我们一个一个加入到classpath中
**Maven中使用 scope 来指定当前包的依赖范围和依赖的传递性。常见的可选值有：compile, provided, runtime, test, system 等**
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061012628.png)

1. compile :这个是默认的:。如果在定义依赖关系的时候，没有明确指定依赖有效范围的话，则默认采用该依赖有效范围。此种依赖，在编译、运行、测试时均有效。
2. provided:在编译、测试时有效，但是在运行时无效。例如：servlet-api，运行项目时，容器已经提供，就不需要Maven重复地引入一遍了。
3. runtime ：在运行、测试时有效，但是在编译代码时无效。例如：JDBC驱动实现，项目代码编译只需要JDK提供的JDBC接口，只有在测试或运行项目时才需要实现上述接口的具体JDBC驱动
4. test ：只在测试时有效，例如：JUnit

### maven中的依赖传递
Maven 中的依赖关系是有传递性的。例如：项目B依赖项目C（B —> C），如果有一个项目A依赖项目B（A —> B）的话，那么项目A也会依赖项目C（A —> C）。虽然，这种依赖的自动传递性给我们维护项目的必要依赖关系带来了极大地帮助。

依赖调节的原则：
1. 路径最近者优先; 
2. 第一声明者优先

下面阐明了依赖传递的两个原则的工作原理：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221229160308.png)


### maven中的依赖排除
依赖排除是指某个构建中的数据不想继续被传递，或者传递过程中存在了冲突需要解决，或者使用的是非安全的版本的构件这些都是需要进行排除的

例如： 当 A 依赖 B，B 依赖 C 而且 C 可以传递到 A 的时候，A 不想要 C，需要在 A 里面把 C 排除掉。而往往这种情况都是为了避免 jar 包之间的冲突

- 可选依赖：关于可选依赖的话最好是不要使用，常见的就是连接数据库的2个驱动，实际过程中只有一个
- 排除依赖：
  1. 排除依赖的情况：
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221229163351.png)
  2. 排除依赖的写法：
  ```xml
    <exclusions>  
    <exclusion> <!-- we prefer our explicit version, though it should be the same -->  
    <groupId>asm</groupId>  
    <artifactId>asm</artifactId>  
    </exclusion>  
    </exclusions>
  ```
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221229181003.png)
  3. 对于一些被排除的依赖，需要显示声明B中排除依赖，然后自己单独写明依赖C的版本
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221229181109.png)

### 关于依赖优化
通过mvn dependency:analyze 进行分析

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221229182656.png)

该结果中重要的是两个部分：
- Used undeclared dependencies 意指项目中使用到的，但是没在显式声明的依赖，这种依赖意味着潜在的风险，当前项目直接在使用它们，但是是通过传递依赖传递过来的，容易发生变化不易察觉
- Unused declared dependencies 显示声明了但是没有用到，仔细分析是否需要删除


### 关于maven坐标中的packaging字段-父项目和子模块的关系
这个字段定义里对于这个构件的打包的类型：
- 主要包括jar war pom；
- 其中如果整个项目中是有多个子模块的话，各个子模块有自己的pom文件，同时父模块的packaging一定是pom；
- 父模块一个比较重要的是GAV packaging一定是pom，然后还定义了版本信息，其中的modules标签把项目中的所有的子模块都list出来，build父会导致子逐一被build
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221227182122.png)
- 子模块packaging是jar或者是war，并且需要对父进行引用，即子模块写在parent上述POM中使用parent元素声明父模块，parent下的子元素groupld、artifactld和version指定了父模块的坐标，这三个元素是必须的
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221227181748.png)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221227181904.png)


### maven 仓库
存放maven构件的位置就是仓库，搜寻构件的时候先从本地搜寻，然后从中央仓库下载
- 本地仓库：默认地址为~/.m2/, 一个构件只有在本地仓库存在之后, 才能由Maven项目使用.
  - 本次仓库可以做个指向：
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221228095302.png)
  - 远程仓库：
    - 这个是存放多数可用的构件的地址，但是一般来说很慢还是配置一下镜像最好（镜像就是截取了对于远程仓库请求，所有请求到远程仓库的都是会去镜像上找）
    - 添加远程仓库：
      - 主要是修改pom中的repositories 标签，支持多个远程仓库，注意id的唯一性（否则后面的会覆盖前面的）
      - 对于每个仓库，需要设置是能否从这里下载发布版本或者是快照版本
      - 实际在解析依赖的时候也是遍历所有的远程仓库的
    ```shell
    <repositories>
            <repository>
                <id>maven-ali</id>
                <url>https://maven.aliyun.com/repository/public</url>
                <releases>
                    <enabled>true</enabled>
                </releases>
                <snapshots>
                    <enabled>true</enabled>
                    <!--检查更新频率 -->
                    <updatePolicy>always</updatePolicy>
                    <checksumPolicy>fail</checksumPolicy>
                </snapshots>
            </repository>
    </repositories>
    ```
      - 也可以通过配置不同的profile来实现指定和生效多个远程仓库

### maven 私服 @TODO

### maven 部署构件到远程仓库 @TODO


### maven镜像配置
如果X的构件都能从Y下载来，那么Y就是X的一个镜像

![新建的中央仓库的一个镜像仓库](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221230174321.png)

这样配置之后所有的对于中央仓库的构件请求都会转为对于镜像仓库的请求;

- 私服可以代替所有的中央仓库，可以使用私服作为所有的外部的中央仓库的镜像
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221230174858.png)
- 但是使用私服的一点隐患：对于中央仓库的所有的请求都是转为镜像的请求，如果私服出现问题，则全部失效


### maven的安装
- 本地仓库地址配置（一般搞个软链）
- 镜像使用ali云的镜像
- 环境变量配置
- 最好不要使用idea自带的maven（主要是版本问题和路径指向问题）


### maven约定的目录
```
根目录：工程名
|—src：源码
|—|—main:存放主程序
|—|—|—java：java源码文件
|—|—|—resource：存放框架的配置文件
|—|—test：存放测试程序
|—pop.xml：maven的核心配置文件
```

### maven的生命周期
主要是三个生命周期：
- clean：这个主要是清理
- default：这是主要的构建动作
- site：用于生成相应的项目说明，静态site等

三个周期进一步细分为 阶段，阶段和插件的目标绑定

抽离出常见的几个maven命令：
- maven clean 清理target目录
- mvn compile 编译主项目代码放在target/classes下
- mvn clean test 执行测试
- mvn package 打包
- mvn clean install 这个是把当前的构件打包安装在本地仓库
- mvn deploy 产出构件安装在远程仓库，其他的组可以使用


### maven 命令总结
```shell
mvn clean package -DskipTests # 跳过测试
mvn dependency:tree # 可以查看当前项目的依赖树
mvn dependency:list # 查看当前项目依赖的list
mvn dependency:analyze # 依赖情况分析
mvn install -Dmaven.test.skip=true # maven 使用参数

```

### maven插件
maven只是定义了抽象的生命周期，实际哪个生命周期的哪个阶段绑做什么事情都没有定义，这个动作是如何定义的呢？
- 插件+目标绑定到周期中的阶段
- 那么调用mvn的阶段时候就调用了插件+目标，启动了动作
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221230181644.png)
- 一些内置的绑定：
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221230181745.png)
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221230181800.png)
- 自定义绑定：
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20221230182941.png)
  - 执行mvn verify就会调用这个插件的goal


### maven helper的使用 @TODO

### maven bom 物料包 @TODO

## 大数据应用常用打包方式
通过maven将项目打成 JAR 包的常用打包方式如下：
- 不在pom中加任何插件，直接使用 mvn package 打包；这个存在局限性，只有不使用任何的第三方的jar才可，不过可以通过 spark 提交时候的--jars 来补充，不过这存在版本不一致风险；所以最好是把所有的依赖都打包到一个jar中，直接提交这个jar是最好的，即 ALL IN ONE
- 使用 maven-assembly-plugin 插件：
- 使用 maven-shade-plugin 插件；
- 使用 maven-jar-plugin 和 maven-dependency-plugin 插件；

### 使用 maven-assembly-plugin 插件
https://github.com/heibaiying/BigData-Notes/blob/master/notes/%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%BA%94%E7%94%A8%E5%B8%B8%E7%94%A8%E6%89%93%E5%8C%85%E6%96%B9%E5%BC%8F.md


### maven在pom中配置跳过测试
```xml
    <build>
        <finalName>maven_learn</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

### 
todo ： https://zhuanlan.zhihu.com/p/119306367