# Digging deeper with Postgresql

* [Cheat sheet](./resources/PostgreSQL_Org_processes_and_eval_performance/digging_deeper_organize_and_performance_cheat_sheet.txt)

- [Digging deeper with Postgresql](#digging-deeper-with-postgresql)
	- [Key Concept Review](#key-concept-review)
		- [Aliasing](#aliasing)
		- [Joins](#joins)
	- [Advanced functions](#advanced-functions)
		- [Having](#having)
		- [Str conccat](#str-conccat)
		- [Math funcs](#math-funcs)
		- [Sub-select, With and temporary tables](#sub-select-with-and-temporary-tables)
			- [sub-select](#sub-select)
			- [WITH or CTE (Common Table expressions)](#with-or-cte-common-table-expressions)
			- [Temporary tables](#temporary-tables)
	- [Views](#views)
		- [Create](#create)
		- [DELETE](#delete)
	- [Indexes](#indexes)

## Key Concept Review
* In PG, `SELECT .. GROUP BY` is more performant than `SELECT .. DISTINCT` on large DBs.

### Aliasing
* queries can refer to columns by their order in the output (e.g. `ORDER BY 1`)
* tables can be aliased in queries to shorter aliases to reduce len of queries

### Joins
* `ON`
	* is preferrable to use instead of `WHERE`
	* Query planner optimizes them differently?
* Set operations
	* `UNION`, `INTERSECT`, `EXCEPT`

## Advanced functions
### Having
* allows you to filter the results on result set
	* in cases `WHERE` would not be allowed or work
	* because `WHERE` needs access to the result set to operate and hence can't operate on agg funcs
* only allows filtering on aggregate functions

### Str conccat
* using `||`

### Math funcs
* `random`, `floor`, `ceil`

### Sub-select, With and temporary tables
* help organize data
* reduce cognitive load
* separate process components

#### sub-select
* Query within a query - nested `SELECT`
* the sub-query needs to have an alias where it is used as a table (`FROM (SELECT ..)`)
	* but no alias is needed when the sub-query is used as a list (`IN (SELECT ..)`)

#### WITH or CTE (Common Table expressions)
* temp view only available in the scope of the query
* fundamentally equiv to sub-select
	* but query optimizer might operate differently
	* creating efficient hashes instead of nested inner joins as with sub-selects
* `WITH <name> AS <query> ..`

#### Temporary tables
* exists within the connection/session
* but outside the query context as well
	* unlike CTE
* snapshot of data at the time of creation
* helps simplification of complex queries
* `CREATE TEMPORARY TABLE <name> as <query>`


## Views
* Persisted outside of a session/query
	* can also be optionally temporary
* Reflects updates to tables
	* reruns the query each time its accessed
* Materialized view stores results on disk
* common uses
	* summarize, consolidate data
	* restructure data to be easier to consume
	* limit access to data
* tables (even temp) with same name will override the views
	* tables will be created without an error or warning
	* best practice is to put views and tables in different schemas
		* and explicitly refer to them

### Create
* `CREATE [TEMPORARY] VIEW <name> AS <query>`

### DELETE
* `TRUNCATE <table>`
	* is more efficient to get rid of all data and start over
		* compared to `DELETE FROM ..`
			* since `DELETE` can have `WHERE` clause
			* so `TRUNCATE` offers near constant runtime
	* preserves perms and other meta-data on the table/view


## Indexes
* create quick lookup tables that postgres uses
* essentially a pointer to data in table
* over-indexing - diminishing returns
* speads up read queries
* slows down write queries - update, insert
* primary keys are automatically indexed
* to make an index take effect immediately on an existing table use `Vacuum <table>`
* Where to apply indexes
	* nested joins
		* columns being joined on
	* full table scans
* Use output of `EXPLAIN` to determine if an index is helping
	* compare outputs before and after creating index
