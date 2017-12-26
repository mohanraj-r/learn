* JSX is a convenience notation 
    - equivalent to using `Render.createElement(tag, attributes, text)`
        - JSX: `<tag atrributes> text </tag>`
    - Nested elements are easier to read and maintain
        - in JSX compared to nested `Render.createElement()`
        
* The `return` of a `render()` of a component
    - can only return one element
    - so multiple elements need to be wrapped in an enclosing element
    
* Rendering the top level <App/> component
    - should be done into its own container
        - e.g. <div id="app">
    - using existing top level container such as `<body>`
        - is not recommended
        - because third party browser plugins and other sources might render into the body
        - rendering the diffing algorithm inefficient

## Setting state
* Initial state of a component can be set using
    - `this.state = {}` in the `constructor()` of the component class
    - state should be set on the component using `this.state`
        - instead of trying to maintain it elsewhere outside the class
        - because only then functions to set state and propagate changes etc will work as expected

## JS Expressions
* JS expressions can be included in JSX
    - by enclosing them in `{}`
    - the expression will be evaluated and result replaced in the JSX