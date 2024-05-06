### 参考文档： 
- b站 https://www.bilibili.com/video/BV1rN411P74V/?spm_id_from=333.788&vd_source=d63883aafa94bea28d7963f231cca55b
- 官网：https://www.graphviz.org/

### 什么是graphviz
- 图论中的图的可视化的工具
- 通过dot语言，类似一个脚本描述图
- 不同的布局引擎
- 导出为不同的格式
- 在线工具 https://dreampuf.github.io/GraphvizOnline/

### dot语言 
- 定义无向图：`graph G { ... }    // 无向图`
- 定义有向图：`digraph G { ... }  // 有向图`
- 定义节点 `NodeA[color = blue,fontcolor = red,label="This is node A"]`
- 定义无向边 `NodeA -- NodeB  [label="Edge from A to B", color=blue];` 只能在无向图中
- 定义有向边 `NodeC->NodeD [label="Edge from A to B", color=blue];` 只能在有向图中使用
- 定义连续无向边 `NodeA -- NodeB --NodeC`
- 定义连续有向边 `m->n->j`
- 定义从节点的那个方向伸出来边 `Nodea:e->Nodeb:w`
- 定义边的颜色 `Q->k [color = red]`


### 一些常见的属性

1. 节点属性：  
> label: 节点的标签文本。  
> A [label="Node A"];  
> color: 节点的颜色。  
> A [color="red"];  
> shape: 节点的形状，例如 ellipse（默认）、circle、box、diamond 等。  
> A [shape="box"];  
> style: 节点的样式，例如 filled、dashed、dotted、bold 等。  
> A [style="filled"];  
> fillcolor: 当节点的样式设置为 filled 时，此属性确定填充颜色。  
> A [style="filled", fillcolor="yellow"];  
> fontsize: 节点标签的字体大小。  
> A [fontsize=16];  
2. 边属性：
> label: 边的标签文本。  
> A -> B [label="Edge from A to B"];   
> color: 边的颜色。  
> A -> B [color="blue"];   
> style: 边的样式，例如 dashed、dotted、bold 等。  
> A -> B [style="dashed"];  
> weight: 边的权重，该权重在布局图时会被考虑。较重的边会更短。  
> A -> B [weight=2];  
> dir: 边的方向。例如 forward（默认，有向边）、back、both、none。  
> A -> B [dir="both"];  
> arrowhead 和 arrowtail: 定义边的箭头的样式。例如 normal、inv、dot、odot、none 等。  
> A -> B [arrowhead="normal", arrowtail="dot"];  
3. 全局属性：
> 你还可以设置图、节点或边的默认属性。这些属性会应用于没有明确设置这些属性的所有图、节点或边。  
> graph [bgcolor="gray"];
> node [color="red", shape="ellipse"];  
> edge [color="blue"];  
> 在这个例子中，背景颜色被设置为灰色，所有节点默认为红色椭圆形，所有边默认为蓝色。

### Graphviz + dot文件 自动布局的，可生成svg图像 dot通过终端产出图片
https://jeanhwea.github.io/article/drawing-graphs-with-dot.html
https://zhuanlan.zhihu.com/p/644358139


### 子图


