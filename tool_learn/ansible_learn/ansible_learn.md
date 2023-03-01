### ansible 学习笔记

### ansible架构
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303011030918.png)
- 基于ssh服务
- 被控制端无需按照任务client只需要支持ssh
- ansible模块丰富，安装简单，不污染被控节点

### ansible的安装
- mac 下 `brew install ansible`

### ansible配置文件的查找顺序和主要的一些配置项说明
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303011037467.png)
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303011037318.png)


### 工作流程
- 本机生成了ssh秘钥
- 配置远程被控主机的host，主机清单，这可以分组 `vim /etc/ansible/hosts`
```  
[hostname] # 可以随意命名
192.168.1.2
192.168.1.3

[hostname1] # 可以有多个分组
192.168.1.4
192.168.1.5

[hadoop_p1] # 冒号的使用方法
hdp[1:3].bigdata.getyou
hadp[001:099].bigdata.getyou
hdp567.bigdata.tyc

# 关于特殊的登录主机的用户配置（ip 登录用户 登录密码 切换用户密码-提权时候使用）
h01.bigdata.gtu	ansible_ssh_user=slave ansible_ssh_pass=xxxx ansible_become_pass=xxxx

```
- 执行 ansible hadoop_p1 -m shell -a 'id yarn' -i ./hadoop.host # 指明了哪些机器，使用shell 模块执行 id yarn 命令，host文件也被指明


### 被控制机器上有sudo权限设置
需要满足两个前提条件：
- 主控机器 上`/etc/ansible/hosts` 中配置类似 `h01.bigdata.gtu	ansible_ssh_user=slave ansible_ssh_pass=xxxx ansible_become_pass=xxxx`
- 目标机器上 `/etc/sudoers` 中配置了登录用户的sudo权限
- 主控机器上执行： `ansible XXXX -b -K `

### 模块
- 本质就是脚本
- 命令格式
```
-a MODULE_ARGS　　　#模块的参数，如果执行默认COMMAND的模块，即是命令参数，如： “date”，“pwd”等等
-k，--ask-pass #ask for SSH password。登录密码，提示输入SSH密码而不是假设基于密钥的验证
--ask-su-pass #ask for su password。su切换密码
-K，--ask-sudo-pass #ask for sudo password。提示密码使用sudo，sudo表示提权操作
--ask-vault-pass #ask for vault password。假设我们设定了加密的密码，则用该选项进行访问
-B SECONDS #后台运行超时时间
-C #模拟运行环境并进行预运行，可以进行查错测试
-c CONNECTION #连接类型使用
-f FORKS #并行任务数，默认为5
-i INVENTORY #指定主机清单的路径，默认为/etc/ansible/hosts
--list-hosts #查看有哪些主机组
-m MODULE_NAME #执行模块的名字，默认使用 command 模块，所以如果是只执行单一命令可以不用 -m参数
-o #压缩输出，尝试将所有结果在一行输出，一般针对收集工具使用
-S #用 su 命令
-R SU_USER #指定 su 的用户，默认为 root 用户
-s #用 sudo 命令
-U SUDO_USER #指定 sudo 到哪个用户，默认为 root 用户
-T TIMEOUT #指定 ssh 默认超时时间，默认为10s，也可在配置文件中修改
-u REMOTE_USER #远程用户，默认为 root 用户
-v #查看详细信息，同时支持-vvv，-vvvv可查看更详细信息
```

一些例子：
- command ssh去登录不使用解释其，不可挂后台，不可管道
- shell 拥有解释器，可后台，可管道，但是不能交互
```
ansible all -m shell -a 'id hdfs' -i ./hadoop.hosts # 指定使用shell解释器
```
- script 控制被控机执行脚本，脚本是放在主控机器上的
```
ansible all -m script -a '/home/hdfs/ansible/a.sh' -i ./hadoop.hosts 
```
- file 用于各台控制机器上文件的创建，新增，删除，权限控制；
``` 
# 如果不存则新建，如果存在则改权限
ansible all -m file -a "path=/hore/hdfs/ansible state=directory owner=hdfs group=hadoop rode=777" -i ./hadoop.hosts
state的状态取值：
    directory：如果目录不存在，就创建目录
    file：即使文件不存在，也不会被创建
    link：创建软链接
    hard：创建硬链接
    touch：如果文件不存在，则会创建一个新的文件，如果文件或目录已存在，则更新其最后修改时间
    absent：删除目录、文件或者取消链接文件
    
# 删除文件
ansible all -m file -a "path=/home/hdfs/ansible/1.txt state=absent owner=hdfs group=hadoop mode=707" -1 ./hadoop.hosts

# 递归操作
ansible all -m file -a "path=/home/hdfs/ansible state=directory recurse=yes" -i ./hadoop.hosts
```
- copy 用户将主控机的文件发送到被控机上
``` 
ansible hadoop_1 -m copy -a "src=/hane/hdfs/ansible/hdp1.host dest=/hame/hdfs/" -i ./hdp1.host
```
- fetch 用于收集被控机器文件到主控机
```
ansible hadoop_1 -m fetch -a "src=/hare/hdfs/hdp1.host dest=/home/hdfs/ansible/" -i hpl.host # 每个机器都会单独存储一个文件夹
```