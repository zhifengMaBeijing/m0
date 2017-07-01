package com.bfd.es;

import com.alibaba.fastjson.JSONObject;
import com.bfd.test.util.EsWriter;
import com.bfd.test.util.FileLoader;
import com.bfd.test.util.RandomDate;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

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
        String[] types = {"杂志", "短篇", "学术期刊"};
        String[] includes = {"name", "datetime"};
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
        //String[] types = {"战役", "内战","通信","发邮件"};
        String[] types = {"打电话"};
        String[] includes = {"name", "datetime"};
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
    public void bulkInsert() throws IOException, URISyntaxException {
        File s = FileLoader.loadFile("story/people.json");
        FileReader fr = new FileReader(s);
        BufferedReader bfr = new BufferedReader(fr);
        String line = null;

        int asize = 965;
        String index = "atom";
        String type = "通信";
        String name = "通信";

        String[] ids = new String[asize];
        String[] docs = new String[asize];

        int i = 0;
        while ((line = bfr.readLine()) != null && i < asize) {
            ids[i] = String.format("%s", 1000 + i + 1);

            JSONObject mDoc = new JSONObject();
            mDoc.put("ontology_type", "event");
            mDoc.put("type", type);
            mDoc.put("id", ids[i]);
            mDoc.put("name", name);

            String dateTimeString = RandomDate.randomDateString("2015-01-01 00:00:00", "2017-06-29 00:00:00");
            Date date = RandomDate.randomDate("2015-01-01 00:00:00", "2017-06-29 00:00:00");
            mDoc.put("datetime", dateTimeString);
            mDoc.put("date_time", date);
            mDoc.put("modify_time", "2017-06-29 17:29:32");
            mDoc.put("create_time", "2017-06-26 23:12:02");
            String[] items = line.trim().split(",");

            mDoc.put("from", items[0].trim());
            mDoc.put("to", items[1].trim());

            docs[i] = mDoc.toJSONString();
            i = i + 1;
        }

        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            client.bulkInsert(index, type, ids, docs);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void bulkInsert_email() throws IOException, URISyntaxException {
        File s = FileLoader.loadFile("story/people.json");
        FileReader fr = new FileReader(s);
        BufferedReader bfr = new BufferedReader(fr);
        String line = null;

        int asize = 965;
        String index = "atom";
        String type = "打电话";
        String name = "打电话";

        String[] ids = new String[asize];
        String[] docs = new String[asize];

        int i = 0;
        while ((line = bfr.readLine()) != null && i < asize) {
            ids[i] = String.format("%s", 2 * 1000 + i + 1);

            JSONObject mDoc = new JSONObject();
            mDoc.put("ontology_type", "event");
            mDoc.put("type", type);
            mDoc.put("id", ids[i]);
            mDoc.put("name", name);

            String dateTimeString = RandomDate.randomDateString("2015-01-01 00:00:00", "2017-06-29 00:00:00");
            Date date = RandomDate.randomDate("2015-01-01 00:00:00", "2017-06-29 00:00:00");
            mDoc.put("datetime", dateTimeString);
            mDoc.put("date_time", date);
            mDoc.put("modify_time", "2017-06-29 17:29:32");
            mDoc.put("create_time", "2017-06-26 23:12:02");
            String[] items = line.trim().split(",");

            mDoc.put("from", items[0].trim());
            mDoc.put("to", items[1].trim());

            docs[i] = mDoc.toJSONString();
            i = i + 1;
        }

        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            client.bulkInsert(index, type, ids, docs);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void bulkInsert_phone() throws IOException, URISyntaxException {
        File s = FileLoader.loadFile("story/people.json");
        FileReader fr = new FileReader(s);
        BufferedReader bfr = new BufferedReader(fr);
        String line = null;

        int asize = 965;
        String index = "atom";
        String type = "打电话";
        String name = "打电话";

        String[] ids = new String[asize];
        String[] docs = new String[asize];

        int i = 0;
        while ((line = bfr.readLine()) != null && i < asize) {
            ids[i] = String.format("%s", 3 * 1000 + i + 1);

            JSONObject mDoc = new JSONObject();
            mDoc.put("ontology_type", "event");
            mDoc.put("type", type);
            mDoc.put("id", ids[i]);
            mDoc.put("name", name);

            String dateTimeString = RandomDate.randomDateString("2015-01-01 00:00:00", "2017-06-29 00:00:00");
            Date date = RandomDate.randomDate("2015-01-01 00:00:00", "2017-06-29 00:00:00");
            mDoc.put("datetime", dateTimeString);
            mDoc.put("date_time", date);
            mDoc.put("modify_time", "2017-06-29 17:29:32");
            mDoc.put("create_time", "2017-06-26 23:12:02");
            String[] items = line.trim().split(",");

            mDoc.put("from", items[0].trim());
            mDoc.put("to", items[1].trim());

            docs[i] = mDoc.toJSONString();
            i = i + 1;
        }

        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            client.bulkInsert(index, type, ids, docs);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void addMapping() {
        String index = "atom";
        String type = "通信";
        String mappingSource = "{\n" +
                "    \"user\":{\n" +
                "        \"properties\": {\n" +
                "            \"name\": {\n" +
                "                \"type\": \"string\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        try {
            EsClient client = new EsClient("172.24.5.149", "9300", "logging-dev");
            client.addMapping(index, type, mappingSource);

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }

    }


    @Test
    public void constructIds(){
        for(int i=2001;i<2966;i++){
            String tmp= String.format("{ \"delete\": {\"_id\":\"%s\"}}",i);
            System.out.println(tmp);
        }
    }


    //scp -P 3222  /cygdrive/d/temp/atom/BFD-ATOM/information/target/information-0.0.1-SNAPSHOT.jar
    // root@172.24.8.34:/root/dzn/

    //scp -P 3222  /cygdrive/d/temp/atom/BFD-ATOM/information/src/main/resources/application.yml
    // root@172.24.8.34:/root/dzn/

    //D:\temp\atom\BFD-ATOM\information\src\main\resources\application.yml
}
