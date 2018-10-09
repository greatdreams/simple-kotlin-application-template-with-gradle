package com.greatdreams.learn.java.example;

import com.greatdreams.learn.java.IMessageProcessor;
import com.greatdreams.learn.java.Message;
import com.greatdreams.learn.java.Server;
import com.greatdreams.learn.java.http.HttpMessageReaderFactory;

import java.io.IOException;

public class ExampleTest {

    public static void main(String[] args) throws IOException {

        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";

        byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");

        IMessageProcessor messageProcessor = (request, writeProxy) -> {
            System.out.println("Message Received from socket: " + request.socketId);

            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(httpResponseBytes);

            writeProxy.enqueue(response);
        };
        Server server = new Server(9999, new HttpMessageReaderFactory(), messageProcessor);
        server.start();
    }

}
