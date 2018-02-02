# Lesson 7: React Fundamentals with New ES6 Syntax

* Classes are not hoisted
    - so component classes need to be declared in the order
        - that they are going to be used
        - e.g. child components before the parent component

## Props and state
* Properties are immutable
* State is mutable
* Binding event handlers in constructor
    - hard-binding the event handler functions of a component 
        - using `eventHander.bind(this)` is recommended
    - without this when the event callback is invoked
        - the `this` could be a different context and cause errors
* Another alternate is to use arrow functions for event handlers
    - arrow functions correctly bind `this` as expected
    - preventing errors and requiring to explicitly bind in constructor etc
* `setState(obj)`
    - passed in object is superimposed on the initial state 
        - set in the constructor
    - same keys are overridden
        - new keys are inserted
        - existing keys are left alone
    - `setState()` has to be used for React to be aware of the state states
        - and initiate a re-render
        - react does not monitor/poll state for changes
        
## Stateless functional components
* Components that do not have a state 
    - and just use the `render()` function
    - can be written as a stateless functional component
* Example
```javascript
   let FunctionalComponent = props => (
       <p> {props.foo} </p>
   )
   
   // can be used as
   <FunctionalComponent foo={"Functional component"}/>
```
* `this` is dropped in a functional component
    - `props` is accessed directly