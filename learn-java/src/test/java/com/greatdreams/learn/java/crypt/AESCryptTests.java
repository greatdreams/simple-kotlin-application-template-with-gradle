package com.greatdreams.learn.java.crypt;

import org.apache.commons.codec.Charsets;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher
 * https://howtodoinjava.com/security/java-aes-encryption-example/
 */

public class AESCryptTests {
    private String privateStr = "afdkafjakfa;f";

    private String plaintext = "I love you";
    private byte[] plaintextBytes = plaintext.getBytes(Charsets.UTF_8);

    @Test
    public void testAES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(privateStr.getBytes(Charsets.UTF_8));
        byte[] privateValue = md.digest();
        byte[] temp = Arrays.copyOf(privateValue, 16);
        SecretKeySpec key = new SecretKeySpec(temp, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // crypt value
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cryptedValue = cipher.doFinal(plaintextBytes);

        // decrypt value
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue = cipher.doFinal(cryptedValue);

        assertArrayEquals(decryptedValue, plaintextBytes);

    }
}
