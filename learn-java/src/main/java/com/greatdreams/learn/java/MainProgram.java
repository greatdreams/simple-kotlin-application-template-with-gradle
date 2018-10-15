package com.greatdreams.learn.java;

import com.greatdreams.learn.java.net.udp.DatagramSocketServer;
import com.greatdreams.learn.java.security.ScryptTestProgram;
import com.greatdreams.learn.java.security.*;

import com.greatdreams.learn.java.security.bouncy.GeneratingCertificateTestProgram;
import com.greatdreams.learn.java.security.bouncy.PemReadAndWriteTestProgram;
import org.bouncycastle.operator.OperatorCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.SortedMap;

/**
 * @author greatdreams
 * @since 0.0.1
 */
public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String[] args) throws SocketException, UnknownHostException {
        logger.info("application name: learn-java");
        logger.info("application begins to run");

        // availableCharset();
        // getAllAvailableNetworkInterfaces();
        // byte ip[] = new byte[] { (byte) 10, (byte) 108, (byte)115, (byte)171 };
        // getNetworkInterfaceInfoByAddress(ip);
/*
        SecurityTestProgram.main(args);
        System.out.println("------------------");
        KeyStoreTestProgram.main(args);
        System.out.println("------------------");
        Thread thread;
*/

        // DNSResolutionTest.main(args);
        // KeyGeneratorTestProgram.main(args);
        // KeyPairGeneratorTestProgram.main(args);
        // KeyFactoryTestProgram.main(args);
        // KeyStoreTestProgram.main(args);
        // CipherTestProgram.main(args);
        // AESEncryptionTestProgram.main(args);
        // KeyAgreementTestProgram.main(args);
        // MacTestProgram.main(args);
         MessageDigestTestProgram.main(args);
        // FontListDemo.main(args);
        // CharsetTestProgram.main(args);
        // LocaleTestProgram.main(args);

        // DSATestProgram.main(args);
        // DigitalSignatureTestProgram.main(args);

        // ECCTestProgram.main(args);
        // ECCJDKTestProgram.main(args);
        // ECCEncryptAndDecryptProgram.main(args);

        // DatagramSocketServer.main(args);

        // ScryptTestProgram.main(args);

        // SecureRandomTestProgram.main(args);

        // PemReadAndWriteTestProgram.main(args);

        // KeyPairGeneratorTestProgram.main(args);

        /*try {
            GeneratingCertificateTestProgram.main(args);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        logger.info("application exit normally");
    }

    public static void availableCharset() {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for(String key: charsets.keySet()) {
            logger.debug(key + ":" + charsets.get(key).displayName());
        }
    }

    public static void getAllAvailableNetworkInterfaces() throws SocketException {
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        while(nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();
            printNetworkInterfaceInfo(nif);
        }
    }
    public static void printNetworkInterfaceInfo(NetworkInterface nif) throws SocketException {
        StringBuffer info = new StringBuffer();
        info.append(nif.getDisplayName() + " ");
        info.append(nif.getName());
        info.append(nif.getInetAddresses() + " ");
        info.append(nif.getHardwareAddress() + " ");
        info.append(nif.getInterfaceAddresses() + " ");
        System.out.println(info);
    }

    public static void getNetworkInterfaceInfoByAddress(byte[] ip) throws UnknownHostException, SocketException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(Inet4Address.getByAddress(ip));
        printNetworkInterfaceInfo(nif);
    }
}
