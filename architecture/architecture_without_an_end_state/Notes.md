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
* visualize both 
    - control flow (usually dotted arrows)
    - data flow (usually solid arrows)
        - data doesn't flow from one store to another
            - without the help of a process
                - processes don't produce data without reading from a data source    
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

#### Microservices
* based on business workflow lifecycles are better
    - than entity CRUD based services

#### References
* [On the criteria to be used in decomposing systems into modules | the morning paper](https://blog.acolyer.org/2016/09/05/on-the-criteria-to-be-used-in-decomposing-systems-into-modules/)


# Incremental Architecture
* Better at times to defer a decision until we know more
    - and/or we have to act on it
* Time is fundamental
    - system lifespan
    - change time after launch
    
### What limits system lifespan?
* Tech dependencies get out of date
    - languages, libraries ..
* Messy code
    - hacks and workarounds piling up over time
* Business changes 

### Early work in architecture
Discovering/Clarifying 
* Business goals
* Constraints
* Architecture quality ranking
    - what are our priorities ?
* Architecturally Significant Requirements

### Ongoing work in architecture
Leave options open where possible
* Interface table
* Architecture Quality scenarios
* Internal boundaries (and dispute resolution)
* Information Architecture
* Evaluations of the Architecture
* Verification of the Implementations

### Risks
* Risk areas of the project need to be identified and focused more than others

#### Tech risks
* new language
* new deployment platform (cloud, new os, mobile)
* highly general tech stack

#### De-risking
* Benchmark 
    - comparision to other systems
    - use similar domain, tech
* Set-based concurrent engineering
    - instead of defining a solution
    - define a set of feasible choices 
    - more models, prototypes and POCs
    - common to have multiple full-scale models built at the same time
        - more throw away code at end
        - but more productive discussion 
            - rather than people getting hung on specifics
    - slow progression from rough to detailed design
    - avoid premature commitment
    - leave decisions open
        - cost of decision + switching cost < cost of working around lack of decision 
    - [Rational Unified Process - Wikipedia](https://en.wikipedia.org/wiki/Rational_Unified_Process)

### Minumum marketable feature (MMF)
* distinct, deliverable feature of the system
    - self-contained - deliverable without other features
    - smallest possible realization of that feature
* Observable to the user
    - provides significant value to the user
* Benefits
    - iterative dev, deploy
    - match investment to benefit at fine grain            
    
#### Architecture Element (AE)
* Underlying support for MMFs
* allows delivery of one or more MMFs
* Prioritize AEs that benefits more MMFs (Weighted Shortest Job First method)  
    - [WSJF – Scaled Agile Framework](https://www.scaledagileframework.com/wsjf/)
* do not deliver user-facing value on their own

##### Benefits
* makes architecture visible to stakeholders
* clearly shows deps 


# Responsibility allocation
### Coupling
* Runtime/operational
    - consumer cannot run without the provider
* development
    - code changes between consumer, provider should be co-ordinated
* responsibility
    - changes because of shared responsibility or concepts

### Cohesion
* does the module fit together as a logical unit ?

### Orthogonal
* separation of concerns
* high cohesion within a module/component
* low coupling across modules/components
* little overlap in functionality between modules
* information/decision hiding
    - Use interfaces
        - that are not 1:1 mapping of their corresponding concrete types

### Separation of concerns
* perennial struggle
    - from OOP to Micro-services
* common concerns for every system
    - I/O
    - config
    - credentials
    - encryption, authn, authz
    - deployment
    - failure and recovery
* and then there are domain specific concerns
* Ideally there is one mechanism per concern
    - all perfectly orthogonal and composable
* but pragmatically
    - look at the architectural priorities, constraints and ASRs
* Dimensions to work with in solving for sep of concerns
    - modules, components
    - processes
    - hosts
    - services
* beware of target fixation
    - focused so hard on avoiding something
        - that we run right into it
    - e.g. dep injection, microservices

### Localize
* Localize decisions
    - don't reveal your decisions
    - use common interfaces 
* don't let entity types proliferate through systems
    - keep them local
* even for internal systems
    - [Stevey's Google Platforms Rant I was at Amazon for about six and a half year...](https://plus.google.com/+RipRowan/posts/eVeouesvaVX)

### Upstream, downstream
* Changes in modules/components cause ripple effects 
* We want to build systems to minimize surface area of change
* Augment upstream
    - Add to data as early as possible
    - to avoid creating privileged downstreams
    
# Complex systems
* _skipped - only in slides_

# Information Architecture
* Type
    - Set of allowed values
        - preferrable: decision process to check for membership
    - Operations
* Focus on behavior
* Data duplication is ok
    - as long as there is a way to reconcile dups
* Too much normalization impedes future adaptability

## Challenge for microservices
* create noun based services
    - leads to entity service antipattern
* instead use adverbs, collection of actions/workflows on nouns   

# API Design
* make it easy for consumers to do the right thing
* also make it possible for provider to keep evolving
* Hyrum's law
    - all observable system behavior will be depended upon by users
        - irrespective of the doc or the published contract

## Breaking APIs
* API breaks when
    - a request which was accepted before is rejected
    - a response contains less than before
* Avoid breakage
    - prefer adding new interface

# Application architecture
* Patterns oriented Software arch
    - Layers
    - Big ball of mud
        - Big, ugly systems emerge from throwaway code
        - Well-defined architectures subject to structural erosion
    - Event stream
        - Scalable distributed systems with many writers
    - CQRS
        - Scalable distributed systems with many writers, particularly with a relational database involved.
        - optimized for reads vs writes
    - Dispatcher/Worker
        - Partitioning work into semantically identical subtasks.
    - Model- View-Controller
        - Interactive applications with a flexible human-computer interface.
    - Pipes and Filters
        - processing data streams in stages
    - Broker
        - Distributed, heterogeneous systems with independent components.
    - Blackboard
        - An immature domain in which no closed approach to a solution is known or feasible.
    - Microkernel    
        - Developing several applications that use similar APIs on top of the same core functionality.
    
