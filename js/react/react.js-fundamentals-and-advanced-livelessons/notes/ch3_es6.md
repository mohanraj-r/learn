
## ES6 features frequently used in React applications
### Template strings
* ```javascript
     // Any JS expression can be used in the template    
     console.log(`hello ${foo} ${bar}`)
  ```

### Default arguments for functions
* ```javascript
    function add(x, y=5) {
      return x+y;
    }
  
    add(2); // 7
  ```

### Rest/Spread for variadic function arguments
* Support for variadic function args
* ```javascript

    // functions can take variadic args using `...`
    function add(...nums) {
      return nums.reduce(function(sum, num) {
          return sum + num
      })
    }
  
    add(1, 2, 3, 4, 5); // 15
  
    add([1, 2, 3, 4, 5]); // result not as expected as we are passing in an arry
    // arrays can be unpacked into args for functions using spread
    add(...[1, 2, 3, 4, 5]); // 15
  ```

### Arrow function
* Shorthand for function
    - especially handy in functional programming
* ```javascript
    function isEven(num) {
      return num % 2 === 0
    }
  
    // becomes
    var isEven = (num) => num % 2 === 0   // `{}` and `return` can be omitted for single statement functions
    
    // which then makes it handy to use in functional programming
    var numbers = [1, 2, 3, 4, 5];
    numbers.filter((num) => num % 2 === 0)
  ```
* Most useful case for arrow functions are how they bind `this`
    - `this` is bound dynamically in normal functions
        - this causes issues with callbacks such as `setTimeouts`
            - when the callback function uses `this`, it gets a different `this` than expected
    - in arrow functions `this` gets lexically scoped
        - so it falls back to using the `this` in the lexical context as expected 

### Destructuring - multiple assignments in a single go
* Multiple assignments in a single statement 
    - using `[]` for arrays
    - using `{}` for objects
* Example
    ```javascript
    var numbers = [1, 2];

    // old style
    var number1 = numbers[0];
    var number2 = numbers[1];

    // new style
    var [number1, number2] = numbers;

    // swapping without a temp var
    [number1, number2] = [number2, number1];

    console.log(number1, number2);
  
    // works similarly for obj properties
    var person = {name: "hi", age:9};

    // create local vars from the object properties
    //  - notice the local vars follow the obj properties
    var {name: localName, age:localAge} = person;
    console.log(localName, localAge);

    // if we want the local vars to be named the same as the object properties
    var {name, age} = person;
    console.log(name, age);
    ```

### Classes
* with es6 classes functions and constructors can be directly defined on the class
    - without using `prototypes` and binding ..
    - classes assume `use strict` mode within their scope
* Example
    ```javascript
        class Creature {
            constructor(name) {
                this.name = name
            }
    
            // functions inside classes don't need the `function` keyword or the `=>` syntax
            sayName() {
                console.log('Class: Creature, Function: sayName, Line 98 (): '
                , this.name);
            }
        }
    
        // Classes can be inherited from
        class Person extends Creature {
            constructor(name, job) {
                super(name)
                this.job = job
            }
    
            sayName() {
                super.sayName()
                console.log('Class: Person, Function: sayName, Line 112 (): '
                , this.job);
            }
        }
    
        var p1 = new Person('bob', 90);
        p1.sayName();
    ```

### Promises
* Constructs to facilitate async programming

#### Traditional callback mechanism in JS
* Event queue
    - works in the form of an event queue
    - all code in current context is executed
        - before the callback function is invoked
        - even if the async operation tied to the callback function has finished
    - if the code in the current context hangs 
        - the callback is not executed
* Nested callbacks
    - can get pretty unreadable pretty quickly

* Promises
```javascript
    // node module to read file
    var fs = require('fs');
    
    // execution trace : 1
    function promiseFile(file) {
        // execution trace : 3 - promise is now put on the event queue and rest of any remaining statements executed
        return new Promise(((resolve, reject) => {
            // typical async with callback function - now wrapped in a promise
            fs.readFile(file, (err, data) => {
                if (err) {
                    reject(err);
                } else {
                    // execution trace : 5 - on no err case
                    resolve(data.toString());
                }
            })
        }))
    }

    // execution trace : 2 - promise is now invoked
    promiseFile('./scratch.js')
        // callback functions can now be chained instead of nesting
        .then(data => {
            // execution trace : 6 - finally it ends up here
            console.log('Class: callbacks, Function: , Line 138 (): '
            , data);
            // Can return another promise for nested callback scenarios
            return promiseFile('./anotherFile.js')
        })
        .then(data => console.log('read another file'))
        .catch(err => {
            console.log('Class: callbacks, Function: , Line 142 (): '
            , err);
        })
        
    // execution trace : 4 - no more statements to execute - back to event queue for the promise
```
* When promises need to be chained
    - the `then` part of a promise can return the next promise
        - that needs to be invoked down the callchain
    - the returned promise can be handled in the next `then` part chained 
    - the `catch` part handles errors commonly for all chained promises

### Fetch
* New API to perform AJAX requests
* Example
```javascript
fetch("https://www.google.ca") // returns a promise
.then(r=> r.json()) // with a request object
.then(data=>console.log(data))
```
