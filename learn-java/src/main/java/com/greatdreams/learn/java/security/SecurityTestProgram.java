package com.greatdreams.learn.java.security;

import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;

import java.security.Provider;
import java.security.Security;

public class SecurityTestProgram {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleJsseProvider());
        Provider[] providers = Security.getProviders();
        for(Provider provider: providers) {
            System.out.println("---" + provider.getName() + "---");
            System.out.println("Name: " + provider.getName() + "\nversion: " +
                    provider.getVersionStr() + "\ninformation: " +
                    provider.getInfo());
        }
    }
}
