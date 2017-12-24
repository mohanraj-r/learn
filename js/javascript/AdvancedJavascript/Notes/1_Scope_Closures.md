Intro
-----
* HTTP2
 - Goes against all the single file concatenation optimizations done for HTTP1

# Function Declarations, Function Expressions and Block Scope

* `catch` block (of `try` .. `catch`) has its own scope

* Downsides of using anonymous functions
    - no way to reference the function from within itself
        - e.g. to un-register from events etc
        - can't use `this`
    - debugging is painful without function names in stack-traces
    - naming a function is self-documenting
        - without that we have to refer to the outer context 
            - to understand what the function does
 
 * Unless a `function` is the first word of a statement
    - its a function expression
    - functions assigned to variables are function expressions
        - they cannot be called by their name from outer scope
            - only referenced by the var they are assigned to from outer scope
        - but the function name can be used in its own (function) scope
        
        
# Lexical, Dynamic scope
* Vast majority of languages only have `lexical scope`
    - Unlike JS which also has `dynamic scope`
* Lexical scope
    - is the (nested) scope defined at compile time
    - the scope that the compiler builds as it goes through the code
        - global scope
        - function scope (recursively for nested functions)
        - class scope .. etc
* Dynamic scope
    - JS creates scopes (namespaces) dynamically at run-time under certain conditions
        - e.g. when it sees the keywords
            - `eval`
            - `with`
            - `setTimeout()` function with a str param
    - JS compilers have to turn off optimizations when they see dynamic scopes
        - which results in slower code
    - `eval`
        - evaluates a `str` and injects resulting code into current scope where `eval` is present
        - but under `strict` mode `eval` creates its own scope
            - hence does not interfere with the existing scope
            - compilers can optimize in this case
        - don't use `eval` unless you absolutely need the dynamic code evaluation feature
            - e.g. when build templating engines etc
    - `with`
        - creates a entirely new scope associated with given object
        - lookups within the scope are tied to the given object 
            - without have to prefix `obj.foo`
        - but assignments to variables that cannot be resolved to the object
            - are created in the `global` scope !
        - can't be used with `strict` mode 

# IIFE pattern
* Immediately Invoked Function Expression
* Unlike most languages JS is not block scoped
    - it is function scoped
        - var defs are scoped to the function in which they are defined
            - and hoisted to the top of the function
            - irrespective of the code block inside the function in which they are declared
* When we need to create isolated scopes 
    - other languages might be able to do it with a new set of curly braces `{ .. }`
        - JS can do it too with ES6
    - but in JS we have to create a scoped of function expression 
        - that is immediately invoked
    - the scope of vars inside the IIFE is isolated from the rest of the scope
* example
    ```javascript
    var foo = "foo";
    
    (function(){
        var foo = "42";
        console.log(foo); // "42"
    })();
    
    console.log(foo); // "foo"
    ```
    
# Block scope in ES6
* with `let` keyword as of ES6 variables can be scoped only to the immediate code block
    - e.g. `if`, `for` blocks 
    - basically any code block with a `{..}`
* without `let`, using `var` creates the variable in the function scope 
    - even if we are declaring the variable inside a `if` or `for` block
* `let` gotchas
    - `let` does not block scope the variable to the entire block
        - but only to the part of the block following the use of `let` 
    - mental overhead
        - switching from `var` to `let` and getting used to the stricter block scopes has mental overhead
    - implicit scope
* `let` recommendation
    - to make more the scope blocks more explicit
        - move all `let` declarations to beginning of the code block
        - create a new explicit scope using `{..}` for using `let`
            - instead of riding back on existing blocks such as `if`, `for` ..
 
# Hoisting
* the compiler moves the declarations of variables and functions
    - to the beginning of the code 
    - so that references to them are resolved before their definition 
    - example
    ```javascript
      a; // 1, not undefined
      var a = 1;
    ``` 
* functions definitions are hoisted before variable definitions 
    - function expressions are not hoisted
        - as they are not considered declarations but expressions
* `let` is not hoisted
    - only `var` is
* hoisting is similar in concept to header files in c
    - where all declarations are stated upfront
    - only JS does it automatically 
* Duplication function declarations override one another
    - so the last one will prevail
    - but duplicate variable declarations are ignored
        - so the first one prevails
        
# this
* Every function, *while executing*, has a reference to its current execution context called `this`
* With `this` keyword JS seems to take on some runtime dynamic scoping 
    - in addition to the compile time lexical scoping
    - but there is no way to create a cross-over between the dynamic context `this` and the lexical environment
        - we cannot get a reference to the lexical context in JS
