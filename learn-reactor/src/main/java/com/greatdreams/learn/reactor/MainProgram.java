package com.greatdreams.learn.reactor;

import com.greatdreams.learn.reactor.reactivestream.Test1;
import com.greatdreams.learn.reactor.reactivestream.Test2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String []args) {
        /*
        logger.debug("learn projectreactor library");
        Flux.range(0, 1000)
                .publishOn(Schedulers.parallel())
                .map( a -> a * a )
                .subscribe(System.out::println);

        Flux<Integer> ints = Flux.range(1, 4);
        ints.filter( i -> i % 2 == 0 );
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> {System.out.println("Done");});

        */
        // Test1.main(args);
        Test2.main(args);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
