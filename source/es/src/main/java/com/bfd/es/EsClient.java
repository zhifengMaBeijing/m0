package com.bfd.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by BFD-483 on 2017/6/6.
 */
public class EsClient {
    Logger log = LogManager.getLogManager().getLogger("EsClient");
    final TransportClient esClient;

    /**
     * constructor
     *
     * @param esnode
     * @param esport
     * @param cluster
     * @throws UnknownHostException
     */
    public EsClient(String esnode, String esport, String cluster) throws UnknownHostException {
        Settings settings = Settings.builder()
                //.put("client.transport.ignore_cluster_name","true").build();
                .put("cluster.name", cluster).build();
        esClient = new PreBuiltTransportClient(settings);
        esClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esnode), Integer.parseInt
                (esport)));
    }

    /**
     * 返回单个文档
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    public JSONObject getByDocID(String index, String type, String id) {
        return null;
    }

    /**
     * 返回在 时间轴各个时间点上 各类型事件 的统计数据
     *
     * @param index
     * @param types
     * @param dateField
     * @param termField
     */
    public JSONObject dateHistogram_keywords(String index, String[] types, String dateField, String keyField, String
            keyValue, String termField) {

        AggregationBuilder aggs = QueryUtility.dateHistogramSubTermBuilder(dateField, termField);

        SearchRequestBuilder srb = esClient.prepareSearch(index).setTypes(types);
        SearchResponse sr = srb
                .setQuery(QueryBuilders.matchQuery(keyField, keyValue))
                .addAggregation(aggs).get();

        Histogram agg = sr.getAggregations().get("agg");
        JSONObject jobj = new JSONObject();

        for (Histogram.Bucket entry : agg.getBuckets()) {
            String keyAsString = entry.getKeyAsString(); // Key as String
            long allDocCount = entry.getDocCount();         // Doc count

            JSONObject jobj_child = new JSONObject();
            jobj_child.put("all", allDocCount);
            Histogram agg_child = entry.getAggregations().get("agg");
            for (Histogram.Bucket entry_child : agg_child.getBuckets()) {
                String keyAsString_child = entry_child.getKeyAsString(); // Key as String
                long docCount_child = entry_child.getDocCount();         // Doc count
                jobj_child.put(keyAsString, docCount_child);
            }
            jobj.put(keyAsString, jobj_child);
        }
        return jobj;
    }

    public JSONObject dateHistogram_matchall(String index, String[] types, String dateField, String termField) {

        AggregationBuilder aggs = QueryUtility.dateHistogramSubTermBuilder(dateField, termField);

        SearchRequestBuilder srb = esClient.prepareSearch(index).setTypes(types);
        SearchResponse sr = srb
                //.setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(aggs).get();
        //System.out.println(srb);
        Histogram agg = sr.getAggregations().get("agg");
        // System.out.println(agg);
        JSONArray jarr = new JSONArray();
        JSONObject jobj = new JSONObject();

        for (Histogram.Bucket entry : agg.getBuckets()) {
            String keyAsString = entry.getKeyAsString(); // Key as String
            long allDocCount = entry.getDocCount();         // Doc count

            JSONObject jobj_child = new JSONObject();
            jobj_child.put("all", allDocCount);
            org.elasticsearch.search.aggregations.bucket.terms.Terms agg_child = entry.getAggregations().get("agg");
            if (agg_child != null) {
                for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket entry_child : agg_child
                        .getBuckets()) {
                    String keyAsString_child = entry_child.getKeyAsString(); // Key as String
                    long docCount_child = entry_child.getDocCount();         // Doc count
                    jobj_child.put(keyAsString_child, docCount_child);
                }
            }
            jobj.put(keyAsString, jobj_child);

        }
        return jobj;
    }

    public JSONObject dateHistogramSubterm(String index, String[] types, String[] ids, String dateField, String
            termField) {
        AggregationBuilder aggs = QueryUtility.dateHistogramSubTermBuilder(dateField, termField);
        SearchRequestBuilder srb = esClient.prepareSearch(index).setTypes(types).setFetchSource(new String[]{"sold"},
                new String[]{});
        //SearchRequestBuilder srb = esClient.prepareSearch(index).setTypes(types).setSize(0);
        SearchResponse sr = srb
                .setQuery(QueryBuilders.idsQuery().addIds(ids))
                .addAggregation(aggs).get();
        System.out.println(srb);
        System.out.println("newline");

        System.out.println(sr);
        for (SearchHit hit : sr.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
            //todo: replace getSourceAsString with hit.getSource()
        }

        return QueryUtility.getDateHistogramSubTermWithIDs(sr.getAggregations().get("agg"));
    }

    public void dump(String index, String[] types, String[] includes, int from, int page) {
        SearchRequestBuilder srb = esClient.prepareSearch(index)
                .setTypes(types)
                .setFetchSource(includes, new
                        String[]{})
                .setSize(page)
                .setFrom(from);

        SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).get();
        printCSVResult(includes, sr);
    }

    private void printCSVResult(String[] includes, SearchResponse sr) {
        for (SearchHit hit : sr.getHits().getHits()) {
            String csv = parseHit(hit, includes);
            System.out.println(csv);
        }
    }

    //http://mojijs.com/2016/10/219751/index.html
    //http://hansight.com/blog-elasticsearch-scroll-api.html
    public void dumpScroll(String index, String[] types, String[] includes, int size) {
        String scrollId = searchByScroll(index, types, includes, size);
        while (true) {
            SearchScrollRequestBuilder srsb = esClient.prepareSearchScroll(scrollId).setScroll("1m");
            SearchResponse sr = srsb.get();
            if (sr.getHits().getHits().length == 0) {
                break;
            }
            scrollId = sr.getScrollId();
            printCSVResult(includes, sr);
        }
    }

    public String searchByScroll(String index, String[] types, String[] includes, int size) {
        SearchRequestBuilder srb = esClient.prepareSearch(index)
                .setTypes(types)
                .setFetchSource(includes, new
                        String[]{})
                .setScroll("1m").setSize(size);

        SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).get();

        printCSVResult(includes, sr);
        String scrollId = sr.getScrollId();
        return scrollId;
    }

    private String parseHit(SearchHit hit, String[] includes) {
        StringBuffer sb = new StringBuffer();
        for (String include : includes) {
            sb.append(hit.getSource().get(include));
        }
        return String.format("%s,%s,%s", hit.getId(), hit.getType(), sb.substring(0));
    }

    public void createDocs(String index, String[] types, int from, int page) {
        SearchRequestBuilder srb = esClient.prepareSearch(index)
                .setTypes(types)
                .setSize(page)
                .setFrom(from);

        SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).get();
        List<String> ids = new LinkedList<>();
        List<String> docs = new LinkedList<>();

        for (SearchHit hit : sr.getHits().getHits()) {
            //revise

            //cache
            ids.add(hit.getIndex());
        }

        //write

    }

    public void bulkInsert(String index, String type, List<String> ids, List<String> docs) {

    }

}
