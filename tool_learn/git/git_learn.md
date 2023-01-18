## 什么是版本控制：
版本控制系统（version control system，VCS）提供了一个公共中央位置来存储和合并工程文件。
是一种记录一个或若干文件内容变化，以便将来查阅特定版本修订情况的系统

### 图记
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301180939499.png)

### 分散式版本控制器和集中式版本控制器
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171514687.png)
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171515498.png)

### git的基础和原理
git存储的文件的快照版本，其实就是文件的状态快照，所以如何理解下面的图呢：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171635125.png)
里面的节点就是一个文件快照，然后分支其实是指针，指向不同的快照，git 记录的不是差异的比较，而是快照

### git的四个区域
- 工作区域
- 暂存区 gid add可以加入到暂存区，实现文件的追踪
- 远程仓库 快照的存储位置，通过gid commit -m 加入到本地仓库
- 本地仓库 云端的数据存储位置
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171436549.png)


### git几个概念关系
- 分支
- clone： git clone 是从远程下载到本地
- fork： 远程仓库到远程仓库，这个一般在webui上进行操作、

### 命令总结

```shell
初始化仓库 git init 这个其实就是生成了.git 文件夹
查看仓库的状态 git status 
开启文件的追踪,添加到暂存区 git add file.txt
进行文件的提交 git commit -m "Initial Commit" 如果是需要大量的记录的信息的话还是不加 -m 启动编辑器来编辑比较好
查看git的提交日志 git log 或者是 git log --pretty=short
添加远程仓库地址 git remote add <shortname> <url>
检查远程仓库地址,可能不止一个 git remote -v 
推送分支到远程仓库 git push [origin] [my_branch]
查看工作区和暂存区的所有文件差异 git diff
查看某个文件在工作区和暂存区的差异 git diff -- file.txt
查看多个文件的工作区和暂存区的差异 git diff -- file1 file2 file3
查看工作区的某个文件和版本库的差异 git diff XXX -- file 可以看出工作区的名字是可以省略的
查看查看版本库之间的文件差异 git diff XXX1 XXX2 -- file
查看暂存区和版本库的文件差异 git diff --cacheed -- file
查看所有的分支 git branch 其中结果的*表示了现在所在的分支
查看远程分支 git branch -v
查看所有的分支 git branch -a
切换分支 git checkout master
分支合并 git merge master 前提是转到需要merge的结果分支上
重置和回滚 git reset --hard XXX 保持本地仓库 暂存区 工作区都拉齐
修改提交的信息 git commit --amend
删除并提交这个变化 git rm file
移除对于文件的追踪 git rm --cached file.txt
从远程仓库中抓取与拉取 git fetch orgin, 命令会将数据拉取到本地仓库,它并不会自动合并或修改当前的工作
git标签 git tag -a tagName -m "my tag" 这个是带comment的标签,本地打完标签之后再推送到远程
查看git的标签 git tag -l
删除标签 git tag -d XXX
创建分支 git branch <branch name>
删除分支 git branch -D <branch name>
拉取远程分支并创建本地分支 git checkout -b 本地分支名x origin/远程分支名x
查看本地分支和远程分支的追踪关系 git branch -vv
修改本地分支和远程分支的对应关系 git branch --set-upstream-to origin/分支名
拉取 git pull 这个底层是 git fetch + git merge 
并将master分支重置为develop git reset --hard develop  强制转本地分支为develop,然后可以强制进行提交远程 git push origin master --force
```



### git消除冲突
git merge如果出现冲突的情况，需要进行手动的merge，执行merge命令之后会出现
```shell
<<<<<<< HEAD
  - feature-A
  =======
    - fix-B
>>>>>>> 
     fix-B
```
note:
======= 以上的部分是当前HEAD的内容，以下的部分是要合并的fix-B分支中的内容；
我们需要手动进行修复，然后更新为正确的，然后add 提交 commit


### git stash
- 开发到一半，同步远端的代码，此时代码不想提交，
- 工作流被打断,需要先做别的需求
```shell
git stash
git pull
git stash pop
```
note：这个主要是在工作空间进行的操作，暂时隐藏了工作空间，然后又弹出栈![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171742603.png)

### git ignore
添加[.gitignore](..%2F..%2F.gitignore) 文件，然后进行配置哪些文件进行忽略，之后git add .gitignore,git commit
 -m 'git ignore'，当然可以使用idea安装相关的插件

### git rebase 
https://www.yiibai.com/git/git_rebase.html
- 开始状态是 

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171804765.png)
- 然后其他协作者做了跟新并推进，自己分支也做了推进 
 
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171806277.png)

- 之后的处理方式有两种： 要么在mywork分支上直接merge origin ； 要么使用rebase
  ![git rebase origin之后](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171810742.png)
  ![git merge origin之后](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171811223.png)

### git pull 
一个git pull 底层其实是git fetch + git merge
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301171801408.png)一般来说不建议无脑的使用git pull，都是先git fetch 然后git diff 然后再git merge