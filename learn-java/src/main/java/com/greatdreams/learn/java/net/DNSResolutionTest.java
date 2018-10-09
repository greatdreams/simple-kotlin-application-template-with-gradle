package com.greatdreams.learn.java.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSResolutionTest {
    public static void main(String[] args) {
        try {
            InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
            for(InetAddress address : addresses) {
                System.out.println(address.getHostAddress() + ": " + address.getCanonicalHostName() + ":"
                + address.getHostName());
            }

            System.out.println("----------------------");
            InetAddress address = InetAddress.getByName("www.baidu.com");
            System.out.println(address.getHostAddress());

            System.out.println("-----------------------");
            InetAddress localAdress = InetAddress.getLocalHost();
            System.out.println(localAdress.toString());

            System.out.println("-----------------------");
            InetAddress loopbackAdress = InetAddress.getLoopbackAddress();
            System.out.println(loopbackAdress.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
