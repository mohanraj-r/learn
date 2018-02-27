# Ch1 Programming style and your Brain
* Thinking fast and slow by Daniel Kahneman
* Two systems
    * Head - system 2
        * Slow, deliberate
    * Gut - system 1
        * Fast, instinctive, approximate
        * Cannot turn off
* System 1 supplies working assumptions for System 2
    * System 2 is unaware of this

## Programming
* Computer programs are some of the most complicated things
    * created by human brain
* We are doing this with the brains of hunters and gatherers
* Programming makes use of System 2 
    * and system 1 in terms of coming up with a solution to a problem
    * cannot describe the means of coming up with a solution to a problem description

## JSLint
* Linter for JS
* Language subsetting
    * Tries to restrict to a safe subset of the language
    * tries to deprecate bad parts of the language

## Styles
* Prefer styles that are error resistant
* Prefer styles that are less ambigous 
    * even at the expense of being more verbose
    * to make it easier to read and follow 
    * to aid in reducing debugging time
        * the black hole abyss which ends up sucking most of our productive time
* A good style can help produce better programs
    * style should **not** be about personal preference and self-expression
    * e.g. English language
        * Romans wrote words without word breaks, spaces or any punctuations
            * in all caps
        * Medieval copyists introduced lowercase, word breaks and punctuation
            * these helped reduce the error rate 
            * and also making text more readable
* Programs must communicate clearly to people
    * Make your programs look like what they do
        * transparency, clarity of intent
    * Avoid forms that are difficult to distinguish from common errors
* By adopting a more rigourous sytle
    * many forms of errors can be automatically avoided

### Block style
* Silent errors due to automatic insertion of `;` at end of statements
```javascript
// bad style
return  // ";" gets automatically inserted resulting in returning `undefined`
{
    foo: "bar"
};

// good style
return {
    foo: "bar"
};
```

### Misc
* Switch statement
    * The fallthrough hazard (not illustrated - have to find info from another source)
* `with` statement
    * reduces readability by increasing ambiguity 
    * don't use `with`
* Always use `===`, never `==`
    * to prevent errors from automatic type conversion
* Block scope vs function scope
    *  Most languages have block scope
    *  But JS uses function scope
    *  `var` statement hoisting
        *  gets split into two
        *  declaration part gets hoisted to top of function
        *  initialization part turns into ordinary assignment
    *  Declare all variables at top of function
    *  Declare all functions before you call them
    *  `let` statement fixes this behavior
* Global variables
    * Avoid them
    * if you have to use them, use ALL_CAPS to mark them making them easier to identify
* Constructor functions
    * should be named with InitialCaps 
    * nothing else should be named with InitialCaps
* `++`
    * why should incrementing by 1 be a special case than incrementing by any other number
    * confusion of post-increment vs pre-increment
    * prefer `+=`
* 

### Performance
* Performance specific code is usually crufty
* clean code is easier to reason about
* Most of the code has negligible impact on performance
    * esp w.r.t DOM
* Only optimize when performance becomes an explicit problem 
* Algorithm replacement is vastly more efficient than code fiddling

## History
* JS was designed to make it easy for beginners 
    * and its design choice hence raise many challenges for professionals
* JS was designed and delivered in about 10 days
* Bad parts come from legacy, good intention and haste
    * bad parts can be avoided for most part 


# Ch 2 Fundamentals

## Objects
* JS is an OO language
    * everything is an object
    * an object is a *dynamic* collection of properties
        * each property has a key : "string"
            * that is unique within that object
    * Keys must be strings
        * automatically type coerced
        * would have been better if keys can be any object
* Get, Set, Delete
```javascript
// get
object.name;
object[expression];

// set
object.name = value;
object[expression] = value;

// delete
delete object.name;
delete object[expression];
```
* Object literals
    * expressive notation for creating objects
    * e.g.
    ```javascript
    var my_object = {foo: bar};
    ```
* Classes vs Prototypes
    * JS doesn't have classes
        * it has prototypes
    * working with prototypes is much easier and powerful than classes
        * make an object that you like
        * create new instances that inherit from that object
        * customize the new objects
    * taxonomy and classification are not necessary
        * you need perfect domain knowledge to perform them
        * which we often don't have when solving a problem
        * which results in inefficient domain modelling
            * and hard to refactor code 
    * delegation via differential inheritance
        * each new object needs to have only the functionality 
            * that differentiates it from other objects
    * `new` prefix operator
        * used to create objects using constructor functions
    * accidental collisions with predefined object properties
        * every object inherits a set of predefined properties e.g. `constructor` 
        * which could result in accidental collisions with user code
        * e.g.
        ```javascript
        word_count[word] += 1; // will fail if the 'word' is a predefined property e.g. `constructor`
        // which results in requiring additional checks
        if (typeof word_count[word] == 'number') {
            word_count[word] += 1;
        }
        ```
        * this is fixed in ES5 
            * where we can inherit from the `null` object without the properties
    * enumeration of object properties problem
        * all of the object properties are included in the `for name in object` enumeration
            * including anything anyone added to the `Object.prototype`
        * we have to filter these out 

## Numbers
* Numbers are objects
* There is only one number type
    * no integer type
* JS uses IEEE-754 64 bit floating point
* can use number literals
    * with floating point notation using `e`
*  associate law might not hold good 
    *  for numbers past a certain threshold
        *  e.g. `a + (b + c) === (a + b) + c`
            *  might not be true for numbers > 9 quadrillion 
            *  because numbers in computers are finite in precision
                *  and tend to overflow after a point
    *  this is esp true for decimal fractions
        *  `0.1 + (0.2 + 0.3) === (0.1 + 0.2) + 0.3` is not true
*  Methods can be added to `Number.prototype` 
    *  and have it available on all numbers
*  Math object
    *  There is a separate `Math` object
    *  containing math operations like `floor`, `random` .. 
*  `NaN`
    *  Special number: not a number
    *  `typeof(NaN) -> number`
    *  Result of undefined or erroneous operations 
        *  Much easier to compare results of invalid operations
        *  instead of returning a valid number such as -1
        *  any math operation with NaN as input 
            *  will output NaN
        *  NaN is not equal to anything
            *  including NaN
*  Other special values
    *  `Infinity`
    *  `Number.MAX_VALUE`

## Boolean
* `true` and `false`

## String
* A sequence of 0+ 16 bit unicode chars
    * UCS-2, not UTF-16
    * no awareness of surrogate pairs
* No separate character type
    * string of length 1
* strings are immutable
* similar strings are equal (`===`)
* string literals can use single or double quotes
    * as a convention can use double quotes for external user-facing strings
    * and single quotes for interal strings
* `+` can concatenate or add
    * depending on context