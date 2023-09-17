# 这里记录学习docker的过程和命令集合

### docker中的三个基本概念

- 镜像（Image）：Docker 镜像（Image），就相当于是一个 root 文件系统。比如官方镜像 ubuntu:16.04 就包含了完整的一套 Ubuntu16.04
  最小系统的 root 文件系统。
- 容器（Container）：镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。
- 仓库（Repository）：仓库可看成一个代码控制中心，用来保存镜像

---
***三者关系***：通过镜像来创建容器，一个镜像可以创建过多个容器
本身docker是Cs的架构的，所以每次使用docker要先启动自己手守护进程
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061353235.png)
---

--- 

# docker配置加速器

### docker的基础命令

```shell
docker version #查看docker的版本信息 docker -v
docker info #查看docker的系统信息,包括镜像和容器的数量
docker 具体COMMAND --help #帮助命令(可查看可选的参数)
systemctl start docker #启动docker的服务
systemctl stop docker #停止docker
systemctl restart docker  #重启docker
systemctl status docker #查看docker的状态
systemctl enable docker #设置开机自启动
```

### docker的镜像命令

```shell
docker images  #查看本地的docker镜像 
docker images -a #列出本地所有的镜像
docker images -p #只显示镜像ID
docker images --digests #显示镜像的摘要信息
docker images --no-trunc #显示完整的镜像信息
docker search tomcat #从Docker Hub上查找tomcat镜像
docker pull tomcat #从Docker Hub上下载tomcat镜像，等价于docker pull tomcat:latest
docker commit -m "提交的描述信息" -a "作者" 容器ID 要创建的目标镜像名称:[标签名]提交容器使之成为一个新的镜像。
eg:docker commit -m "新的tomcat" -a "getyou123" f9e29e8455a5 mytomcat:1.2
docker rmi hello-world #从Docker中删除hello-world镜像
docker rmi -f hello-world #从Docker中强制删除hello-world镜像
docker rmi -f hello-world nginx #从Docker中强制删除hello-world镜像和nginx镜像
docker rmi -f $(docker images -p) #通过docker images -p查询到的镜像ID来删除所有镜像
```

---

### docker 容器命令

```shell
docker run [可选参数] image
# 参数说明
–name="名字" 指定容器名字
-d 后台方式运行
-it 使用交互方式运行,进入容器查看内容
-p 指定容器的端口
(
-p ip:主机端口:容器端口 配置主机端口映射到容器端口
-p 主机端口:容器端口
-p 容器端口
)
-P 随机指定端口(大写的P)
exit 停止并退出容器（后台方式运行则仅退出）
Ctrl+P+Q 不停止容器退出

 进入已经运行的容器中：docker exec -it 243c32535da7 /bin/bash
```

```shell
docker ps -a列出所有的容器
docker ps -l列出最近创建的容器
docker ps -n 3列出最近创建的3个容器
docker ps -q只显示容器ID
docker ps --no-trunc显示当前所有正在运行的容器完整信息
docker logs CONTAINER_ID # 查询指定的日志信息
```

```shell
docker start 容器ID或容器名称启动容器
docker restart 容器ID或容器名称重新启动容器
docker stop容器ID或容器名称停止容器
docker kill 容器ID或容器名称强制停止容器
docker rm 容器ID或容器名称删除容器
docker rm -f 容器ID或容器名称强制删除容器
docker rm -f $(docker ps -a -q)删除多个容器这个是删除所有的容器
```

```shell
docker容器日志查看
docker logs -f -t --since --tail 容器ID或容器名称查看容器日志
如 docker logs -f -t --since="2018-09-10" --tail=10 f9e29e8455a5
-f : 查看实时日志
-t : 查看日志产生的日期
--since : 此参数指定了输出日志开始日期，即只输出指定日期之后的日志
--tail=10 : 查看最后的10条日志
```

```shell
docker top 容器ID或容器名称    查看容器内运行的进程
docker inspect 容器ID或容器名称   # 查看容器内部细节，包括看里面的卷
docker attach 容器ID   进到容器内
docker exec 容器ID   进入容器内: docker exec -it XXX /bin/bash
```

```shell #拷贝容器中的数据到宿主机
docker cp 4b1a0fe53315:/etc/mysql/ /mydata/mysql/conf # 把容器中数据导出到宿主机器
```

