## 学习和使用github 主要是学习《github入门和实践》

### pull request
- Pull Request是指开发者在本地对源代码进行更改后，向GitHub中托管的Git仓库请求合并的功能；
- Pull Request是自己修改源代码后，请求对方仓库采纳该修改时采取的一种行为
- 在GitHub上发送Pull Request后，接收方的仓库会创建一个附带源代码的Issue，我们在这个Issue中记录详细内容。这就是Pull Request。
- 只要Pull Request被顺利采纳，我们就会成为这个项目的Contributor（贡献者），我们编写的这段代码也将被全世界的人使用。
- pr不仅仅在自己的远程仓库和别人的远程仓库之间可以，同一个仓库的各个分支之间也是可以的

主要的工作流如下所示：
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301181130853.png)
1. fork其他人的远程仓库到自己的远程仓库A，这个在web端操作
2. git fetch拉取自己远程仓库A到本地，从本地新建一个特性分支，git branch -b origin/XXX feature_a
3. 在feature_a分支上修改（edit -> git add -> git commit）并推送(git push -u origin feature_a)到远程仓库 feature_a 分支
4. 在web端自己的远程分支上提交pr给远端other

note：关于本地产出新的特性分支的原因，在GitHub上发送Pull Request时，一般都是发送特性分支。这样一来，Pull Request就拥有了更明确的特性（主题）。让对方了解自己修改代码的意图，有助于提高代码审查的效率。

pr的提交尽量早，这样的话可以避免自己需要全部修改之后才去完整的提交，避免大量的代码更改；只要在想发起讨论时发送Pull Request即可，不必等代码最终完成。即便某个功能尚在开发之中，只要在Pull Request中附带一段简单代码让大家有个大体印象，就能获取不少反馈；

pr不仅仅在多个仓库之间，也可以在同一个仓库的多个分支之间
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301181355724.png) 一个仓库的pr_test提交pr到master分支

### Issue

#### 社会化编程
即：接触核心代码内容不是某些人的特权，即使是杠杆接触编程的小白也能接触和学习世界上最优秀的代码，并以此为教材进行学习。

### Wiki


### 创建仓库的点
- 名称
- 简介
- 私有、公共
- 初始化readme.md
- 是否增加ignore文件
- 许可协议：即便在GitHub上公开了源代码，也不代表著作者放弃了著作权等权利

### github中的社区功能-follow
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301180955919.png) 这个就是关注了某人，然后看他们在github上活动


### github删除项目
- 点到项目的settings ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301180949678.png)
- 划到最下面 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301180950442.png) 执行删除
- 输入项目名称进行确认删除 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301180951133.png)
- 执行结果 ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301180952544.png)


### github修改密码
- 个人页进入settings![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301181022847.png)

### github flow流程（当然有专门的git_flow工具可以使用）

1. 令master分支时常保持可以部署的状态
2. 进行新的作业时要从master分支创建新分支，新分支名称要具有描述性
3. 在❷新建的本地仓库分支中进行提交
4. 在GitHub端仓库创建同名分支，定期push
5. 需要帮助或反馈时创建Pull Request，以Pull Request进行交流
6. 让其他开发者进行审查，确认作业完成后与master分支合并
7. 与master分支合并后立刻部署

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202301181441909.png)

其中的主要注意的点是： 
- 必须要时刻保证master的可部署； 
- 每次增加或者修改代码都是新产出一个分支； 
- pr 不一定要完成时才提交； 
- 务必使用其他人来进行review
- 减少pr的体积，要做到功能细分 & 结对编程 & 不要积攒pr
- 必须要有测试代码