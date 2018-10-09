package com.greatdreams.learn.java.security;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESEncryptionTestProgram {
    private static Logger logger = LoggerFactory.getLogger(AESEncryptionTestProgram.class);

    public static void main(String[] args) {
        logger.info("the program is used to encrypt and decrypt message using aes algorithm");

        try {
            // generate secret key with AES algorithm
            // AES keysize must be equal to 128, 192, 256
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new SecureRandom());
            SecretKey key = keyGenerator.generateKey();

            // output key information
            logger.info("secret key information: " +
                    key.getAlgorithm() + "," +
                    key.getFormat() + "," +
                    Hex.toHexString(key.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
