package com.greatdreams.learn.java;

import org.bouncycastle.util.encoders.Hex;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SSLContextTest {
    public static void main(String []args) {
        // protocols support : https://download.java.net/java/early_access/jdk11/docs/specs/security/standard-names.html#sslcontext-algorithms
       /* String[] protocols = {
                "SSL",
                "SSLv2",
                "SSLv3",
                "TLS",
                "TLSv1",
                "TLSv1.1",
                "TLSv1.2",
                "TLSv1.3", // support since java 11
                "DTLS",
                "DTLSv1.0",
                "STLSv1.2"
        };*/
       String[] protocols = {"TLSv1.3"};

        KeyManager[] keyManagers = getAllKeyManagers();

        for(String protocol: protocols) {
            System.out.println("======================================");
            try {
                SSLContext ctx = SSLContext.getInstance(protocol);

                //init the ssl context
                ctx.init(null, null, new SecureRandom());

                System.out.println("SSLContext(" + protocol + ") instance");
                //output provider information
                Provider provider = ctx.getProvider();
                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());

                SSLParameters sslParameters = ctx.getSupportedSSLParameters();
                StringBuffer cipherList = new StringBuffer();
                cipherList.append(sslParameters.getCipherSuites().length);
                cipherList.append('[');
                for(String cipher: sslParameters.getCipherSuites()) {
                    cipherList.append(cipher);
                    cipherList.append(" ");
                }
                cipherList.append(']');

                System.out.println(" SSLParameter info: cipher suite list - " + cipherList);

                var serverSocket =(SSLServerSocket) ctx.getServerSocketFactory().createServerSocket();


            }catch (NoSuchAlgorithmException | IOException | KeyManagementException e) {
                e.printStackTrace();
            }

            // getAllKeyManagers();
            // getKeyStore();

        }
    }

    private static KeyManager[] getAllKeyManagers() {
        System.out.println("=======================");
        KeyManager[] keyManagers = new KeyManager[10];

        String [] algorithms = {
          "SunX509",
          "PKIX"
        };
        System.out.println("default key manager factory algorithm: " + KeyManagerFactory.getDefaultAlgorithm());

        for(String al: algorithms) {
            System.out.println("=======================");
            try {
                KeyManagerFactory km = KeyManagerFactory.getInstance(al);
                System.out.println("KeyManager(" + al + ")");
                //output provider information
                Provider provider = km.getProvider();
                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());

                //init key manager factory
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return keyManagers;
    }

    private static KeyStore getKeyStore(){
        String [] types = {
                "jceks",
                "jks",
                "dks",
                "pkcs11",
                "pkcs12"
        };
        System.out.println("===================");
        for(String type: types) {
            try {
                KeyStore keyStore = KeyStore.getInstance(type);
                System.out.println("keystore(" + type + ")");

                Provider provider = keyStore.getProvider();
                System.out.println("Provider info: " +
                        provider.getName() + "," +
                        provider.getClass() + "," +
                        provider.getInfo());
            }catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }

        System.out.println("================================");
        try {
            // load keystore file
            String filePath = "src/main/resources/newServerKeyStore";
            char[] password = "123456".toCharArray();
            KeyStore keyStore = KeyStore.getInstance(new File(filePath), password);

            // list all alias name of keystore
            Enumeration<String> alias = keyStore.aliases();
            StringBuffer aliasList = new StringBuffer();

            aliasList.append('[');
            while (alias.hasMoreElements()) {
                String alia = alias.nextElement();
                aliasList.append(alia);
                aliasList.append(" ");

                if (keyStore.isCertificateEntry(alia)) {
                    System.out.println("certificate information:");
                    Certificate[] cers = keyStore.getCertificateChain(alia);
                    for (Certificate cer: cers) {
                        System.out.println(cer.toString());
                    }
                }

                if (keyStore.isKeyEntry(alia)) {
                    System.out.println("key info");
                    Key key = keyStore.getKey(alia, password);
                    System.out.println(key.getAlgorithm() +
                            "," + key.getFormat() + "," +
                            key.getClass().getCanonicalName() + "," +
                            Hex.toHexString(key.getEncoded())
                    );

                }

            }
            aliasList.append(']');

            System.out.println("alias info: " + aliasList);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }


}
