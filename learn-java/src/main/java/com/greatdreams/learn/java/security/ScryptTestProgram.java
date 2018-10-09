package com.greatdreams.learn.java.security;

import org.bouncycastle.crypto.generators.SCrypt;

import javax.crypto.KeyGenerator;
import java.util.Base64;

/**
 * scrypt - is a password-based key derivation function created by Colin Percival
 * https://en.wikipedia.org/wiki/Scrypt
 */
public class ScryptTestProgram {
    public static void main(String[] args) {
        var password = "123456";
        var salt = "0621f185e1ba732d";

        var beginTime = System.currentTimeMillis();
        byte[] resBytes = SCrypt.generate(password.getBytes(),
                salt.getBytes(),
                16384,
                8,
                8,
                192);
        var key = Base64.getEncoder().encode(resBytes);
        System.out.println(key);

        var endTime = System.currentTimeMillis();

        var time = (endTime - beginTime) / (1000 * 1000.00);

        System.out.println("total time: " + time);
    }

}
