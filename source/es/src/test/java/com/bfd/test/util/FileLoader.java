package com.bfd.test.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by BFD-483 on 2017/6/9.
 */
public class FileLoader {
    public static String loadResourceAsString(String path) throws IOException,URISyntaxException {
        return FileUtils.readFileToString(new File(
                Thread.currentThread().getContextClassLoader().getResource(path).toURI()
        ));
    }
}
