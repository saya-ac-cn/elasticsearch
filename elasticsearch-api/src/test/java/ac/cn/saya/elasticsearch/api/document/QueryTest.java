package ac.cn.saya.elasticsearch.api.document;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

/**
 * @Title: QueryTest
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/23 0023 21:06
 * @Description:
 */

public class QueryTest {

    private PreBuiltTransportClient client;

    @Before
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        // 获取客户流对象
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("103.46.128.20"),36921));
    }

    @After
    public void closeClient(){
        client.close();
    }

    /**
     * MatchAll 查询所有
     */
    @Test
    public void queryMatchAll(){
        // 查询
        SearchResponse response = client.prepareSearch("news").setTypes("article").setQuery(QueryBuilders.matchAllQuery()).get();
        // 获取查询的对象（文档）
        SearchHits hits = response.getHits();
        // 打印查询结果条目
        System.out.println("查询结果为："+hits.getTotalHits());
        // 遍历打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            System.out.println((iterator.next()).getSourceAsString());
        }
    }

    /**
     * range查询
     */
    @Test
    public void queryRange(){
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("id").gte(0).lte(10);
        SearchResponse response = client.prepareSearch("news").setTypes("article").setQuery(rangeQueryBuilder).get();
        // 获取查询的对象（文档）
        SearchHits hits = response.getHits();
        // 打印查询结果条目
        System.out.println("查询结果为："+hits.getTotalHits());
        // 遍历打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            System.out.println((iterator.next()).getSourceAsString());
        }
    }

    // 对所有字段分词查询
    @Test
    public void queryStringQuery(){
        // 查询
        SearchResponse response = client.prepareSearch("news").setTypes("article").setQuery(QueryBuilders.queryStringQuery("ElasticSearch")).get();
        // 获取查询的对象（文档），获取命中次数，查询结果有多少对象
        SearchHits hits = response.getHits();
        // 打印查询结果条目
        System.out.println("查询结果为："+hits.getTotalHits());
        // 遍历打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            System.out.println((iterator.next()).getSourceAsString());
        }
    }

    // 词条查询
    @Test
    public void queryTermQuery(){
        // 查询
        SearchResponse response = client.prepareSearch("news").setTypes("article").setQuery(QueryBuilders.termQuery("title","Redis")).get();
        // 获取查询的对象（文档），获取命中次数，查询结果有多少对象
        SearchHits hits = response.getHits();
        // 打印查询结果条目
        System.out.println("查询结果为："+hits.getTotalHits());
        // 遍历打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            System.out.println((iterator.next()).getSourceAsString());
        }
    }

    /**
     * 通配符查询
     */
    @Test
    public void wildcardQuery(){
        // 查询
        SearchResponse response = client.prepareSearch("news").setTypes("article").setQuery(QueryBuilders.wildcardQuery("content","*是*")).get();
        // 获取查询的对象（文档），获取命中次数，查询结果有多少对象
        SearchHits hits = response.getHits();
        // 打印查询结果条目
        System.out.println("查询结果为："+hits.getTotalHits());
        // 遍历打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            System.out.println((iterator.next()).getSourceAsString());
        }
    }

    /**
     * 前缀查询
     */
    @Test
    public void prefixQuery(){
        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("content", "ElasticSearch");
        SearchResponse response = client.prepareSearch("news").setTypes("article").setQuery(prefixQueryBuilder).get();
        // 获取查询的对象（文档），获取命中次数，查询结果有多少对象
        SearchHits hits = response.getHits();
        // 打印查询结果条目
        System.out.println("查询结果为："+hits.getTotalHits());
        // 遍历打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            System.out.println((iterator.next()).getSourceAsString());
        }
    }


    // 模糊查询
    @Test
    public void fuzzyQuery(){
        // 查询
        SearchResponse response = client.prepareSearch("news").setTypes("article").setQuery(QueryBuilders.fuzzyQuery("content","redis")).get();
        // 获取查询的对象（文档），获取命中次数，查询结果有多少对象
        SearchHits hits = response.getHits();
        // 打印查询结果条目
        System.out.println("查询结果为："+hits.getTotalHits());
        // 遍历打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            System.out.println((iterator.next()).getSourceAsString());
        }
    }

}
