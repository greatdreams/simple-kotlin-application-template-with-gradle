package com.greatdreams.learn.java.security.ecc;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;


/**
 * @author greatdreams
 * 2018-07-19
 * learn Elliptic Curve Cryptography
 */
public class ECCTestProgram {
    private static Logger logger = LoggerFactory.getLogger(ECCTestProgram.class);

    public static void main(String[] args) {
        logger.info("Learn elliptic curve cryptography");
    }

    /**
     * generate ECC key pair generation with Bouncy Castle Specific API,
     * using explicityly created parameters
     */
    public static void generateKeyPair() {
        ECCurve curve = new ECCurve.Fp(
                new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), // q
                new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), // a
                new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16));

        ECParameterSpec ecParameterSpec = new ECParameterSpec(
                curve,
                curve.decodePoint(Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf")), // G
                new BigInteger("883423532389192164791648750360308884807550341691627752275345424702807307")); // n

        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
            g.initialize(ecParameterSpec, new SecureRandom());
            KeyPair pair = g.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }

    /**
     * generate ECC key pair generation with Bouncy Castle Specific API,
     * using explicityly created parameters
     */
    public static void generateKeyPairFromNamedCurves() {
        ECParameterSpec ecParameterSpec = ECNamedCurveTable.getParameterSpec("prime192v1");
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            keyPairGenerator.initialize(ecParameterSpec, new SecureRandom());
            KeyPair pair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    /**
     * generate ECC key pair generation with Bouncy Castle Specific API, using keyfactory
     * using explicityly created parameters
     */
    public static void generateKeyPairWithKeyFactoryFromExplicitParams() {
        ECCurve curve = new ECCurve.F2m(
                239, // m
                36, // k
                new BigInteger("32010857077C5431123A46B808906756F543423E8D27877578125778AC76", 16), // a
                new BigInteger("790408F2EEDAF392B012EDEFB3392F30F4327C0CA3F31FC383C422AA8C16", 16)); // b
        ECParameterSpec params = new ECParameterSpec(
                curve,
                curve.decodePoint(Hex.decode("0457927098FA932E7C0A96D3FD5B706EF7E5F5C156E16B7E7C86038552E91D61D8EE5077C33FECF6F1A16B268DE469C3C7744EA9A971649FC7A9616305")), // G
                new BigInteger("220855883097298041197912187592864814557886993776713230936715041207411783"), // n
                BigInteger.valueOf(4)); // h

        ECPrivateKeySpec priKeySpec = new ECPrivateKeySpec(
                new BigInteger("145642755521911534651321230007534120304391871461646461466464667494947990"), // d
                params);
        ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(
                curve.decodePoint(Hex.decode("045894609CCECF9A92533F630DE713A958E96C97CCB8F5ABB5A688A238DEED6DC2D9D0C94EBFB7D526BA6A61764175B99CB6011E2047F9F067293F57F5")), // Q
                params);

        KeyFactory f = null;
        try {
            f = KeyFactory.getInstance("ECDSA", "BC");

            PrivateKey  sKey = f.generatePrivate(priKeySpec);
            PublicKey   vKey = f.generatePublic(pubKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


    }

}
