# Chapter 2. Database Administration

- [Chapter 2. Database Administration](#chapter-2-database-administration)
		- [Reference](#reference)
	- [Screwing up](#screwing-up)
	- [Configuration Files](#configuration-files)
	- [postgresql.conf](#postgresqlconf)
		- [CHECKING POSTGRESQL.CONF SETTINGS](#checking-postgresqlconf-settings)
		- [setting, boot_val, reset_val](#setting--boot-val--reset-val)
		- [Context](#context)
		- [Level](#level)
		- [pg_file_settings](#pg-file-settings)
		- [Connection settings](#connection-settings)
		- [Performance settings](#performance-settings)
	- [pg_hba.conf](#pg-hbaconf)
		- [Order of execution of rules](#order-of-execution-of-rules)
		- [SSL](#ssl)
		- [Auth methods](#auth-methods)
	- [Managing Connections](#managing-connections)
		- [operational parameters](#operational-parameters)
		- [User auth](#user-auth)
	- [Database Creation](#database-creation)
		- [Template Databases](#template-databases)
		- [Using Schemas](#using-schemas)
			- [search_path](#search-path)
	- [Privileges](#privileges)
	- [Extensions](#extensions)
		- [Installing](#installing)
		- [Popular extensions](#popular-extensions)
	- [Backup and Restore](#backup-and-restore)
	- [Managing Disk Storage with Tablespaces](#managing-disk-storage-with-tablespaces)
	- [Common mistakes](#common-mistakes)

### Reference
* [PostgreSQL: System Views](https://www.postgresql.org/docs/current/static/views-overview.html)
	* built-in views provided information about the system

## Screwing up
* The easiest way to figure out what you screwed up is to look at the logfile,
	* located at the root of the data folder,
	* or in the `pg_log` subfolder.
* A common culprit is setting shared_buffers too high.
* Another suspect is an old postmaster.pid left over from a failed shutdown.
	* You can safely delete this file, located in the data cluster folder, and try restarting again.

## Configuration Files
* postgresql.conf
	* Controls general settings, such as memory allocation, default storage location for new databases, the IP addresses that PostgreSQL listens on, location of logs, and plenty more.
* pg_hba.conf
	* Controls access to the server, dictating which users can log in to which databases, which IP addresses can connect, and which authentication scheme to accept.
* pg_ident.conf
	* If present, this file maps an authenticated OS login to a PostgreSQL user.
* Location of config files
	* `SELECT name, setting FROM pg_settings WHERE category = 'File Locations';`
* Reloading config
	* You can also log in as a superuser to any database and execute the following SQL:
		* `SELECT pg_reload_conf();`
	* More fundamental configuration changes require a restart.
		* You can perform a restart by stopping and restarting the postgres service (daemon)

## postgresql.conf
* You can override many settings at the database, role, session, and even function levels.
* [Tuning Your PostgreSQL Server - PostgreSQL wiki](https://wiki.postgresql.org/wiki/Tuning_Your_PostgreSQL_Server)
* instead of editing postgresql.conf directly,
	* you should override settings using an additional file called postgresql.auto.conf
* change settings using the ALTER SYSTEM SQL command
	* e.g `ALTER SYSTEM SET work_mem = '500MB';`
	* This command does not directly edit postgres.conf
		* but will make the change in postgres.auto.conf.
* If you have to track many settings, consider organizing them into multiple configuration files
	* and then linking them back using the `include` or `include_if_exists` directive within the postgresql.conf.

### CHECKING POSTGRESQL.CONF SETTINGS
* An easy way to read the current settings without opening the configuration files is to query the view named [pg_settings](https://www.postgresql.org/docs/current/static/view-pg-settings.html)
	```sql
	SELECT * FROM pg_settings
	```
* Key settings
	```sql
	SELECT
		name,
		context,
		unit,
		setting, boot_val, reset_val,
		applied,
	FROM pg_settings
	WHERE name IN ('listen_addresses','deadlock_timeout','shared_buffers',
		'effective_cache_size','work_mem','maintenance_work_mem')
	ORDER BY context, name;
	```
### setting, boot_val, reset_val
* setting is the current setting;
* boot_val is the default setting;
* reset_val is the new setting if you were to restart or reload the server.
* Make sure that setting and reset_val match after you make a change.
	* If not, the server needs a restart or reload.

### Context
* The context is the scope of the setting.
	* Some settings have a wider effect than others, depending on their context.
* User settings can be changed by each user to affect just that user’s sessions.
* If set by the superuser, the setting becomes a default for all users who connect after a reload.
	* Superuser settings can be changed only by a superuser, and will apply to all users who connect after a reload.
	* Users cannot individually override the setting.
* Postmaster settings affect the entire server (postmaster represents the PostgreSQL service) and take effect only after a restart.

### Level
* Settings with user or superuser context can be set for a specific database, user, session, and function level.
	* e.g if you have one function that is sort-intensive, you could raise work_mem just for it.
* Settings set at database, user, session, and function levels do not require a reload.
* Settings set at the database level take effect on the next connect to the database.
* Settings set for the session or function take effect right away.

### [pg_file_settings](https://www.postgresql.org/docs/current/static/view-pg-file-settings.html)
* pg_file_settings is a system view that outputs lists the source file where the settings can be found.
* The applied column tells you whether the setting is in effect
	* or if you need to reload or restart to make it take effect
* In cases where a particular setting is present in both postgresql.conf and postgresql.auto.conf,
	* the postgresql.auto.conf one will take precedent
	* and you’ll see the other files with applied set to false (f).
* query
	```sql
	SELECT name, sourcefile, sourceline, setting, applied
	FROM pg_file_settings
	WHERE name IN ('listen_addresses','deadlock_timeout','shared_buffers',
		'effective_cache_size','work_mem','maintenance_work_mem')
	ORDER BY name;
	```

### Connection settings
* *Caution*
	* an incorrect entry here will prevent clients from connecting.
	* Changing their values requires a service restart
* `listen_addresses`
	* IP addresses to listen on, usually defaults to local
* `port`
	* Defaults to 5432
* `max_connections`
	* maximum number of concurrent connections allowed
* `log_destination`
	* specifies the format of the logfiles rather than their physical location
* If you intend to perform extensive analysis on your logs,
	* we suggest changing it to `csvlog`,
	* which is easier to export to third-party analytic tools

### Performance settings
* `max_parallel_workers_per_gather`
	* The setting determines the maximum parallel worker threads that can be spawned for each gather operation
	* default setting is 0, which means parallelism is completely turned off
	* If you have more than one CPU core, you will want to elevate this
	* this number should be less than `max_worker_processes`,
		* which defaults to 8
		* because the parallel background worker processes are a subset of the maximum allowed processes.
* `shared_buffers`
	* Allocated amount of memory shared among all connections to store recently accessed pages
	* profoundly affects the speed of your queries
	* recommended setting - probably as much as 25% of your RAM
* `effective_cache_size`
	* An estimate of how much memory PostgreSQL expects the operating system to devote to it
	* This setting has no effect on actual allocation,
		*  but the query planner figures in this setting to guess whether intermediate steps and query output would fit in RAM
	* If you set this much lower than available RAM, the planner may forgo using indexes
	* recommended setting - probably as much as 50% of your RAM
* `work_mem`
	* Controls the maximum amount of memory allocated for each operation such as sorting, hash join, and table scans.
	* If you have many users running simple queries,
		* you want this setting to be relatively low to be democratic;
		* otherwise, the first user may hog all the memory
	* [Understanding postgresql.conf : work_mem](https://www.depesz.com/2011/07/03/understanding-postgresql-conf-work_mem/)
* `maintenance_work_mem`
	* total memory allocated for housekeeping activities
	* recommended setting - probably max 1GB


## pg_hba.conf
* The pg_hba.conf file controls which IP addresses and users can connect to the database
* it dictates the authentication protocol that the client must follow
* the [pg_hba_file_rules](https://www.postgresql.org/docs/10/static/view-pg-hba-file-rules.html) system view that lists all the contents of the pg_hba.conf file

### Order of execution of rules
* For each connection request, pg_hba.conf is checked from the top down.
* As soon as a rule granting access is encountered, a connection is allowed
	* and the server reads no further in the file.
* As soon as a rule rejecting access is encountered, the connection is denied
	* and the server reads no further in the file.
* If the end of the file is reached without any matching rules, the connection is denied.
*  A common mistake people make is to put the rules in the wrong order.

### SSL
* SSL configuration settings can be found in postgres.conf or postgres.auto.conf: `ssl`, `ssl_cert_file`, `ssl_key_file`.
* Once the server confirms that the client is able to support SSL, it will honor the connection request
	* and all transmissions will be encrypted using the key information.

### Auth methods
*  popular methods: trust, peer, ident, md5, and password
	*  More esoteric options abound, such as gss, radius, ldap, and pam.
	* You can elect more than one authentication method, even for the same database.
* `trust`
	* least secure authentication, essentially no password is needed
	* most common for PostgreSQL installed on a desktop for single-user local access where security is not a concern
* `password`
	* Uses clear-text password authentication.
* `ident`
	* Uses pg_ident.conf to check whether the OS account of the user trying to connect has a mapping to a PostgreSQL account.
	* The password is not checked
* `cert`
	* Stipulates that connections use SSL.
	* The client must have a registered certificate.

## Managing Connections
* Retrieve a listing of recent connections and process IDs
	* `SELECT * FROM pg_stat_activity;`
* Cancel active queries on a connection with PID
	* `SELECT pg_cancel_backend(pid);`
	* This does not terminate the connection itself, though.
* Terminate the connection
	* `SELECT pg_terminate_backend(pid);`
* Even though pg_terminate_backend and pg_cancel_backend act on only one connection at a time,
	* you can kill multiple connections by wrapping them in a SELECT.
	* `SELECT pg_terminate_backend(pid) FROM pg_stat_activity
WHERE usename = 'some_role';`
* to determine what queries are being blocked
	*  `wait_event IS NOT NULL` in `pg_stat_activity`

### operational parameters
* You can set certain operational parameters at the server, database, user, session, or function level.
* Any queries that exceed the parameter will automatically be cancelled by the server.
* Setting a parameter to 0 disables the parameter
* `deadlock_timeout`
	* This is the amount of time a deadlocked query should wait before giving up.
	* This defaults to 1000 ms.
	* If your application performs a lot of updates, you may want to increase this value to minimize contention.
	* Instead of relying on this setting, you can
		* include a NOWAIT clause in your update SQL: SELECT FOR UPDATE NOWAIT ...
			* The query will be automatically cancelled upon encountering a deadlock.
		* SELECT FOR UPDATE SKIP LOCKED will skip over locked rows.
* `statement_timeout`
	* This is the amount of time a query can run before it is forced to cancel.
	* This defaults to 0, meaning no time limit.
* `lock_timeout`
	* This is the amount of time a query should wait for a lock before giving up,
		* and is most applicable to update queries.
	* Before data updates, the query must obtain an exclusive lock on affected records.
	* The default is 0, meaning that the query will wait infinitely.
	* This setting is generally used at the function or session level.
	* lock_timeout should be lower than statement_timeout,
		* otherwise statement_timeout will always occur first, making lock_timeout irrelevant.
* `idle_in_transaction_session_timeout`
	* This is the amount of time a transaction can stay in an idle state before it is terminated.
	* This defaults to 0, meaning it can stay alive infinitely.
	* useful for preventing queries from holding on to locks on data indefinitely or eating up a connection.

### User auth
* PostgreSQL officially refers to users as roles.
* Not all roles need to have login privileges.
	* For example, group roles often do not.
	* We use the term user to refer to a role with login privileges.

## Database Creation
* `CREATE DATABASE mydb;`

### Template Databases
* A template database is a database that serves as a skeleton for new databases
* When you create a new database, PostgreSQL copies all the database settings and data from the template database to the new database.
* If you don’t specify a template database to follow when you create a database, `template1` is used.
* basic syntax to create a database modeled after a specific template
	* `CREATE DATABASE my_db TEMPLATE my_template_db;`
* You can pick any database to serve as the template
* You can also mark any database as a template database.
	* Once you do, the database is no longer editable and deletable
	* `UPDATE pg_database SET datistemplate = TRUE WHERE datname = 'mydb';`
	* If ever you need to edit or drop a template database,
		* first set the `datistemplate` attribute to FALSE.
		* Don’t forget to change the value back after you’re done with edits.

### Using Schemas
* Schemas organize your database into logical groups
* Objects must have unique names within a schema
	* but need not be unique across the database

#### search_path
* When the query executes, the planner searches for the table as defined in the `search_path`.
* If not found, it continues to the public schema and stops there.
* PostgreSQL has a little-known variable called `user` that retrieves the role currently logged in.
	* SELECT user returns this name.
	* user is just an alias for current_user, so you can use either.
* `search_path = "$user", public;` in postgresql.conf
	* would put the schema named with the same username in front of the public schema space
* Another practice that we strongly encourage is to create schemas to house extensions
	* When you install an extension, new tables, functions, data types, and plenty of other relics join your server
	* Before you install any extensions, create a new schema
		* `CREATE SCHEMA my_extensions;`
		* When you install extensions, be sure to indicate your new schema as their new home
		* Then add your new schema to the search path
			* `ALTER DATABASE mydb SET search_path='$user', public, my_extensions;`
			* will not take effect for existing connections. You’ll need to reconnect.

## Privileges
* You can assign different privileges to each data point of your table, if that ever becomes necessary.
* Row-level security (RLS)

*(skimmed)*

## Extensions
* You should install extensions to your individual database on an as-needed basis.
* If you want all your databases to have a certain set of extensions, you can develop a template database with all the extensions installed
* To view all extension binaries already available on your server
	* `SELECT * FROM pg_available_extensions;`
* To see which extensions you have already installed in a database
	```sql
	SELECT name, default_version, installed_version, left(comment,30) As comment
	FROM pg_available_extensions
	WHERE installed_version IS NOT NULL
	ORDER BY name;
	```
	* If you want to see all the extensions installed on the server, regardless of if they are installed in your current database, leave out the `WHERE installed_version IS NOT NULL`.
* To get more details about a particular extension already installed in your database, enter the following command from psql:
	* `\dx+ fuzzystrmatch`

### Installing
* First, download the extension and install it onto your server.
* Second, install the extension into your database.
* Use the CREATE EXTENSION command to install extensions into each database
	* uninstall them at will using DROP EXTENSION
* We strongly suggest you create one or more schemas to house extensions to keep them separate from production data.
	* After you create the schema, install extensions into it
	* `CREATE EXTENSION fuzzystrmatch SCHEMA my_extensions;`
*  To retrieve additional extensions, visit the [PGXN: PostgreSQL Extension Network](https://pgxn.org/)
	*  You’ll also find many PostgreSQL extensions on GitHub by searching for postgresql extension.

### Popular extensions
* hstore
	* An extension that adds key-value pair storage and index support, well-suited for storing pseudonormalized data.
	* If you are looking for a comfortable medium between a relational database and NoSQL, check out hstore.
	* Usage of hstore in many cases has been replaced with the built-in jsonb type.

## Backup and Restore
* PostgreSQL ships with three utilities for backup: pg_dump, pg_dumpall, and pg_basebackup
* Use `pg_dump` to back up specific databases.
* use the `pg_restore` utility to restore
* To back up all databases in plain text along with server globals, use `pg_dumpall`,
	* which needs to run under a superuser account so that it back up all databases.
* Use `pg_basebackup` to do system-level disk backup of all databases.
	* most efficient way of doing a full postgresql server cluster backup
* Two popular open source third-party tools you might want to consider are pgBackRest and Barman.
	* These offer additional features like backup scheduling, multiserver support, and restore shortcuts.

*(skimmed)*

## Managing Disk Storage with Tablespaces
*(skimmed)*

## Common mistakes
* Don’t Delete PostgreSQL Core System Files and Binaries
	* some folders sport innocuous names such as pg_log, pg_xlog, and pg_clog
	* Files in the other folders, except for pg_xlog, should never be deleted, even if they have log-sounding names.
	* Don’t even think of touching pg_clog, the active commit log, unless you want to invite disaster.
* Don’t Grant Full OS Administrative Privileges to the Postgres System Account (postgres)
* Don’t Set shared_buffers Too High
* Don’t Try to Start PostgreSQL on a Port Already in Use
