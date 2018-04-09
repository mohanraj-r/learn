# Intro
* Learn how to design systems 
    - that evolve over time
        - part of it is extinction
            - extinction of parts of system
            - so that others can go on to survive 
    - in the face of technological and business change

# Architecture and Trade-offs
## What is architecture ?
### Purpose
* Choose and create desirable system-wide properties
* Make trade-offs with deliberation and understanding
* Allow orderly construction of a system
    - delivering incremental values
    - challenge 
        - very field of us build green field systems
        - requires more thought and diligence in dealing with legacy
* Divide responsibilities among team members
    - clear boundaries and responsibilities
        - resulting in clearly defined data formats / interfaces
* Provide common vocabulary 
    - to facilitate clear communication
* Managing cost
    - Arch trade-offs in order to reduce  
        - time to break-even or become net positive 
    - so that eventually the system might have to be redesigned/rebuilt
        - but its not a problem if the choice was made with deliberation and understanding
    - design for early returns
        - systems should support themselves from the beginning
        - without sacrificing the future completely 
        - build pyramids, not arches
            - bottom layer is infra/common stuff (logging, db ..)
            - top layers are the competitive advantage 
            - spend less time in the bottom layers
                - reuse existing systems
                

## Fundamentals of architecture
### Look for all constituents
* Customers are easy to spot
* But look also for users that facilitate the business
    - people who need a diff view of the system/data
    - people who need to create demos/dummy data
* look for users that run the system itself
    - ops, admins

### Diagrams
* key is keep them lightweight
    - text based (diag generated)
    - so they don't get the bad rap 
    - easier to throw away and start over
* use them to help discover and communicate
* provide a legend

#### Views and viewpoints
* can't have one diagram serve them all
* diff stake holders have diff needs

### Interfaces
* Each arrow in the arch dia is an interface
* too often we pay attention to boxes not arrows

#### API
* for each API create an interface table listing
    - throughput, latency reqs
    - synchronicity
    - transport, framing(payload), semantics
    - test harness needs

### Architectural qualities
* Qualities observed at run-time
    - Performance
        - how fast a transaction is handled
    - Security
        - confidentiality, integrity, availability 
    - Availability 
        - probability of a request being serviced 
    - Usability 
* Qualities not observable at run-time
    - Scalability
        - how far can increasing resources increase availability / perf
    - Modifiability 
    - Portability
        - across systems
    - Integrability
        - ability to integrate with other systems
        - e.g. are we going to develop a plugin framework that acommodates ..
    - Reusability 
    - Testability 
* Trade-offs among qualities
    - e.g. security vs usability
    - make explicit latent assumptions to rank the qualities in order of importance
    - rank changes at diff stages of sys life-cycle
    - e.g. Before MVP - emphasize modifiability 
            - finding product/market fit - rank: scalability, modifiability
            - ramping up - rank: security, availability, scalability 
            
### Architecturally Significant Requirements (ASRs)
* aka architecture killers
* requirements that cause significant arch change
* and people tend to keep them in their head as implicit assumptions
* e.g. 
    - multiple currencies, timezones
    - compliance, reporting
    - intelligence, semi-automated processes
    - any kind of DB synchronization 
    - human overrides
        - esp policy overrides
    - interfacing with physical world 

#### Constraints vs Requirements
* Constraints are absolute
* if a constraint is broken, the system can't go live
* constraints are less unique than requirements
    - e.g. similar constraints within a domain, market, company 
    - guidance can be shared
        - domain expertise can become valuable

### Decomposing the system
#### Internal boundaries
* Our decomposition must
    - terminate every external interface at the component
    - deliver the features
* Our decomposition should
    - support system wide qualities
    - support independent dev
    - isolate effect of changes
    - separate concerns into mechanisms
    
#### Terminology
* Component
    - runtime entity 
    - part of dynamic behaviour
    - interacts with other components
* Module
    - dev/compile time entity (libs, dlls)
    - part of the static structure 

#### Maxims
* Ask - what knowledge must be shared across this boundary ?
* Look for common mechanisms you can factor out 
* use both static, dynamic structures
    - can this be a lib ?
    - does this need to be a service ?
 
#### Evaluate the decomposition    
* Play out use cases / scenarios over the arch diag
    - trace out the workflow and analyze the flow across boundaries
    - to test out and exercise the decomposition    
    - Domain driven design helps here
* score less for more pass arounds (like golf)

#### decision hiding
* interfaces hide decisions inside modules
* provide an abstraction of the underlying data

#### References
* [On the criteria to be used in decomposing systems into modules | the morning paper](https://blog.acolyer.org/2016/09/05/on-the-criteria-to-be-used-in-decomposing-systems-into-modules/)
