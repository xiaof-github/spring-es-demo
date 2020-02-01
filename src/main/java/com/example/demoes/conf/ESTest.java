//package com.example.conf;
//
//import org.apache.http.Header;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
//import org.elasticsearch.client.*;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentType;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ESTest {
//
//    public static void main(String[] args) throws IOException {
//        String username = "admin";
//        String password = "admin123";
//        String hostname = "192.168.49.131";
//        int port = 9200;
//
//        final CredentialsProvider provider = new BasicCredentialsProvider();
//
//        provider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials(username, password));
//
//        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(hostname, port));
//
//        clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                httpClientBuilder.disableAuthCaching();
//                return httpClientBuilder.setDefaultCredentialsProvider(provider);
//            }
//        });
//        RestClient restClient = clientBuilder.build();
//
//        RestHighLevelClient highLevelClient = new RestHighLevelClient(clientBuilder);
//
//        Request request = new Request(
//                "GET",
//                "_cat/indices?v");
//        Response response = restClient.performRequest(request);
//        HttpHost host = response.getHost();
//        int statusCode = response.getStatusLine().getStatusCode();
//        Header[] headers = response.getHeaders();
//        String responseBody = EntityUtils.toString(response.getEntity());
//
//        String index = "es-demo-" + todayDate();
//
//        try {
//            indexCreate1(highLevelClient, index);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        restClient.close();
//        highLevelClient.close();
//    }
//
//    /**
//     * 验证索引是否存在
//     *
//     * @param index
//     *            索引名称
//     * @return
//     * @throws Exception
//     */
//    public static boolean indexExists(RestHighLevelClient client, String index) throws Exception {
//        GetIndexRequest request = new GetIndexRequest();
//        request.indices(index);
//        request.local(false);
//        request.humanReadable(true);
//
//        boolean exists = client.indices().exists(request);
//        return exists;
//    }
//
//    /**
//     *
//     * @param index
//     * @param indexType
//     * @param properties
//     *            结构: {name:{type:text}} {age:{type:integer}}
//     * @return
//     * @throws Exception
//     */
//    public static boolean indexCreate(RestHighLevelClient client, String index, String indexType,
//                               Map<String, Object> properties) throws Exception {
//
//        if (indexExists(client, index)) {
//            return true;
//        }
//        CreateIndexRequest request = new CreateIndexRequest(index);
//        request.settings(Settings.builder().put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 1));
//
//        Map<String, Object> jsonMap = new HashMap<>();
//        Map<String, Object> mapping = new HashMap<>();
//        mapping.put("properties", properties);
//        jsonMap.put(indexType, mapping);
//        request.mapping(indexType, jsonMap);
//
//        CreateIndexResponse createIndexResponse = client.indices().create(
//                request, RequestOptions.DEFAULT);
//        boolean acknowledged = createIndexResponse.isAcknowledged();
//        return acknowledged;
//    }
//
//    /**
//     *
//     * @param client
//     * @param index
//     * @return
//     * @throws Exception
//     */
//    public static boolean indexCreate1(RestHighLevelClient client, String index)
//                                throws Exception {
//        if (indexExists(client, index)) {
//            return true;
//        }
//
//        CreateIndexRequest request = new CreateIndexRequest(index);
//        request.settings(Settings.builder()
//                .put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 1)
//        );
//        request.mapping("_doc",
//                "{\n" +
//                        "  \"_doc\": {\n" +
//                        "    \"properties\": {\n" +
//                        "      \"message\": {\n" +
//                        "        \"type\": \"text\"\n" +
//                        "      }\n" +
//                        "    }\n" +
//                        "  }\n" +
//                        "}",
//                XContentType.JSON);
//        CreateIndexResponse createIndexResponse = client.indices().create(
//                request, RequestOptions.DEFAULT);
//        boolean acknowledged = createIndexResponse.isAcknowledged();
//        return acknowledged;
//    }
//
//    public static String todayDate() {
//        Date date=new Date();
//        System.out.println(date);
//        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY.MM.dd");//设置当前时间的格式，为年-月-日
//        System.out.println(dateFormat.format(date));
//        return dateFormat.format(date);
//    }
//
//}
//
