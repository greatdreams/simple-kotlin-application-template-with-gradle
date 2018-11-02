package com.greatdreams.learn.reactor.reactivestream.util;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncIterablePublisher<T> implements Publisher<T> {
    private final static int DEFAULT_BATCHSIZE = 1024;

    private final Iterable<T> elements;
    private final Executor executor;
    private final int batchSize;

    public AsyncIterablePublisher(final Iterable<T> elements, final Executor executor) {
        this(elements, DEFAULT_BATCHSIZE, executor);
    }

    public AsyncIterablePublisher(final Iterable<T> elements, final int batchSize, final Executor executor) {
        if(elements == null) throw null;
        if(executor == null) throw null;
        if(batchSize < 1) throw new IllegalArgumentException("batchSize must be greater than zero");
        this.elements = elements;
        this.executor = executor;
        this.batchSize = batchSize;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {

    }

    static interface Signal {};
    enum Cancel implements Signal { Instance; };
    enum Subscribe implements Signal { Instance; };
    enum Send implements Signal { Instance; };

    static final class Request implements Signal {
        final long n;

        Request(final long n) {
            this.n = n;
        }
    };

    final class SubscriptionImpl implements Subscription, Runnable {
        final Subscriber<? super T> subscriber;
        private boolean cancelled = false;
        private long demand = 0;
        private Iterator<T> iterator;

        public SubscriptionImpl(final Subscriber<? super T> subscriber) {
            if(subscriber == null) throw null;
            this.subscriber = subscriber;
        }

        private final ConcurrentLinkedDeque<Signal> inboundSignals = new ConcurrentLinkedDeque<>();

        private final AtomicBoolean on = new AtomicBoolean(false);

        // This method will register inbound demand from our `Subscriber` and validate it against rule 3.9 and rule 3.17
        private void doRequest(final long n) {
            if(n<1)
                terminateDueTo(new IllegalArgumentException(subscriber + " violated the Reactive Streams rule 3.9 by requesting a non-positive number of elements."));
            else if(demand + n < 1) {
                // As governed by rule 3.17, when demand overflows `Long.MAX_VALUE` we treat the signalled demand as "effectively unbounded"
                demand = Long.MAX_VALUE; // Here we protect from the overflow and treat it as "effectively unbounded"
                doSend(); // Then we proceed with sending data downstream
            }else {
                demand += n; // Here we record the downstream demand
                doSend(); // Then we can proceed with sending data downstream
            }
        }

        private void doCancel() {
            cancelled = true;
        }

        private void doSubscribe() {
            try {
                iterator = elements.iterator();
                if( iterator == null)
                    iterator = Collections.<T>emptyList().iterator();
            }catch (final Throwable t) {
                // We need to make sure we signal onSubscribe before onError, obeying rule 1.9
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {

                    }

                    @Override
                    public void cancel() {

                    }
                });
                terminateDueTo(t);
            }

            if(!cancelled) {
                // Deal with setting up the subscription with the subscriber
                try {
                    subscriber.onSubscribe(this);
                }catch (final Throwable t) {
                    terminateDueTo(new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from onSubscribe.", t));
                }

                // Deal with already complete iterators promptly
                boolean hasElements = false;

                try {
                    hasElements = iterator.hasNext();
                }catch (final Throwable t) {
                    terminateDueTo(t); // If hasNext throws, there's something wrong and we need to signal onError as per 1.2, 1.4,
                }

                if(!hasElements) {
                    try {
                        doCancel(); // Rule 1.6 says we need to consider the `Subscription` cancelled when `onComplete` is signalled
                        subscriber.onComplete(); // As per rule 2.13, `onComplete` is not allowed to throw exceptions, so we do what we can, and log this.
                    }catch (final Throwable t) {
                        (new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from onComplete.", t)).printStackTrace(System.err);
                    }
                }
            }
        }

        private void doSend() {
            try {
                // In order to play nice with the `Executor` we will only send at-most `batchSize` before
                // rescheduing ourselves and relinquishing the current thread.
                int leftInBatch = batchSize;

                do {
                    T next;
                    boolean hasNext;
                    try {
                        next = iterator.next();
                        hasNext = iterator.hasNext();
                    } catch (final Throwable t) {
                        terminateDueTo(t);
                        return;
                    }

                    subscriber.onNext(next);

                    if(!hasNext) {
                        doCancel(); // We need to consider this `Subscription` as cancelled as per rule 1.6
                        subscriber.onComplete(); // Then we signal `onComplete` as per rule 1.2 and 1.5
                    }
                }while(!cancelled // This makes sure that rule 1.8 is upheld, i.e. we need to stop signalling "eventually"
                && --leftInBatch > 0 // This makes sure that we only send `batchSize` number of elements in one go (so we can yield to other Runnables)
                && --demand > 0); // // This makes sure that rule 1.1 is upheld (sending more than was demanded)

                if(!cancelled && demand > 0) {
                    signal(Send.Instance);
                }
            }catch (final Throwable t) {
                // We can only get here if `onNext` or `onComplete` threw, and they are not allowed to according to 2.13, so we can only cancel and log here.
                doCancel(); // Make sure that we are cancelled, since we cannot do anything else since the `Subscriber` is faulty.
                (new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from onNext or onComplete.", t)).printStackTrace(System.err);
            }
        }


        //  What `signal` does is that it sends signals to the `Subscription` asynchronously
        private void signal(final Signal signal) {
            if(inboundSignals.offer(signal)) // No need to null-check here as ConcurrentLinkedQueue does this for us
                tryScheduleToExecute(); // Then we try to schedule it for execution, if it isn't already
        }

        // This method makes sure that this `Subscription` is only running on one Thread at a time,
        // this is important to make sure that we follow rule 1.3
        private final void tryScheduleToExecute() {
            if(on.compareAndSet(false, true)) {
                try {
                    executor.execute(this);
                }catch (Throwable t) {  // If we can't run on the `Executor`, we need to fail gracefully
                    if(!cancelled) {
                        doCancel(); // First of all, this failure is not recoverable, so we need to follow rule 1.4 and 1.6
                        try {
                            terminateDueTo(new IllegalStateException("Publisher terminated due to unavailable Executor.", t));
                        } finally {
                            inboundSignals.clear();
                            on.set(false);
                        }
                    }
                }
            }

        }

        // This is the main "event loop" if you so will
        @Override public void run() {
            if(on.get()) { // establishes a happens-before relationship with the end of the previous run
                try {
                    final Signal s = inboundSignals.poll(); // We take a signal off the queue
                    if(!cancelled) { // to make sure that we follow rule 1.8, 3.6 and 3.7
                        // Below we simply unpack the `Signal`s and invoke the corresponding methods
                        if(s instanceof Request)
                            doRequest(((Request)s).n);
                        else if (s == Send.Instance)
                            doSend();
                        else if (s == Cancel.Instance)
                            doCancel();
                        else if(s == Subscribe.Instance)
                            doSubscribe();
                    }
                }finally {
                    on.set(false); // establishes a happens-before relationship with the beginning of the next run
                    if(!inboundSignals.isEmpty()) { // If we still have signals to process
                        tryScheduleToExecute(); // Then we try to schedule ourselves to execute again
                    }
                }
            }
        }

        // Our implementation of `Subscription.request` sends a signal to the Subscription that more elements are in demand
        @Override public void request(long n) {
            signal(new Request(n));
        }

        // Our implementation of `Subscription.cancel` sends a signal to the Subscription that the `Subscriber` is not interested
        @Override
        public void cancel() {
            signal(Cancel.Instance);
        }

        // This is a helper method to ensure that we always `cancel` when we signal `onError` as per rule 1.6
        private void terminateDueTo(final Throwable t) {
            cancelled = true;
            try {
                subscriber.onError(t);
            }catch (final Throwable t2) {
                (new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from onError.", t2)).printStackTrace(System.err);
            }
        }
    }
}
