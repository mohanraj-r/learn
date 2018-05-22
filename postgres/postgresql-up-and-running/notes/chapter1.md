# Chapter 1. The Basics

- [Chapter 1. The Basics](#chapter-1-the-basics)
	- [Application platform](#application-platform)
	- [PostgreSQL Database Objects](#postgresql-database-objects)
		- [Attributes](#attributes)
	- [new features in 10](#new-features-in-10)
		- [new features in 9.6](#new-features-in-96)
		- [new features in 9.5](#new-features-in-95)

## Application platform
* you can define additional data types to suit your needs
	* PostgreSQL automatically creates a companion array type for you
* PostgreSQL also automatically creates types from any tables you define
* This amazingly useful bridge between the relational world and the object world 
	* means that you can treat data elements in a way that’s convenient for the task at hand. 
	* You can create functions that work on one object at a time or functions that work on sets of objects at a time.
*  Many third-party extensions for PostgreSQL leverage custom types 
	*  to achieve performance gains, provide domain-specific constructs for shorter and more maintainable code

## PostgreSQL Database Objects
Quick overview to those objects that you should be familiar with:
* Databases
* Schemas
	* immediate next level of organization within each database
	* Most database objects first belong to a schema, which belongs to a database.
	* When you create a new database, PostgreSQL automatically creates a schema named public to store objects that you create
		* if you have thousands of tables, you should organize them into different schemas.
* Tables
	* are first citizens of their respective schemas
	* PostgreSQL tables have two remarkable talents: 
		* first, they are inheritable.
			* Table inheritance streamlines your database design and 
			* can save you endless lines of looping code when querying tables with nearly identical structures
		* Second, whenever you create a table, PostgreSQL automatically creates an accompanying custom data type.
* Views
	* In a view, you can query multiple tables and present additional derived columns based on complex calculations
	* PostgreSQL allows you to update the underlying data by updating the view, 
		* provided that the view draws from a single table. 
		* To update data from views that join multiple tables, 
			* you need to create a trigger against the view.
	* materialized views cache data to speed up commonly used queries 
		* at the sacrifice of having the most up-to-date data.
* Extension
	* Extensions allow developers to package functions, data types, casts, custom index types, tables, attribute variables, etc., for installation or removal as a unit.
	* you must enable the extension for each database separately
	* When you enable extensions, you choose the schemas where all constituent objects will reside.
		* We recommend that you create a separate schema that will house all extensions
		* For an extension with many objects, we suggest that you create a separate schema devoted entirely to it
	* Extensions may depend on other extensions
		* you simply need to add the CASCADE option and PostgreSQL will take care of the rest
* Functions
	* You can program your own custom functions to handle data manipulation, perform complex calculations, or wrap similar functionality
* Languages
	* PostgreSQL installs three by default: SQL, PL/pgSQL, and C
* Operators
	* In PostgreSQL, you can invent your own operators
	* This is often the case when you create custom data types
* Foreign tables and foreign data wrappers
	* Foreign tables are virtual tables linked to data outside a PostgreSQL database
	* Foreign tables can link to 
		* CSV files, a PostgreSQL table on another server, a table in a different DB
		* a NoSQL database such as Redis
		* or even a web service such as Twitter or Salesforce
* Triggers and trigger functions
	* A trigger can run in response to particular types of statements or 
		* in response to changes to particular rows, and 
		* can fire before or after a data-change event.
	* Trigger functions differ from regular functions in that they have access to special variables 
		* that store the data both before and after the triggering event. 
		* This allows you to reverse data changes made by the event during the execution of the trigger function. 
			* Because of this, trigger functions are often used to write complex validation routines 
				* that are beyond what can be implemented using check constraints.
* Catalogs
	* Catalogs are system schemas that store PostgreSQL builtin functions and metadata. 
	* Every database contains two catalogs: 
		* pg_catalog, 
			* which holds all functions, tables, system views, casts, and types packaged with PostgreSQL; 
		* information_schema, 
			* which offers views exposing metadata in a format dictated by the ANSI SQL standard.
* Types
	* PostgreSQL has composite types, which are made up of other types.
	* Whenever you create a new table, PostgreSQL automatically creates a composite type 
		* based on the structure of the table. 
		* This allows you to treat table rows as objects in their own right. 
	* pgAdmin doesn’t make the automatic type creation obvious because it does not list them under the types node
* Full text search
	* Full text search (FTS) is a natural language–based search
	* Unlike regular expression search, FTS can match based on the semantics of an expression
		* e.g. searching for the word running in a long piece of text, you may end up with run, running, ran, runner, jog, sprint, dash, and so on
	* Three objects in PostgreSQL together support FTS: FTS configurations, FTS dictionaries, and FTS parsers.
		* For general use cases, the configurations, dictionaries, and parsers packaged with PostgreSQL are sufficient
		* But should you be working in a specific industry with specialized vocabulary and syntax rules such as pharmacology
			* you can swap out the packaged FTS objects with your own
* Casts
	* Casts prescribe how to convert from one data type to another. 
	* They are backed by functions that actually perform the conversion.
	* In PostgreSQL, you can create your own casts and override or enhance the default casting behavior. 
* Sequences
	* A sequence controls the autoincrementation of a serial data type. 
	* PostgresSQL automatically creates sequences when you define a serial column, 
		* but you can easily change the initial value, step, and next available value.
	* Because sequences are objects in their own right, more than one table can share the same sequence object. 
		* This allows you to create a unique key value that can span tables.
* Rules
	* Rules are instructions to rewrite an SQL prior to execution
	* they’ve fallen out of favor because triggers can accomplish the same things

### Attributes
* For each object, PostgreSQL makes available many attribute variables that you can set. 
* You can set variables at the server level, at the database level, at the function level, and so on.


## new features in 10
* Query parallelization improvements
	* There are new planner strategies for parallel queries
	* These changes allow a wider range of queries to be parallelized 
* Logical replication
	* Logical replication provides two features that streaming replication did not have. 
	* You can now replicate just a table or a database (no need for the whole cluster); 
		* since you are replicating only part of the data, 
		* the slaves can have their own set of data that is not involved in replication.
* Full text support for JSON and JSONB
* FDW push down aggregates to remote servers
	* FDW API can now run aggregations such as COUNT(*) or SUM(*) on remote queries
* Declarative table partitioning
	* In prior versions, if you had a table you needed to partition but query as a single unit, 
		* you would utilize PostgreSQL table inheritance support. 
	* Using inheritance was cumbersome in that you had to write triggers to reroute data to a table PARTITION 
		* if adding to the parent table. 
	* PostgreSQL 10 introduces the PARTITION BY construct. 
		* PARTITION BY allows you to create a parent table with no data, 
		* but with a defined PARTITION formula. 
		* Now you can insert data into the parent table without the need to define triggers. 
* CREATE STATISTICS
	* New construct for creating statistics on multiple columns

### new features in 9.6
* Query parallelization
	* PostgreSQL engine can distribute certain types of queries across multiple cores and processers
	* Qualified queries include those with sequential scans, some joins, and some aggregates.
	* However, queries that involve changing data such as deletes, inserts, and updates are not parallelizable
* Phrase full text search
	* Use the distance operator <-> in a full text search query 
		* to indicate how far two words can be apart from each other and still be considered a match

### new features in 9.5
* Insert and update conflict handling
	* Prior to 9.5, any inserts or updates that conflicted with primary key and check constraints would automatically fail. 
	* Now you have an opportunity to catch the exception and offer an alternative course, or to skip the records causing the conflict.
* Row-level security
	* ability to set visibility and updatability on rows of a table using policies
	* especially useful for multitenant databases or 
		* situations where security cannot be easily isolated by segmenting data into different tables