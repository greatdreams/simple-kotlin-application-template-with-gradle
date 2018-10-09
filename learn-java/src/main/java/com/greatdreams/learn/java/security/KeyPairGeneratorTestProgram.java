package com.greatdreams.learn.java.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import java.security.*;

public class KeyPairGeneratorTestProgram {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        String[] algorithms = {
                "DiffieHellman",
                "DSA",
                "RSA",
                "RSASSA-PSS",
                "EC",
                "XDH",
                "X25519",
                "X448"
        };

        String[] providerNames = {
          "SunEC",
          "BC"
        };

        for(var algorithm : algorithms) {
            for(var providerName: providerNames) {
                System.out.println("==================================");
                try {
                    // KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm, providerName);
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
                    System.out.println("KeyPairGenerator(" + algorithm + ")");

                    Provider provider = keyPairGenerator.getProvider();
                    System.out.println("Provider info: " +
                            provider.getName() + "," +
                            provider.getClass() + "," +
                            provider.getInfo());

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }

        //generate key pair with algorithm RSA
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2047, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            System.out.println("private key information: " + privateKey.getAlgorithm() + "," +
                    privateKey.getFormat() + "," + Hex.toHexString(privateKey.getEncoded()));

            System.out.println("public key information: " + publicKey.getAlgorithm() + "," +
                    publicKey.getFormat() + "," + Hex.toHexString(publicKey.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
