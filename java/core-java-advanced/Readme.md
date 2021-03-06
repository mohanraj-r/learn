# [Core Java: Advanced](https://learning.oreilly.com/videos/core-java-advanced/9780134643595?autoplay=false)

- [Core Java: Advanced](#core-java-advanced)
  - [Resources](#resources)
  - [TODO](#todo)
  - [Scripting API](#scripting-api)
    - [ScriptEngine object](#scriptengine-object)
    - [Direct invocation from Java using interfaces](#direct-invocation-from-java-using-interfaces)
  - [Streams](#streams)
    - [workflow](#workflow)
  - [Annotations](#annotations)
    - [Creating annotations](#creating-annotations)
      - [meta-annotations](#meta-annotations)
    - [annotations in Stdlib](#annotations-in-stdlib)

## Resources

- [Core Java LiveLessons (1)](<http://horstmann.com/corejava/livelessons2/#(1)>)

## TODO


---

## Scripting API

### ScriptEngine object

* `manager = new ScriptEngineManager()`
* `engine = manager.getEnginebyName("nashorn")`
    * JS interpreter that comes with jdk
* `engine.eval(scriptString)`
    * to run the given script in the JS interpreter 
* `engine.put("key", value)`
    * create variable in the global scope
    * that persists across invocations on the engine
    * can create a scope to restrain variables to a particular scope
        * `scope = engine.createBindings()`
        * `scope.put(“key”, value)`
        * `engine.eval(scriptString, scope)` 
* To get the results from the JS interpreter into java var 
    * `result = ((Invocable) engine.invokeFunction(functionName, funcargs...)`
    * to invoke object methods
        * `Object foo = engine.eval("new Foo(...)") `
            * invoke constructor
        * `result = ((Invocable) engine.invokeMethod(foo, method, args...)`

### Direct invocation from Java using interfaces

* instead of going through the `Invocable` every time
* Define an interface in Java
    * that would be implemented by a JS function
* Provide a implementation of the interface in JS
* Call the function from Java
    * `f = ((Invocable) engine).getInterface(FooInterface.class)`
        * invocable called only once
    * `result = f.func(args...)`
        * called directly from Java

## Streams

- superficially similar to a collection
  - but stream doesn't store its elements
- doesn't mutate its source
- stream operation
  - yields another stream (e.g. `filter`) or a result (e.g. `count`)
  - are lazy when possible

### workflow

- create a stream
- transform with intermediate operations
  - that yield another stream
  - lazy evaluation
- produce result with a terminal operation
  - forces execution of lazy operations
  - stream can't be used after
  - stream can't be used after

---

## Annotations

- tag that is inserted into source
  - `@foo`
- can annotate
  - declarations
    - of methods, vars, classes ..
  - types
    - that occur in declarations, casts, instanceOf checks and method references
- doesn't change how the programs are compiled
  - annotations are processed by tools
    - at source level
    - when class is loaded
    - through reflection
- can have key/value pairs, called elements, passed as params
  - e.g. `@Test(timeout=1000)`
  - elements can't be any arbitrary object, can only be
    - a primitive type
    - instance of `String`, `Class` or `enum` type
    - an annotation
    - array of the above
      - but not array of arrays
  - elements can have default values
  - key can be omitted if there was only one key by name `value`
  - array literals with 2 or more values are enclosed using `{...}` as usual
    - if there is only one value in array, then `{}` can be omitted
- an item can have multiple different annotations
- can be repeated, if an annotation is declared to be repeatable

### Creating annotations

- use `@interface` to declare an annotation interface
- methods in the interface correspond to elements
  - return types denote type of elements
- default values of elements are denoted by specifying `default`
  - e.g.
  ```java
     public @interface Test {
        long timeout() default 0L;
        // translates to @Test(timeout=0L)
     }
  ```

#### meta-annotations

useful when creating annotations

- `@Target` - specify the targets for the annotation e.g. METHOD, FIELD etc
- `@Retention` - specify the context of the annotation e.g. RUNTIME, CLASS, SOURCE etc
- `@Documented` - indicate that annotation should be included in (java)doc
- `@Inherited` - indicates that the class annotation should be inherited by subclasses
- `@Repeatable` - indicates that the annotation can be applied multiple times

### annotations in Stdlib

- `@Deprecated` - to indicate deprecated features that compiler should warn users about
- `@Override` - to explicitly indicate to compiler about overridden methods
- `@SuppressWarnings(..)` - to suppress compiler warnings
- `@FunctionalInterface` - to indicate functional interfaces with single abstract methods
