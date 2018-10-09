package com.greatdreams.learn.java.security;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/**
 * http://tutorials.jenkov.com/java-cryptography/keystore.html
 */
public class KeyStoreTestProgram {
    public static void main(String []args) {
        System.out.println("Default kyestore type:" + KeyStore.getDefaultType());
        File kestoreFile = new File("/Users/greatdreams/tmp/keytool_value/keystore.jks");
        char[] password = "changeit".toCharArray();
        try {
            KeyStore keyStore = KeyStore.getInstance(kestoreFile, password);

            System.out.println("keystore path: " + kestoreFile.getAbsolutePath());
            System.out.println("keystore password: " + new String(password));
            System.out.println("keystore type: " + keyStore.getType());

            Enumeration<String> alias = keyStore.aliases();
            alias.asIterator().forEachRemaining( alia -> {
                try {
                    System.out.println(alia + " : " + keyStore.getEntry(alia, new KeyStore.PasswordProtection(password)).getClass().getName());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnrecoverableEntryException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
            });

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

    }
}
