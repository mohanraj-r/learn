# Digging deeper with Postgres - inheritance

## Functions and scripts
* Create
	```sql
		CREATE FUNCTION <name>()
		RETURNS <data type>
		LANGUAGE [SQL|PLPGSQL|...]
		[IMMUTABLE|STABLE|VOLATILE]
		AS $$ <query> $$
	```
* Optional optimization indicators - will not be forced if it doesn't match the function content
	* IMMUTABLE - function cannot alter or read from database
  	* STABLE    - function may read from the database, but not alter it
  	* VOLATILE  - function read from database may change within the function:
* functions can have same names as long as they have diff args

### Variables
* `CREATE FUNCTION <name>(data type, data type ...)`
* Args are referred to within the function as `$1, $2 ..`
* Special case for dealing with `NULL`
	* `RETURNS NULL ON NULL INPUT`
* `DECLARE <var>` to declare vars to be used in functions
	* `SELECT .. INTO <var>` to assign values into vars
* `EXECUTE <query str>` can be used to dynamically construct and execute queries from inside functions

## Schemas
* namespace for (logical) organization of data
* use cases
	* separation by type of data by diff sources
	* separate data to improve queries
	* simplify queries
* `CREATE SCHEMA <name>`
* Use in queries as `... <schema>.<table>`
	* by default `public` is assumed
* `SET search_path to <schema>,...`
	* will limit queries to only the specified schema
	* applies to the session
	* public schema and all other schema that are not explicitly specified will be excluded

## Inheritance
* [PostgreSQL: Documentation: 10: 5.9. Inheritance](https://www.postgresql.org/docs/current/static/ddl-inherit.html)
* concept from object-oriented databases
* All check constraints and not-null constraints on a parent table are automatically inherited by its children,
	* unless explicitly specified otherwise with NO INHERIT clauses.
	* Other types of constraints (unique, primary key, and foreign key constraints) are not inherited.
* Table inheritance is typically established when the child table is created,
	* using the INHERITS clause of the CREATE TABLE statement.
	* can also be done using the INHERIT variant of ALTER TABLE
