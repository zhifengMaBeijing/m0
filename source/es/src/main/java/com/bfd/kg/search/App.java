package com.bfd.kg.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by BFD-483 on 2017/6/10.
 */

@SpringBootApplication
@ServletComponentScan
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

}

