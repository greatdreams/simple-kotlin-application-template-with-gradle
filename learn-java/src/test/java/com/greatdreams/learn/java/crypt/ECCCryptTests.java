package com.greatdreams.learn.java.crypt;

import kotlin.text.Charsets;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

import static org.junit.Assert.assertArrayEquals;

public class ECCCryptTests {
    private static int keySize = 256;
    private static String plainText = "I love you";


    @Test
    public void testECC() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        byte [] plainTextValue = plainText.getBytes(Charsets.UTF_8);

        Cipher cipher = Cipher.getInstance("ECIES", "BC");

        ECGenParameterSpec ecsp = new ECGenParameterSpec("secp192k1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(keySize);
        keyPairGenerator.initialize(ecsp);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // crypt data
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] crypttedValue = cipher.doFinal(plainTextValue);

        // decrypt data
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedValue = cipher.doFinal(crypttedValue);

        assertArrayEquals(decryptedValue, plainTextValue);

    }
}
