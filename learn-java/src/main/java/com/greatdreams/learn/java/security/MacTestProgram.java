package com.greatdreams.learn.java.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Set;

/**
 * @author greatdreams(871155310 @ qq.com)
 * 2018-07-12
 * MAC Message Authentication Code
 * HMAC hash-based message authentication code
 */
public class MacTestProgram {

    private static Logger logger = LoggerFactory.getLogger(MacTestProgram.class);

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            System.out.println("===================================");
            System.out.println("Provider info: " +
                    provider.getName() + "," +
                    provider.getClass() + "," +
                    provider.getInfo());

            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                if (service.getType().equals(Mac.class.getSimpleName())) {
                    System.out.println("\t----------");
                    System.out.println("\t" + service.getAlgorithm() + " : " +
                            service.getType() + " : " + service.getClassName());
                }

            }
        }

        showMACUsageWithHmacSHA256();
    }

    /**
     * just show how to generate MAC using HMACSHA256
     * https://en.wikipedia.org/wiki/HMAC
     */
    public static void showMACUsageWithHmacSHA256() {
        String message = "The quick brown fox jumps over the lazy dog";
        String[] algorithms = {
                "HmacMD5",
                "HmacSHA1",
                "HmacSHA256",
                "HmacSHA384",
                "HmacSHA512"
        };

        for(String algorithm: algorithms) {
            try {
                Mac mac = Mac.getInstance(algorithm);

                //initiate mac object
                SecretKeySpec key = new SecretKeySpec("key".getBytes(), "AES");
                mac.init(key);

                // generate hmac value
                byte[] macBytes = mac.doFinal(message.getBytes());
                System.out.println(algorithm + " of " + message + " is " + Hex.toHexString(macBytes));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
    }
}
