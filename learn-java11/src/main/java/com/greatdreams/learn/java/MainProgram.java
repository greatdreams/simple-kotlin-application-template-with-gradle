package com.greatdreams.learn.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);
    public static void main(String[] args) {
        var names = new ArrayList<String>();
        names.add("greatdreams");
        names.add("huawei");

        names.forEach((name) ->
                System.out.println(name));

    }
}
