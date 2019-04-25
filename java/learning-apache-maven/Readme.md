# [Learning Apache Maven](https://learning.oreilly.com/videos/learning-apache-maven/9781771373661)

- [Learning Apache Maven](#learning-apache-maven)
    - [TODO](#todo)
  - [Intro](#intro)
  - [Archetypes](#archetypes)
  - [Inheritance](#inheritance)
  - [Profiles](#profiles)
  - [Dependency Management](#dependency-management)

### TODO

* [Maven – Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/index.html)
* [Maven – Introduction to the Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
* https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html
* http://maven.apache.org/plugins/maven-dependency-plugin/examples/resolving-conflicts-using-the-dependency-tree.html
* http://maven.apache.org/enforcer/maven-enforcer-plugin/

## Intro

* Convention over config
* `<build>` 
    * override std maven dirs
* `-X` 
    * debug flag
* ``jar tf foo.jar``
    * list contents of jar

## Archetypes

* `mvn archetype``:generate` 
    * generates a project structure based on an archetype
    * `mvn archetype``:``generate ``-``DarchetypeGroupId``=``org``.``apache``.``maven``.``archetypes ``-``DarchetypeArtifactId``=``maven``-``archetype``-``quickstart ``-``DarchetypeVersion``=``1.4`
        *  [Maven Quickstart Archetype – Maven Quickstart Archetype](https://maven.apache.org/archetypes/maven-archetype-quickstart/)
        * a quickstart archetype which generates a sample Maven project
    * 

## Inheritance

* `mvn help:effective-pom`
    * to display all the inherited POMs
* `<parent></parent>` 
    *  specify the project to inherit the POM from
*  `mvn install`
    * gets the parent POM and puts it in the local repo (~/.m2/...)

## Profiles

* `<profile>`
    * define different configuration for different environments
    * `mvn -P<profile> ...` 
        * select profile when executing
* `<activation>`
    * Activators to automatically select environment
        * without having to specify it in command line args
    * based on os, jdk, files, environment vars
    * `<property`>
        * specify environment vars to be associated with profiles

## Dependency Management

* dependencies are cached in local repository from remote repository