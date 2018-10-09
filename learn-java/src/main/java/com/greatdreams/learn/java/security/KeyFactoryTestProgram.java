package com.greatdreams.learn.java.security;

import org.bouncycastle.util.encoders.Hex;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyFactoryTestProgram {
    public static void main(String []args) {
        String[] algorithms = {
                "DiffieHellman",// Keys for the Diffie-Hellman KeyAgreement algorithm
                "DSA", // Keys for the Digital Signature Algorithm.
                "RSA", // Keys for the RSA algorithm (Signature/Cipher)
                "EC" // Keys for the Elliptic Curve algorithm.
        };

        for(var algorithm : algorithms) {
            System.out.println("========================================");
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
                System.out.println("KeyFactory(" + algorithm + ")");

                Provider provider = keyFactory.getProvider();
                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            // using RSA algorithm to test key factory

            //generate key pair with algorithm RSA
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048, new SecureRandom());
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();

                // create key factory instance
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                // return rsa key specification from the given key
                RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
                RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

                // This class specifies the set of parameters used to generate an RSA key pair.
                RSAKeyGenParameterSpec rsaKeyGenParameterSpec = (RSAKeyGenParameterSpec) rsaPublicKeySpec.getParams();

                System.out.println("rsa private key specification :(modulus-" + privateKeySpec.getModulus() +
                        ", private exponent: " + privateKeySpec.getPrivateExponent() + ")");

                System.out.println("rsa public key specification :(modules-" + rsaPublicKeySpec.getModulus()+
                        ", public exponent:" + rsaPublicKeySpec.getPublicExponent() + ")");

                if(rsaKeyGenParameterSpec != null) {
                    System.out.println("key size: " + rsaKeyGenParameterSpec.getKeysize() + "public exponent:" +
                            rsaKeyGenParameterSpec.getPublicExponent());
                }

                System.out.println("private key information: " + privateKey.getAlgorithm() + "," +
                        privateKey.getFormat() + "," + Hex.toHexString(privateKey.getEncoded()));

                System.out.println("public key information: " + publicKey.getAlgorithm() + "," +
                        publicKey.getFormat() + "," + Hex.toHexString(publicKey.getEncoded()));


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    }
}
