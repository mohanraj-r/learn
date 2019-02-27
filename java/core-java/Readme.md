# [Core Java](https://learning.oreilly.com/videos/core-java/9780134540603)

- [Core Java](#core-java)
  - [Resources](#resources)
    - [TODO](#todo)
  - [Data types](#data-types)
    - [Variables](#variables)
    - [Strings](#strings)
    - [Console IO](#console-io)
    - [Arrays](#arrays)
    - [ArrayList](#arraylist)
    - [Enum](#enum)
  - [Collections | Core Java](#collections--core-java)
    - [Interfaces](#interfaces)
    - [Iterators](#iterators)
    - [Different Interfaces](#different-interfaces)
    - [LinkedList](#linkedlist)
    - [Hash sets](#hash-sets)
    - [tree sets](#tree-sets)
    - [queues](#queues)
    - [maps](#maps)
    - [views](#views)
    - [Common methods](#common-methods)
    - [Legacy collections](#legacy-collections)
  - [OOP](#oop)
    - [Constructor](#constructor)
    - [packages](#packages)
    - [classpath](#classpath)
    - [Methods](#methods)
    - [Call by value](#call-by-value)
    - [javadoc](#javadoc)
    - [Reflection](#reflection)
  - [Inheritance](#inheritance)
    - [Polymorphism](#polymorphism)
    - [Abstract](#abstract)
    - [Object](#object)
    - [Best practices](#best-practices)
  - [Interfaces](#interfaces-1)
    - [Methods in an interface](#methods-in-an-interface)
      - [Default interface functions](#default-interface-functions)
    - [Functional interfaces](#functional-interfaces)
    - [Lambda expressions (Closures)](#lambda-expressions-closures)
      - [Method references](#method-references)
    - [Inner classes](#inner-classes)
      - [Local inner classes](#local-inner-classes)
  - [Generics](#generics)
    - [Generic class](#generic-class)
    - [Generic methods](#generic-methods)
    - [Type bounds](#type-bounds)
    - [Type erasure](#type-erasure)
    - [Limitations of generics](#limitations-of-generics)
      - [Inheritance and generic types](#inheritance-and-generic-types)
    - [Reflection](#reflection-1)

## Resources

- [Core Java LiveLessons (1)](http://horstmann.com/corejava/livelessons/index.html)

### TODO

- [Exceptions, Assertions, Logging](https://learning.oreilly.com/videos/core-java/9780134540603/9780134540603-CORJ_07_00)

---

## Data types
## Data types

8 primitive types (Everything else in Java is an object)

- int, long, short, byte
- double, float
- char
- `boolean` (`true`, `false`)
  - no conversion between int and boolean

### Variables

- `final` makes a variable into a constant
- type conversions are automatic when conversions are possible and lossless
  - explicit casts are required otherwise
- `null` is a valid value of any object variable

### Strings

- immutable sequence of unicode characters
  - direct unicode points specified using `\u`
- instances of `String` class
- indices are counted in char (16 byte) terms
  - some unicode chars take 2 chars (utf-16)
- string concat with `+` implicitly converts other types to string
- `==` checks by memory not value
  - use `String.equals` to compare strings by value
- empty string `””` has length 0
- but `null` value assigned to a string var means no object
  - would result in null pointer exception on any methods including `length`()

### Console IO

- `Scanner` used to read inputs
- `System.in` represents stdin

### Arrays

- collect values of the same type
- `new` makes/allocates the array
  - or literal arrays can be specified with values enclosed in `{..}`
- `Array.length` is a property, not a method
- iterate using `for (elem : array) {}`
- variables are references
  - `Arrays.copyOf` to make a true copy

### ArrayList

- Arrays are fixed length
- `ArrayList` manages an array that grows and shrinks by demand
  - defaults to `Object[]` type
  - for specific type use `ArrayList<Class>`
    - type can be omitted when instantiating
      - e.g. `ArrayList<Foo> foo = new ArrayList<>()`
- `add()` to add object to end
- `size()` returns current size
- `get(index)`, `set(index, object)` to get and set items
  - can't use `[index]` like arrays
- can only hold objects
  - not primitive types
    - Java offers wrapper classes for primitive types
      - e.g. `Integer` wrapper class for type `int`
        - can be used as `ArrayList<Integer>`
        - conversion between `int` and `Integer` is automatic
          - called as boxing/unboxing
      - operators such as `++` work with wrapper classes
        - but `==` doesn't work with wrappers
          - will check for object equality
          - have to use the `equals` method
      - wrappers can be `null` as they are objects

### Enum

- enums are classes

  - defines all possible instances of the class
    - e.g. `enum Size {SMALL, MEDIUM, LARGE}`
  - can add constructors, methods, fields

    - methods are then made available on each value of the enum

      - e.g.

        ````java
            public enum Size {
                SMALL("S"), MEDIUM("M"), LARGE("L");

                private String abbreviation;

                //constructor
                private Size(String abbreviation) {
                  this.abbreviation = abbreviation;
                  }
            }```
        ````

- all enum classes are subclasses of `Enum` and hence inherit methods from it
  - `toString` yields the corresponding string version automatically
  - `ordinal` returns the position in which a value is defined
  - static methods
    - `valueOf(Size.class, "SMALL)` -> `Size.SMALL`
      - useful for dynamically generating values from e.g. user input
    - `values()` yields all values as a array

---

## [Collections | Core Java](https://learning.oreilly.com/videos/core-java/9780134540603/9780134540603-CORJ_09_00)

### Interfaces

- Collections framework separates interfaces and implementations
- a interface can have multiple implementations
- use the interface to declare the type of collection objects (best practice)
  - even though we have to use the implementations to instantiate
  - this makes it easier to switch implementations if/when required

### Iterators

- Every collection implements a `Iterator` interface
  - which has methods to iterate over all elements of the collection
    - `hasNext()`
    - `next()`
  - this is used by the `for (elem: Collection)`
  - also accomplished using the `forEachRemaining()`
- Traversal order depends on the collection
- `remove()` removes the last element iterated over
  - calling `remove()` twice without calling `next()` will error

### Different Interfaces

- Map hold key value pairs
- List is an ordered collection
- Set is an unordered collection
  - no duplicates
  - SortedSet provides an iterator which traverses in sorted order
- Lists
  - linear traversal with iterators
  - random access with integer indexes
  - `ListIterator` is bidirectional

### LinkedList

- Linked lists have poor performance with random access
  - `RandomAccess` tagging is sometimes used to distinguish between linked list and array list
- `ArrayList` have poor performance with add/remove at the middle
  - rest of elements need to be moved over
  - `LinkedList` performs better for frequent add/remove in the middle
- `ConcurrentModifiiedException` is thrown
  - when there are multiple iterators over a linked list
  - and one of them modified the linked list
    - causing the other iterator(s) to be out of sync
  - implemented using a modification count which is stored in the linked list

### Hash sets

- when element ordering isn't important
  - elements are iterated over in random order
- sets can find, add, remove elements quickly
- uses hash codes to group elements into buckets

### tree sets

- elements iterated in sorted order
  - slower than hash sets
  - but performance is guaranteed
    - unlike hash sets where performance depends (on distribution of data)
- elements need to be comparable
  - needs total ordering of all elements in the set
    - not always possible

### queues

- add at tail, remove at head
- deque can add/remove at both ends
- Priority queue removes elements of highest priority
  - irrespective of the order in which they were added

### maps

- stores key, value associations
- `HashMap` hashes the keys
- `TreeMap` organizes them in sorted order
- `LinkedHashMap` iterates over keys in the order they were added
- `map.get(key)`
  - retrieves the value by key
  - returns null if key is not present
- `map.getOrElse(key, defaultValue)`
  - returns default value if key is not found
- `map.keySet()` to get all keys
- `map.entrySet()` to get both key, value in one go
- `map.forEach((k,v) -> lambdaExpression)`
  - to iterate over map
  - efficient and elegant way of iterating over a map
- `map.put(key, value)` to store entries
- `map.putIfAbsent(key, value)`
  - stores value only if key doesn't already have a value
- `map.merge(key, value, functionRef)`
  - takes existing value if any passes it along with the given value to the given function and stores the result as new value

### views

- View implements a collection interface
  - without actually storing a copy of the elements
- `Collections.singleton(..)`
  - returns a collection with a single instance of given object
- `Collections.empty*()`
  - returns a empty collection of a particular type
- `list.subList(i, j)`
  - sub view on a list
- `Collections.unmofiable*()`
  - gives a read-only copy of a particular type
  - throws exception on mutating methods
- `Collections.checked*(collection, type)`
  - gives a safer collection that ensures all elements stored in will be of the given type
  - useful for generics along with raw types

### Common methods

- `Collections.sort` sorts a list of `Comparable`
- `Collections.binarySearch`finds en element in a sorted list
- `Collections.min`, `Collections.max`
- `Collections.fill` fills a list with specified element
- `Collections.shuffle` randomly shuffles a list
- `Collections.replaceAll` replaces all instances of a element with given element
  - there is also a version which takes in a lambda expression and applies it to every element
- `Collections.removeIf` removes elements matching a condition
- `Bulk operations`
  - `addAll adds all elements from one collection to another (union)`
  - `removeAll` removes all elements from one collection that are in another (difference)
  - `retainAll` removes all elements from one collection that are not in another collection (intersection)
- `Arrays.asList` to turn an array into a collection
- `toArray(..)` to turn a collection into an array of given type

### Legacy collections

- `Vector`, `Hashtable` are predecessors of `ArrayList, HashMap`
- `` Enumeration` is predecessor of `Iterator ``
- ``Stack`is a classic stack structure.`ArrayList`can be used instead`

---

## OOP

- `final` fields cannot be mutated
  - a `final` field when referring to an object
    - the object's state can still be mutated
    - its the reference which can't be mutated
  - convention to use UPPERCASE
- `static` exists once per class
  - shared among all instances of the class
  - static methods don't operate on object state
    - can only access `static` fields of the class
    - can't use `this`

### Constructor

- instantiated using `new Class()`
  - receiving variables hold references to the initialized object
- a field that isn't explicitly initialized in constructor is set to
  - `0`, `false` or `null`
  - calling methods on `null` objects results in null pointer exception
    - and causes program to terminate
- fields could be initialized with default values outside constructors
  - as they are declared
- if a class has no constructor a no-argument default constructor is provided
  - which sets all fields to their default values
  - but if a class has at least one constructor
    - the default constructor is not provided
      - a no-argument constructor if necessary should be provided explicitly
- constructors can call other constructors in the class
  - but have to use `this()` instead of the name
- factory methods can be used in addition to or instead of constructors when necessary
  - e.g. `LocalDate.now()`, `LocalDate.of(...)`
- class declarations can contain arbitrary blocks of code
  - they are executed when an object is constructed
  - `static { ... }` initialization blocks
    - are executed once when class is initialized
    - are used to initialize static variables in class
      - as they can't be initialized in constructors

### packages

- package declaration at top of file
  - without which classes are put in the `default` package
- place source file into a dir structure that reflects the package name
  - `javac` would require the path containing the dir structure to be specified when compiling
  - `java` would require the full package name to access the class
- use reverse domain name for your own packages
  - _com.uniquedomain.packagename_
- packages do not nest
  - no relationship between `java.util` and `java.util.jar`
  - just different packages with different names
- cannot have multiple wildcards in imports e.g. `import java.*.*`
- error when multiple wildcard imports contain same names
- `import static <package>`
  - imports static fields and methods of the package

### classpath

- list of dirs and jar files separated by `:`
  - where java needs to look for classes during execution
- specify all jar files in a dir by using `dir/*`
- `java -classpath dir1:dir2`
  - can also set `CLASSPATH` env var

### Methods

- method with the same name is the class constructor
  - invoked when `new Class()` is called
- every method in the class receives the implicit param of the current object
  - all actions in the method default to the object namespace
  - can make it explicit by using `this`
- methods with same name but with different signatures
  - compiler chooses the appropriate method automatically
- Accessor methods don't change state of object
  - Mutator methods change state
  - Tip: Minimize mutator methods
    - as they make concurrency difficult
    - prefer immutable classes when possible
- Variable num of args indicated with `...` e.g. `foo(Bar... bars)`

### Call by value

- Java uses call by value
- methods gets copies of arg values
  - for primitive types this works as expected
  - with the receiving method unable to mutate the passed in arg
- but for objects, the value of the object stored in var is its reference
  - hence methods can mutate the passed in obj
  - but it is not pass by reference
    - because we can't swap references to objects
    - because the references are copies local to the method
      - and hence don't affect the calling scope

### javadoc

- comments delimited by `/** */`
- `javadoc` generates HTML documentation
- can document
  - packages
  - public classes, interfaces
  - public/protected fields, constructors, methods
- first sentence should be a summary statement
- followed by
  - `@param` for documenting parameters
  - `@return` for documenting return value
- for formatting
  - can use html tags, e.g. `<em>` for emphasis
  - code use `{@code ...}`
    - so that we don't have to escape `<` chars etc
    - images need to be placed in a special `doc-files` dir

### Reflection

- `Object.getClass()` returns a `Class` object
- `Class.getName()` returns the class name
- `Class.forName(string)` returns class instance
- `Class.getMethods()` methods that we can call
- `Class.newInstance()` returns an instance using the no-arg constructor
- `Arrays.toString(array)` returns correct string representation of an array

---

## Inheritance

- no multiple inheritance
  - can only extend one super class
- using `extends`
- subclasses can't access `private` fields/methods of super class
- `protected` fields/methods are accessible from subclasses
  - but they have package visibility
  - and could restrict evolution of design if others depend on it
- subclass constructor invokes the super class constructor using `super(...)`
  - must be the first statement in the subclass constructor
  - if super class constructor is not invoked
    - the no arg constructor for super class is invoked
      - provided it has one, errors otherwise
- to access super class fields/methods explicitly use `super.`
- `final` classes/methods cannot be extended/overridden
- `instanceOf` checks if a object is of a certain type

### Polymorphism

- a var can refer to multiple types
- dynamic binding: the appropriate method is selected at runtime
- overriding methods in subclass should exactly match the parameter types of the super class
  - `@Override` annotation is used to denote to compiler that it is an overridden method
    - helps compiler catch errors when types are mismatched unintentionally
- return types can be _co-variant_

### Abstract

- when a super class makes sense from a refactoring common code out perspective
  - but doesn't make sense to instantiate
  - classes/methods are marked as `abstract`
    - class with `abstract` methods must be declared as `abstract`
      - ok to have fields, constructors, concrete methods in a abstract class
  - abstract classes cannot be instantiated
    - only concrete subclasses can be instantiated

### Object

- `Object` is a superclass of all java classes
  - only primitive types are not objects
- even Arrays are objects
- Any object reference can be stored in a variable of type `Object`
- `Object.equals(..)` tests whether object references are identical
  - abstract method that needs to be implemented by objects being compared
    - when implementing `equals` subclasses need to invoke `super.class`
      - and then compare the subclass fields
  - equals method needs to be
    - reflexive - `x.equals(x)`
    - symmetric - if `x.equals(y)` then `y.equals(x)`
    - transitive - if `x.equals(y)` and `y.equals(z)` then `x.equals(z)`
- `Object.getClass()` returns the class type
- `Object.hashCode` integer derived from the object
  - hash codes of different objects should be different
    - hash collisions are rare
  - override `hashCode` whenever you override `equals`
    - use the convenience method `Objects.hash(<fields>)`
      - using the fields used in the `equals` method
- `Object.toString` yields a string representation of a object
  - defaults to class name and hash code

### Best practices

- don't use protected fields
  - their benefits outweigh the design evolution limitations
- use inheritance to model a "is-a" relationship
- don't use inheritance unless "all" inherited methods makes sense
  - don't try to fix this by changing the expected behavior by overridden the method
- use polymorphism, not type information
  - to perform different actions depending on type explicitly using `if .. else`
  - use polymorphism instead and let it take care of it implicitly
- don't overuse reflection

---

## Interfaces

- Set of requirements for classes
  - Not a class
    - Can't instantiate using `new`
    - can be declared as type of variables
      - which objects conforming to the interface can be assigned to
    - can be used to check with `instanceOf`
- Can have fields
  - which are automatically `public static final`
- Can extend another interface
  - using `extends`
- Classes can choose to conform to one or more interfaces
  - explicitly by using `implements`
- Marker interfaces
  - are interfaces with no methods
  - are used to mark classes as belonging to a group
    - used in std lib e.g. `Cloneable`
- `Comparator` interface has useful methods for creating and composing comparators

### Methods in an interface

- are automatically `public` and `abstract`
  - no need to declare as `public`
- can also be `static`

#### Default interface functions

- can supply a `default` implementation
  - instead of being `abstract`
  - with a `return` value that implementing classes would get by default
    - without having to provide an implementation
    - essentially making the default methods as optional to be overridden
      - can be used to evolve interfaces by adding new methods
        - without breaking older implementations of the interface
  - default methods could call the abstract methods
- Duplicate resolution
  - when 2 interfaces provide the same default method
    - programmer must manually resolve by providing a override
  - when a superclass has the same method
    - superclass implementation wins over the interface(s)

### Functional interfaces

- Interface with a single abstract method
- `java.util.function` package contains generic functional interfaces

### Lambda expressions (Closures)

- Blocks of code passed around
  - to be executed later
- Can be used where ever a functional interface is used
- Reduces tedious boilerplate for callbacks
- `(params) -> expression`
  - if code doesn't fit a single expression, use a block `{}` with `return` statement
- param types can be omitted if types can be inferred
  - if there is just 1 param with inferred type, `()` can be omitted
- can access variables from the enclosing scope
  - closure
    - thereby are more than just syntactic sugar over functional interfaces
  - referenced variables from enclosing scope should be effectively `final`
    - can't modify enclosing variables
    - variables that are modified in scope out of the lambda expression can't be used either

#### Method references

- when a lambda expression only calls a single method
  - by directly passing in the param to the single method
    - e.g. `Timer(100, event -> System.out.println(event))`
  - the method reference can be directly used instead
    - omitting the params entirely
    - e.g. `Timer(100, System.out::println)`
    - uses `::` to signify the reference
- `::new` is used to reference a constructor
  - e.g. `Foo::new` instead of `param -> new Foo(param)`

### Inner classes

- classes defined inside another class
  - has reference to outer class object that created it
    - e.g `OuterClass.this.instanceVariable`
- were commonly used for
  - callbacks before lambda expressions
  - and hiding implementation details

#### Local inner classes

- classes defined inside a method
- cannot be accessed from outside the enclosing function
- are never public, private or protected
- can access effectively final variables from enclosing scope
- can be anonymous without a name
  - instantiated directly with a `new`
  - can extend a class
    - unlike lambda expressions
  - can implement a interface
- static inner classes
  - without reference to creating object
  - useful for private/scoped class that doesn't need to know about creating object

---

## Generics

### Generic class

- `class Foo<T>`
  - single letter used by convention
    - but could be anything
  - can have multiple types e.g. `class Foo<T, U>`
- When instantiating the class, the actual type is substituted for the generic type e.g `foo = new Foo<String>();`

### Generic methods

- can have generic methods in normal non-generic classes
  - as well as generic classes
- generic type marker should be placed before the return type and after the modifiers (e.g. `public static`..)
  - e.g. `public static <T> T bar(T a) {...}`
- when invoking the method, the actual type comes before the method name
  - e.g. `Foo.<String>bar("test")`
  - but when the types can be inferred, they can be omitted
    - e.g. `Foo.bar("test")`

### Type bounds

- In order to ensure that the generic type conforms to certain interfaces
- use `<T extends FooInterface>`
  - to ensure the specific types passed in adhere to the interface
    - in order to ensure we can call particular methods on the type
- can have multiple bounds e.g. `<T extends FooInterface & BarInterface>`

### Type erasure

- JVM has no knowledge of generics
  - The compiler
    - translates generic code
    - inserts casts
    - creates polymorphic bridge methods
- When adding generics to legacy code
  - any compiler warnings can be suppressed using `@SuppressWarnings("unchecked")`

### Limitations of generics

- Primitive types don't work with type variables
  - workarounds
    - use wrapper classes
    - use additional versions for primitive types from `java.util.function`, `java.util.stream`
- Runtime queries involving generics might not work as expected
  - as type info is erased by compiler
  - e.g. `instanceOf` checks will error,
    - `getClass()` on different types will be equal
- arrays of parameterized types are not possible
  - workaround
    - use array list of generic types
- varargs
  - of generics params can only be read from
    - not written to
  - annotate with `@SafeVarargs` to inform compiler that the programmer has checked and verified that the var args are only being read from
- instantiating types
  - can't instantiate a generic type
    - e.g. `foo = new T();`
  - workaround
    - caller passes in constructor expression
    - or use reflection using the class object
- generic arrays are not allowed
  - workaround
    - use `ArrayList`
      - which uses a generic object array internally
      - and casts/reflection to achieve the generic types
- type variables can't be used in static fields/methods
- exceptions
  - can't throw or catch objects of generic types
  - but generic type can be used in exception specifications
    - e.g. `.. void Foo() throws T`
- name clashes
  - can't create conditions that lead to name clashes after type erasure
    - e.g. methods of std built-in objects
      - have to rename our method to avoid clash
  - bridge methods of inheriting classes clash

#### Inheritance and generic types

- it is not possible to convert between generic types of a super class and generic types of a subclass
- wilcard types
  - `Pair<? extends Employee>`
    - can hold a `Pair<Employee>` or `Pair<Manager>`
  - but can't write to a wildcard type var
  - used for producers
- super wildcards
  - `Pair<? super Manager>`
  - can be written into but not read from
  - used for consumers
  - mnemonic PECS: Producer Extend, Consumer Super

### Reflection

- class and methods retain some sense of their generic type origins
  - even after type erasure
  - that can be gleaned using reflection
- but objects don't retain the info
