package com.bfd.kg.search.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BFD-483 on 2017/6/10.
 */
@RestController
@RequestMapping(value = "/v1/get.json")
//@EnableAutoConfiguration
public class GetController {
    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    public String getUser(String uid) {
        return String.format("{\"hello\": \"%s\"}", uid);
    }

    @RequestMapping(value = "/o", method = RequestMethod.GET)
    public String getOid(String oid, String oname) {
        return String.format("{\"hello\": \"%s-%s\"}", oid, oname);
    }

    @RequestMapping(value = "/jobj", method = RequestMethod.GET)
    public JSONObject getJobj(String uid) {
        Map<String, Object> m = new HashMap<>();
        m.put("hello", uid);
        return new JSONObject(m);
    }

    @RequestMapping(value = "/jarr", method = RequestMethod.GET)
    public JSONArray getJarr(String uid) {
        Map<String, Object> m = new HashMap<>();
        m.put("hello", uid);
        JSONObject jobj = new JSONObject(m);

        JSONArray arr = new JSONArray();
        arr.add(jobj);
        arr.add(jobj);
        return arr;
    }

}
