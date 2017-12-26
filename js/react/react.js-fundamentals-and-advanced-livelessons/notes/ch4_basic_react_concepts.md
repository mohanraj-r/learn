## Design principles
* Component based
    - smaller components put together
    - semantically break up the app into smaller components
        - starting with the bigger/high level components
            - e.g. <App/>
        - and working down to smaller composable components
            - e.g. 
                - Header
                    - ..
                - Footer
                    - ..
                - Article list
                    - Article
                        - p
                        - a    
    - advantages
        - easier to maintain
        - more reusable 
* use one way data flow and rendering
    - easier to debug and reason
* Components have 
    - Properties
        - immutable data that do not change
    - State
        - mutable data that change over time
        - one way flow of mutable data
        - common pattern is for state of parents
            - to become properties of children
            - e.g. list of articles is a state of <Article list>
                - and for each <Article> derived from the <Article list> it becomes a property
        - `setState()` function sets state of a component
            - can also be invoked using wrapper methods on the parent component
                - from the child component
                - so control flow is not necessarily one way
                    - just the data flow
                    
        