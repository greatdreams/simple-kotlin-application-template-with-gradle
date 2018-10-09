package com.greatdreams.learn.java.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecureRandomTestProgram {
    private static Logger logger = LoggerFactory.getLogger(SecureRandomTestProgram.class);

    private static String[] algorithms = {
            "NativePRNG",
            "NativePRNGBlocking",
            "NativePRNGNonBlocking",
            "PKCS11",
            "SHA1PRNG",
            "Windows-PRNG"
    };

    public static void main(String[] args) {

        int i = 0;
        byte[] randomBytes = new byte[100];

        for (var algorithm : algorithms) {
            try {
                SecureRandom random = SecureRandom.getInstance(algorithm);
                random.setSeed(i++);
                random.nextBytes(randomBytes);
                logger.info("random value is " +  Base64.getEncoder().encodeToString(randomBytes));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        double[] randomDoubles = new double[1000];
        for(var j = 0; j < 1000; j++) {
            randomDoubles[j] = Math.random();
            System.out.print(randomDoubles[j] + " ");
        }
        System.out.println();

        testRandom();
    }

    private static void testRandom() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[100];
        secureRandom.nextBytes(randomBytes);
        logger.info("random value is " +  Base64.getEncoder().encodeToString(randomBytes));
    }
}
