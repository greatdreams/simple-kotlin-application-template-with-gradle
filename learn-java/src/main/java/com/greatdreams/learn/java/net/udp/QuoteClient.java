package com.greatdreams.learn.java.net.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

public class QuoteClient {
    private static Logger logger = LoggerFactory.getLogger(QuoteClient.class);

    public static void main(String[] args) {

        int times = 100;

        while (times > 0) {
            try {
                // get a datagram socket
                DatagramSocket ds = new DatagramSocket();
                // send a request
                byte[] buffer = (times + " - udp request test data").getBytes();
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length,
                        Inet4Address.getByName("127.0.0.1"), 30500);
                ds.send(dp);

                // get response
                byte[] data = new byte[512];
                dp = new DatagramPacket(data, data.length);
                ds.receive(dp);
                // display response
                String result = new String(data, 0, dp.getLength());
                logger.info("receive data - " + result + " from udp server " + dp.getAddress() + ":" + dp.getPort());

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            times--;
            try {
                Thread.sleep((long) (1000 * 1.5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
