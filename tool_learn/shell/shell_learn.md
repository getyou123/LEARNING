### linux 命令行

# linux的系统：
![1](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz20230105175728.png)
init进程：这个是os加载之后的第一个进程，进程号为1

### linux的目录结构
主要包括下面的几个路径：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301092045869.png)

### 磁盘和挂载的关系
***挂载mount***：通过挂载机制将硬盘分区与文件目录联系起来
本来每个磁盘会进行分区，这个分区就是磁盘内的逻辑分区情况，这个是磁盘的事情，和具体这块磁盘被那个os使用是无关的
然后，磁盘的分区需要挂载到os的文件系统的挂载点上，可以通过df命令来实现查询分区和挂载点的对应关系
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301101034355.png)
所以逻辑上说分区和挂载点的对应关系：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301101036811.png)

### 磁盘使用情况
```shell
df -h #查看每个分区挂载盘的剩余情况
du -sh ./ --max-depth=1 # 查询一层的文件占用位置大小
ls -l | grep "^-" | wc -l # 查询普通文件数目
ls -l | grep "^d"|wc -l # 查询文件夹的数目
du -h | sort -nr # 按照磁盘的容量进行排序
```
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061612659.png)

### grep 进行文本查询
grep 显示周围几行
grep 分别就是-A 之后 -B 之前 -C周围
grep -A2 'exe' 1.txt
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301061807843.png)

反选
grep -v

查询包含模式的有多少行
grep -c 'exec' 1.txt

### 进程查看
ps -ef | grep datax
返回信息是按照 UID PID PPID C STIME TTY TIME CMD
分别为 用户id 进程id 父进程id CPU的利用率 启动时间 启动终端 占用cpu的时间 执行的命令
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301101113728.png)

### top命令实时监测系统的进程
top的产出
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301101509033.png)
主要部分是：第一个部分显示整个系统的运行状态，当前时间，登录用户数，负载等；第二部分是各个进程的状态情况

可以使用 f 来选择按照哪个指标来排序，使用 d来定义刷新的间隔，使用q来推出退出top

#### 僵尸进程的产生过程（defunct）
- 如果子进程执行完父进程还在，并且可以回收子进程的资源，那么子进程正常结束
- 如果父进程先于子进程结束，那么init进程对孤儿进程进行资源状态回收工作
- 如果父进程在，但是父进程忙无法回收子进程资源，那么子进程变为僵尸进程

#### 查看僵尸进程
ps -A|grep defunct

#### 如何杀死僵尸进程
僵尸进程已死过一回，但是其父进程还在，常见方法是kill其父进程，kill僵尸进程不管用，因为已经死过一回


### 进程结束
使用kill 命令
使用kill -9
使用pkill 这个是用来按照程序的名称来进行kill的


### 数据排序
sort命令可以实现数据的排序，但是默认是使用字符串来排序，如果要使用的是数据的排序的话，还是使用-n参数

sort -n 1.txt 使用数据进行排序
-r 实现逆序排序
-u 实现去重
-t ';' -k 3 指定按照；分割然后按照第三列进行排序


### 压缩
unzip
tar -zcvf 1.tar ./
tar -zxvf 1.tar


### 每个用户登录使用的shell解释器是如何规定的呢
常见的shell类型有bash zsh csh dash ，而用户的默认使用的shell其实在passwd的文件中做了指定
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301111411547.png)
大多数情况都是默认使用的是shell

### 协程


### 内建命令和外部命令
内建命令是shell中的一部分，和shell是编译在一起的，是不需要额外的创建子进程的，这种命令速度快，效率高
外部命令不是shell中的一部分，需要通过子进程的方式来执行命令，会衍生（forking）出新的进程，然后shell作为其父进程，这些外部命令的常见的存放地方是：/bin /usr/bin /usr/sbin
which只能用来定位外部命令文件


### 命令的别名
alias ll='ls -alF'
这个一般配置在相关的配置文件中，比如常见的/etc/profile等
alias命令设置无法持久生效。你可以把个人的alias设置放在$HOME/.bashrc启动文件中，使其效果永久化。


### 环境变量
查询全局变量 env
set 查询的是特定的进程内的所有的变量：无论是局部变量还是全局变量
设置全局变量：export ALL_ENV='I AM TEST'
删除环境变量：unset ALL_ENV
环境变量是按照:进行分割的
echo $PATH￼
/data/Fiber/bin:/op/bin:/data/big/Hive/Beeline/bin:/data/bin:/home/hive/bin

