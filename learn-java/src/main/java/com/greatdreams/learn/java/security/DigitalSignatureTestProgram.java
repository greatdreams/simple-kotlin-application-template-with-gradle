package com.greatdreams.learn.java.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;

public class DigitalSignatureTestProgram {
    private static Logger logger = LoggerFactory.getLogger(DigitalSignatureTestProgram.class);

    private static String[] algorithms = {
        "RIPEMD160withRSA"
    };

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            String message = "The quick brown fox jumps over the lazy dog";

            MessageDigest md = MessageDigest.getInstance("RipeMD160");
            String hash = Hex.toHexString(md.digest(message.getBytes()));
            logger.info("hash(RipeMD160) of message is " + hash);

            Signature signature = Signature.getInstance(algorithms[0]);
            Provider provider = signature.getProvider();
            logger.debug("signature algorithm: " + signature.getAlgorithm() + ", provider name :" +
                    provider.getName() + "provider information: " + provider.getInfo());

            KeyPair keyPair = generateRSAKeyPair();
            signature.initSign(keyPair.getPrivate());
            signature.update(message.getBytes());
            byte[] signBytes = signature.sign();
            String signMessage = Hex.toHexString(signBytes);

            signature.initVerify(keyPair.getPublic());
            signature.update(message.getBytes());
            boolean verifiedResult = signature.verify(Hex.decode(signMessage));

            logger.info("signature of message is " + signMessage + " and signature verification " +
                    (verifiedResult ? " is passed" : "  is failed"));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    private static KeyPair generateRSAKeyPair() {
        String algorithm = "RSA";
        KeyPair keyPair = null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(2048, new SecureRandom());
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
    }
}
