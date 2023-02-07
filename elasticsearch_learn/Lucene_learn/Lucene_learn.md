### Lucene 学习笔记
Lucene 只是一个工具包，它不是一个完整的全文检索引擎。Lucene 的目的是为软件开发人员提供一个简单易用的工具包，以方便的在目标系统中实现全文检索的功能，或者是以此为基础建立起完整的全文检索引擎。

### 全文检索
数据分类和查询
- 结构化数据：常见的数据库中的数据，sql就可以实现查询
- 非结构化数据：word文档，html等格式不是很固定的；这个的查询工作就是需要其他技术的支持

全文实际就是通过建立倒排索引实现转为结构化数据的查询来


### 倒排索引
Apache Lucene将所有信息写到一个称为倒排索引（inverted index ）的结构中。不同于关系型数据库中表的处理方式，倒排素引建立索引中词和文档之间的映射。你可以把倒排索引看成这样一种数据结构，其中的数据是面向词而不是面向文档的。eg： 有一些文档，只有它们的标题字段需要被索引：
![文档](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071350803.png)

![倒排索引的样式](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071350474.png)
- 正向索引：文档ID->文档内
- 倒排索引：从内容词->文档ID

![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071441154.png)

其中主要有如下几个核心术语需要理解：
- 词条：索引里面最小的存储和查询单元，对于英文来说是一个单词，对于中文来说一般指分词后的一个词。
- 词典：（Term Dictionary）：或字典，是词条 Term 的集合。搜索引擎的通常索引单位是单词，单词词典是由文档集合中出现过的所有单词构成的字符串集合，单词词典内每条索引项记载单词本身的一些信息以及指向“倒排列表”的指针。
- 倒排表（Post list
- 倒排文件（Inverted File）

整个倒排索引表就是词典和倒排文件组成的，词典在内存中，倒排文件在磁盘上

### Lucene的实现全文索引的过程
- 获取文档
- 构建文档对象（document）
  - 一个文档中有多个filed，主要是name 和 value
  - 每个文档具有唯一的id
- 分析文档
  - 分词
  - 大小写处理
  - 处理符号
  - 处理停词
  - 每个关键词被封装一个Term
- 创建索引：
  - 保存到索引库中
  - 倒排索引结构（从词到文档，所以是倒排）
- 查询索引：
  - 把查询的词封装为一个查询对象
  - 执行查询
  - 渲染结果

### 构建一个索引
```
    @Test
    public void testCreate() throws Exception{
        //1 创建文档对象
        Document document = new Document();

        // 创建并添加字段信息。参数：字段的名称、字段的值、是否存储，这里选Store.YES代表存储到文档列表。Store.NO代表不存储
        document.add(new StringField("id", "1", Field.Store.YES));

        // 这里我们title字段需要用TextField，即创建索引又会被分词。StringField会创建索引，但是不会被分词
        document.add(new TextField("title","谷歌地图之父跳槽facebook",Field.Store.YES));

        //2 索引目录类,指定索引在硬盘中的位置
        Directory directory = FSDirectory.open(new File("/Users/XXXX/Desktop/indexDir"));

        // Lucene5.0.0以上
        //Directory directory = FSDirectory.open(Paths.get("/Users/XXX/Desktop/indexDir"));

        //3 创建分词器对象
        //Analyzer analyzer = new StandardAnalyzer();

        Analyzer analyzer = new IKAnalyzer();

        //4 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST,analyzer);

        //5 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter indexWriter = new IndexWriter(directory,conf);

        //6 把文档交给IndexWriter
        indexWriter.addDocument(document);

        //7 提交
        indexWriter.commit();
        //8 关闭
        indexWriter.close();
    }
```

### 通过lukeall包打开观察索引文件
记住你的lukeall的版本和lucene的版本要一致不然打不开，会出问题
- 从这里下载 https://github.com/DmitryKey/luke/releases
- ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071518590.png)
- Document的概念不在赘述，它是Field的载体，Field是term的载体（term有field+分词来定）
- Document中的每个字段都是一个Field，这里展示所有的出现的Field ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071529577.png)
- 可以看出不同的filed中相同的词是不同的term ![](https://raw.githubusercontent.com/getyou123/git_pic_use/master/zz202302071528931.png)
- term默认按照词频进行排序，如果分词产出的term不存在，即这个词条不存在的话是不可能被查出的
  - 比如 "进行排序"这句话要是不进行分词，那么只能在搜索"进行排序"时候才能搜出来，搜"进行"是搜不出来的 这个在代码中也是有展示的

### 如何设置进行分词呢？
- 其实就是去设置Document的字段类型：
  - DoubleField、FloatField、IntField、LongField、StringField、TextField，这些子类一定会被创建索引，但是不会被分词，而且不一定会被存储到文档列表
  - TextField即创建索引，又会被分词。
  - StringField会创建索引，但是不会被分词，如果不分词，会造成整个字段作为一个词条，除非用户完全匹配，否则搜索不到
  - 、、、

### 如何确定一个Field是否要索引，存储，分词
- 如何确定一个字段是否需要存储？
  - 如果一个字段要显示到最终的结果中，那么一定要存储，否则就不存储
- 如何确定一个字段是否需要创建索引？
  - 如果要根据这个字段进行搜索，那么这个字段就必须创建索引。
- 如何确定一个字段是否需要分词？
  - 前提是这个字段首先要创建索引。然后如果这个字段的值是不可分割的，那么就不需要分词。例如：ID


