package com.greatdreams.learn.reactor.reactivestream;

import org.reactivestreams.example.unicast.AsyncSubscriber;
import org.reactivestreams.example.unicast.RangePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
    public static void main(String []args) {
        Executor executor = Executors.newSingleThreadExecutor();
        RangePublisher rangePublisher = new RangePublisher(-1, 100);
        rangePublisher.subscribe(new TestAsyncSubscriber(executor));
    }
}

class TestAsyncSubscriber extends AsyncSubscriber {
    Logger logger = LoggerFactory.getLogger(TestAsyncSubscriber.class);

    public TestAsyncSubscriber(Executor executor) {
        super(executor);

    }

    @Override
    protected boolean whenNext(Object element) {
        logger.debug(element.toString());
        return true;
    }
}