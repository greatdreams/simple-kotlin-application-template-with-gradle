package com.greatdreams.learn.reactor.reactivestream;

import org.reactivestreams.Subscriber;
import org.reactivestreams.example.unicast.AsyncIterablePublisher;
import org.reactivestreams.example.unicast.InfiniteIncrementNumberPublisher;
import org.reactivestreams.example.unicast.SyncSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class Test2 {
    public static void main(String[] args) {
        int batchSize = 2;
        String[] nameList = {"Hello",
                "Main",
                "tomcat",
                "google",
                "Hello",
                "Main",
                "tomcat",
                "google"};

        AsyncIterablePublisher publisher = new AsyncIterablePublisher(Arrays.asList(nameList), batchSize,
                Executors.newFixedThreadPool(2));
        Subscriber<String> subscriber = new SimpleSyncSubscriber();
        publisher.subscribe(subscriber);

        InfiniteIncrementNumberPublisher publisher1 = new InfiniteIncrementNumberPublisher(Executors.newCachedThreadPool());
        publisher1.subscribe(new SimpleSyncSubscriber());
    }
}

class SimpleSyncSubscriber extends SyncSubscriber {
    private static Logger logger = LoggerFactory.getLogger(SimpleSyncSubscriber.class);

    public SimpleSyncSubscriber() {
    }

    @Override
    protected boolean whenNext(Object element) {
        logger.debug(element.toString());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}