### shell配置文件的加载顺序
- /etc/profile 只要登录linux系统就会生效，每个用户的登录行为都是先使用这个文件
- $HOME/.bash_profile
- $HOME/.bashrc
- $HOME/.bash_login
- $HOME/.profile
当然因为发行版的不同，这几个文件不是在一个系统中都是存在的,具体的可以去看下文件的内容，比如某个文件可能会先去source另外的那个
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301111532672.png)

### shell中的数组：
```shell
mytest=(zero one two three four) # 声明数组
echo ${mytest[2]}  # 数组某个位置上的值，注意这里是从零开始下标的
echo ${mytest[*]} # 整个数组元素
mytest[2]=seven # 更改某个索引上的值 
```


### linux权限管理


### linux添加用户& 删除用户
```shell
user add XXX # 添加用户
user add -m XXX # 添加并创建$HOME目录
userdel XXX # 默认只删除/etc/passwd和/etc/shadow文件中的用户信息，属于该账户的文件会被保留
userdel -r XXX # 删除用户并删除其home目录
```

### 更新用户信息
usermod的语法：
```
usermod [-LU][-c <备注>][-d <登入目录>][-e <有效期限>][-f <缓冲天数>][-g <群组>][-G <群组>][-l <帐号名称>][-s <shell>][-u <uid>][用户帐号]
```
锁定用户使其无法登陆：usermod -L test
usermod -G 是修改附加组 usermod -G hive_G hive
usermod -g 是修改主组 usermod -g hive_G hive
usermod -p hive 更新密码
passwd test 也用来更新密码
特别需要注意：-g选项，则指定的组名会替换掉在/etc/passwd文件中为该用户分配的主要组。-G选项则会将该组加入该用户的属组列表，不会影响主要组

### 更新系统中的组
groupadd new_G

### 更新文件的权限
chmod 777 1.txt
chown new_user 1.txt
setfacl -m g:sales:rw test.txt￼# 给text.txt增加了group 组 sales的读写权限
getfacl test.txt # 查看文件的权限acl列表
setfacl -x g:sales test.txt # 删除组的acl权限

### 查看cpu的信息
cat /proc/cpuinfo # 查看cpu的info信息

### linux中逻辑卷的作用
前面已经知道了磁盘分区和linux的挂载点的关系,磁盘中的多个分区可以添加到同一个卷中，这样在系统用户看来这个路径就挂载了很大的磁盘容量![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301131554343.png)
LVM允许将多个分区组合在一起，作为单个分区（逻辑卷）进行格式化、在Linux虚拟目录结构上挂载、存储数据等。随着数据需求的增长，你还可以继续向逻辑卷添加分区

### vi编辑器
- 快速翻页 ctrl+f
- 快速翻页 ctrl+b
- 快速首部 gg
- 快速到最后 G
- 快速全删 ggdG
- 删除行 dd d10d
- 撤销 u
- 复制 y y1y
- 粘贴 p
- 查找 / 输入单词 然后N n
- 替换 :s///g

### 脚本中的命令替换
可以使用$() 或者 `` 包裹住命令
```shell
test=`date`
test1=$(date)
echo $test
echo $test1
```
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301131650788.png)


### 重定向
nohup sh 1.sh 1>run.log 2>&1 & # 后台执行

### shell 中的数据运算
[] 套住所需的计算的式子
注意bash 只支持 整数的运算
zsh 支持浮点数的运算

### if语句
```shell
if pwd
then
  echo "it worked"
fi


string1=Soccer
string2=''
#￼
if [ -n $string1 ]
then  
	echo "The string '$string1' is NOT empty"
else
	echo "The string '$string1' IS empty"
fi
#￼
if [ -z $string2 ]
then
   echo "The string '$string2' IS empty"
else
   echo "The string '$string2' is NOT empty"
fi
```
注意else后面不要有不必要的空格![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301131742183.png)

#### if-文件的比较
```shell
-d 是否为路径
-f 是否为文件
-e 是否存在
-s 是否存在且非空
```
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301131745044.png)

#### if-逻辑组合
&& ||
```shell
if [ -d $HOME ] && [ -w $HOME/newfile ]
then 
  echo "yes"
else
  echo "no"
fi
```

#### if-判定命令返回结果
```shell
if (cat /etc/PASSWORD)
then
  echo "The subshell command operated successfully."
else
  echo "The subshell command was NOT successful."
fi

if [ $? -ne 0 ]; then
    echo "ERROR: $dt分区数据不正常 退出"
        exit 1
    else
        echo "INFO:当天分区数据正常 继续"
    fi
```

