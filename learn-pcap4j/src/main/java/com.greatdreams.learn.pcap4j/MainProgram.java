package com.greatdreams.learn.pcap4j;

import com.greatdreams.learn.pcap4j.test.Pca4jLoop;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);
    public static void main(String[] args) throws InterruptedException, PcapNativeException, NotOpenException, IOException {
        logger.info("Hello pcap4j, application begins to run...");
        Pca4jLoop.main(args);
        logger.info("The application exit!");
    }
}