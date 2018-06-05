# [7. SQL: The PostgreSQL Way - PostgreSQL: Up and Running, 3rd Edition](https://www.safaribooksonline.com/library/view/postgresql-up-and/9781491963401/ch07.html)

- [7. SQL: The PostgreSQL Way - PostgreSQL: Up and Running, 3rd Edition](#7-sql--the-postgresql-way---postgresql--up-and-running--3rd-editionhttps---wwwsafaribooksonlinecom-library-view-postgresql-up-and-9781491963401-ch07html)
	- [Views](#views)
		- [Single Table Views](#single-table-views)
		- [Using Triggers to Update Views](#using-triggers-to-update-views)
		- [Materialized Views](#materialized-views)
	- [Handy Constructions](#handy-constructions)
		- [DISTINCT ON](#distinct-on)
		- [LIMIT and OFFSET](#limit-and-offset)
		- [Shorthand Casting](#shorthand-casting)
		- [Multirow Insert](#multirow-insert)
		- [ILIKE for Case-Insensitive Search](#ilike-for-case-insensitive-search)
		- [ANY Array Search](#any-array-search)
		- [Set-Returning Functions in SELECT](#set-returning-functions-in-select)
		- [Restricting DELETE, UPDATE, and SELECT from Inherited Tables](#restricting-delete--update--and-select-from-inherited-tables)
		- [DELETE USING](#delete-using)
		- [Returning Affected Records to the User](#returning-affected-records-to-the-user)
		- [UPSERTs: INSERT ON CONFLICT UPDATE](#upserts--insert-on-conflict-update)
		- [Composite Types in Queries](#composite-types-in-queries)
		- [Dollar Quoting](#dollar-quoting)
		- [DO](#do)
		- [FILTER Clause for Aggregates](#filter-clause-for-aggregates)
		- [Percentiles and Mode](#percentiles-and-mode)
	- [Window Functions](#window-functions)
		- [PARTITION BY](#partition-by)
		- [ORDER BY](#order-by)
	- [Common Table Expressions](#common-table-expressions)
		- [Basic CTEs](#basic-ctes)
		- [Recursive CTE](#recursive-cte)
	- [Lateral Joins](#lateral-joins)
	- [WITH ORDINALITY](#with-ordinality)
	- [GROUPING SETS, CUBE, ROLLUP](#grouping-sets--cube--rollup)

* In this chapter, we’ll cover some SQL tidbits not often found in other databases.

## Views
* When you find yourself writing the same query over and over again, create a view.
* Simply put, a view is nothing more than a query permanently stored in the database.
* Some purists have argued that one should always query a view, never tables.
	* This means you must create a view for every table that you intend to query directly.
	* The added layer of indirection eases management of permissions and facilitates abstraction of table data.
* automatically updatable views
	* If your view draws from a single table and you include the primary key as an output column,
	* you can issue an update command directly against your view.
	* Data in the underlying table will follow suit.
* materialized (cached) views
	* When you mark a view as materialized, it will requery the data only when you issue the REFRESH command.
	* The upside is that you’re not wasting resources running complex queries repeatedly;
	* the downside is that you might not have the most up-to-date data when you use the view.
	* Furthermore, under some circumstances you are barred from access to the view during a refresh.
	* Version 9.4 allows users to access materialized views during refreshes.
		* It also introduced the WITH CHECK OPTION modifier, which prevents inserts and updates outside the scope of the view.

### Single Table Views
* The simplest view draws from a single table.
* Always include the primary key if you intend to write data back to the table
* `CREATE OR REPLACE VIEW <view name> AS <query>;`
* you can alter the data in this view by using INSERT, UPDATE, or DELETE commands.
* Updates and deletes will abide by any WHERE condition you have as part of your view.
* Be aware that you can insert data that places it outside of the view’s WHERE
	* or update data so it is no longer visible from the view
	* or excludes all data from the view
* For the sake of sanity, you may find it desirable to prevent updates or inserts that leave data invisible to further queries.
	* Include `WITH CHECK OPTION` modifier when creating the view and
	* PostgreSQL will forever balk at any attempts to add records outside the view and to update records that will put them outside the view
	* `CREATE OR REPLACE VIEW <view name> AS <query> WITH CHECK OPTION;`

### Using Triggers to Update Views
* Views can encapsulate joins among tables.
* When a view draws from more than one table, updating the underlying data with a simple command is no longer possible.
* Nonetheless, you can still modify the underlying data through the view using triggers.
*skimmed*

### Materialized Views
* Materialized views cache the fetched data.
* This happens when you first create the view as well as when you run the REFRESH MATERIALIZED VIEW command.
* The most convincing cases for using materialized views are
	* when the underlying query takes a long time and
	* when having timely data is not critical.
* Unlike nonmaterialized views, you can add indexes to materialized views to speed up the read.
* Current limitations of materialized views include:
	* You can’t use CREATE OR REPLACE to edit an existing materialized view.
		* You must drop and re-create the view even for the most trivial of changes.
		* you’ll lose all your indexes
	* You need to run REFRESH MATERIALIZED VIEW to rebuild the cache.
		* PostgreSQL doesn’t perform automatic recaching of any kind.
		* You need to resort to mechanisms such as crontab, pgAgent jobs, or triggers to automate any kind of refresh.
	* Refreshing materialized views is a blocking operation
		* you can lift this quarantine by adding the CONCURRENTLY keyword to your REFRESH command
		* provided that you have established a unique index on your view.
		* The trade-off is concurrent refreshes could take longer to complete.
*skimmed*


## Handy Constructions
* mostly not ANSI-compliant

### DISTINCT ON
* It behaves like `DISTINCT`, but with two enhancements:
	* you can specify which columns to consider as distinct and to sort the remaining columns
* The `ON` modifier accepts multiple columns, considering all of them to determine distinctness.
* The `ORDER` BY clause has to start with the set of columns in the DISTINCT ON;
	* then you can follow with your preferred ordering.

### LIMIT and OFFSET
* `LIMIT` returns only the number of rows indicated
* `OFFSET` indicates the number of rows to skip
* You can use them in tandem or separately.
* You almost always use them in conjunction with an ORDER BY

### Shorthand Casting
* ANSI SQL defines a construct called `CAST` that allows you to morph one data type to another.
	* e.g `CAST('2011-1-11' AS date)`
* PostgreSQL has shorthand for doing this, using a pair of colons `::`
	* e.g `'2011-1-1'::date`, `someXML::text::integer`

### Multirow Insert
* multirow constructor to insert more than one record at a time
* ```sql
	INSERT INTO <table> (<column>, <column>, <column>)
	VALUES
		(...),
		(...),
		...
	```
* `VALUES` can also be used as a stand-in for a virtual table
	* ```sql
		SELECT *
		FROM (
		VALUES
			(...),
			(...)
		) AS <table> (<column>, <column>, <column>);
		```
	* you need to specify the names for the columns.
	*  You also need to explicitly cast the values to the data types in the table if the parser can’t infer the data type from the data.

### ILIKE for Case-Insensitive Search
*  You can apply the `upper` function to both sides of the ANSI `LIKE` operator,
*  or you can simply use the ILIKE (~~*) operator
* `SELECT foo FROM bar WHERE foo ILIKE '%duke%';`

### ANY Array Search
* `ANY` can be used in conjunction with arrays, combined with a comparator operator or comparator keyword.
* If any element of the array matches a row, that row is returned.
* You can use ANY with other comparators such as LIKE, =, and ~ (the regex like operator).
* ```sql
	SELECT foo
	FROM bar
	WHERE foo ILIKE ANY(ARRAY['%99%duke%','%06%Barnstable%']::text[]);
	```

### Set-Returning Functions in SELECT
* A set-returning function is a function that could return more than one row.
* PostgreSQL allows set-returning functions to appear in the SELECT clause of an SQL statement.
* ```sql
	SELECT i_type,
    generate_series('2012-01-01'::date,'2012-12-31'::date,i_type) As dt
	FROM interval_periods;
	```

### Restricting DELETE, UPDATE, and SELECT from Inherited Tables
### DELETE USING
### Returning Affected Records to the User
*skimmed*

### UPSERTs: INSERT ON CONFLICT UPDATE
* This feature is useful if you don’t know a record already exists in a table and rather than having the insert fail, you want it to either update the existing record or do nothing.
```sql
	INSERT INTO colors(color, hex)
  		VALUES('Blue', '0000FF'), ('Red', 'FF0000'), ('Green', '00FF00')
	ON CONFLICT(lower(color))
	    DO UPDATE SET color = EXCLUDED.color, hex = EXCLUDED.hex;
```

### Composite Types in Queries
* Composites can serve as input to several useful functions
	* among which are `array_agg` and `hstore`
* you can take advantage of the built-in JSON and JSONB support
	* and use a combination of `array_agg` and `array_to_json` to output a query as a single JSON object
* ```sql
	SELECT array_to_json(array_agg(f)) As cat 1
	FROM (
		SELECT MAX(fact_type_id) As max_type, category 2
		FROM census.lu_fact_types
		GROUP BY category
	) As f;
	```
* This will give you an output of:
	```
	cats
	----------------------------------------------------
	[{"max_type":102,"category":"Population"},
	{"max_type":153,"category":"Housing"}]
	````

### Dollar Quoting
* In standard ANSI SQL, single quotes (') surround string literals.
* Should you have a single quote in the string itself you need to escape it with another
	* Affixing yet another single quote to all existing single quotes is both tedious to add and challenging to read.
* PostgreSQL lets you escape single quotes in strings of any length by surrounding them with two sequential dollar signs (`$$`), hence the name dollar quoting.

### DO
* The `DO` command allows you to inject a piece of procedural code into your SQL on the fly.
* You can think of it as a one-time anonymous function.

### FILTER Clause for Aggregates
* This replaces the standard CASE WHEN clause for reducing the number of rows included in an aggregation.
* The benefit is that FILTER is a little clearer in purpose and for large datasets is faster.
*skimmed*

### Percentiles and Mode
* statistical functions for computing percentile, median (aka .5 percentile), and mode.
* These functions are `percentile_disc` (percentile discrete), `percentile_cont` (percentile continuous), and `mode`.
* The two percentile functions differ in how they handle even counts.
	* For the discrete function, the first value encountered is taken, so the ordering of the data matters.
	* For the continuous case, values within the same percentile are averaged.
* Median is merely the .5 percentile; therefore, it does not deserve a separate function of its own.
* The mode function finds the most common value.
	* Should there be more than one mode, the first one encountered is returned; therefore, ordering matters


## Window Functions
* Window functions are a common ANSI SQL feature.
* A window function has the prescience to see and use data beyond the current row; hence the term window.
* A window defines which other rows need to be considered in addition to the current row.
* Windows let you add aggregate information to each row of your output where the aggregation involves other rows in the same window.
* Window functions such as row_number and rank are useful for ordering your data in sophisticated ways that use rows outside the selected results but within a window.
* Without window functions, you’d have to resort to using joins and subqueries to poll neighboring rows.
* The OVER sets the boundary of the window.
* You can use all SQL aggregate functions as window functions.

### PARTITION BY
* You can run a window function over rows containing particular values instead of using the whole table.
* This requires the addition of a PARTITION BY clause, which instructs PostgreSQL to take the aggregate over the indicated rows.

### ORDER BY
* Window functions also allow an ORDER BY in the OVER clause.

*skimmed*


## Common Table Expressions
* Essentially, common table expressions (CTEs) allow you to define a query that can be reused in a larger query.
* CTEs act as temporary tables defined within the scope of the statement;
	* they’re gone once the enclosing statement has finished executing.
* There are three ways to use CTEs:
	* Basic CTE
		* used to make your SQL more readable or to encourage the planner to materialize a costly intermediate result for better performance
	* Writable CTE
		* This is an extension of the basic CTE with UPDATE, INSERT, and DELETE commands.
		* A common final step in the CTE is to return changed rows.
	* Recursive CTE
		* The rows returned by a recursive CTE vary during the execution of the query.
		* PostgreSQL allows you to have a CTE that is both writable and recursive.

### Basic CTEs
* The `WITH` keyword introduces the CTE.
```sql
	WITH cte AS (
		SELECT
			...
	)
	SELECT ...
	FROM cte ...
```

### Recursive CTE
* recursive CTEs utilize UNION ALL to combine tables,
	* a kind of combination that can be done repeatedly as the query adds the tables over and over.
* To turn a basic CTE to a recursive one, add the `RECURSIVE` modifier after the `WITH`.
* `WITH RECURSIVE` can contain a mix of recursive and nonrecursive table expressions.
* A common use of recursive CTEs is to represent message threads and other tree-like structures.


## Lateral Joins
* `LATERAL` lets you share data in columns across two tables in a `FROM` clause.
* However, it works only in one direction: the righthand side can draw from the lefthand side, but not vice versa.
* There are situations when you should avail yourself of `LATERAL` to avoid extremely convoluted syntax.
* Lateral is also helpful for using values from the lefthand side to limit the number of rows returned from the righthand side.
* Although you can achieve the same results by using window functions, lateral joins yield faster results with more succinct syntax.
* You can use multiple lateral joins in your SQL and even chain them in sequence as you would when joining more than two subqueries.


## WITH ORDINALITY
* `WITH ORDINALITY` adds a sequential number column to a set-returning function result.
* Although you can’t use `WITH ORDINALITY` with tables and subqueries, you can achieve the same result for those by using the window function `ROW_NUMBER`
* You’ll find WITH ORDINALITY often used with functions like generate_series, unnest, and other functions that expand out composite types and arrays.
* It can be used with any set-returning function, including ones you create yourself.
* `WITH ORDINALITY` always adds an additional column at the end of the result called ordinality,
	* and `WITH ORDINALITY` can only appear in the `FROM` clause of an SQL statement.
	* You are free to rename the ordinality column.


## GROUPING SETS, CUBE, ROLLUP
* If you’ve ever tried to create a summary report that includes both totals and subtotals,
	* you’ll appreciate the capability to partition your data on the fly.
	* Grouping sets let you do exactly that.
