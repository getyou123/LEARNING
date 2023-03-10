package com.getyou123;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.client.RestClient;
import org.apache.http.HttpHost;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class CreateIndex {

    /**
     * 创建指定 副本数和分片数的 索引
     */
    @Test
    public void createIndexWithSettings() throws IOException {
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 索引名称
        String indexName = "example_index";
        // 分片数和副本数
        int numOfShards = 3;
        int numOfReplicas = 2;

        // 创建索引请求对象并设置分片和副本数量
        CreateIndexRequest request = new CreateIndexRequest(indexName)
                .settings(Settings.builder()
                        .put("index.number_of_shards", numOfShards)
                        .put("index.number_of_replicas", numOfReplicas)
                );

        // 发送创建索引请求并获取响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        // 打印索引创建是否成功
        boolean acknowledged = response.isAcknowledged();
        System.out.println("索引创建状态: " + acknowledged);

        // 关闭客户端连接
        client.close();
    }

    @Test
    public void createIndexWithMappings() throws IOException {
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );


        // 设置索引名称、映射和配置项
        String indexName = "example_index22";
        Settings.Builder settingsBuilder = Settings.builder()
                .put("number_of_shards", 3)
                .put("number_of_replicas", 1);

        XContentBuilder mappingBuilder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("mappings")
                        .startObject("properties")
                            .startObject("title").field("type", "text").endObject()
                            .startObject("description").field("type", "text").endObject()
                        .endObject()
                    .endObject()
                .endObject();

        // 创建索引请求对象，并设置名称、映射和配置项
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(settingsBuilder);
//        request.mapping(mappingBuilder.toString(), XContentType.JSON);
        request.source(mappingBuilder);

        // 发送创建请求，获取响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        // 检查请求是否成功
        boolean acknowledged = response.isAcknowledged();
        System.out.println("索引创建状态: " + acknowledged);

        // 关闭客户端连接
        client.close();
    }


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


    }
}
