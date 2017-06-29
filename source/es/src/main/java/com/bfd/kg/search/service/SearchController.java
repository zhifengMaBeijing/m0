package com.bfd.kg.search.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/search.json")
//@EnableAutoConfiguration
public class SearchController {
    @RequestMapping(value = "/aggregation")
    String aggregation() {
        return "aggregation";
    }


    @RequestMapping(value = "/fast")
    String fast() {
        return "fast";
    }

}
