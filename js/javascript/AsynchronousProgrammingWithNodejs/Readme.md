# [Asynchronous Programming with Node.js | Live Training](https://learning.oreilly.com/live-training/courses/asynchronous-programming-with-nodejs/0636920335153/)

## Async feature in JS
* Built-in objects
  * Promise
  * Generator
* Expressions and operators
  * await
  * yield and yield*
* Statements and declarations
  * async 
  * for await..of

## Legacy
* Callback drawbacks
  * deep nested levels
    * harder to read, debug, maintain
  * lack of execution flow control
    * inability to wait for a function to complete before proceeding
  * Lack of Data and Error Propagation Control

## Promise
* Object that holds a future value
* Created with `new Promise(<function>)`
  * initial state: pending, value: undefined
* promise function takes 2 functions
  * resolve
    * called when result is available
    * state: fulfilled, value: result
  * reject
    * called on error condition
    * state: rejected, value: error
* Handlers
  * add a fullfillment handler
    * `promise.then(handler)`
  * add a rejection handler
    * `promise.then(fullfillmentHandler, rejectionHandler)`

### Promise guarantees
* Promise can only succeed or fail
* Immutable once settled
  * once settled, it stays in the same state
    * subsequent calls to resolve, reject have no effect
* Guarantees to call success or failed callback only once, even when success/failure callback are added to a promise after it is settled. 

### Gotchas
* Unhandled rejections are reported as warnings to console
  * Fixed with cli flags to node


## Async, Await
* `async` funcs always return a promise
* the intitial state of the returned promise
  * state: fulfilled/rejected
  * value: return value/error of function
* can `await` for multiple promises with `Promise.all([..])`
  * helpful to `catch` errors from multiple promises in a single try .. catch blocks


## Nodejs async programming APIs
* scheduling timer APIs
  * setTimeout .. 
* Process
  * process.nextTick()
    * processed immediately after the event loop's current operation is completed
    * high priority queue
    * gets executed regardless of the current phase of the event loop
* async IO APIs
* Pitfalls
  * Memory, IO starvation if not used carefully
  * [The Node.js Event Loop, Timers, and process.nextTick() | Node.js](https://nodejs.org/en/docs/guides/event-loop-timers-and-nexttick/)
* [exercise](./resources/olt_async_programming_with_node/exercises/5_event_loop.js)

### Streams
* allows async handling of large amounts of data in chunks
* bi-directional control 
* allows hundreds of concurrent streams without overwhelming memory

### Iterable and iterator protocols

#### Generators
* defined with `function *`
* `yield`s values 
  * resuming for subsequent invocations of `next()`
* can return Promises for async iterators
  * `for await .. of` loop can be used to iterate over async iterable
* `Readable.from()` can be used to create a stream from any iterable 