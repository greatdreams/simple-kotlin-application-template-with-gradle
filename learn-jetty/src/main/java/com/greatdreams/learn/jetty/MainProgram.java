package com.greatdreams.learn.jetty;

import org.eclipse.jetty.util.Jetty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String [] args) {
        logger.info("Jetty version is " + Jetty.VERSION);
    }
}
