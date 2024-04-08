### 根据json生成java实体类
- Gsonformat IDEA插件 

### 命名风格转化
CamelCase IDEA插件

### jar包查看和反编译
File Expander IDEA插件

### 每行代码上提示谁做了啥更新
GitToolBox IDEA插件

### 数据库表信息生成java类
Easy Code IDEA插件


### java & scala 参数解析
``` 
        <!-- 参数解析 -->
        <dependency>
            <groupId>com.beust</groupId>
            <artifactId>jcommander</artifactId>
            <version>1.78</version>
            <scope>compile</scope>
        </dependency>
```


如果是java的话：
```
public class MyParams {
       @Parameter(names = {"-i", "--input"}, description = "Input file path", required = true)
       private String inputFilePath;
        @Parameter(names = {"-o", "--output"}, description = "Output file path", required = true)
       private String outputFilePath;
        @Parameter(names = {"-n", "--number"}, description = "Number of iterations", required = false)
       private int iterations = 10;
   }
   
   // 使用方法的话
   MyParams params = new MyParams();
   JCommander.newBuilder()
           .addObject(params)
           .build()
           .parse(args);
```


如果是scala的话：
``` 
object sharerHolderLabel {

// 定义
  class MyParams {
    @Parameter(names = Array("-pt", "--partition"), description = "partition", required = true)
    var pt: String = _
    @Parameter(names = Array("-pa", "--runningPartitions"), description = "runningPartitions", required = true)
    var runningPartitions: Int = 200
    @Parameter(names = Array("-lm", "--levelLimit"), description = "level limit", required = true)
    var levelLimit: Int = 1000
    @Parameter(names = Array("-companyIdStart"), description = "companyIdStart notice: if(companyIdEnd == companyIdStart && companyIdEnd == 0) then read all companyId", required = false)
    var companyIdStart: Long = 0L
    @Parameter(names = Array("-companyIdEnd"), description = "companyIdEnd notice: if(companyIdEnd == companyIdStart && companyIdEnd == 0) then read all companyId", required = false)
    var companyIdEnd: Long = 0L
    @Parameter(names = Array("-updateWay"), description = "updateWay overwrite or into ", required = true)
    var updateWay: String = "overwrite"

  }

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName(this.getClass.getName).enableHiveSupport().getOrCreate()


    // 提取参数
    val params = new MyParams()
    JCommander.newBuilder().addObject(params).build().parse(args: _*)

    val pt = params.pt
    val runningPartitions = params.runningPartitions
    val levelLimit = params.levelLimit
    val companyIdStart = params.companyIdStart
    val companyIdEnd = params.companyIdEnd
    val updateWay = params.updateWay
    }
}

```