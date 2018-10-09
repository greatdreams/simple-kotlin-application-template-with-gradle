package com.greatdreams.learn.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    private static String HOST = "0.0.0.0";
    private static int PORT = 8888;

    public static void main(String[] args) throws IOException {
        logger.debug("start server 0.0.0.0:8888");

        ServerSocket serverSocket = new ServerSocket();
        SocketAddress address = new InetSocketAddress(HOST, PORT);
        serverSocket.bind(address);

        while(true) {
            Socket socket = serverSocket.accept();
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            byte[] bytes = new byte[1024];
            int n = -1;

            while((n = input.read(bytes)) > 2) {
                System.out.print( n + ", " + new String(Arrays.copyOf(bytes, n), "UTF-8"));
                output.write("server reply: ".getBytes());
                output.write(Arrays.copyOf(bytes, n));
                output.flush();
            }
        }

    }
}
