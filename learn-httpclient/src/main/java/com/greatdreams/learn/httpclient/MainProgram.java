package com.greatdreams.learn.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainProgram {
    public static void main(String []args) {
        requestWithoutProxy();
    }

    public static void requestWithProxy() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpHost target = new HttpHost("bcv.org.ve", 80, "http");
            HttpHost proxy = new HttpHost("proxy.asec.buptnsrc.com", 8001, "http");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpGet request = new HttpGet("/");
            request.setConfig(requestConfig);
            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpClient.execute(target, request);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());

                String body = EntityUtils.toString(response.getEntity());

            } finally {
                response.close();
            }

        }catch (IOException e) {

        } finally{

        }
    }

    public static void requestWithoutProxy() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpHost target = new HttpHost("bcv.org.ve", 80, "http");
            HttpGet request = new HttpGet("/");
            // request.setConfig(requestConfig);
            System.out.println("Executing request " + request.getRequestLine() + " to " + target);

            CloseableHttpResponse response = httpClient.execute(target, request);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());

                String body = EntityUtils.toString(response.getEntity());
                Document doc = Jsoup.parse(body);
                Element ele = doc.select("div#dolar").select("span").first();

                System.out.println(ele.text());
                double rate = Double.parseDouble(ele.text().split("X")[0].replace(",", "."));
                System.out.println(rate);
            } finally {
                response.close();
            }

        }catch (IOException e) {
            e.printStackTrace();
        } finally{

        }
    }
}
