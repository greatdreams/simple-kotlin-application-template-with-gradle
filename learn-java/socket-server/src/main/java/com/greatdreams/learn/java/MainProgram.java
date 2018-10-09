package com.greatdreams.learn.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 9999));
            while(true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                SocketAddress clientAddress = socketChannel.getRemoteAddress();

                // read data from client
                ByteBuffer readBuffer = ByteBuffer.allocate(1000);
                socketChannel.read(readBuffer);
                logger.info("receive data '" + new String(readBuffer.array(), Charset.forName("UTF-8")) +
                        " from client " + clientAddress);
                readBuffer.clear();

                String serverData = "Simple server!!!";

                // write data to client
                byte[] message  = serverData.getBytes(Charset.forName("UTF-8"));
                ByteBuffer buffer = ByteBuffer.allocate(message.length);
                buffer.put(message);
                buffer.flip();
                socketChannel.write(buffer);
                // buffer.clear();

                logger.info("send back data " + serverData + " to client " + clientAddress);
                // socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
