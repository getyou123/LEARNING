# 这里记录学习docker的过程和命令集合

### docker中的三个基本概念
• 镜像（Image）：Docker 镜像（Image），就相当于是一个 root 文件系统。比如官方镜像 ubuntu:16.04 就包含了完整的一套 Ubuntu16.04 最小系统的 root 文件系统。
• 容器（Container）：镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。
• 仓库（Repository）：仓库可看成一个代码控制中心，用来保存镜像

---
***三者关系***：通过镜像来创建容器，一个镜像可以创建过多个容器
本身docker是Cs的架构的，所以每次使用docker要先启动自己手守护进程
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061353235.png)
---
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
#参数说明
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
```

```shell
docker ps -a列出所有的容器
docker ps -l列出最近创建的容器
docker ps -n 3列出最近创建的3个容器
docker ps -q只显示容器ID
docker ps --no-trunc显示当前所有正在运行的容器完整信息
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
docker容器日志查看：
docker logs -f -t --since --tail 容器ID或容器名称查看容器日志
如：docker logs -f -t --since=”2018-09-10” --tail=10 f9e29e8455a5
-f : 查看实时日志
-t : 查看日志产生的日期
--since : 此参数指定了输出日志开始日期，即只输出指定日期之后的日志
--tail=10 : 查看最后的10条日志
```

```shell
docker top 容器ID或容器名称    查看容器内运行的进程
docker inspect 容器ID或容器名称   查看容器内部细节
docker attach 容器ID   进到容器内
docker exec 容器ID   进到容器内
```


### dockerfile：
dockerfile是用来构建docker的image的
其中的命令主要包括

- FROM：指定基础镜像
- RUN：用于在镜像构造过程中的执行命令，比如安装某个应用程序 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061411925.png)
- CMD：指定容器中的默认命令，docker run中没有指定其他命令时候就运行这个![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061411804.png)
- ENTRYPOINT： 这个是和docker run一起配置使用的，ENTRYPOINT 的 Exec 格式用于设置容器启动时要执行的命令及其参数 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061414959.png)
- WORKDIR：主要用于指定工作路径，推荐需要使用的是绝对的路径，没有的话会创建 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061407083.png)
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
docker build -t spark-for-seatunnel:v1.0 --build-arg SPARK_VERSION=2.4.0  --build-arg HADOOP_VERSION=2.7  --no-cache .
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
查看正在运行的docker容器： docker ps![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061459937.png)
停止容器 docker kill  2aa6d3f122ee ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061501719.png)
删除容器 docker rm  2aa6d3f122ee ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061506584.png)
删除镜像 docker rmi e6a0117ec169 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061507247.png)


### 启动mysql的docker镜像
docker run --name mysql57 -e MYSQL_ROOT_PASSWORD=123456 -v /Users/haoguowang/Documents/Docker_use/mysql57/logs:/logs -v /Users/haoguowang/Documents/Docker_use/mysql57/data:/var/lib/mysql -p 3306:3306 -d  mysql:5.7 --platform linux/amd64
