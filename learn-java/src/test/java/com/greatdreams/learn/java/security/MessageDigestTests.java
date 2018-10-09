package com.greatdreams.learn.java.security;

import kotlin.text.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.Assert.*;

// online hashing tool https://www.fileformat.info/tool/hash.htm
public class MessageDigestTests {

    private String value = "";

    @BeforeEach
    public void init() {
        value = "我爱你";
    }
    @Test
    public void testSHA1() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(value.getBytes(Charsets.UTF_8));
        String hash  = Hex.encodeHexString(md.digest());
        assertEquals("5890a73fed38bf09622c34ad9391f1d09c0ec100", hash);
    }
    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes(Charsets.UTF_8));
        String hash  = Hex.encodeHexString(md.digest());
        assertEquals("4f2016c6b934d55bd7120e5d0e62cce3", hash);
    }
    @Test
    public void testSHA256() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(value.getBytes(Charsets.UTF_8));
        String hash  = Hex.encodeHexString(md.digest());
        assertEquals("c0ad5411b19cfcba9d674d21411a970159f6ae4e180831ddd6a91797be547752", hash);
    }
    @Test
    public void testSHA384() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-384");
        md.update(value.getBytes(Charsets.UTF_8));
        String hash  = Hex.encodeHexString(md.digest());
        assertEquals("9c6a0c9fa2e2e5b656d0128af9b433779db8f800474331c5ebe03014c" +
                "8160cee43f97e4400eb7f05c4bf86a83b46fe5c", hash);
    }
    @Test
    public void testSHA512() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(value.getBytes(Charsets.UTF_8));
        String hash  = Hex.encodeHexString(md.digest());
        assertEquals("5b0753c33ba23fe2fe9e529afd6b97c3d8480bf17c1a4baea42dadc2504d1c57fa" +
                "4dc8afd51f79ae6fad719c611aa5749ab0b2b2edebdad661da78cc3759cbf0", hash);
    }
}
