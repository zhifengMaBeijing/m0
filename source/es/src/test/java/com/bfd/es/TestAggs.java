package com.bfd.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfd.test.util.EsWriter;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by BFD-483 on 2017/6/6.
 */
public class TestAggs {

    @Ignore
    public void basic() {
        try {
            EsWriter.prepareData();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("basic");
    }

    @Ignore
    public void date_stats() {
        String index = "cars";
        String[] types = {"transactions"};
        String dateField = "sold";
        String termField = "color.keyword";
        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            JSONObject result = client.dateHistogram_matchall(index, types, dateField, termField);
            System.out.println(result);
            //System.out.println(result.toJSONString(result,true));
            assert (result.containsKey("2014-01-01"));
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Ignore
    public void timeAnalysis() {
        String index = "cars";
        String[] types = {"transactions"};
        String[] ids = {"AVyfaoNddZyJEjcQ1aiB",
                "AVyfaoNddZyJEjcQ1aiF",
                "AVyfaoNddZyJEjcQ1aiC"};
        String dateField = "sold";
        String termField = "color.keyword";
        String expected = "{\"2014-08-01\":{\"all\":1,\"green\":1},\"2014-11-01\":{\"all\":1,\"red\":1}," +
                "\"2014-10-01\":{\"all\":1,\"red\":1}}";
        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            JSONObject result = client.dateHistogramSubterm(index, types, ids, dateField, termField);
            System.out.println(result.toString());
            //assert (result.containsKey("2014-08-01"));
            //assert (expected.compareTo(result.toString()) == 0);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void dumpData() {
        String index = "atom";
        String[] types = {"人类"};
        String[] includes = {"name"};
        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            int page = 1000;
            for (int i = 0; i < 10; i++) {
                int from = i * page;
                client.dump(index, types, includes, from, page);
            }

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void dumpDataScrollPeople() {
        String index = "atom";
        String[] types = {"人类"};
        String[] includes = {"name"};
        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            int page = 1000;
            client.dumpScroll(index, types, includes, page);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void dumpDataScrollMagzine() {
        String index = "atom";
        String[] types = {"杂志","短篇","学术期刊"};
        String[] includes = {"name"};
        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            int page = 1000;
            client.dumpScroll(index, types, includes, page);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void dumpDataScrollEvent() {
        String index = "atom";
        String[] types = {"战役","内战"};
        String[] includes = {"name"};
        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            int page = 1000;
            client.dumpScroll(index, types, includes, page);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void bulkInsert(){

    }

    //scp -P 3222  /cygdrive/d/temp/atom/BFD-ATOM/information/target/information-0.0.1-SNAPSHOT.jar
    // root@172.24.8.34:/root/dzn/
}
