package com.getyou123;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class DeleteIndex {
    public static void main(String[] args) throws IOException {
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        System.out.println(client);


        DeleteIndexRequest userDelReq = new DeleteIndexRequest("user");
        AcknowledgedResponse deleteResponse = client.indices().delete(userDelReq, RequestOptions.DEFAULT);
        boolean acknowledged = deleteResponse.isAcknowledged();

        System.out.println("删除状态：" + acknowledged);

        // 关闭客户端连接
        client.close();
    }
}
