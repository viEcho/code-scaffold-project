package com.es.sample.controller;

import cn.hutool.json.JSONUtil;
import com.base.common.vo.ResponseVO;
import com.es.sample.po.EsBook;
import com.es.sample.repository.EsBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * es积垢测试控制器
 *
 * @author echo
 * @date 2024/01/27
 */
@RestController
@RequestMapping("/esTest")
@Slf4j
public class EsCrudTestController {

    /**
     * es客户端
     */
    @Autowired
    private RestHighLevelClient esClient;

    /**
     * 创建索引
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/index/createIndexByClient")
    public ResponseVO createIndexByClient() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("test_index_books_client");
        //2.客户端执行请求IndicesClient，执行create方法创建索引，请求后获得响应
        CreateIndexResponse response =
                esClient.indices().create(request, RequestOptions.DEFAULT);
        return ResponseVO.success().data(response);
    }

    /**
     * 查询索引
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/index/searchIndexByClient")
    public ResponseVO searchIndexByClient() throws IOException {
        //1.查询索引请求
        GetIndexRequest request = new GetIndexRequest("test_index_books_client");
        //2.执行exists方法判断是否存在
        boolean exists = esClient.indices().exists(request, RequestOptions.DEFAULT);
        return ResponseVO.success().data(exists);
    }

    /**
     * 删除索引
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/index/delIndexByClient")
    public ResponseVO delIndexByClient() throws IOException {
        //1.删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("test_index_books_client");
        //执行delete方法删除指定索引
        AcknowledgedResponse delete = esClient.indices().delete(request, RequestOptions.DEFAULT);
        return ResponseVO.success().data(delete);
    }


    /**
     * 新增文档
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/document/addByClient")
    public ResponseVO addByClient() throws IOException {
        //1.创建对象
        EsBook user = new EsBook("0","java程序设计","中文","echo",27.5f,"上岸");
        //2.创建请求
        IndexRequest request = new IndexRequest("test_index_books_client");
        //3.设置规则 PUT /ljx666/_doc/1
        //设置文档id=6，设置超时=1s等，不设置会使用默认的
        //同时支持链式编程如 request.id("6").timeout("1s");
        request.id("6");
        request.timeout("1s");

        //4.将数据放入请求，要将对象转化为json格式
        //XContentType.JSON，告诉它传的数据是JSON类型
        request.source(JSONUtil.toJsonStr(user), XContentType.JSON);

        //5.客户端发送请求，获取响应结果
        IndexResponse indexResponse = esClient.index(request, RequestOptions.DEFAULT);
        return new ResponseVO(indexResponse);
    }


    /**
     * 获取文档中的数据
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/document/getByClient")
    public ResponseVO getByClient() throws IOException {
        //1.创建请求,指定索引、文档id
        GetRequest request = new GetRequest("test_index_books_client", "1");
        GetResponse getResponse = esClient.get(request, RequestOptions.DEFAULT);
        System.out.println(getResponse);//获取响应结果
        //getResponse.getSource() 返回的是Map集合
        System.out.println(getResponse.getSourceAsString());//获取响应结果source中内容，转化为字符串
        return new ResponseVO(getResponse);
    }

    /**
     * 更新文档数据
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/document/updateByClient")
    public ResponseVO updateByClient() throws IOException {
        //1.创建请求,指定索引、文档id
        UpdateRequest request = new UpdateRequest("test_index_books_client", "1");
        EsBook user = new EsBook("1","java程序设计","中文","echo",27.5f,"上岸");
        //将创建的对象放入文档中
        request.doc(JSONUtil.toJsonStr(user), XContentType.JSON);
        UpdateResponse updateResponse = esClient.update(request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());//更新成功返回OK
        return new ResponseVO(updateResponse);
    }


    /**
     * 删除文档数据
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/document/deleteByEsClient")
    public ResponseVO deleteByEsClient() throws IOException {
        //1.创建删除文档请求
        DeleteRequest request = new DeleteRequest("test_index_books_client", "2");
        DeleteResponse deleteResponse = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());//更新成功返回OK
        return new ResponseVO(deleteResponse);
    }

    /**
     * 批量新增文档数据
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/document/addBatchByClient")
    public ResponseVO addBatchByClient() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        //设置超时
        bulkRequest.timeout("10s");

        List<EsBook> esBookList = new ArrayList<>();
        esBookList.add(new EsBook("1","java程序设计","中文","echo",27.5f,"上岸"));
        esBookList.add(new EsBook("2","java程序设计1","中文","echo1",27.5f,"上岸"));
        esBookList.add(new EsBook("3","java程序设计2","中文","echo2",27.5f,"上岸"));
        esBookList.add(new EsBook("4","java程序设计3","中文","echo3",27.5f,"上岸"));

        int id = 1;
        //批量处理请求
        for (EsBook book : esBookList) {
            //不设置id会生成随机id
            bulkRequest.add(new IndexRequest("test_index_books_client")
                    .id("" + (id++))
                    .source(JSONUtil.toJsonStr(book), XContentType.JSON));
        }

        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());//是否执行失败,false为执行成功
        return new ResponseVO(bulkResponse);
    }

    /**
     * 查询所有、模糊查询、分页查询、排序、高亮显示
     * @return
     * @throws IOException
     */
    @GetMapping("/testQuery")
    public ResponseVO testQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("test_index_books_client");//里面可以放多个索引
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();//构造搜索条件
        //此处可以使用QueryBuilders工具类中的方法
        //1.查询所有
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //2.查询name中含有Java的
        sourceBuilder.query(QueryBuilders.multiMatchQuery("java", "userName"));
        //3.分页查询
        sourceBuilder.from(0).size(5);
        //4.按照score正序排列
        sourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.ASC));
        //5.按照id倒序排列（score会失效返回NaN）
        sourceBuilder.sort(SortBuilders.fieldSort("_id").order(SortOrder.DESC));
        //6.给指定字段加上指定高亮样式
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("userName").preTags("<span style='color:red;'>").postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        //获取总条数
        System.out.println(searchResponse.getHits().getTotalHits().value);
        //输出结果数据（如果不设置返回条数，大于10条默认只返回10条）
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("分数:" + hit.getScore());
            Map<String, Object> source = hit.getSourceAsMap();
            System.out.println("index->" + hit.getIndex());
            System.out.println("id->" + hit.getId());
            for (Map.Entry<String, Object> s : source.entrySet()) {
                System.out.println(s.getKey() + "--" + s.getValue());
            }
        }
        return new ResponseVO(searchResponse);
    }


    /**
     * 查询分页
     * 根据scrollId深分页
     *
     * @return {@link ResponseVO}
     */
    @PostMapping("/document/queryPageByClient")
    public ResponseVO queryPageByClient(String index,String key,Object value) throws IOException {
        Scroll scroll = new Scroll(TimeValue.timeValueMillis(3L));
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(10);
        if (StringUtils.isBlank(key)) {
            sourceBuilder.query(QueryBuilders.matchAllQuery());
        } else {
            sourceBuilder.query(QueryBuilders.termQuery(key, value));
        }
        searchRequest.source(sourceBuilder);
        searchRequest.scroll(scroll);

        List<String> result = new ArrayList<>();
        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        searchHits.forEach(searchHit -> result.add(searchHit.getSourceAsString()));

        String scrollId = response.getScrollId();
        while (searchHits.getHits().length > 0) {
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
            searchScrollRequest.scroll(scroll);
            response = esClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
            scrollId = response.getScrollId();
            searchHits = response.getHits();
            searchHits.forEach(searchHit -> result.add(searchHit.getSourceAsString()));
        }
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = esClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        if (!clearScrollResponse.isSucceeded()) {
            log.error("清空滚动失败");
        }
        return ResponseVO.success();
    }


    @Autowired
    private EsBookRepository esBookRepository;

    /**
     * 向es添加文本
     *
     * 修改和新增类似，区别在于id;id相同就在原数据上修改
     *
     * @return {@link ResponseVO}
     */
    @PostMapping("/addByRepository")
    public ResponseVO addByRepository(){
        List<EsBook> esBookList = new ArrayList<>();
        esBookList.add(new EsBook("1","java程序设计","中文","echo",27.5f,"上岸"));
        esBookList.add(new EsBook("2","java程序设计1","中文","echo1",27.5f,"上岸"));
        esBookList.add(new EsBook("3","java程序设计2","中文","echo2",27.5f,"上岸"));
        esBookList.add(new EsBook("4","java程序设计3","中文","echo3",27.5f,"上岸"));
        esBookRepository.saveAll(esBookList);
        return ResponseVO.success();
    }

    /**
     * 测试删除
     *
     * @return {@link ResponseVO}
     */
    @PostMapping("/deleteByRepository")
    public ResponseVO deleteByRepository(){
        esBookRepository.deleteById("1");
        return ResponseVO.success();
    }

    /**
     * 查询
     *
     * @return {@link ResponseVO}
     */
    @PostMapping("/queryByRepository")
    public ResponseVO queryByRepository(){
        List<EsBook> esBookList = (List<EsBook>)esBookRepository.findAllById(Arrays.asList("1", "2"));
        return ResponseVO.success().data(esBookList);
    }
}