### dockerfile：

dockerfile是用来构建docker的image的
其中的命令主要包括

- FROM：指定基础镜像
-

RUN：用于在镜像构造过程中的执行命令，比如安装某个应用程序 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061411925.png)

- CMD：指定容器中的默认命令，docker
  run中没有指定其他命令时候就运行这个![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061411804.png)
- ENTRYPOINT： 这个是和docker run一起配置使用的，ENTRYPOINT 的 Exec
  格式用于设置容器启动时要执行的命令及其参数 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061414959.png)
-

WORKDIR：主要用于指定工作路径，推荐需要使用的是绝对的路径，没有的话会创建 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061407083.png)

- COPY： 将本机的路径文件拷贝到镜像中，容器中自然也会有 例如： COPY test.txt relativeDir/
- ADD：将宿主机目录下的文件拷贝进镜像且ADD命令会自动处理URL和解压tar压缩包
- VOLUME：容器数据卷，用于数据保存和持久化工作
-

#### dockerfile中的命令的一些区别

***区别 RUN-CMD-ENTRYPOINT***：
ENTRYPOINT中的参数始终会被使用，而CMD的额外参数可以在容器启动时动态替换掉
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061416615.png)

***区别ARG-ENV***
主要就是arg的只是在构建过程中是生效的,env是构建的镜像中是生效的
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061404338.png)

***区别 COPY-ADD***
主要就是COPY不带解压，而ADD会进行解压

#### dockerfile常见例子：

### docker build 产出镜像文件：

docker build的工作主要是：产出相应的image,然后再通过image构造相应的容器

```shell
docker build [OPTIONS] PATH | URL | -

OPTIONS说明：
• --build-arg=[] :设置镜像创建时的变量；
• --cpu-shares :设置 cpu 使用权重；
• --cpu-period :限制 CPU CFS周期；
• --cpu-quota :限制 CPU CFS配额；
• --cpuset-cpus :指定使用的CPU id；
• --cpuset-mems :指定使用的内存 id；
• --disable-content-trust :忽略校验，默认开启；
• -f :指定要使用的Dockerfile路径；
• --force-rm :设置镜像过程中删除中间容器；
• --isolation :使用容器隔离技术；
• --label=[] :设置镜像使用的元数据；
• -m :设置内存最大值；
• --memory-swap :设置Swap的最大值为内存+swap，"-1"表示不限swap；
• --no-cache :创建镜像的过程不使用缓存；
• --pull :尝试去更新镜像的新版本；
• --quiet, -q :安静模式，成功后只输出镜像 ID；
• --rm :设置镜像成功后删除中间容器；
• --shm-size :设置/dev/shm的大小，默认值是64M；
• --ulimit :Ulimit配置。
• --squash :将 Dockerfile 中所有的操作压缩为一层。
• --tag, -t: 镜像的名字及标签，通常 name:tag 或者 name 格式；可以在一次构建中为一个镜像设置多个标签。
--network: 默认 default。在构建期间设置RUN指令的网络模式
```

docker build -t name:v1.0 .
需要在有dockerfile的文件夹中执行
也可使用 -f显示指定构建镜像的 Dockerfile 文件（Dockerfile可不在当前路径下）

常见的例子：
docker build -t spark-for-seatunnel:v1.0 --build-arg SPARK_VERSION=2.4.0 --build-arg HADOOP_VERSION=2.7 --no-cache .
这里指定了ARG的声明的参数的值

### 例子命令总结：

启动一个容器

```shell
docker run --name ubuntu_bash --rm -i -t ubuntu bash 启动一个
```

查看本地的docker的image: docker images
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061439097.png)

搜索远程仓库的某个镜像：docker search [OPTIONS] 镜像名字
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061440298.png)

从远程仓库下载某个镜像：docker pull 镜像名字[:TAG] 如果不写TAG就是最新的版本
例如拉取 mysql5.7:docker pull --platform linux/x86_64 mysql:5.7

### 构建一个centos的镜像：

拉取：docker pull centos
新建&启动容器： docker run -it centos /bin/bash
查看正在运行的docker容器： docker
ps ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061459937.png)
停止容器 docker kill
2aa6d3f122ee ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061501719.png)
删除容器 docker rm
2aa6d3f122ee ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061506584.png)
删除镜像 docker rmi
e6a0117ec169 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061507247.png)

