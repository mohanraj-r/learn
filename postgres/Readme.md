# Postgres

- [Postgres](#postgres)
	- [Reference](#reference)
		- [Functions and Operators](#functions-and-operatorshttps---wwwpostgresqlorg-docs-current-static-functionshtml)
		- [Data types](#data-types)
		- [Tools](#tools)
		- [PostgreSQL: System Views](#postgresql--system-viewshttps---wwwpostgresqlorg-docs-current-static-views-overviewhtml)
	- [Resources](#resources)
		- [SQL](#sql)
		- [Books](#books)
		- [Courses](#courses)
		- [Performance](#performance)
			- [Monitors](#monitors)
		- [Go](#go)
		- [Extensions](#extensions)

## Reference
* [PostgreSQL: Documentation: 10](https://www.postgresql.org/docs/10/static/index.html)
* [PostgreSQL: Documentation: 10: Part I. Tutorial](https://www.postgresql.org/docs/current/static/tutorial.html)
* [PostgreSQL: Documentation: 10: 7.8. WITH Queries (Common Table Expressions)](https://www.postgresql.org/docs/current/static/queries-with.html)
* [PostgreSQL: Documentation: 10: Chapter 42. PL/pgSQL - SQL Procedural Language](https://www.postgresql.org/docs/current/static/plpgsql.html)
* [Foreign data wrappers - PostgreSQL wiki](https://wiki.postgresql.org/wiki/Foreign_data_wrappers)

### [Functions and Operators](https://www.postgresql.org/docs/current/static/functions.html)
* [PostgreSQL: Documentation: 10: 9.18. Array Functions and Operators](https://www.postgresql.org/docs/current/static/functions-array.html)
* [PostgreSQL: Documentation: 10: 9.4. String Functions and Operators](https://www.postgresql.org/docs/current/static/functions-string.html)
* [PostgreSQL: Documentation: 10: 9.20. Aggregate Functions](https://www.postgresql.org/docs/current/static/functions-aggregate.html)
* [PostgreSQL: Documentation: 10: 9.12. Network Address Functions and Operators](https://www.postgresql.org/docs/current/static/functions-net.html)
* [PostgreSQL: Documentation: 10: 9.15. JSON Functions and Operators](https://www.postgresql.org/docs/current/static/functions-json.html)
* [PostgreSQL: Documentation: 10: 9.9. Date/Time Functions and Operators](https://www.postgresql.org/docs/current/static/functions-datetime.html)

### Data types
* [PostgreSQL: Documentation: 10: 8.17. Range Types](https://www.postgresql.org/docs/current/static/rangetypes.html)

### Tools
* [PostgreSQL: Documentation: devel: pgbench](https://www.postgresql.org/docs/devel/static/pgbench.html)
* [PostgreSQL: Documentation: 10: pg_dump](https://www.postgresql.org/docs/current/static/app-pgdump.html)
* [PgBouncer FAQ](https://pgbouncer.github.io/faq.html)

### [PostgreSQL: System Views](https://www.postgresql.org/docs/current/static/views-overview.html)
* [pg_settings](https://www.postgresql.org/docs/current/static/view-pg-settings.html)
* [pg_file_settings](https://www.postgresql.org/docs/current/static/view-pg-file-settings.html)
* [pg_hba_file_rules](https://www.postgresql.org/docs/10/static/view-pg-hba-file-rules.html)
* [PostgreSQL: Documentation: The Statistics Collector](https://www.postgresql.org/docs/current/static/monitoring-stats.html)
	* a subsystem that supports collection and reporting of information about server activity
	* [pg_stat_activity](https://www.postgresql.org/docs/current/static/monitoring-stats.html#PG-STAT-ACTIVITY-VIEW)

## Resources
* [GitHub - dhamaniasad/awesome-postgres: A curated list of awesome PostgreSQL software, libraries, tools and resources, inspired by awesome-mysql](https://github.com/dhamaniasad/awesome-postgres)
    * [Postgres Guide](http://postgresguide.com/)
    * [Postgres Weekly Archives](https://postgresweekly.com/issues)
    * [GitHub - jackc/pgx: PostgreSQL driver and toolkit for Go](https://github.com/jackc/pgx)
* [Postgres Open SV 2018 - YouTube - YouTube](https://www.youtube.com/user/postgresopen/videos)
    * [Automated Performance Testing of Postgres - Greg Burek - YouTube](https://www.youtube.com/watch?v=QHxoXboBllo)
* [Jess88/Introduction-to-PostgreSQL: Introduction to PostgreSQL materials](https://github.com/Jess88/Introduction-to-PostgreSQL)

### SQL
* [SQL Fundamentals LiveLessons](https://www.safaribooksonline.com/library/view/sql-fundamentals-livelessons/9780768694765/)
* [Introduction to SQL | Pluralsight](https://app.pluralsight.com/library/courses/introduction-to-sql/table-of-contents)
* [Getting Started with SQL](https://www.safaribooksonline.com/library/view/getting-started-with/9781491938607/)

### Books

### Courses
* [The PostgreSQL Document Database - jsonb type | Pluralsight](https://app.pluralsight.com/library/courses/postgresql-document-database/table-of-contents)

### Performance
* [awesome-postgres/Monitoring](https://github.com/dhamaniasad/awesome-postgres/blob/master/README.md#monitoring)
* [awesome-postgres/Optimization](https://github.com/dhamaniasad/awesome-postgres/blob/master/README.md#optimization)
* [GitHub - gregs1104/pgbench-tools: PostgreSQL Benchmarking Toolset](https://github.com/gregs1104/pgbench-tools)
#### Monitors
* [pg_activity/README.md at master · julmon/pg_activity](https://github.com/julmon/pg_activity/blob/master/README.md)
* [dalibo/pgbadger: A fast PostgreSQL Log Analyzer with fully detailed reports and graphs](https://github.com/dalibo/pgbadger)
* [GitHub - powa-team/powa-archivist: PostgreSQL Workload Analyzer](https://github.com/powa-team/powa-archivist)
	* gathers performance stats and provides real-time charts and graphs to help monitor and tune your PostgreSQL servers.
* [GitHub - ankane/pghero: A performance dashboard for Postgres](https://github.com/ankane/pghero)
* [GitHub - spotify/postgresql-metrics: Tool that extracts and provides metrics on your PostgreSQL database](https://github.com/spotify/postgresql-metrics)
* [pgmetrics - Home](https://pgmetrics.io/)
	* looks like the metrics collector is OSS but the reporter dashboard is commercial

### [Go libs](./go-libs.md)

### Extensions
* [PGXN: PostgreSQL Extension Network](https://pgxn.org/)
