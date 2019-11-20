# [Design Patterns with TypeScript](https://learning.oreilly.com/live-training/courses/design-patterns-with-typescript/0636920310402/)

- [Design Patterns with TypeScript](#design-patterns-with-typescript)
  - [Resources](#resources)
  - [Notes](#notes)
    - [What ?](#what-)
    - [Why ?](#why-)
    - [Behavioral design patterns](#behavioral-design-patterns)
      - [Observer pattern](#observer-pattern)
      - [State pattern](#state-pattern)
      - [Iterator pattern](#iterator-pattern)
    - [Creational patterns](#creational-patterns)
      - [Factory method pattern](#factory-method-pattern)
      - [Prototype pattern](#prototype-pattern)
      - [Singleton pattern](#singleton-pattern)
    - [Structural design patterns](#structural-design-patterns)
      - [Adapter pattern](#adapter-pattern)
      - [Facade pattern](#facade-pattern)
      - [Decorator pattern](#decorator-pattern)

## Resources

- [Slides](./resources/slides.pdf)
- [Head First Design Patterns](https://learning.oreilly.com/library/view/head-first-design/0596007124/)
  - More easier to read
- [Design Patterns: Elements of Reusable Object-Oriented Software](https://learning.oreilly.com/library/view/design-patterns-elements/0201633612/)
  - A bit dry
- [Documentation · TypeScript](https://www.typescriptlang.org/docs/home.html)

## Notes

### What ?

- Recipe to solve predefined set of problems
- Language agnostic

### Why ?

- DRY
- Communicate with common set of terms among programmers

### Behavioral design patterns

- Handles responsibilities of objects
- Manages how objects interact with one another

#### Observer pattern

- Notifies multiple objects about a change in a object
- Problem: Keep consistency across tightly coupled classes
- Solution: Pub-Sub implementation
- Effects: Loose coupling
- [Example](./resources/TypeScriptPatterns/Observer.ts)

#### State pattern

- Allows an object to change its state when dependent data changes
- Problem: Change behavior based on state (data), esp with large conditional statements
- Solution: State interface to track state between composed objects
- Effects: All state is handled in one object, which localizes state. State can be shared, and transitions are explicit
- [Example](./resources/TypeScriptPatterns/State.ts)

#### Iterator pattern

- Makes it easier to view collections of objects
- Problem: Client code coupling with aggregate code
- Solution: Separate list aggregation code from client code
- Effects: Various traversal implementations, simplified aggregate interface
- TS has inbuilt `Iterator` interface that can be used to implement iterators
- [Example](./resources/TypeScriptPatterns/Iterator.ts)

### Creational patterns

- Abstract complexities dealing with object creation

#### Factory method pattern

- Problem: Locked into implementation with concrete types, making it harder to easily extend
- Solution: Extract the creation code that is only concerned with creation
- [Example](./resources/TypeScriptPatterns/Factory.ts)

#### Prototype pattern

- Problem: Creating instances of other classes is complex
- Solution: Delegate object creation to the new objects
  being created
- [Example](./resources/TypeScriptPatterns/Prototype.ts)

#### Singleton pattern

- Problem: Provide global access to a single instance of a class
- Solution: Use private constructor to override usual object creation
- [Example](./resources/TypeScriptPatterns/Singleton.ts)

### Structural design patterns

- How objects and classes can be assembled into larger systems
  - while still keeping them flexible, efficient and easy to change

#### Adapter pattern

- Problem: Integrating third party code with incompatible or unchangeable classes
- Solution: Create a adapter that uses composition to wrap the incompatible code / 3rd party system to provide the target interface
  - Similar to travel electrical outlet adapters
- [Example](./resources/TypeScriptPatterns/Adapter.ts)

#### Facade pattern

- Problem: Multiple entry points into many complex subsystems that share data
- Solution: A single interface that allows access to multiple complex subsystems
  - Provides loose coupling, abstracts complexity in clients
- Gives access to subsystems via a single object
- [Example](./resources/TypeScriptPatterns/Facade.ts)

#### Decorator pattern

- Problem: Require ability to add functionality to an existing object without modifying its object
  - Allows open-closed principle
- Encapsulate object that needs to change with another object
- TS has only experimental support for decorators
- [Example](./resources/TypeScriptPatterns/Decorator.ts)