### 启动mysql的docker镜像

```shell
docker run --name mysql5.7 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d  -v /Users/haoguowang/Documents/Docker_use/mysql57/data:/var/lib/mysql -v /Users/haoguowang/Documents/Docker_use/mysql57/conf:/etc/mysql -v /Users/haoguowang/Documents/Docker_use/mysql57/logs:/var/log/mysql mysql:5.7
```

主要是需要三个进行映射

- 宿主机创建数据存放目录映射到容器
- 宿主机创建配置文件目录映射到容器
- 宿主机创建日志目录映射到容器

处理错误：启动之后直接失败，通过docker logs 看
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061835701.png)
主要是因为配置文件不在，宿主机的...../mysql/conf是空的，所以找不到/etc/mysql/conf.d中conf.d这个目录，导致容器创建失败，去指定的路径下建立文件夹和指定的文件

从官网的信息可以看到 https://hub.docker.com/_/mysql

- 容器的配置文件是：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301071036476.png)
- 容器的数据存储为：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301071037839.png)
- 一般可以不配置log，而是通过docker logs 来查看相应的容器日志

进入到容器中：docker exec -it d70a5deda3eb /bin/bash
然后登录mysql中：mysql -uroot -p
可以看到数据已经加载到容器中了：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301070023731.png)
当我们配置了数据卷的对应关系：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301070033786.png)

### docker中进行编译doris

### docker中部署tomcat

#### servlet学习使用[servlet_learn](..%2Fservlet_learn)

- 首先拉取tomcat镜像，`docker pull tomcat:8.5.85-jre8-temurin-jammy`
- 在idea中编译产出war包 放在宿主机的卷
-

启动容器 ``` docker run --name tomcat_for_servlet_learn -p 8080:8080 -v /Users/XXX/Documents/Docker_use/servlet_learn/:/usr/local/tomcat/webapps/ -d tomcat:8.5.85-jre8-temurin-jammy ```
，注意下这里的卷

即idea mvn package 产出的war包，是docker 中的 tomcat 所指向的加载的war包

tomcat出现404的解决方式：
主要原因是因为tomcat的版本过高就会存在这个问题
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301201415827.png)

