package com.greatdreams.learn.java.security.ecc;

import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;

/**
 * @author greatdreams
 * 2018-07-19
 * generate elliptic curve key pair using jdk api
 */
public class ECCJDKTestProgram {
    private Logger logger = LoggerFactory.getLogger(ECCJDKTestProgram.class);

    public static void main(String[] args) {

    }

    public static void generateKeysFromExplicitParameters() {
        EllipticCurve curve = new EllipticCurve(
                new ECFieldFp(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839")), // q
                new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), // a
                new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16)); // b
        ECParameterSpec ecSpec = new ECParameterSpec(
                curve,
                ECPointUtil.decodePoint(curve, Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf")), // G
                new BigInteger("883423532389192164791648750360308884807550341691627752275345424702807307"), // n
                1); // h
        KeyPairGenerator g = null;
        try {
            g = KeyPairGenerator.getInstance("ECDSA", "BC");
            g.initialize(ecSpec, new SecureRandom());
            KeyPair pair = g.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
    } catch (NoSuchProviderException e) {
        e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
        e.printStackTrace();
    }
    }

    public static void generateKeysWithNamedCurvesFromExplicitParameters() {
        ECGenParameterSpec  ecGenSpec = new ECGenParameterSpec("prime192v1");
        KeyPairGenerator  g = null;
        try {
            g = KeyPairGenerator.getInstance("ECDSA", "BC");
            g.initialize(ecGenSpec, new SecureRandom());
            KeyPair pair = g.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }
}
