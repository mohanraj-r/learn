# [Effective Java, 3rd Edition](https://learning.oreilly.com/library/view/effective-java-3rd/9780134686097/)

- [Effective Java, 3rd Edition](#effective-java-3rd-edition)
  - [1 Introduction](#1-introduction)
  - [2 Creating and Destroying Objects - Effective Java, 3rd Edition](#2-creating-and-destroying-objects---effective-java-3rd-edition)
    - [ITEM 1: CONSIDER STATIC FACTORY METHODS INSTEAD OF CONSTRUCTORS](#item-1-consider-static-factory-methods-instead-of-constructors)
  - [11 Concurrency - Effective Java, 3rd Edition](#11-concurrency---effective-java-3rd-edition)
    - [ITEM 78: SYNCHRONIZE ACCESS TO SHARED MUTABLE DATA](#item-78-synchronize-access-to-shared-mutable-data)
    - [ITEM 79: AVOID EXCESSIVE SYNCHRONIZATION](#item-79-avoid-excessive-synchronization)
    - [ITEM 80: PREFER EXECUTORS, TASKS, AND STREAMS TO THREADS](#item-80-prefer-executors-tasks-and-streams-to-threads)

## [1 Introduction](https://learning.oreilly.com/library/view/effective-java-3rd/9780134686097/ch1.xhtml)

* The language supports four kinds of types: 
    * *interfaces* (including *annotations*), 
    * *classes* (including *enums*), 
    * *arrays*, and 
    * *primitives*. 
* The first three are known as *reference types*. 
    * Class instances and arrays are *objects*; 
        * primitive values are not. 
* A class’s *members* consist of its *fields*, *methods*, *member classes*, and *member interfaces.* 
* A method’s *signature* consists of its name and the types of its formal parameters; 
    * the signature does *not* include the method’s return type.

## [2 Creating and Destroying Objects - Effective Java, 3rd Edition](https://learning.oreilly.com/library/view/effective-java-3rd/9780134686097/ch2.xhtml)

### ITEM 1: CONSIDER STATIC FACTORY METHODS INSTEAD OF CONSTRUCTORS

* One advantage of static factory methods is that, unlike constructors, they have names.
* A class can have only a single constructor with a given signature.
    * Because they have names, static factory methods don’t share the restriction
    * In cases where a class seems to require multiple constructors with the same signature, 
        * replace the constructors with static factory methods and carefully chosen names to highlight their differences.
* A second advantage of static factory methods is that, unlike constructors, they are not required to create a new object each time they’re invoked
    * This allows immutable classes (Item 17) to use preconstructed instances,
    * or to cache instances as they’re constructed, 
    * and dispense them repeatedly to avoid creating unnecessary duplicate objects.
* Instance control allows a class to guarantee that it is a singleton (Item 3) or noninstantiable (Item 4).
* A third advantage of static factory methods is that, unlike constructors, they can return an object of any subtype of their return type


## [11 Concurrency - Effective Java, 3rd Edition](https://learning.oreilly.com/library/view/effective-java-3rd/9780134686097/ch11.xhtml)

### ITEM 78: SYNCHRONIZE ACCESS TO SHARED MUTABLE DATA
* confine mutable data to a single thread.
* The `synchronized` keyword ensures that only a single thread can execute a method or block at one time. 
  * ensures that each thread entering a synchronized method or block sees the effects of all previous modifications that were guarded by the same lock.
* Synchronization is required for reliable communication between threads as well as for mutual exclusion.
* Synchronization is not guaranteed to work unless both read and write operations are synchronized.
* the `volatile` modifier performs no mutual exclusion, it guarantees that any thread that reads the field will see the most recently written value
  * If you need only inter-thread communication, and not mutual exclusion, the volatile modifier is an acceptable form of synchronization
  * but it can be tricky to use correctly.
* `java.util.concurrent.atomic` provides primitives for lock-free, thread-safe programming on single variables. 
  * While `volatile` provides only the communication effects of synchronization, this package also provides atomicity. 
  * likely to outperform the synchronized version
* Do not use `Thread.stop()`
* In summary, when multiple threads share mutable data, each thread that reads or writes the data must perform synchronization
* The penalties for failing to synchronize shared mutable data are liveness and safety failures. 
  * These failures are among the most difficult to debug. 
  * They can be intermittent and timing-dependent, and program behavior can vary radically from one VM to another.

### ITEM 79: AVOID EXCESSIVE SYNCHRONIZATION
* you should do as little work as possible inside synchronized regions.
  * If you must perform some time-consuming activity, find a way to move it out of the synchronized region without violating the guidelines in Item 78.
* If a method modifies a static field and there is any possibility that the method will be called from multiple threads, you must synchronize access to the field internally
* the real cost of excessive synchronization is not the CPU time spent getting locks; it is contention: 
  * the lost opportunities for parallelism and the delays imposed by the need to ensure that every core has a consistent view of memory. 
  * Another hidden cost of oversynchronization is that it can limit the VM’s ability to optimize code execution.
* within a synchronized method or block never cede control to the client (caller) to avoid liveness and safety failures
  * do not invoke a method that is designed to be overridden, 
  * or one provided by a client in the form of a function object 
* `CopyOnWriteArrayList` is a variant of ArrayList in which all modification operations are implemented by making a fresh copy of the entire underlying array.
  * it’s perfect for observer lists, which are rarely modified and often traversed.


### ITEM 80: PREFER EXECUTORS, TASKS, AND STREAMS TO THREADS
