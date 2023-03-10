###    

- 创建索引
- 创建索引-指定副本数和分片数
- 删除索引
- 查询索引结构
- 写入文档-按照文档id
- 获取所有的索引
- 根acknowledged据_id 查询文档
- 按照一般的字段进行查询
- 按照某个字段进行模糊查找
- 查询索引内的全部数据
- 完全覆盖单个文档数据
- 更新文档数据中的部分字段
- 分页查询
- 指定查出的列
- 排序
- 按照某个字段精确查询（完全匹配）

### es的需求背景

面向
非结构化数据存储和查询需求

- 数据格式
    - 结构化
    - 半结构化
    - 非结构化

es基于核心的工具包 Lucene，然后来提供服务框架搭建起来进行应用

### Es的技术选型，使用es的原因

The Elastic Stack, 包括 Elasticsearch、 Kibana、 Beats 和 Logstash（也称为 ELK
Stack）。能够安全可靠地获取任何来源、任何格式的数据，然后实时地对数据进行搜索、分析和可视化。

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
- 类型Type 相当于表，不过现在几乎不用了
- 文档Document 相当于行记录，就是实际的数据，文档ID不必须是一个数字，实际上它是一个字符串。ES文档操作使用了版本的概念，即文档的初始版本为1，每次的写操作会把文档的版本加1，每次使用文档时，ES返回给用户的是最新版本的文档
- 映射：建立索引时需要定义文档的数据结构，这种结构叫作映射。在映射中，文档的字段类型一旦设定后就不能更改。因为字段类型在定义后，ES已经针对定义的类型建立了特定的索引结构，这种结构不能更改。借助映射可以给文档新增字段。另外，ES还提供了自动映射功能，即在添加数据时，如果该字段没有定义类型，ES会根据用户提供的该字段的真实数据来猜测可能的类型，从而自动进行字段类型的定义
    - 最好还是在创建时候就定义好文档的数据结构
- 域Field 相当于列
- 分片：类似mysql中的分表
- 副本：高可用


![和关系型数据库的对应关系](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071341462.png)

note:type的概念已经被弱化，就是说一个index下就直接存储数据了，或者index下只有一个type _
doc https://www.elastic.co/guide/en/elasticsearch/reference/7.17/removal-of-types.html

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

指定创建索引时候的副本等信息

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
    },
    "settings": {
    "index": {
      "number_of_shards": "3",
      "number_of_replicas": "3"
    }
  }
}
```

### 删除索引

DELETE /es_test_0818_hgw

#### 写入文档-按照文档id

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

### 查询索引内的所有的数据

```
GET /hotel/_search
```

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303091048998.png)

### 更新文档数据中的部分字段

使用post而不是put

```
POST hotel/_update/1
{
  "doc": {
    "title": "New title"
  }
}
```

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202303091052176.png)

### 完全覆盖单个文档数据

使用put

```
PUT /hotel/_doc/002
{
"title":"内蒙古酒店1",
"city":"河北",
"price":530
}
```

### 分页查找

```
GET /hotel/_search
{
    "query":{
        "match":{
                "title":"酒店"
        }
    },
    "from":0,
    "size":2
}
```

from 是针对每条数据的编码，不是针对页码的编号 (page_num-1)*page_size

### 指定查出的列

```
{
  "query": {
    "match":{
      "title": "小米手机"
    }
  },
  "_source":"price"
}
```

通过 "_source":"price" 来指定查询的列

### 排序

```
GET  /hotel/_search
{
  "query": {
    "match":{
      "title": "酒店"
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

### 按照某个字段精确查询（完全匹配）

```
GET my-index/_search
{
  "query": {
    "term": {
      "name": "John Smith"
    }
  }
}

GET my-index/_search
   {
     "query": {
       "terms": {
         "city": ["New York", "Chicago"]
       }
     }
   }
```

### 聚合操作

```
全表加和
GET /hotel/_search
{
    "aggs": {
        "total_price": {
            "sum": {
                "field": "price"
            }
        }
    }
}

```

### es索引的 _mapping

- 这个就是es中的文档数据的格式，可在建立索引时候指定

```
# 获取索引的mappings
GET /example_index/_mappings
```

### es索引的_settings

主要是指定了分片数，副本数等，可以在建立索引时候指定

```
# 获取索引的settings
GET /example_index/_settings


```

###   

### es_search 中的java客户端

java客户端分为两种

- 低级客户端：兼容所有版本的ES，但是需要提供json串，效率低
- 高级客户端：屏蔽了细节，官方推荐的开发方式

### java api

- 获取client [HelloElasticSearch.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2FHelloElasticSearch.java)
- 创建索引 [CreateIndex.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2FCreateIndex.java)
- 查询所有的索引 [SearchIndex.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2FSearchIndex.java)
- 删除指定的索引 [DeleteIndex.java](src%2Fmain%2Fjava%2Fcom%2Fgetyou123%2FDeleteIndex.java)

### es 集群和节点

- 集群高可用，多个节点组成
- 集群名称使得多个节点组成一个集群，节点自己就加入到了集群中
- 就是单点机器 `elasticsearch.yml` 文件，用于指定节点名称，网络地址，端口等。可以在配置文件中指定集群名称等信息，以便所有节点能够互相发现和加入。


### 系统架构
