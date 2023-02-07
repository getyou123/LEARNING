### es的需求背景
面向
非结构化数据存储和查询需求
- 数据格式
  - 结构化
  - 半结构化
  - 非结构化

### Es的技术选型，使用es的原因
The Elastic Stack, 包括 Elasticsearch、 Kibana、 Beats 和 Logstash（也称为 ELK Stack）。能够安全可靠地获取任何来源、任何格式的数据，然后实时地对数据进行搜索、分析和可视化。
* ELK的技术核心栈为ES：
* 基于Lucene的
* 可扩展
* 分析友好


### es的端口
- 9200 web访问页面端口
- 9300 es内部通信端口

### Es中的核心概念
- 面向文档型，一条数据就是一个文档Document，下面展示和关系型数据库的对应关系
- 索引Index 相当于库
- 类型Type 相当于表
- 文档Document 相当于行
- 域Field 相当于列

note:type的概念已经被弱化，就是说一个index下就直接存储数据了

### 倒排索引
- 正向索引：文档ID->文档内容
- 倒排索引：从内容词->文档ID

### 创建索引

### 使用Postman作为client
- 在postman新建collection
- 使用Postman创建索引 put
  - ``` http://127.0.0.1:9200/shopping ```
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061812607.png)
- 获取指定索引信息 get
  - ``` http://127.0.0.1:9200/shopping ```
  - ![找到的话](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061813099.png)
  - ![找不到的话](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061819147.png)
- 获取所有的索引信息 get
  - http://127.0.0.1:9200/_cat/indices?v
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061816539.png)
- 删除指定索引 DELETE
  - ``` http://127.0.0.1:9200/shopping ```
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061818147.png)
- 向索引中添加数据 POST
  - ![上传参数设置](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061825333.png)
  - ![完成上传操作](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061826701.png)
- 自己来定义写入的doc_id POST
  - ``` http://127.0.0.1:9200/shopping/_doc/1001 ```
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061830424.png)
- 通过主键查询文档 get
  - ```http://127.0.0.1:9200/shopping/_doc/1001```
  - ![查询成功](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061831566.png)
  - ![查询失败](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061833128.png)
- 查询所有的数据 get
  - ```http://127.0.0.1:9200/shopping/_search```
  - ![获取es index中的所有数据](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302061834244.png)
- 修改数据
  - 完全覆盖是幂等的 put ``` http://127.0.0.1:9200/shopping/_doc/1001 ```  返回结果是updated ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302070943417.png)
  - 局部修改   post  ``` http://127.0.0.1:9200/shopping/_update/1001   ```
  ```  
  { 
    "doc": {
    "price":499.00
    }
  }
  ```
    - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302070949667.png)
- 条件查询
  - get http://127.0.0.1:9200/shopping/_search?q=price:499.00 这种的直接写的
  - get http://127.0.0.1:9200/shopping/_search + query中的body  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071005402.png) 
  - 不知道为啥postman不支持get中包含body内容，正常是不支持请求体的
```
{
  "query": {
    "match":{
      "title": "小米手机"
    }
  }
}
```

- 条件查询-分页查询
  - post http://127.0.0.1:9200/shopping/_search + query中的body  ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071005402.png)
  - 不知道为啥postman不支持get中包含body内容，正常是不支持请求体的
```
{
  "query": {
    "match":{
      "title": "小米手机"
    }
  },
  "from":0,    
  "size":2
}
``` 
  - from 是针对每条数据的编码，不是针对页码的编号 (page_num-1)*page_size
  - ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071018730.png)

- 指定查出的列
```
{
  "query": {
    "match":{
      "title": "小米手机"
    }
  },
  "from":0,    
  "size":2,
  "_source":"price"
}
```

- 排序
```
{
  "query": {
    "match":{
      "title": "小米手机"
    }
  },
  "from":0,    
  "size":2,
  "_source":"price",
  "sort":{
      "price":{
          "order":"desc"
      }
   }
}
```

- 组合条件进行查询
```json
{
　　"query": {
　　　　"bool": {
　　　　　　"must": { "match": { "name": "tom" }},
　　　　　　"should": [
　　　　　　　　{ "match": { "hired": true }},
　　　　　　　　{ "bool": {
　　　　　　　　　　"must": { "match": { "personality": "good" }},
　　　　　　　　　　"must_not": { "match": { "rude": true }}
　　　　　　　　}}
　　　　　　],
　　　　　　"minimum_should_match": 1
　　　　}
　　}
}
```

- 过滤
- 完全匹配
- 高亮字段
- 聚合查询


### es中的映射 _mapping



### es_search 中的java api


### [HelloElasticSearch.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2FHelloElasticSearch.java)



