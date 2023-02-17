package com.getyou123;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class CreateIndex {
    public static void main(String[] args) throws IOException {
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        // 创建索引 - 请求对象
        CreateIndexRequest request = new CreateIndexRequest("user");

        // 发送请求，获取响应
        CreateIndexResponse response = client.indices().create(request,
                RequestOptions.DEFAULT);

        // 获取其中response响应状态
        boolean acknowledged = response.isAcknowledged();

        System.out.println("操作状态 = " + acknowledged);

        // 关闭客户端连接
        client.close();


        /**
         * @TODO
         * 这里存在报错信息
         * 二月 07, 2023 11:40:11 上午 org.elasticsearch.client.RestClient logResponse
         * 警告: request [PUT http://localhost:9200/user?master_timeout=30s&include_type_name=true&timeout=30s] returned 1 warnings: [299 Elasticsearch-7.8.0-757314695644ea9a1dc2fecd26d1a43856725e65 "[types removal] Using include_type_name in create index requests is deprecated. The parameter will be removed in the next major version."]
         * Exception in thread "main" java.lang.BootstrapMethodError: call site initialization exception
         * 	at java.lang.invoke.CallSite.makeSite(CallSite.java:341)
         */

    }
}
