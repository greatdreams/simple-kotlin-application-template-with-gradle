package com.greatdreams.learn.java.base64;

import kotlin.text.Charsets;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Tests {

    // https://www.base64encode.org/
    @Test
    public void testBase64EncodingAndDecoding() throws UnsupportedEncodingException {
        String str = "我爱你中国";
        String strEncoded = Base64.getEncoder().encodeToString(str.getBytes(Charsets.UTF_8));
        assertEquals(strEncoded, "5oiR54ix5L2g5Lit5Zu9");

        String str1 = "5L2g5piv5oiR55qE5bCP5qOJ6KKE";
        String result = "你是我的小棉袄";
        String strDecoded = new String(Base64.getDecoder().decode(str1), Charsets.UTF_8.name());
        assertEquals(strDecoded, result);

    }
}
