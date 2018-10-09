package com.greatdreams.learn.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    private static String HOST = "0.0.0.0";
    private static int PORT = 8888;

    private static final char[] passphrase = "123456".toCharArray();
    private static final String keystoreFilePath = "src/main/resources/serverkeystore";
    private static final String clientKeystoreFilePath = "src/main/resources/clientkeystore";
    private static final String[] enabledCipherSuites = {"TLS_RSA_WITH_AES_256_CBC_SHA256"};

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException {


        logger.debug("start server 0.0.0.0:8888");
        /*
        SSLContext ctx = SSLContext.getInstance("TLS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(new File(keystoreFilePath)), passphrase);
        kmf.init(ks, passphrase);

        KeyStore serverKey = KeyStore.getInstance("JKS");
        serverKey.load(new FileInputStream(new File(clientKeystoreFilePath)), passphrase);
        TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
        trustManager.init(serverKey);

        ctx.init(kmf.getKeyManagers(), trustManager.getTrustManagers(), null);
        */

        /*
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        // SSLServerSocketFactory factory = ctx.getServerSocketFactory();
        SSLServerSocket sslServerSocket = (SSLServerSocket)factory.createServerSocket(8888);
        // sslServerSocket.setEnabledCipherSuites(enabledCipherSuites);
        // sslServerSocket.setNeedClientAuth(false);

        while(true) {
            try {
                SSLSocket socket = (SSLSocket) sslServerSocket.accept();
                InputStream input = socket.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader bufferedReader = new BufferedReader(reader);

                OutputStream output = socket.getOutputStream();

                String str = null;

                while ((str = bufferedReader.readLine()) != null) {
                    System.out.println(str);
                    System.out.flush();

                    output.write("sever reply: ".getBytes());
                    output.write(str.getBytes());
                    output.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

                /*
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
        */

        HttpsURLConnection conn;
        SSLContextTest.main(args);
    }
}