进入容器进行修复 docker exec -it XXX /bin/bash
然后移动文件 cp -r webapps.dist/* webapps/ 这里注意是把里面的内容放在webapps/ 下
重启容器docker restart XXX

```shell
# webapps 下的内容正常来说是类似
root@0ec7e79b9916:/usr/local/tomcat/webapps# pwd
/usr/local/tomcat/webapps
root@0ec7e79b9916:/usr/local/tomcat/webapps# ls
docs  examples  host-manager  manager  ROOT  servlet_learn_1  servlet_learn_1.war  servlet_learn.war
```

如果war发生变化需要，重启容器
docker中获取tomcat的运行日志情况： 日志位置 /usr/local/tomcat/logs

#### javaweb_learn学习使用[javaweb_learn](..%2Fjavaweb_learn)

```shell
docker run --name tomcat_for_javaweb_learn -p 8081:8080 -v /Users/haoguowang/Documents/Docker_use/javaweb_learn/:/usr/local/tomcat/webapps/ -d tomcat:8.5.85-jre8-temurin-jammy、
docker tomcat中访问 docker mysql容器，首先获取docker msyql的ip 地址，docker inspect 然后获取之后更新 jdbc.property localhost=> msyql的ip
```

#### es_learn学习

- 搭建单机的版本的es

```shell
 ~/Documents/Docker_use/es_learn  mkdir config                                                                                                                              ok  base py
 ~/Documents/Docker_use/es_learn  mkdir data                                                                                                                                ok  base py
 ~/Documents/Docker_use/es_learn  mkdir plugins                                                                                                                        127 err  base py
 ~/Documents/Docker_use/es_learn  ll                                                                                                                                        ok  base py
total 0
drwxr-xr-x@ 2 haoguowang  staff    64B  2  6 17:44 config
drwxr-xr-x@ 2 haoguowang  staff    64B  2  6 17:44 data
drwxr-xr-x@ 2 haoguowang  staff    64B  2  6 17:44 plugins
 ~/Documents/Docker_use/es_learn  echo "http.host: 0.0.0.0" >> ./config/elasticsearch.yml                                                                                   ok  base py
 ~/Documents/Docker_use/es_learn  tree                                                                                                                                      ok  base py
.
├── config
│   └── elasticsearch.yml
├── data
└── plugins
```

```shell
docker run --name elasticsearch -p 9200:9200  -p 9300:9300 \                                                                  125 err  base py
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms84m -Xmx512m" \
-v /Users/haoguowang/Documents/Docker_use/es_learn/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /Users/haoguowang/Documents/Docker_use/es_learn/data:/usr/share/elasticsearch/data \
-v /Users/haoguowang/Documents/Docker_use/es_learn/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.12.0
```

访问9200之后出现相应的信息：![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061750782.png)

- 搭建kibana
    1. 拉取镜像 ``` docker pull --platform linux/x86_64 kibana:7.8.0  ```
    2.
  启动一个container `docker run -it -d -p 5601:5601 -e ELASTICSEARCH_URL=http://127.0.0.1:9200 --name kibana kibana:7.8.0`
  其中的ip 使用docker ps 获取es实例的ip
    3. 进入kibana容器中，修改
       config/kibana.yml ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071733057.png)
    4. 重启容器，这个会耗时很久注意等待
    5. 最终可以在kibana中使用dev
       tool ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071734954.png)

### docker安装mongo db

```shell
# 拉取镜像
docker pull mongo:4.4
# 启动容器，开启验证
docker run -itd --name mongo -v /Users/haoguowang/Documents/Docker_use/mongodb_use:/data/db -p 27017:27017 mongo:4.4 --auth   ok  base py
# 进入容器
docker exec -it mongo mongo admin
# 创建用户和授权
 db.createUser({ user:'root',pwd:'123456',roles:[ { role:'userAdminAnyDatabase', db: 'admin'},'readWriteAnyDatabase']});
```

### docker 安装tomcat作为spring mvc容器使用：

- 编写Dockerfile文件[Dockerfile](..%2FSpring_Learn%2Fspring_mvc_learn%2FDockerfile)
- 编写执行的脚本代替原来的启动脚本 [start-docker.sh](..%2FSpring_Learn%2Fspring_mvc_learn%2Fstart-docker.sh)
- 通过Dockerfile构造镜像  `docker build -t mvc_use .`
- 启动容器
  `docker run --name mvc_learn -p 9090:8080 -v /Users/haoguowang/IdeaProjects/LEARNING/Spring_Learn/spring_mvc_learn/target/spring_mvc_learn.war:/usr/local/tomcat/webapps/spring_mvc_learn.war -d mvc_use`
- 更新代码之后idea中进行maven clean && mvn
  package ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303161354057.png)
- 之后再idea中直接重启
  容器即可 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303161355982.png) -- 这一步其实不执行也可以刷新
- 访问 `http://localhost:9090/spring_mvc_learn/`

### docker 安装 jira


### docker 安装kafka单机版本
``` 
docker-compose.yml 文件内容
version: '2'

services:
  kafka:
    image: wurstmeister/kafka:latest
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_ZOOKEEPER_CONNECT: zk:2181 # 需要与下方的服务名对应
    depends_on:
      - zk

  zk:
    image: wurstmeister/zookeeper:latest
    container_name: zk
    ports:
      - "2181:2181"
```
- 启动 `docker-compose up -d`
- 创建topic 
``` 
/opt/kafka/bin/kafka-topics.sh --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 2 \
  --partitions 3 \
  --topic test_topic
```
- 生产者发送数据 && 消费者消费数据
``` 
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
docker exec -it kafka /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic sensor
```


### docker创建clickhouse单机版本
拉取和创建容器
``` 
docker run -d --name clickhouse-server --ulimit nofile=262144:262144 \
-p 8123:8123 -p 9000:9000 -p 9009:9009 --privileged=true \
-v /Users/haoguowang/Documents/clickHouseUse/log:/var/log/clickhouse-server \
-v /Users/haoguowang/Documents/clickHouseUse/data:/var/lib/clickhouse clickhouse/clickhouse-server:22.1.4.30
```
- 本机curl验证
``` 
curl localhost:8123
Ok.
```
- 客户端连接8123端口可以连接上
- 掉启容器内的客户端
``` 
docker exec -it clickhouse-server bash
docker exec -it clickhouse-server /usr/bin/clickhouse-client
```