### shell中的for循环
```shell
#!/bin/bash

# 从文件中读出内容,从命令中读取数据
for i in $(cat /root/users.txt)
do
  echo $i
  echo "123456"
done

#!/bin/bash
# C语言版本的for循环
j=$1
for ((i=1; i<=j; i++))
do
  echo file $i is ok
done

# 定义一个列表进行循环,这里做了拼接也是作为一个list的
list="Alabama Alaska Arizona Arkansas Colorado"
list=$list" Connecticut"
for state in $list
do   echo "Have you ever visited $state?"
done

# shell中的for数组
a=("Fdf" "df" "fd")
for str in ${a[@]};do
echo $str
done

# for循环遍历文件
for file in /path/*
do
   echo $file
done

# for循环遍历所有的参数
for param in "$@"
do     
  echo "\$@ Parameter #$count = $param"
  count=$[ $count + 1 ]
done

# for循环逐个处理参数匹配，处理选项
while [ -n "$1" ]
do
  case "$1" in￼
            -a) echo "Found the -a option" ;;￼
            -b) echo "Found the -b option" ;;￼
            -c) echo "Found the -c option" ;;￼
            *) echo "$1 is not an option" ;;￼
  esac    
  shift 
done
```


### linux中的信号
比如我们常见的kill -9 是就是无条件的kill
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301161756274.png)

Ctrl+C组合键会生成SIGINT信号，并将其发送给当前在shell中运行的所有进程。

Ctrl+Z组合键会生成SIGTSTP信号，停止shell中运行的任何进程。停止（stopping）进程跟终止（terminating）进程不同，前者让程序继续驻留在内存中，还能从上次停止的位置继续运行


### shell中的IFS
环境变量IFS（internal field separator，内部字段分隔符）。IFS环境变量定义了bash shell用作字段分隔符的一系列字符。在默认情况下，bash shell会将下列字符视为字段分隔符。
这种会造成在cat $FILE 作为列表时候导致分割符不正确,可以自行制定分隔符
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301131841768.png)

### shell中脚本读取传递的参数
- $0 文件自身
- $1 第一个参数
- $2 第二个参数
- $# 参数个数
- $@ 所有的参数

### shell中的函数
```shell
# 函数最好定义在脚本之前，这样就可以在后面进行调用
function func1 {
  tmp_str = "test str"
  echo "This is an example of a function"
}
  
# 函数的默认退出码，下面就是ls -l badfile 的执行结果作为返回码
function func1() {
  echo "trying to display a non-existent file"
  ls -l badfile
}

# 使用return 指定返回码
function dbl {
  read -p "Enter a value: " value
  echo "doubling the value"
  return $[ $value * 2 ]
}

# 向函数传递参数，直接按照$1 $2 来使用
function func7 {
  echo $[ $1 * $2 ]
}

# 函数中的局部变量，这个需要声明local，否则不生效
function func1 {
  tmp_str="test str"
  echo "This is an example of a function"
 }
echo $tmp_str
func1
echo $tmp_str

## 输出是：This is an example of a function
#         test str
# 可以看到这个默认是全局的

# 向函数传入数组
# 如果试图将数组变量作为函数参数进行传递，则函数只会提取数组变量的第一个元素
function addarray {
   local sum=0
   local newarray
   newarray=(`echo "$@"`)
   for value in ${newarray[*]}
   do
      sum=$[ $sum + $value ]
   done
   echo $sum
}
myarray=(1 2 3 4 5)
echo "The original array is: ${myarray[*]}"
arg1=$(echo ${myarray[*]})
result=$(addarray $arg1)
echo "The result is $result"
```

### 配置机器之间的免密登录
- 首先生成秘钥 ``` ssh-keygen -t rsa ```，之后这里会生成在用户家目录下的一个.ssh文件夹，主要包括下面的几个文件：id_rsa （私钥） 和  id_rsa.pub (公钥)
- 实现的原理就是把本机的公钥放在远程的服务器上，然后写入到远程的authorized_keys中
```shell
ssh-keygen -t rsa 
ssh-copy-id -i ~/.ssh/id_rsa.pub root@192.168.235.22
```
note: 如果存在相应的公钥可以直接发送，所以还是先去home目录下找下是否已经生成了公钥；实际是把公钥上传到远程，然后每次通信使用自己的私钥


### awk
```
# 按照切分的字段从第二个位置开始打印
cat 1.txt| awk -F '`' '{print $3}' |awk -F 'COMMENT ' '{print $1}' | awk '{for (i=2; i<=NF; i++) printf "%s ", $i; printf "\n"}'
```

### eval
执行命令
```
# 从文件中读取命令并逐行执行
while IFS= read -r line; do
    eval "$line"
done < commands.txt
```




