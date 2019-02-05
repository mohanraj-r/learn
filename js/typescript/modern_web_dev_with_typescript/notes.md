# Typescript

## Introduction to Typescript

### Intro

- strict super-set of ES6
- just compile time layer
  - no run time

### Basic types

- boolean, number, string
- Array
  - elements of same type
- Tuples
  - elements of different type
  - dependent on order of declaration of types
    - e.g. [num, string] != [string, num]
- Enums
  - list of typed constants
  - `enum Color {R, G, B}; let c: Color = Color.G`
- Any, Void
  - any - when type is not known
    - silences type checks and warnings
  - void - used to mark functions that return nothing

### Iterators and loops

- for .. in
  - returns index of elements in the array
- for .. of
  - returns elements of the array

## Typescript variables and object oriented features

### let vs var

- var
  - global scope - hoisting
- let
  - block scoped
- const
  - similar to `let`
  - but allows only one assignment

### Interfaces

- way to define a contract
- implicit definition
  - explicit "implements" declaration is not required
    - but might be better to catch errors early
  - having the right shape / signature is sufficient
- optional properties can be denoted with `?`
- can also define
  - the structure of a function rather than an object
    - name of the params don't need to match
      - just the types
  - an array type
    - useful when you have a custom array like structure
- interfaces can extend other interfaces
- [json2ts - generate TypeScript interfaces from json](http://json2ts.com/)

### Classes

- visibility
  - default visibility is `public`
  - `private` restricts to the class
  - `protected` restricts to the class and its subclasses
- constructor params marked as `public`, `private` or `protected`
  - are automatically turned into class property declarations

* inheritance
  - using `extends`
  - `super` used to invoke parent constructor, methods
* class can implement one of more interfaces
  - interface can also extend a class
* `static` properties
  - shared across all instances of the class
  - <class>.<static property> is used to access instead of `this`
* `abstract` classes
  - mark functions that are meant to be extended and implemented
* Getters and Setters
  - functions that act as aliases for properties

### Advanced types

- Generics
  - allows to work over different types than a specific type
- Union types
  - set of types
  - can be used in place of enums
    - e.g. `declare type Currency = 'USD' | 'CAD' | 'INR'; let c Currency = 'CAD';`
      - can use the string values directly instead of going through the type as in enums
- Typeof
  - returns type of var as string
- instanceof
  - checks for types
- Guards and assertions
  - Mode advanced type assertions
  - [Advanced Types · TypeScript](https://www.typescriptlang.org/docs/handbook/advanced-types.html)

## Modules in Typescript + important ES6 features

### Modules

- Modules are a typescript feature
  - ES5 doesn't have concept of modules
    - third party module loaders fix this for ES5
    - hence when using ES5 we have to choose the module loader
- any file containing a top-level import / export is a module
- `export`ing a definition makes it available outside the current module
- can be exported/imported under different names

### ES6 features

- `` allow for template and multi-line strings
- arrow functions
  - short-hand syntax for functions
- spread operator
  - copy/merge objects using `...`
- rest operator
  - used to get variable number of params in a function
    - e.g. `function foo(a, ...args) {}`
  - can also be used with object properties
    - e.g. `let obj = {x:1, y:2}; let {x, ...rest} = obj;`
- destructuring arrays
