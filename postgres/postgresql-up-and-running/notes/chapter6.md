# Chapter 6. Tables, Constraints, and Indexes
- [Tables, Constraints, and Indexes](#tables--constraints--and-indexes)
	- [Indexes](#indexes)
		- [B-Tree](#b-tree)
		- [BRIN](#brin)
		- [GiST](#gist)
		- [GIN](#gin)
			- [vs GiST](#vs-gist)

## Indexes
* PostgreSQL is packaged with several types of indexes.
	* If still unsatisfied, you’re free to invent your own index type.
* PostgreSQL also allows you to mix and match different index types in the same table with the expectation that the planner will consider them all.
	* For instance, one column could use a B-Tree index while an adjacent column uses a GiST index, with both indexes contributing to speed up the queries.
	* [PostgreSQL: Documentation: 10: 11.5. Combining Multiple Indexes](https://www.postgresql.org/docs/current/static/indexes-bitmap-scans.html)
* [PostgreSQL: Documentation: 10: CLUSTER](https://www.postgresql.org/docs/current/static/sql-cluster.html)

### B-Tree
* default index
*  If PostgreSQL automatically creates an index for you or you don’t bother specifying the index method, B-Tree will be chosen.
*  It is currently the only indexing method for primary keys and unique keys.

### BRIN
* designed specifically for very large tables
	* where using an index such as B-Tree would take up too much space and not fit in memory.
* approach of BRIN is to treat a range of pages as one unit
* BRIN indexes are much smaller than B-Tree and other indexes and faster to build.
* But they are slower to use and can’t be used for primary keys or certain other situations.

### GiST
* Generalized Search Tree (GiST) is an index optimized for FTS, spatial data, scientific data, unstructured data, and hierarchical data.
* Although you can’t use it to enforce uniqueness, you can create the same effect by using it in an exclusion constraint.
* GiST is a lossy index, in the sense that the index itself will not store the value of what it’s indexing,
	* but merely a bounding value such as a box for a polygon.
	* This creates the need for an extra lookup step if you need to retrieve the value or do a more fine-tuned check.

### GIN
* Generalized Inverted Index (GIN) is geared toward the built-in full text search and binary json data type of PostgreSQL.
* [ Waiting for Faster LIKE/ILIKE](https://www.depesz.com/2011/02/19/waiting-for-9-1-faster-likeilike/)
#### vs GiST
* GIN is a descendent of GiST but without the lossiness.
* GIN will clone the values in the columns that are part of the index.
* If you ever need a query limited to covered columns, GIN is faster than GiST.
* However, the extra replication required by GIN means
	* the index is larger and updating the index is slower than a comparable GiST index.
* Also, because each index row is limited to a certain size, you can’t use GIN to index large objects such as large hstore documents or text.

### SP-GiST
* can be used in the same situations as GiST but can be faster for certain kinds of data distribution.
* PostgreSQL’s native geometric data types, such as point and box, and the text data type, were the first to support SP-GiST.

### hash
* Hash indexes were popular prior to the advent of GiST and GIN.
* General consensus rates GiST and GIN above hash in terms of both performance and transaction safety.
* Although hash indexes were relegated to legacy status for some time, they got some love in PostgreSQL 10.
	* they gained transactional safety and some performance improvements that made them more efficient than B-Tree in some cases.

### B-Tree-GiST/B-Tree-GIN
* the composite B-Tree-GiST or B-Tree-GIN indexes
	* available as extensions and included with most PostgreSQL distributions.
* These hybrids support the specialized operators of GiST or GIN, but also offer indexability of the equality operator like B-Tree indexes.
* You’ll find them indispensable when you want to create a compound index comprised of multiple columns containing both simple and complex types.
	* For example, you can have a compound index that consists of a column of plain text and a column of full text.
* Normally complex types such as full-text, ltree, geometric, and spatial types can use only GIN or GiST indexes, and thus can never be combined with simpler types that can only use B-Tree.
	* These combo methods allow you to combine columns indexed with GIST with columns indexed with B-Tree in a single index.

### third party index extensions
* [postgrespro/rum: RUM access method - inverted index with additional information in posting lists](https://github.com/postgrespro/rum)
* [vodka](https://www.pgcon.org/2014/schedule/attachments/318_pgcon-2014-vodka.pdf)
* [postgrespro/jsquery: JsQuery – json query language with GIN indexing support](https://github.com/postgrespro/jsquery)
	* primary goal is to provide an additional functionality to jsonb (currently missing in PostgreSQL),
		* such as a simple and effective way to search in nested objects and arrays, more comparison operators with indexes support.
	* We hope, that jsquery will be eventually a part of PostgreSQL.
* [PGroonga - Make PostgreSQL fast full text search platform for all languages!](https://pgroonga.github.io/)
	* supports indexing of regular text to produce full-text like functionality without needing to have a full-text vector, as the built-in PostgreSQL FTS requires.
	* PGRoonga also makes ILIKE and LIKE '%something%' indexable similar to the pg_trgm extension.
	* In addition, it supports indexing of text arrays and JSONB.

### Operator Classes
* [PostgreSQL: Documentation: 10: 11.9. Operator Classes and Operator Families](https://www.postgresql.org/docs/current/static/indexes-opclass.html)
* you’ll need to understand opclasses to troubleshoot the perennial question, “Why is the planner not taking advantage of my index?”
	* [Why is my index not being used - Postgres OnLine Journal](http://www.postgresonline.com/journal/archives/78-Why-is-my-index-not-being-used.html)
* A particular index will work only against a given set of opclasses.
* PostgreSQL groups operators into operator classes.
	* For example, the int4_ops operator class includes the operators = < > > < to be applied against the data type of int4 (commonly known as an integer).
* When you create an index without specifying the opclass, PostgreSQL chooses the default opclass for the index.
	* Generally, this is good enough, but not always.
* remember that each index you create works against only a single opclass.
	* If you would like an index on a column to cover multiple opclasses, you must create separate indexes
* The pg_opclass system table provides a complete listing of available operator classes, both from your original install and from extensions.
* To see this complete list, you can either open up pgAdmin and look under operator classes, or execute the query
	```sql
	SELECT am.amname AS index_method, opc.opcname AS opclass_name,
	opc.opcintype::regtype AS indexed_type, opc.opcdefault AS is_default
	FROM pg_am am INNER JOIN pg_opclass opc ON opc.opcmethod = am.oid
	WHERE am.amname = 'btree'
	ORDER BY index_method, indexed_type, opclass_name;
	```

### Functional Indexes
* PostgreSQL lets you add indexes to functions of columns.
	* `CREATE INDEX idx ON featnames_short
		USING btree (upper(fullname) varchar_pattern_ops);`
* Always use the same functional expression when querying to ensure use of the index.
	* `SELECT fullname FROM featnames_short WHERE upper(fullname) LIKE 'S%';`

### Partial indexes
* Partial indexes (sometimes called filtered indexes) are indexes that cover only rows fitting a predefined WHERE condition.
	* For instance, if you have a table of 1,000,000 rows, but you care about a fixed set of 10,000, you’re better off creating partial indexes.
* The resulting indexes can be faster because more can fit into RAM, plus you’ll save a bit of disk space on the index itself.
* Partial indexes let you place uniqueness constraints only on some rows of the data
	* `CREATE UNIQUE INDEX uq ON subscribers USING btree(lower(name)) WHERE is_active;`
	* This index is both PARTIAL and functional because what it indexes is lower(name) (not name).
* Functions used in the index’s WHERE condition must be immutable.
	* This means you can’t use time functions like CURRENT_DATE
	* or data from other tables (or other rows of the indexed table) to determine whether a record should be indexed.
* One warning we stress is that when you query the data, in order for the index to be considered by the planner,
	* the conditions used when creating the index must be a part of your WHERE condition and
	* any functions used in the index must also be used in the query filter.
* An easy way to not have to worry about this is to use a view.
	* `CREATE OR REPLACE VIEW vw_subscribers_current AS
SELECT id, lower(name) As name FROM subscribers WHERE is_active = true;`
	* Then always query the view instead of the table
	* any query against the view will automatically have that condition in it and be able to use the PARTIAL index

### Multicolumn Indexes
* you can also create functional indexes using more than one underlying column
* If you’re unable to predict how you’ll be querying compound fields in the future,
	* you may be better off creating single-column indexes and let the planner decide how to combine them during search.
* Keep in mind that the more columns you have in an index, the fatter your index and the less of it that can easily fit in RAM.
	* Don’t go overboard with compound indexes.
