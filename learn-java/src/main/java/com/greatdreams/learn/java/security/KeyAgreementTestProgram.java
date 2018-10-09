package com.greatdreams.learn.java.security;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyAgreement;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KeyAgreementTestProgram {

    private static Logger logger = LoggerFactory.getLogger(KeyAgreementTestProgram.class);

    public static void main(String[] args) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        logger.info("learn to use key agreement tool");
        printKeyAgreementImplInfo();
        printKeyAgreementImplInfoForBC();
        testECDH();
    }

    //output keytool implementation information in jdk implementation
    public static void printKeyAgreementImplInfo() {
        String[] algorithms = {
           "DiffieHellman",
           "ECDH",
           "ECMQV",
           "XDH",
           "X25519",
           "X448"
        };

        for(String algorithm: algorithms) {
            System.out.println("-----------------------------------");
            System.out.println("KeyAgreement(" + algorithm + ")");
            try {
                KeyAgreement ka = KeyAgreement.getInstance(algorithm);
                Provider provider = ka.getProvider();

                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    //output keytool implementation information in bouncy castle implementation
    public static void printKeyAgreementImplInfoForBC() {
        String[] algorithms = {
                "DiffieHellman",
                "ECDH",
                "ECMQV",
                "XDH",
                "X25519",
                "X448"
        };

        for(String algorithm: algorithms) {
            System.out.println("-----------------------------------");
            System.out.println("KeyAgreement(" + algorithm + ") with bouncy castle library");
            try {
                KeyAgreement ka = KeyAgreement.getInstance(algorithm, "BC");
                Provider provider = ka.getProvider();

                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
    }

    // show a key agreement example with ECDH(Elliptic Curve Diffie-Hellman) algorithm
    // https://neilmadden.wordpress.com/2016/05/20/ephemeral-elliptic-curve-diffie-hellman-key-agreement-in-java/
    public static void testECDH() {
        // define communication parties - pa1 and pa2
        try {
            KeyPairGenerator pa1KPA = KeyPairGenerator.getInstance("EC");
            pa1KPA.initialize(256);
            KeyPair pa1KP = pa1KPA.generateKeyPair();
            byte[] pa1PK = pa1KP.getPublic().getEncoded();
            // display public key of party 1
            System.out.println("party 1 public key : " + Hex.toHexString(pa1PK));

            KeyPairGenerator pa2KPA = KeyPairGenerator.getInstance("EC");
            pa2KPA.initialize(256);
            KeyPair pa2KP = pa2KPA.generateKeyPair();
            byte[] pa2PK = pa2KP.getPublic().getEncoded();
            // display public key of party 2
            System.out.println("party 2 public key : " + Hex.toHexString(pa2PK));

            //perform key agreement for party 1
            KeyAgreement pa1KA = KeyAgreement.getInstance("ECDH");
            pa1KA.init(pa1KP.getPrivate());
            pa1KA.doPhase(pa2KP.getPublic(), true);
            // read shared secret
            byte[] pa1SharedSecret = pa1KA.generateSecret();
            System.out.println("party 1 shared secret: " + Hex.toHexString(pa1SharedSecret));

            //perform key agreement for party 2
            KeyAgreement pa2KA = KeyAgreement.getInstance("ECDH");
            pa2KA.init(pa2KP.getPrivate());
            pa2KA.doPhase(pa1KP.getPublic(), true);
            // read shared secret
            byte[] pa2SharedSecret = pa2KA.generateSecret();
            System.out.println("party 2 shared secret: " + Hex.toHexString(pa2SharedSecret));


            //Derive a key from the shared secret and both public keys
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pa1SharedSecret);
            // Simple deterministic ordering
            List<ByteBuffer> keys = Arrays.asList(
                    ByteBuffer.wrap(pa1PK),
                    ByteBuffer.wrap(pa2PK)
            );

            Collections.sort(keys);

            md.update(keys.get(0));
            md.update(keys.get(1));

            byte[] derivedKey = md.digest();
            System.out.println("party two derived key: " + Hex.toHexString(derivedKey));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
