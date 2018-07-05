# Postgres Go libs

## [go-pg/pg: PostgreSQL ORM for Golang with focus on PostgreSQL features and performance](https://github.com/go-pg/pg)
* [Home · go-pg/pg Wiki](https://github.com/go-pg/pg/wiki)
### Extensive set of features
* types: .. net.IP, net.IPNet
* Structs, maps and arrays are marshalled as JSON by default.
	* Hstore using hstore tag and Hstore wrapper.
* Pagination and URL filters helpers.
* ForEach that calls a function for each row returned by the query without loading all rows into the memory.
* Transactions.
* Notifications using LISTEN and NOTIFY.
* Timeouts.
	* Automatic connection pooling with circuit breaker support.
	* Queries retries on network errors.
	* Works with PgBouncer in transaction pooling mode.
* Bulk/batch inserts, updates, and deletes.
* Copying data using COPY FROM and COPY TO.
* Working with models using ORM and SQL.
	* Scanning variables using ORM and SQL.
* SelectOrInsert using on-conflict.
	* INSERT ... ON CONFLICT DO UPDATE using ORM.
* Common table expressions using WITH and WrapWith.
* CountEstimate using EXPLAIN to get estimated number of matching rows.
* ORM supports has one, belongs to, has many, and many to many with composite/multi-column primary keys.
* Creating tables from structs.
* Migrations.
* Sharding.

## [jackc/pgx: PostgreSQL driver and toolkit for Go](https://github.com/jackc/pgx)
* Maps inet and cidr PostgreSQL types to net.IPNet and net.IP
	* Supports custom types
* Able to set runtime params on conn config
	* e.g. work mem when we need complex joins etc
* Batch queries
* Binary format support for custom types (can be much faster)
* Copy protocol support for faster bulk data loads
* Listen / notify
* PostgreSQL array to Go slice mapping for integers, floats, and strings
* JSON and JSONB support
    * Hstore support
* Extendable logging support including built-in support for log15 and logrus

## [mgutz/dat: Go Postgres Data Access Toolkit](https://github.com/mgutz/dat)
* JSON Document retrieval
* SQL-like API
* Nested transactions
* Per query timeout with database cancellation logic pg_cancel_backend
* SQL and slow query logging
* caching backed by an in-memory or Redis store

## [xo/xo: Command line tool to generate idiomatic Go code for SQL databases supporting PostgreSQL, MySQL, SQLite, Oracle, and Microsoft SQL Server](https://github.com/xo/xo)
* works by using database metadata and SQL introspection queries to discover the types and relationships contained within a schema, and applying a standard set of base (or customized) Go templates against the discovered relationships.
* can generate types for tables, enums, stored procedures, and custom SQL queries for PostgreSQL, MySQL, Oracle, Microsoft SQL Server, and SQLite3 databases.

## [lib/pq: Pure Go Postgres driver for database/sql](https://github.com/lib/pq)
### bad project management
* 110 open issues, 53 open PRs (June 7, 2018)

## [gobuffalo/pop: A Tasty Treat For All Your Database Needs](https://github.com/gobuffalo/pop)
* [callbacks](https://github.com/gobuffalo/pop#callbacks)
   * execute code before and after database operations.
   * This is done by defining specific methods on your models.
* allows you to create models and their associations in one step
* support for migrations
	* will generate SQL migrations (both the up and down) files for you
* [Generating Models](https://github.com/gobuffalo/pop#generating-models)
	*  will generate Go models and, optionally, the associated migrations for you
* easily configured using a YAML file
	* DB connection params

## Other notable libs/tools
* [rapidloop/ptgo: PostgreSQL Triggers in Go](https://github.com/rapidloop/ptgo)
* [GitHub - PostgREST/postgrest: REST API for any Postgres database](https://github.com/PostgREST/postgrest)
    * [GitHub - prest/prest: pREST is a way to serve a RESTful API from any databases written in Go](https://github.com/prest/prest)
* [galeone/igor: igor is an abstraction layer for PostgreSQL with a gorm like syntax.](https://github.com/galeone/igor)
* [sosedoff/pgweb: Cross-platform client for PostgreSQL databases](https://github.com/sosedoff/pgweb)
