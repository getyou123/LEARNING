
#  输出日期
date

# 日期获取年
date +%Y

# 日期时分秒
date "+%Y-%m-%d %H:%M:%S"

# 日期前一天
date -d '1 days ago'

# 输出字符串
echo hello

# 输出环境变量
echo $PATH

#
which echo

# 查看ip
ifconfig

# ip连通性
ping

# 本机host配置

pwd #显示当前工作目录的绝对路径

# 软链
# ln -s [原文件或目录] [软链接名]

# 历史命令
history

# 用户管理
# user add liming
# useradd -g 组名 用户名

# 更新用户的密码
# passwd 用户名

# 查看用户是否存在
# id liming
# uid=1001(hive) gid=1001(hive) groups=1001(hive)

# su liming 切换但是不获取环境变量
# su - liming 切换并获取环境变量

# 删除用户
# userdel liming 不删除家目录
# userdel -r  liming 删除家目录

# 修改用户
# usermod

# 修改文件和所有者
#chmod a+r file1.txt
#chmod 777 file1.txt
#chmod -R  ./file
#chown root /var/run/httpd.pid



# find 查找文件
find . -name "11.png"
find ./ -name '*.txt'
find . ! -name "*.png"


#grep


# 磁盘使用情况
# df -h 查看每个管挂载盘的剩余情况
# du -sh ./ --max-depth=1 # 查询一层的文件占用位置大小

# 查询进程相关
ps -ef

# top

# 端口
netstat -tunlp | grep

# grep 查询周围几行
grep -A 100 "keyword" ./1.txt

# 定时任务配置crontab
crontab -e # 配置
crontab -l # 列出定时任务