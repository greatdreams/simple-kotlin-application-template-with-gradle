package com.greatdream.learn.java.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.SortedMap;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String[] args) {
        logger.info("application name: learn-java");
        logger.info("application begins to run");

        availableCharset();

        logger.info("application exit normally");
    }

    public static void availableCharset() {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for(String key: charsets.keySet()) {
            logger.debug(key + ":" + charsets.get(key).displayName());
        }
    }
}
