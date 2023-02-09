### es的需求背景
面向
非结构化数据存储和查询需求
- 数据格式
  - 结构化
  - 半结构化
  - 非结构化

es基于核心的工具包 Lucene，然后来提供服务框架搭建起来进行应用

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
- 索引Index 相当于库：ES中的索引和Lucene中的索引不是一一对应的。ES中的一个索引对应一个或多个Lucene索引，这是由其分布式的设计方案决定的。
- 类型Type 相当于表
- 文档Document 相当于行记录，就是实际的数据，文档ID不必须是一个数字，实际上它是一个字符串。ES文档操作使用了版本的概念，即文档的初始版本为1，每次的写操作会把文档的版本加1，每次使用文档时，ES返回给用户的是最新版本的文档
- 域Field 相当于列
- 映射：建立索引时需要定义文档的数据结构，这种结构叫作映射。在映射中，文档的字段类型一旦设定后就不能更改。因为字段类型在定义后，ES已经针对定义的类型建立了特定的索引结构，这种结构不能更改。借助映射可以给文档新增字段。另外，ES还提供了自动映射功能，即在添加数据时，如果该字段没有定义类型，ES会根据用户提供的该字段的真实数据来猜测可能的类型，从而自动进行字段类型的定义
  - 最好还是在创建时候就定义好文档的数据结构

![和关系型数据库的对应关系](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071341462.png)

note:type的概念已经被弱化，就是说一个index下就直接存储数据了，或者index下只有一个type _doc https://www.elastic.co/guide/en/elasticsearch/reference/7.17/removal-of-types.html

### Es和RDBMS的对比
- 索引：倒排索引 B-tree
- 事务支持：不支持和支持
- SQL DSL
- 扩展：分库分表 方便扩展

### es应用场景
- 搜索引擎
  - 百度 360 谷歌
- 站内搜索
  - 微博 论坛 电商
- 推荐系统
- 日志分析


### 倒排索引，详见[Lucene_learn.md](..%2FLucene_learn%2FLucene_learn.md)


### kibana dev tool操作es
#### 创建索引
```
PUT /hotel
{
    "mappings":{
        "properties":{
            "title":{
                "type":"text"
            },
            "city":{
                "type":"keyword"
            },
            "price":{
                "type":"double"
            }
        }
    }
}
```
![如果索引已经存在](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071739363.png)
![创建成功的索引](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071740117.png)

#### 写入文档
``` 
POST /hotel/_doc/001
{
"title":"好再来酒店",
"city":"青岛",
"price":578.23
}
```

![写入成功](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071742508.png)

post这个多次写是幂等的


#### 根据_id 查询文档
``` 
GET /hotel/_doc/001
```
![查询得到的](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071748946.png)
![查询不到的](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071746824.png)

#### 获取所有的索引
``` 
GET _cat/indices?v
```
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071738603.png)


#### 按照一般的字段进行查询
``` 
## 搜索需要使用到query语句
GET /hotel/_search
{
    "query":{
        "term":{
            "price":{
                "value":578.23
            }
        }
    }
}
```

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071808059.png)


#### 按照某个字段进行模糊查找
``` 
# 这里match是模糊匹配
GET /hotel/_search
{
    "query":{
        "match":{
                "title":"再来"
        }
    }
}
```
![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071811139.png)

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



### es_search 中的java客户端
java客户端分为两种
- 低级客户端：兼容所有版本的ES，但是需要提供json串，效率低
- 高级客户端：屏蔽了细节，官方推荐的开发方式


### [HelloElasticSearch.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2FHelloElasticSearch.java)



