##### What's rabbitmq?

RabbitMQ is the most widely deployed open source message broker, implement AMQP-0-9-1.

##### Some Concepts

* Exchange

  Exchanges are AMQP entities where messages are sent. Exchanges take a message and route it into zero or more queues. The routing algorithm used depends on the exchange type and rules called bindings. AMQP 0-9-1 brokers provide four exchange types:
  
  |Name|	Default pre-declared names| Java Type|Descriptions|
  | --- | --- | --- | --- |
  |Direct exchange|	(Empty string) and amq.direct| BuiltinExchangeType.DIRECT|delivers messages to queues based on the message routing key. |
  |Fanout exchange|	amq.fanout|BuiltinExchangeType.FANOUT|routes messages to all of the queues that are bound to it and the routing key is ignored.|
  |Topic exchange|	amq.topic|BuiltinExchangeType.TOPIC| route messages to one or many queues based on matching between a message routing key and the pattern that was used to bind a queue to an exchange. |
  |Headers exchange|	amq.match (and amq.headers in RabbitMQ)|BuiltinExchangeType.HEADERS|designed for routing on multiple attributes that are more easily expressed as message headers than a routing key. |
  
  ```java
    Exchange.DeclareOk channel.exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete,
                                           Map<String, Object> arguments) throws IOException;
  ```
* Queue
 
  they store messages that are consumed by applications. Queues share some properties with exchanges
  
  ```java
  Queue.DeclareOk channel.queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
                                  Map<String, Object> arguments) throws IOException;
  ```
* Binding

  Bindings are rules that exchanges use (among other things) to route messages to queues. To instruct an exchange E to route messages to a queue Q, Q has to be bound to E.  
  
  ```java 
  Queue.BindOk channel.ueueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments) throws IOException;
  ```
 
 * Consumers

   Storing messages in queues is useless unless applications can consume them. In the AMQP 0-9-1 Model, there are two ways for applications to do this:

   * Have messages delivered to them ("push API")
     
     applications have to indicate interest in consuming messages from a particular queue. 
     
     ```java
     String channel.basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException;
     ```
     
   * Fetch messages as needed ("pull API")
 
 * Message Acknowledgements
 
 * Message Attributes and Payload

   Messages in the AMQP model have attributes. 
   
   * Content type
   * Content encoding
   * Routing key
   * Delivery mode (persistent or not)
   * Message priority
   * Message publishing timestamp
   * Expiration period
   * Publisher application id

   AMQP messages also have a payload (the data that they carry), which AMQP brokers treat as an opaque byte array. The broker will not inspect or modify the payload. It is possible for messages to contain only attributes and no payload. It is common to use serialisation formats like JSON, Thrift, Protocol Buffers and MessagePack to serialize structured data in order to publish it as the message payload. AMQP peers typically use the "content-type" and "content-encoding" fields to communicate this information, but this is by convention only.
   
   Messages may be published as persistent, which makes the AMQP broker persist them to disk. If the server is restarted the system ensures that received persistent messages are not lost. 
   
 * Connections
 
   AMQP connections are typically long-lived. AMQP is an application level protocol that uses TCP for reliable delivery. AMQP connections use authentication and can be protected using TLS (SSL). When an application no longer needs to be connected to an AMQP broker, it should gracefully close the AMQP connection instead of abruptly closing the underlying TCP connection.
   
   * ConnectionFactory Convenience factory class to facilitate opening a Connection to a RabbitMQ node.
   * Connection Interface to an AMQ connection.
   
   ```java
    ConnectionFactory factory = new ConnectionFactory();
     factory.setHost(hostName);
     factory.setPort(portNumber);
     factory.setVirtualHost(virtualHost);
     factory.setUsername(username);
     factory.setPassword(password);
     Connection conn = factory.newConnection();
    
     // Then open a channel:
    
     Channel channel = conn.createChannel();
   ```
 
 * Channels
 
   Some applications need multiple connections to an AMQP broker. However, it is undesirable to keep many TCP connections open at the same time because doing so consumes system resources and makes it more difficult to configure firewalls. AMQP 0-9-1 connections are multiplexed with channels that can be thought of as "lightweight connections that share a single TCP connection".

   For applications that use multiple threads/processes for processing, it is very common to open a new channel per thread/process and not share channels between them.

   Communication on a particular channel is completely separate from communication on another channel, therefore every AMQP method also carries a channel number that clients use to figure out which channel the method is for (and thus, which event handler needs to be invoked, for example).
   
   ```java
    ConnectionFactory factory = new ConnectionFactory();
     factory.setHost(hostName);
     factory.setPort(portNumber);
     factory.setVirtualHost(virtualHost);
     factory.setUsername(username);
     factory.setPassword(password);
     Connection conn = factory.newConnection();
    
     // Then open a channel:
    
     Channel channel = conn.createChannel();
   ```

* [Virtual Hosts](http://www.rabbitmq.com/vhosts.html)

  To make it possible for a single broker to host multiple isolated "environments" (groups of users, exchanges, queues and so on), AMQP includes the concept of virtual hosts (vhosts). They are similar to virtual hosts used by many popular Web servers and provide completely isolated environments in which AMQP entities live. AMQP clients specify what vhosts they want to use during AMQP connection negotiation.  
  
    
   ```java
    ConnectionFactory factory = new ConnectionFactory();
     factory.setHost(hostName);
     factory.setPort(portNumber);
     factory.setVirtualHost(virtualHost);
     factory.setUsername(username);
     factory.setPassword(password);
     Connection conn = factory.newConnection();
    
     // Then open a channel:
    
     Channel channel = conn.createChannel();
   ```
   
##### Reference
1. [rabbitmq - homepage](http://www.rabbitmq.com/)   
1. [rabbitmq - github](https://github.com/rabbitmq)
2. [rabbitmq - some concepts](https://www.rabbitmq.com/tutorials/amqp-concepts.html)
3. [rabbitmq - java client tutorial](http://www.rabbitmq.com/api-guide.html)
4. [rabbitmq - java client github](https://github.com/rabbitmq/rabbitmq-java-client)