package com.greatdreams.learn.java.security;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import java.security.*;

public class CipherTestProgram {
    private static Logger logger = LoggerFactory.getLogger(CipherTestProgram.class);

    public static void main(String[] args) {
        String[] transformations = {
                "AES/CBC/NoPadding",
                "AES/CBC/PKCS5Padding", // (128)
                "AES/ECB/NoPadding", // (128)
                "AES/ECB/PKCS5Padding", // (128)
                "AES/GCM/NoPadding", // (128)
                "DES/CBC/NoPadding", // (56)
                "DES/CBC/PKCS5Padding", // (56)
                "DES/ECB/NoPadding", // (56)
                "DES/ECB/PKCS5Padding", // (56)
                "DESede/CBC/NoPadding", // (168)
                "DESede/CBC/PKCS5Padding", // (168)
                "DESede/ECB/NoPadding", // (168)
                "DESede/ECB/PKCS5Padding", //(168)
                "RSA/ECB/PKCS1Padding", // (1024, 2048)
                "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", // (1024, 2048)
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" // (1024, 2048)
        };

        for(String transformation: transformations) {
            try {
                System.out.println("-------------------------------------------");
                Cipher cipher = Cipher.getInstance(transformation);
                System.out.println("cipher(" + transformation + ")");
                System.out.println("algorithm: " + cipher.getAlgorithm());
                System.out.println("cipher block size: " + cipher.getBlockSize());

                Provider provider = cipher.getProvider();
                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());

                AlgorithmParameters algorithmParameters = cipher.getParameters();
                if(algorithmParameters != null) {
                    // ouptput information of AlgorithmParameters object
                    String algorithm = algorithmParameters.getAlgorithm();
                    System.out.println("\talgorithm name: " + algorithm);
                    Provider algorithmProvider = algorithmParameters.getProvider();
                    System.out.println("\tAlgorithmParameters Provider info: " +
                            algorithmProvider.getName() + "," +
                            algorithmProvider.getClass() + "," +
                            algorithmProvider.getInfo());
                }

                getAllAlgorithmParameters();

                testAESEncrypt();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        }

    }

    public static void getAllAlgorithmParameters() {
        String[] algorithms = {
                "AES", //	Parameters for use with the AES algorithm.
                "Blowfish", //	Parameters for use with the Blowfish algorithm.
                "ChaCha20-Poly1305", //	Parameters for use with the ChaCha20-Poly1305 algorithm, as defined in RFC 8103.
                "DES", // Parameters for use with the DES algorithm.
                "DESede", // Parameters for use with the DESede algorithm.
                "DiffieHellman", //	Parameters for use with the DiffieHellman algorithm.
                "DSA",// Parameters for use with the Digital Signature Algorithm.
                "EC", // Parameters for use with the EC algorithm.
                "OAEP", // Parameters for use with the OAEP algorithm.
                "PBEWith<digest>And<encryption>", // Parameters for use with PKCS #5 password-based encryption, where <digest> is a message digest, <prf> is a pseudo-random function, and <encryption> is an encryption algorithm. Examples: PBEWithMD5AndDES, and PBEWithHmacSHA256AndAES.
                "PBEWith<prf>And<encryption>",
                "PBE", // Parameters for use with the PBE algorithm. This name should not be used, in preference to the more specific PBE-algorithm names previously listed.
                "RC2", // Parameters for use with the RC2 algorithm.
                "RSASSA-PSS", // Parameters for use with the RSASSA-PSS signature algorithm.
                "XDH", // Parameters for Diffie-Hellman key agreement with elliptic curves as defined in RFC 7748
                "X25519", // Parameters for Diffie-Hellman key agreement with Curve25519 as defined in RFC 7748
                "X448"
        };

        for(String algorithm: algorithms) {
            System.out.println("=======================================");
            try {
                System.out.println("AlgorithmParameters(" + algorithm + ")");
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(algorithm);
                if(algorithmParameters != null) {
                    // ouptput information of AlgorithmParameters object
                    String algorithm1 = algorithmParameters.getAlgorithm();
                    System.out.println("\talgorithm name: " + algorithm1);
                    Provider algorithmProvider = algorithmParameters.getProvider();
                    System.out.println("\tAlgorithmParameters Provider info: " +
                            algorithmProvider.getName() + "," +
                            algorithmProvider.getClass() + "," +
                            algorithmProvider.getInfo());
                }

            } catch (NoSuchAlgorithmException e) {
                System.out.println("\t no such algorithm exception");
            }
        }
    }

    // encrypt and dencrypt a message with AES algorithm
    public static void testAESEncrypt() {
        System.out.println("--------------testAESEncrypt-----------------");

        String plainText = "This is a plaintext message";
        String cipherText;

        try {
            // generate secret key with AES algorithm
            // AES keysize must be equal to 128, 192, 256
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(256,secureRandom);
            SecretKey key = keyGenerator.generateKey();

            //create a cipher object
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //initiate it
            cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
            // encrypt message
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            cipherText = Hex.toHexString(encryptedBytes);

            //create another cipher object
            Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //initiate it
            decryptCipher.init(Cipher.DECRYPT_MODE, key, secureRandom);
            //decrypt message
            byte[] decryptBytes = decryptCipher.doFinal(encryptedBytes);
            String decryptText = new String(decryptBytes);

            System.out.println("plain text: " + plainText);
            System.out.println("cipher text: " + cipherText);
            System.out.println("decrypted text: " + decryptText);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }


    }
}
