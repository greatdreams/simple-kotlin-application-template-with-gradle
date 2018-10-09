package com.greatdreams.learn.java.security;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

public class KeyGeneratorTestProgram {
    public static void main(String[] args) {
        String[] algorithms = {
                "AES",
                "ARCFOUR",
                "Blowfish",
                "ChaCha20",
                "DES",
                "DESede",
                "HmacMD5",
                "HmacSHA1",
                "HmacSHA224",
                "HmacSHA256",
                "HmacSHA384",
                "HmacSHA512"
        };

        for(var algorithm: algorithms) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
                System.out.println("KeyFactory(" + algorithm + ")");

                Provider provider = keyGenerator.getProvider();
                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        System.out.println("====================================");
        try {
            // generate secret key with AES algorithm
            // AES keysize must be equal to 128, 192, 256
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new SecureRandom());
            SecretKey key = keyGenerator.generateKey();

            // output key information
            System.out.println("secret key information: " +
                    key.getAlgorithm() + "," +
                    key.getFormat() + "," +
                    Hex.toHexString(key.getEncoded()));

            // generate secret key with DES algorithm
            // AES keysize must be equal to 56
            KeyGenerator keyGenerator1 = KeyGenerator.getInstance("DES");
            keyGenerator1.init(56, new SecureRandom());
            SecretKey key1 = keyGenerator1.generateKey();

            // output key information
            System.out.println("secret key information: " +
                    key1.getAlgorithm() + "," +
                    key1.getFormat() + "," +
                    Hex.toHexString(key1.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        generateSecretKey();
    }

    // generate secret key with AES algorithm, try to use SecretKeySpec
    public static SecretKey generateSecretKey() {
        SecretKey secretKey = null;

        String secretValue = "343fakldjfda39";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretValue.getBytes(), "AES");

        System.out.println("secret value length: " + secretValue.length());
        System.out.println("secret key specification: " + secretKeySpec.getEncoded().length);
        return secretKey;
    }
}
