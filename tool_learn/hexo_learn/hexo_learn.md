### 这里记录简单的 hexo搭建的基本使用命令
- 使用前提
  - git 本地 ssh 远程成功
  - npm
  - node js
  - git 项目
  
### hexo 相关命令记录
```shell
npm install -g hexo-cli # 使用 npm 一键安装 Hexo 博客程序，mac可能需要sudo
hexo init      # 初始化一个博客
npm install    # 安装组件
hexo g         # 生成页面
hexo s         # 启动预览 之后在 http://localhost:4000/ 查看本地博客
npm install hexo-deployer-git --save # 之后
# 修改 _config.yml
deploy:
  type: git
  repository: git@github.com:用户名/用户名.github.io.git
  branch: master
# 之后hexo d 部署到github pages

# 访问 https://用户名.github.io 即可

```


### 主要写博客相关命令
``` shell
hexo new "My New Post" # 新的文章
hexo clean   # 清除缓存文件等
hexo g       # 生成页面
hexo s       # 启动预览
```


### 设置网站作者等
https://hexo.io/zh-cn/docs/configuration

### 更换主题
git clone https://github.com/iissnan/hexo-theme-next themes/next
之后修改 _config.yml 文件 theme: next
配置![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302101023067.png)

### 显示摘要信息
在md文件中按照 <!--more--> 来区分
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302101026322.png)

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302101027978.png)
