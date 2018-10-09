package com.greatdreams.learn.java;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class ByteOperationTests {
    @Test
    public void testOpOR() {
        int a = 10;
        int b = 20;
        int c = a | b;
        assertEquals(c, 30);
    }
    @Test
    public void testOpAND() {
        int a = 10;
        int b = 20;
        int c = a & b;
        assertEquals(c, 0);
    }
    @Test
    public void testOpLeftShift() {
        int a = 20;
        int c = a << 2;
        assertEquals(c, 80);
    }
    @Test
    public void testOpRightShift() {
        int a = 20;
        int c = a >> 2;
        int d = a >> 4;
        int e = a >> 5;
        assertEquals(c, 5);
        assertEquals(d, 1);
        assertEquals(e, 0);
    }
}
