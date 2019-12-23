# [Advanced JavaScript | Live Training](https://learning.oreilly.com/live-training/courses/advanced-javascript/0636920323013/)

Prototypes, closures, promises, async/await, IIFEs, and many other concepts holding you back

- [Advanced JavaScript | Live Training](#advanced-javascript--live-training)
  - [Resources](#resources)
  - [Objects, Hoisting and Execution](#objects-hoisting-and-execution)
    - [Objects](#objects)
    - [Execution context](#execution-context)

## [Resources](./resources)
* [Slides](./resources/advanced-javascript-slides.pdf)
* [JavaScript: The Definitive Guide, 6th Edition](https://learning.oreilly.com/library/view/javascript-the-definitive/9781449393854/)
* [Eloquent JavaScript, 3rd Edition](https://learning.oreilly.com/library/view/eloquent-javascript-3rd/9781492071198/)


## Objects, Hoisting and Execution

### Objects
* Collection of `key : value` pairs
  * called as properties
* value can be 
  * data
  * another object 
  * function
* Objects in default JS Browser env
  * global object (window)
  * this (window)
  * outer environment (null)
    * at window level there is no outer env
* All objects in global scope are created in the window object

### Execution context
* Execution context is the environment in which the JS code is executed
  * This environment comprises of variables, objects and functions 
    * available to the JS code being executed
* 2 important contexts 
  * global execution context (default)
  * functional execution context
* Execution stack
  * LIFO struct used to store all execution contexts