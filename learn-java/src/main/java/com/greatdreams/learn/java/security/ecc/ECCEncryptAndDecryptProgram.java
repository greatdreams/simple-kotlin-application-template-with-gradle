package com.greatdreams.learn.java.security.ecc;

import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;

/**
 * @author greatdreams
 * 2018-07-18
 * ECIES - Elliptic Curve Interal Encryptography Schema
 */
public class ECCEncryptAndDecryptProgram {
    private static Logger logger = LoggerFactory.getLogger(ECCEncryptAndDecryptProgram.class);

    private static byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f");
    private static byte[] encoding = Hex.decode("303132333435363738393a3b3c3d3e3f");

    private static String value = "This is a test text.";

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        logger.info("learn to encrypt and decrypt using elliptic curve cryptography-ECIES");

        System.out.println("plain text:" + value);

        KeyPair keyPair = generateKeyPair();

        byte[] secretValueBytes = encrypt(value, keyPair.getPublic());
        String secretValue = Hex.toHexString(secretValueBytes);
        System.out.println("secret text: " + secretValue);

        byte[] plainText = decrypt(secretValue, keyPair.getPrivate());
        System.out.println("decrypted text: " + new String(plainText));

    }

    /**
     * generate key pair with elliptic curve
     * @return keyPair
     */
    private static KeyPair generateKeyPair() {
        KeyPair keyPair = null;

        // initiate elliptic parameters
        EllipticCurve curve = new EllipticCurve(
                new ECFieldFp(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839")), // q
                new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), // a
                new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16)); // b
        ECParameterSpec ecSpec = new ECParameterSpec(
                curve,
                ECPointUtil.decodePoint(curve, Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf")), // G
                new BigInteger("883423532389192164791648750360308884807550341691627752275345424702807307"), // n
                1); // h

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            // keyPairGenerator.initialize(ecSpec, new SecureRandom());
            keyPairGenerator.initialize(256, new SecureRandom());
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * encrypt data
     * @param plainText  it's plain data
     * @param publicKey  public key
     * @return byte array it's secret data
     */
    public static byte[] encrypt(String plainText, PublicKey publicKey) {
        byte[] secretText = null;
        // initiate a cipher object
        try {
            // Cipher cipher = Cipher.getInstance("ECIESwithAES-CBC", "BC");
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            IESParameterSpec iesParameterSpec = new IESParameterSpec(
                    derivation,
                    encoding,
                    128,
                    128, Hex.decode("0001020304050607")
            ); // = new IESParameterSpec("AES128-CBC", "HmacSHA1", null, null);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, iesParameterSpec);
            cipher.update(plainText.getBytes());
            secretText = cipher.doFinal();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return secretText;
    }

    /**
     * decrypt data using private key
     * @param secretText    it's secret text using hex encoding
     * @param privateKey    it's private key
     * @return byte array it's plain text
     */
    public static byte[] decrypt(String secretText, PrivateKey privateKey) {
        byte[] plainText = null;

        try {
            // Cipher cipher = Cipher.getInstance("ECIESwithAES-CBC", "BC");
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            IESParameterSpec iesParameterSpec = new IESParameterSpec(
                    derivation,
                    encoding,
                    128,
                    128, Hex.decode("0001020304050607")
            );
            cipher.init(Cipher.DECRYPT_MODE, privateKey, iesParameterSpec);
            cipher.update(Hex.decode(secretText));
            plainText = cipher.doFinal();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return plainText;
    }
}
