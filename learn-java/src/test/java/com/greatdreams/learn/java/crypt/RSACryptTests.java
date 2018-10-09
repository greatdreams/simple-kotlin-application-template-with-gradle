package com.greatdreams.learn.java.crypt;

import kotlin.text.Charsets;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

/**
 * https://www.mkyong.com/java/java-asymmetric-cryptography-example/
 */
public class RSACryptTests {
    private static int keySize = 1024;
    private static String plainText = "I love you";


    @Test
    public void testRSA() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte [] plainTextValue = plainText.getBytes(Charsets.UTF_8);

        Cipher cipher = Cipher.getInstance("RSA");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // crypt data
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] crypttedValue = cipher.doFinal(plainTextValue);

        // decrypt data
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedValue = cipher.doFinal(crypttedValue);

        assertArrayEquals(decryptedValue, plainTextValue);

    }
}
