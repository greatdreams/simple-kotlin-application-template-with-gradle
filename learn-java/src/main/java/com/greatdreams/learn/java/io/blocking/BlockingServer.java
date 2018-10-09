package com.greatdreams.learn.java.io.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;

public class BlockingServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            FileChannel outChannel = FileChannel.open(Paths.get("2.txt"));
            server.bind(new InetSocketAddress(6666));
            SocketChannel client = server.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(client.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

            buffer.put("content has been uploaded".getBytes());
            buffer.flip();
            client.write(buffer);

            outChannel.close();
            client.close();
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