* Example
    ```javascript
      function foo() {
          console.log(this.bar);
      }
    
      var bar = "bar1";
      var o2 = { bar: "bar2", f2: foo};
      var o3 = { bar: "bar3", f3: foo};
    
      foo();     // "bar1"   - default binding 
    
      o2.f2();   // "bar2"   - implicit binding 
      o3.f3();   // "bar3"   - implicit binding
    
      foo.apply(o2); // "bar2"    - explicit binding
      foo.call(o2);  // "bar2"    - explicit binding
    
      f = foo.bind(o2); // hard binding
      f();   // "bar2"
      f(o3); // "bar2" 
    ```
* call site
    - context of how a function is called
* four rules (in reverse order of precedence)
    1. default binding 
        - applies
            - when a function is invoked on its own in the call site
            - in case of IIFE
        - in `strict mode` default binding is `undefined`
            - the mode is the one of the executing function context
                - so if the `strict mode` is declared within the function
                    - then it applies to the function regardless of the mode outside 
        - in non-strict mode default binding is to the global object
    2. implicit binding
        - applies
            - when a function is invoked in the context of a object in the call site
        - the object upon which the function is invoked
            - becomes the `this` reference
    3. explicit binding
        - applies
            - when a function is invoked using `call()` or `apply()` 
                - and a object is explicitly passed in as a parameter
                - even though the function doesn't declare a parameter
        - the passed in object becomes the `this` reference
    4. hard binding
        - the dynamic binding of `this` could lead to issues
            - in async callbacks and event handlers
            - where the `this` reference gets pointed to a different object (global) than expected
        - solution is to use the `bind` prototype function (ES5+)
            - `bind` allows us to hard bind a function with a object
                - `bind` returns a wrapped function bound to the given object and optional params
            - all invocations of the function will then be bound to the object
                - regardless of the call site context
        - if you find using hard binding more
            - question yourself on why to not use lexical scoping for more static scoping
    
# new
* Unlike other programming languages, `new` is not used to instantiate new objects in JS
* `new` keyword when placed before any function call
    - turns the function call into a constructor
* 4 things happen when `new` is placed before a function call
    1. An object is created    
    2. The created object is linked to another object
    3. Binds the created object to the `this` in the function context
    4. If there is no explicit `return` in the function
        - a `return this` is done
* rules of `this` binding in order of precedence
    1. `new` binding - was the function called with `new`
        - `new` even overrides hard binding 
    2. explicit binding via `call` or `apply`
        - hard binding is a wrapper over explicit binding and hence has the same precedence
    3. implicit binding via a calling object
    4. default binding to global object or undefined in strict mode

# Closure
* Closure is when a function remembers its lexical scope
    - even when the function is executed outside that lexical scope
* Closures have access to the current live lexical scope 
    - so when variables change in the lexical scope
    - closures have access to their current values
        - not a snapshot value
* Nested scopes are possible as well
* Scopes are not GCed until there is a reference to that scope
    - in form of a closure etc
* Gotchas
    - Example
    ```javascript
        for (var i=1; i<=5; i++) {
        	setTimeout(function(){
        		console.log("i:" + i);
        	}, i*1000)
        } // prints: 6, 6, 6, 6, 6
    ```    
    - because with `var`, `setTimeout` function is called 5 times with the same value of `i`
        - and by the time the `setTimeout` executes `i` has been incremented to 6
            - and that is the value all functions get
    - a solution is to use IIFE
        - so that each function invocation gets its own scope
        ```javascript
            for (var i=1; i<=5; i++) {
            	(function(i){
                    setTimeout(function(){
                        console.log("i:" + i);
                    }, i*1000)
                })(i)
            } // prints: 1, 2, 3, 4, 5
        ```
    - another solution is replacing `var` with `let`
        - prints `1, 2, 3, 4, 5` as expected
        - `let` not only binds the `i` for the immediate code block
            - in this case the `for` loop
            - but it binds `i` for each iteration of the loop
    - accessing object references outside of its lexical scope it not called as closures
        - the term closures only applies to functions
        - e.g.
        ```javascript
            var foo = (function() {  
              var o = {bar : "bar"};
              return {obj: o};
            })()
      
            console.log(foo.obj.bar);  // "bar"
        ```
* Module patterns
    - Closures are used to define module patterns
        - where selected functions are exported
            - with variables bound to them
            - offering a encapsulation mechanism
    - ES6+
        - defines `export` and `import` keywords 
            - that provide the same feature
    