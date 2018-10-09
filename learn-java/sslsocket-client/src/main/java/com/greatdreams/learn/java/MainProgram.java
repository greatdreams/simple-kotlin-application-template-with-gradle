package com.greatdreams.learn.java;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;

public class MainProgram {
    private static String SERVERHOST = "127.0.0.1";
    private static int PORT = 8888;

    private static final char[] passphrase = "123456".toCharArray();
    private static final String keystoreFilePath = "src/main/resources/serverkeystore";
    private static final String clientKeystoreFilePath = "src/main/resources/clientkeystore";
    private static final String[] enabledCipherSuites = {"TLS_RSA_WITH_AES_256_CBC_SHA256"};

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        System.out.println("start to connect server(127.0.0.1:8888)");

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

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        // SSLSocketFactory sslSocketFactory = ctx.getSocketFactory();

        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(SERVERHOST, PORT);

        InputStream input = System.in;

        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader bufferedReader = new BufferedReader(reader);

        OutputStream outputStream = sslSocket.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter writer = new BufferedWriter(streamWriter);

        String str = null;

        /*
        while((str = bufferedReader.readLine()) != null) {
            writer.write(str + '\n');
            writer.flush();
        }
        */

        writer.write("Hello ssl socket\n");
        writer.flush();

        /*
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

    }
}
