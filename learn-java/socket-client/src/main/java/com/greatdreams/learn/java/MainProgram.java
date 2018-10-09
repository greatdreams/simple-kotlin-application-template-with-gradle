package com.greatdreams.learn.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String[] args) {
        /*
        System.out.println("start to connect server(127.0.0.1:9999)");
        try {
            SocketChannel socketChannel = SocketChannel.open();
            // connect to a server
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

            System.out.println("begin to send data to  server(127.0.0.1:9999)");
            // write data to server
            byte[] message = "我爱你".getBytes(Charset.forName("UTF-8"));
            ByteBuffer writeByte = ByteBuffer.allocate(message.length);
            writeByte.clear();
            writeByte.put(message);
            writeByte.flip();
            while(writeByte.hasRemaining()) {
                socketChannel.write(writeByte);
            }
            System.out.println("begin to receive from server(127.0.0.1:9999)");
            // read data from server
            ByteBuffer readBuffer = ByteBuffer.allocate(50);
            int byteRead = socketChannel.read(readBuffer);
            System.out.println(new String(readBuffer.array(), Charset.forName("UTF-8")));

            System.out.println("close connection from server(127.0.0.1:9999)");
            socketChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        testWithSelector();
    }

    public static void testWithSelector() {
        try {
            Selector selector = Selector.open();
            for(int i = 0; i < 20; i++) {
                SocketChannel socketChannel = SocketChannel.open();
                // connect to a server
                socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
            }
            while(true) {
                int readyChanels = selector.select();
                if(readyChanels == 0)
                    continue;
                Set<SelectionKey> keySet = selector.selectedKeys();
                for(SelectionKey key: keySet) {
                    SocketChannel channel = (SocketChannel)key.channel();
                    SocketAddress localAddress = channel.getLocalAddress();
                    SocketAddress remoteAddress = channel.getRemoteAddress();
                    if(key.isWritable()) {
                        for(int j = 10; j > 0; j--){
                            // write data to server
                            String clientData = "我爱你 " + j;
                            byte[] message = clientData.getBytes(Charset.forName("UTF-8"));
                            logger.info("client from " + localAddress + " send data '" + clientData +
                                    "' to server(" + remoteAddress + ")");
                            ByteBuffer writeByte = ByteBuffer.allocate(message.length);
                            writeByte.clear();
                            writeByte.put(message);
                            writeByte.flip();
                            while (writeByte.hasRemaining()) {
                                ((SocketChannel) channel).write(writeByte);
                            }
                        }
                    }
                    if(key.isReadable()) {
                        // read data from server
                        ByteBuffer readBuffer = ByteBuffer.allocate(512);
                        int byteRead = channel.read(readBuffer);
                        logger.info("client from " + localAddress + " received data '" +
                                new String(readBuffer.array(), Charset.forName("UTF-8")) +
                                "' from server(" + remoteAddress + ")");
                    }
                    if(key.isAcceptable()) {
                    }
                    if(key.isConnectable()) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
