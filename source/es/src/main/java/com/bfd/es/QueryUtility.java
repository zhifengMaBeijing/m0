package com.bfd.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;

import java.util.*;

/**
 * Created by BFD-483 on 2017/6/14.
 */
public class QueryUtility {

    final static String interval = "1M";
    final static String timeFormat = "yyyy-MM-dd";
    static String lowerBoundAsStr = "2014-01-01";
    static String higherBoundAsStr = "2015-01-01";
    static long timeMinDocCount = 1;
    static long termMinDocCount = 0;
    final static String aggStr = "agg";
    final static String metricStr = "metric";

    static int pageSize = 1000;

    public static AggregationBuilder dateHistogramSubTermBuilder(String dateField, String termField) {

        TopHitsAggregationBuilder aggTopHits = AggregationBuilders.topHits(aggStr).size(pageSize).fetchSource(false);
        TermsAggregationBuilder subAggs = AggregationBuilders.terms(aggStr).field(termField).minDocCount
                (termMinDocCount);
        subAggs.subAggregation(aggTopHits);
        AggregationBuilder aggs = AggregationBuilders
                .dateHistogram(aggStr)
                .field(dateField)
                .minDocCount(timeMinDocCount)
                .dateHistogramInterval(new DateHistogramInterval(interval))
                .format(timeFormat)
                //.extendedBounds(new ExtendedBounds(lowerBoundAsStr, higherBoundAsStr))
                .subAggregation(subAggs);
        return aggs;
    }

    public static JSONObject getDateHistogramSubTermWithIDs(Histogram agg) {
        JSONArray timeSlots = new JSONArray();
        JSONArray dataTotal = new JSONArray();
        Map<String, List<JSONObject>> dataBuckets = new HashMap<>();
        for (Histogram.Bucket entry : agg.getBuckets()) {
            timeSlots.add(entry.getKeyAsString()); // time Key as String
            JSONArray totalIds = new JSONArray();
            //sub aggs
            Terms childAggs = entry.getAggregations().get(aggStr);
            if (childAggs != null) {
                for (Terms.Bucket childEntry : childAggs.getBuckets()) {
                    String childKeyAsString = childEntry.getKeyAsString(); // property Key as String
                    JSONArray ids = new JSONArray();
                    TopHits topHits = childEntry.getAggregations().get(aggStr);
                    if (childAggs != null) {
                        for (SearchHit hit : topHits.getHits().getHits()) {
                            String id = hit.getId() ;
                            ids.add(id);
                        }
                    }
                    JSONObject jobjIDs = new JSONObject();
                    jobjIDs.put("volume", childEntry.getDocCount());
                    jobjIDs.put("ids", ids);
                    if (ids.size() > 0) {
                        totalIds.addAll(ids);
                    }
                    if (dataBuckets.containsKey(childKeyAsString)) {
                        dataBuckets.get(childKeyAsString).add(jobjIDs);
                    } else {
                        List<JSONObject> j1 = new LinkedList<>();
                        j1.add(jobjIDs);
                        dataBuckets.put(childKeyAsString, j1);
                    }
                }
            }
            //add to total
            JSONObject jobjIdsP = new JSONObject();
            jobjIdsP.put("volume", entry.getDocCount());
            jobjIdsP.put("ids", totalIds);
            dataTotal.add(jobjIdsP);
        }
        //prepare result
        JSONArray datum = new JSONArray();
        JSONArray types = new JSONArray();
        datum.add(dataTotal);
        types.add("total");

        for (String key : dataBuckets.keySet()) {
            types.add(key);
            datum.add(dataBuckets.get(key));
        }
        JSONObject result = new JSONObject();
        result.put("time", timeSlots);
        result.put("legend", types);
        result.put("data", datum);
        return result;
    }

    public static JSONObject getDateHistogramSubTermResult(Histogram agg) {
        Set<String> types = new HashSet<>();

        JSONArray typesJArr = new JSONArray();
        JSONArray timesJArr = new JSONArray();
        JSONArray dataJArrMatrix = new JSONArray();

        JSONArray dataJArrTotal = new JSONArray();
        typesJArr.add("total");
        for (Histogram.Bucket entry : agg.getBuckets()) {
            timesJArr.add(entry.getKeyAsString()); // Key as String
            dataJArrTotal.add(entry.getDocCount());
            org.elasticsearch.search.aggregations.bucket.terms.Terms aggChild = entry.getAggregations().get(aggStr);
            if (aggChild != null) {
                JSONArray childJArr = new JSONArray();
                for (Terms.Bucket entry_child : aggChild.getBuckets()) {
                    String childKeyAsString = entry_child.getKeyAsString(); // Key as String
                    long childDocCount = entry_child.getDocCount();         // Doc count
                    if (!types.contains(childKeyAsString)) {
                        //find new type
                        types.add(childKeyAsString);
                        typesJArr.add(childKeyAsString);
                    }
                    childJArr.add(childDocCount);
                }
                dataJArrMatrix.add(childJArr);
            }
        }
        dataJArrMatrix.add(0, dataJArrTotal);
        JSONObject result = new JSONObject();
        result.put("legend", typesJArr);
        result.put("data", dataJArrMatrix);
        result.put("time", timesJArr);

        return result;
    }

}
