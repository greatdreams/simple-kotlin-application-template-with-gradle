package com.greatdreams.kotlin.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String[] args) {
        logger.debug("program begins to run...");
        logger.debug("program exits normally.");
    }
}
