package com.greatdreams.learn.java.crypt;

import kotlin.text.Charsets;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;

import javax.crypto.*;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * DES - Data Encryption Standard
 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher
 * https://www.mkyong.com/java/jce-encryption-data-encryption-standard-des-tutorial/
 */
public class DESCryptTests {
    private String plainText = "I love you!";
    private byte[] plainTextBytes = plainText.getBytes(Charsets.UTF_8);;

    @BeforeEach
    public void init() {
        plainText = "I love you!";
        plainTextBytes = plainText.getBytes(Charsets.UTF_8);
    }
    @Test
    public void testDES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey key = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // crypt data
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] secretBytes = cipher.doFinal(plainTextBytes);

        // decrypt data
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(secretBytes);

        assertArrayEquals(decryptedBytes, plainTextBytes);
    }
}
