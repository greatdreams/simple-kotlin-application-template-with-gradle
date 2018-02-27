package com.greatdreams.learn.java;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class ByteCharStringTests {
    @Test
    public void a() {
        byte a = 38;
        assertEquals("convert explicitly byte to char should be work", (char)a, '&');
        assertEquals("convert explicitly char to byte should be work", (byte)'&', 38);
        char c = '\u1234';
        byte b = 0x12;
        assertEquals("convert explicitly char '\u1234' to byte(0x12) should be work", (byte)'\u1234', 0x34);

    }
    @Test
    public void b() {
        String a = "I love you";
        assertEquals("length of 'I love you' should be 10", a.length(), 10);
        assertEquals("byte length of 'I love you' should be 10 if charset is US-ASCII", a.getBytes().length, 10);
        try {
            assertEquals("byte length of 'I love you' should be 20 if charset is UTF-8", a.getBytes("UTF-8").length, 10);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String b = "我爱你";
        assertEquals("length of '我爱你' should be 3", b.length(), 3);
        assertEquals("byte length of '我爱你' should be 6 if charset is US-ASCII", b.getBytes().length, 9);
        try {
            assertEquals("byte length of '我爱你' should be 6 if charset is UTF-8", b.getBytes("UTF-8").length, 9);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            assertEquals("byte length of '我爱你' should be 6 if charset is GBK", b.getBytes("GBK").length, 6);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void c() {
        assertEquals("5 == 5", 5, 6);
    }
}
