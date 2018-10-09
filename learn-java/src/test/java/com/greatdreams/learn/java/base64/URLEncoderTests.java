package com.greatdreams.learn.java.base64;

import kotlin.text.Charsets;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class URLEncoderTests {
    @Test
    public void testURLEncodeAndDecode() throws UnsupportedEncodingException {
        // reference to https://www.url-encode-decode.com/
        String url = "http://www.baidu.com?search=新疆 大学";
        String urlEncoded = URLEncoder.encode(url, Charsets.UTF_8.name());
        String result = "http%3A%2F%2Fwww.baidu.com%3Fsearch%3D%E6%96%B0%E7%96%86+%E5%A4%A7%E5%AD%A6";
        assertEquals(result, urlEncoded);
        String urlDecoded = URLDecoder.decode(result, Charsets.UTF_8.name());
        assertEquals(url, urlDecoded);
    }
}
