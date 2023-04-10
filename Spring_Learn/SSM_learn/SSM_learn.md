### 整合记录
1. 梳理 mybatis的工作：
   - 核心就是作为DAO层，持久层，从Controller到service 到 DAO，原来是需要从
   - 这个框架工作是需要的一个配置文件的 [mybatis-config.xml](src%2Fmain%2Fresources%2Fmybatis-config.xml) 配置了分页helper和驼峰的映射文件形式
   - mybatis-config.xml中指定库表链接 然后配置                                                