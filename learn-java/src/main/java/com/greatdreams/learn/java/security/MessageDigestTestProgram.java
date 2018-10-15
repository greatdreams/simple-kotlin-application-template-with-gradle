package com.greatdreams.learn.java.security;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;

public class MessageDigestTestProgram {

    private static Logger logger = LoggerFactory.getLogger(MessageDigestTestProgram.class);

    public static void main(String[] args) {
        logger.info("learn java message digest");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        String[] algorithms = {
                "MD2",
                "MD5",
                "SHA-1",
                "SHA-224",
                "SHA-256",
                "SHA-384",
                "SHA-512/224",
                "SHA-512/256",
                "SHA3-224",
                "SHA3-256",
                "SHA3-384",
                "SHA3-512"
        };

        for (String algorithm : algorithms) {
            System.out.println("---------------------------------------------");
            System.out.println("MessageDigest(" + algorithm + ")");
            try {
                MessageDigest md = MessageDigest.getInstance(algorithm);
                System.out.println("\tdigest length is " + md.getDigestLength());

                Provider provider = md.getProvider();
                System.out.println("\tprovider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }

        printMessageDigest("BC");

        for (String algorithm : algorithms) {
            String message = "hello world";
            printHash(message, algorithm);
        }

        testKeccak();
    }

    public static void printMessageDigest(String providerName) {
        String[] algorithms = {
                "GOST3411",
                "Keccak",
                "MD2",
                "MD4",
                "MD5",
                "SHA1",
                "RIPEMD128",
                "RIPEMD160",
                "RIPEMD256",
                "RIPEMD320",
                "SHA224",
                "SHA256",
                "SHA384",
                "SHA512",
                "SHA3-224",
                "SHA3-256",
                "SHA3-384",
                "SHA3-512",
                "Skein",
                "SM3",
                "Tiger",
                "Whirlpool",
                "Blake2b",
                "Blake2s",
                "DSTU7564"
        };

        for (String algorithm : algorithms) {
            System.out.println("---------------------------------------------");
            System.out.println("MessageDigest(" + algorithm + ")");
            try {
                MessageDigest md = MessageDigest.getInstance(algorithm, providerName);
                System.out.println("\tdigest length is " + md.getDigestLength());

                Provider provider = md.getProvider();
                System.out.println("\tprovider info: " +
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

    public static void printHash(String message, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashValue = md.digest(message.getBytes());
            String hash = Hex.toHexString(hashValue);
            System.out.println(message + " " + algorithm + "is " + hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void testKeccak() {
        String[] algorithms = {
                "Keccak-224",
                "Keccak-288",
                "Keccak-256",
                "Keccak-384",
                "Keccak-512",
                "SHA3-224",
                "SHA3-256",
                "SHA3-384",
                "SHA3-512"
        };

        String message = "The quick brown fox jumps over the lazy dog";
        for(var alogrithm : algorithms) {
            try {
                MessageDigest md = MessageDigest.getInstance(alogrithm);
                String hash = Hex.toHexString(md.digest(message.getBytes()));
                System.out.println(alogrithm + " of " + message  + " is " + hash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
