package com.greatdreams.learn.java.signature;

import kotlin.text.Charsets;
import org.junit.Test;
import static org.junit.Assert.*;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Signature
 * https://www.mkyong.com/java/java-digital-signatures-example/
 * https://docs.oracle.com/javase/7/docs/api/
 */
public class SignatureTests {
    private static String text = "I love you!";
    private static int keySize = 1024;

    @Test
    public void testSHA1withRSA() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] textBytes = text.getBytes(Charsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(textBytes);
        byte[] hash = md.digest();
        assertEquals(20, 20);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // sign data
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(hash);
        byte[] signBytes = signature.sign();

        // verify signature
        signature.initVerify(publicKey);
        signature.update(hash);
        assertTrue(signature.verify(signBytes));
    }

    @Test
    public void testSHA1withECDSA() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        byte[] textBytes = text.getBytes(Charsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(textBytes);
        byte[] hash = md.digest();
        assertEquals(20, 20);

        ECGenParameterSpec ecsp = new ECGenParameterSpec("secp192k1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(256);
        keyPairGenerator.initialize(ecsp);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // sign data
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initSign(privateKey);
        signature.update(hash);
        byte[] signBytes = signature.sign();

        // verify signature
        signature.initVerify(publicKey);
        signature.update(hash);
        assertTrue(signature.verify(signBytes));
    }
}
