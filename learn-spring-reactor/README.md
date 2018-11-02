###### What's projectreactor?

Reactor is a fourth-generation Reactive library for building non-blocking applications on
the JVM based on the Reactive Streams Specification.

Reactor is a fully non-blocking reactive programming foundation for the JVM, with efficient demand management 
(in the form of managing "backpressure"). It integrates directly with the Java 8 functional APIs, notably 
CompletableFuture, Stream, and Duration. It offers composable asynchronous sequence APIs Flux (for [N] elements)
 and Mono (for [0|1] elements), extensively implementing the Reactive Extensions specification.

Reactor also supports non-blocking inter-process communication (IPC) with the reactor-ipc components.
 Suited for Microservices Architecture, Reactor IPC offers backpressure-ready network engines for HTTP 
 (including Websockets), TCP, and UDP. Reactive Encoding and Decoding are fully supported.
 
 
###### [reactive-streams-jvm](https://github.com/reactive-streams/reactive-streams-jvm)

Reactive Streams is a standard and specification for Stream-oriented libraries for the JVM that

* process a potentially unbounded number of elements
* in sequence,
* asynchronously passing elements between components,
* with mandatory non-blocking backpressure.

The Reactive Streams specification consists of the following parts:

**The API** specifies the types to implement Reactive Streams and achieve interoperability between different implementations.

**The Technology Compatibility Kit (TCK)** is a standard test suite for conformance testing of implementations.

Implementations are free to implement additional features not covered by the specification as long as they conform to the API requirements and pass the tests in the TCK.

###### API Components

  The API consists of the following components that are required to be provided by Reactive Stream implementations:
        
  * Publisher
  * Subscriber
  * Subscription
  * Processor

   ```java
public interface Publisher<T> {
    public void subscribe(Subscriber<? super T> s);
}

public interface Subscriber<T> {
    public void onSubscribe(Subscription s);
    public void onNext(T t);
    public void onError(Throwable t);
    public void onComplete();
}

public interface Subscription {
    public void request(long n);
    public void cancel();
}

public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
}
```
###### Reference
1. [Reactor 3 Reference Guide](https://projectreactor.io/docs/core/release/reference/)
2. [Reactor projects collection](https://github.com/reactor)
3. [Reactor document](https://projectreactor.io/docs)