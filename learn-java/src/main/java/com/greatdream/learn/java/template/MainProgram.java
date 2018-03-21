package com.greatdream.learn.java.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.nio.charset.Charset;
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
        byte ip[] = new byte[] { (byte) 10, (byte) 108, (byte)115, (byte)171 };
        getNetworkInterfaceInfoByAddress(ip);

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
