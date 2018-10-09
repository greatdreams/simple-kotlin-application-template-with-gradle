package com.greatdreams.learn.java.fonts.charset;

import java.nio.charset.Charset;
import java.util.Map;

public class CharsetTestProgram {
    public static void main(String[] args) {
        Map<String, Charset> charsets = Charset.availableCharsets();
        System.out.println("-----------------------");

        System.out.println("current java platform support " + charsets.keySet().size() + " charsets");
        charsets.forEach((String name, Charset charset) -> {
            System.out.println("\t" + name + ": " + charset.displayName());
        });

        outputBasicChinese();
    }

    public static void outputBasicChinese() {
        int beginIndex = 0;
        int[] chineseCodePoint = new int[20902];

        StringBuffer chinese = new StringBuffer();
        for(; beginIndex < 20902; beginIndex++) {
            chineseCodePoint[beginIndex] =  beginIndex + Integer.parseInt("4E00", 16);
            System.out.println((char)chineseCodePoint[beginIndex]);
        }

        System.out.print(new String(chineseCodePoint, 0, chineseCodePoint.length));

    }
}
