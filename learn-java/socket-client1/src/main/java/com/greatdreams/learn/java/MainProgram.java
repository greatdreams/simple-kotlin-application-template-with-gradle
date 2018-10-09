package com.greatdreams.learn.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class MainProgram {
    public static void main(String[] args) {

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

    }
}
