package com.greatdreams.learn.java.net.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

public class DatagramSocketServer {

    private static Logger logger = LoggerFactory.getLogger(DatagramSocketServer.class);

    public static void main(String[] args) {
        try {

            DatagramSocket server = new DatagramSocket(30500, Inet4Address.getByName("0.0.0.0"));
            logger.info("start udp server at 0.0.0.0:30500!");

            while(true) {
                byte[] data = new byte[512];
                DatagramPacket dp = new DatagramPacket(data, data.length);
                server.receive(dp);
                if(dp != null) {
                    logger.info("received data  of length " + dp.getLength() +
                            " from " + dp.getAddress() + ":" + dp.getPort() + ", data detail: " +
                            new String(dp.getData(), 0, dp.getLength()));
                }

                data = "udp server data".getBytes();
                dp = new DatagramPacket(data, data.length, dp.getAddress(), dp.getPort());
                server.send(dp);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
