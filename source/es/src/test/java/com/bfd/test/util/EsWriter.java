package com.bfd.test.util;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by BFD-483 on 2017/6/9.
 */
public class EsWriter {

    public static void prepareData() throws URISyntaxException, IOException {
        String nodes = "172.24.5.149:9300";
        String port = "9300";
        String cluster = "";


        String s = FileLoader.loadResourceAsString("story/entity_person-jack.json");
        System.out.println(s);

    }

}
