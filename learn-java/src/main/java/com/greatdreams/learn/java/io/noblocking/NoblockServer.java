package com.greatdreams.learn.java.io.noblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;

public class NoblockServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();

            server.configureBlocking(false);

            server.bind(new InetSocketAddress(6666));

            Selector selector = Selector.open();

            server.register(selector, SelectionKey.OP_ACCEPT);

            while(selector.select() > 0 ) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if(key.isAcceptable()) {
                        SocketChannel client = server.accept();

                        client.register(selector, SelectionKey.OP_READ);
                    }else if(key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        FileChannel outChannel = FileChannel.open(Paths.get("2.txt"));
                        while(client.read(buffer) != -1) {
                            buffer.flip();
                            outChannel.write(buffer);
                            buffer.clear();
                        }
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
