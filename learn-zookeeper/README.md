##### learn-zookeeper

###### What's ZooKeeper?
Apache ZooKeeper is an effort to develop and maintain an open-source server which enables highly reliable distributed coordination.

ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services. All of these kinds of services are used in some form or another by distributed applications. Each time they are implemented there is a lot of work that goes into fixing the bugs and race conditions that are inevitable. Because of the difficulty of implementing these kinds of services, applications initially usually skimp on them ,which make them brittle in the presence of change and difficult to manage. Even when done correctly, different implementations of these services lead to management complexity when the applications are deployed.

###### What's ZooKeeper Atomic Broadcast(Zab)?

Zab is the ZooKeeper Atomic Broadcast protocol. We use it to propagate state changes produced by the ZooKeeper leader.

##### Reference

1. [zookeeper homepage](https://zookeeper.apache.org/)
2. [How is a leader elected in Apache ZooKeeper?](https://www.quora.com/How-is-a-leader-elected-in-Apache-ZooKeeper)
3. [Zookeeper's atomic broadcast protocol: theory and practice](http://www.tcs.hut.fi/Studies/T-79.5001/reports/2012-deSouzaMedeiros.pdf)
4. [A simple totally ordered broadcast protocol](http://delivery.acm.org/10.1145/1530000/1529978/a2-reed.pdf?ip=106.120.105.61&id=1529978&acc=ACTIVE%20SERVICE&key=887466AE62D23601%2E887466AE62D23601%2E4D4702B0C3E38B35%2E4D4702B0C3E38B35&__acm__=1529746532_31f263076cf86f1c6e9f286c2c0b37ea)
