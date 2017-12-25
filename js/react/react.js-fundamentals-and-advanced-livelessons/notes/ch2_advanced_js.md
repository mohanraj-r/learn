## Lesson 2: Understanding Advanced JavaScript Required for ReactJS

### 2.1 Functional programming
* React encourages immutable functional style programming
* When we need to change a component
    - more often we will end up creating a new component
        - and throwing the old one away
    - instead of changing the component
* Good to get in the habit of using functional programming style 
    - using `map`, `filter`, `reduce`, `forEach` ..
        - instead of a `for` loop

### 2.2 Execution context and this keyword
* JS is dynamically scoped
    - unlike most other languages which are lexically scoped
* example
    ```javascript
        // Dynamic scoping
      
        // Constructor functions are capitalized by convention
        function Person(name, age) {
            this.name = name;
            this.age = age
        }
        
        Person.prototype.sayName = function () {
            console.log(`${this.name}, ${this.age} years old`);
        };
        
        var p1 = new Person("G", 15);
        p1.sayName();
        sayName.call({name: "hello", age: 10}); // works with any object with expected properties
          
        // Gotcha
        var sayName = p1.sayName;
        sayName();   // Undefined as the call site context lacks a object, hence `this` points to the global object
      
        // Solution is to use explicit or hard binding
        sayName.call(p1); // explicit binding with obj, works as expected
      
        var sayNameBound = sayName.bind(p1); // hard binding, creates a new bound function
        sayNameBound();
    ```

