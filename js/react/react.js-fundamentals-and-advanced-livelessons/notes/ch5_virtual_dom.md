
# Ch5 Virtual DOM
* Dom updates are one of the slowest operations in the render process
* Understanding the process of reconciliation 
    - by which react syncs the virtual dom with the real dom
    - will help us understand the performance implications of our code

## Diffing algorithm
* Diffing algorithm is used to calculate the minimum number of changes
    - required to bring the virtual dom and actual dom in async
    - calculating the minimum number of changes exactly is going to be expensive
* react employs a couple of heuristics and assumptions
    - when calculating the diff between the virtual dom and the actual dom
    - that is very efficient 
        - but still gets us close the minimum number of changes
* assumption: Components of same type have similar subtrees than components of different types
    - e.g. an ArticleList will have a similar subtree to another ArticleList
        - compared to other components e.g. ProductList
    - when there is a change in a component
        - but the component type remains the same
        - a linear algorithm is applied to iterate through all the dom-level leaf elements
            - to determine what has changed
            - and the changes are applied
        - the linear algorithm compares elements on both the DOMs one by one
            - so if an element is added e.g. to the end of a list of elements
                - then all the old elements being ordered the same will match the comparison
                    - resulting only in one operation to insert the new node
            - but if the new element had been added to the beginning
                -  the one by one comparison will fail from the beginning
                    - resulting in many update operations
        - one way to solve is to use the `key` attribute
            - then the comparison is done using hash map
                - instead of depending on the position of the elements
            - the `key`s don't need to be globally unique
                - they just need to be unique among the siblings
                