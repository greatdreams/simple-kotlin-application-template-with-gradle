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
import java.util.Set;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String[] args) throws Exception {
        // SSLEngineSimpleDemo.main(args);

        Provider[] providers = Security.getProviders();
        for(Provider provider : providers) {
            System.out.println(provider.getName() + " : " + provider.getClass().getCanonicalName() + ": " +
                    provider.getVersionStr());

            Set<Provider.Service> services = provider.getServices();
            services.forEach(service ->
            {
                System.out.println("\t" + service.getClassName() + ":" + service.getType() + ":" +service.getAlgorithm());
            });
        }
    }


}
