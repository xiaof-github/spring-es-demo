package com.example.demoes;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class DemoEsTests {

    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    RestClient restClient;

    @Test
    public void creatIndex() throws IOException {
        String index = "es-demo1-" + todayDate();

        try {
            indexCreate1(restHighLevelClient, index);
        } catch (Exception e){
            e.printStackTrace();
        }


        restClient.close();
        restHighLevelClient.close();



    }


    /**
     * 验证索引是否存在
     *
     * @param index
     *            索引名称
     * @return
     * @throws Exception
     */
    public boolean indexExists(RestHighLevelClient client, String index) throws Exception {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        request.local(false);
        request.humanReadable(true);

        boolean exists = client.indices().exists(request);
        return exists;
    }

    /**
     *
     * @param index
     * @param indexType
     * @param properties
     *            结构: {name:{type:text}} {age:{type:integer}}
     * @return
     * @throws Exception
     */
    public boolean indexCreate(RestHighLevelClient client, String index, String indexType,
                                      Map<String, Object> properties) throws Exception {

        if (indexExists(client, index)) {
            return true;
        }
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 1));

        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        jsonMap.put(indexType, mapping);
        request.mapping(indexType, jsonMap);

        CreateIndexResponse createIndexResponse = client.indices().create(
                request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        return acknowledged;
    }

    /**
     *
     * @param client
     * @param index
     * @return
     * @throws Exception
     */
    public boolean indexCreate1(RestHighLevelClient client, String index)
            throws Exception {
        if (indexExists(client, index)) {
            return true;
        }

        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 1)
        );
        request.mapping("_doc",
                "{\n" +
                        "  \"_doc\": {\n" +
                        "    \"properties\": {\n" +
                        "      \"message\": {\n" +
                        "        \"type\": \"text\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);
        CreateIndexResponse createIndexResponse = client.indices().create(
                request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        return acknowledged;
    }

    public String todayDate() {
        Date date=new Date();
        System.out.println(date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY.MM.dd");//设置当前时间的格式，为年-月-日
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
}