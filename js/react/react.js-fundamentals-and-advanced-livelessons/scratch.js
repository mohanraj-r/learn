// "use strict"

// Dynamic scoping
function dynamic_scoping() {
    function Person(name, age) {
        this.name = name
        this.age = age
    }

    Person.prototype.sayName = function () {
        console.log(`${this.name}, ${this.age} years old`)
    }

    var p1 = new Person("G", 15)
    p1.sayName()

    var sayName = p1.sayName
    sayName()   // Undefined as the call site context lacks a object, hence `this` points to the global object

    sayName.call(p1) // explicit binding with obj, works as expected
    sayName.call({name: "hello", age: 10}) // works with any object with expected properties

    var sayNameBound = sayName.bind(p1) // hard binding, creates a new bound function
    sayNameBound()
}

function functional_programming() {
    var nums = [1, 2, 3, 4, 5];

    var evens = nums.filter(function (num) {
        return num % 2 === 0;
    })

    console.log(evens)
}

function rest_spread() {
    function add(...nums) {
        return nums.reduce(function (sum, num) {
            return sum + num
        })
    }

    console.log(add(1, 2, 3, 4, 5))

    console.log(add(...[1, 2, 3, 4, 5]))
}

function arrow_functions() {
    function isEven(num) {
        return num % 2 === 0
    }

    // becomes
    var isEven = (num) => num % 2 === 0   // `{}` and `return` can be omitted for single statement functions

    // which then makes it handy to use in functional programming
    var numbers = [1, 2, 3, 4, 5];
    console.log(numbers.filter((num) => num % 2 === 0));
}

function destructuring() {
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
    console.log(name, age); // undefined error
}

function classes() {
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
}

function promises() {
    var fs = require('fs');

    function promiseFile(file) {
        return new Promise(((resolve, reject) => {
            // typical async with callback function
            fs.readFile(file, (err, data) => {
                if (err) {
                    reject(err);
                } else {
                    resolve(data.toString());
                }
            })
        }))
    }

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
}

// functional_programming()
// dynamic_scoping()
// rest_spread()
// arrow_functions()
// destructuring()
// classes()
promises()