package com.greatdreams.learn.java.security;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;

/**
 * @author greatdreams
 * 2018-07-17
 * DSA is digital signature algorithm
 */
public class DSATestProgram {
    private static Logger logger = LoggerFactory.getLogger(DSATestProgram.class);

    private static String value = "Hello world";

    public static void main(String[] args) {
        KeyPair keyPair = generateKeyPair();

        byte[] signDataBytes = generateSignature(value, keyPair.getPrivate());
        String signData = new String(Hex.encode(signDataBytes));

        System.out.println("data: " + value);
        System.out.println("signature: " + signData);

        boolean verification = verifySignature(value, signDataBytes, keyPair.getPublic());

        if(verification)
            System.out.println("signature is verified");
        else
            System.out.println("signature failed to verify");
    }

    private static KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
            keyPairGenerator.initialize(1024, new SecureRandom());
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return keyPair;
    }

    private static byte[] generateSignature(String data, PrivateKey privateKey) {
        byte[] signValue = null;
        try {
            Signature signature = Signature.getInstance("SHA1withDSA", "SUN");
            signature.initSign(privateKey);
            signature.update(data.getBytes(),0, data.getBytes().length);
            signValue = signature.sign();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return signValue;
    }

    private static boolean verifySignature(String data,
                                           byte[] signData,
                                           PublicKey publicKey) {
        boolean result = false;
        try {
            Signature signature = Signature.getInstance("SHA1withDSA", "SUN");
            signature.initVerify(publicKey);
            signature.update(data.getBytes());
            result = signature.verify(signData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return result;
    }
}
