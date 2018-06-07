# [5. Data Types](https://www.safaribooksonline.com/library/view/PostgreSQL:+Up+and+Running,+3rd+Edition/9781491963401/ch05.html)
- [5. Data Types](#5-data-typeshttps---wwwsafaribooksonlinecom-library-view-postgresql-upandrunning-3rdedition-9781491963401-ch05html)
	- [Intro](#intro)
	- [Numerics](#numerics)
		- [Serials](#serials)
		- [Generate Series Function](#generate-series-function)
	- [Textuals](#textuals)
		- [char](#char)
		- [varchar](#varchar)
		- [text](#text)
	- [String Functions](#string-functions)
		- [Splitting Strings into Arrays, Tables, or Substrings](#splitting-strings-into-arrays--tables--or-substrings)
		- [Regular Expressions and Pattern Matching](#regular-expressions-and-pattern-matching)
	- [Temporals](#temporals)
		- [date](#date)
		- [time (aka time without time zone)](#time-aka-time-without-time-zone)
		- [timestamp (aka timestamp without time zone)](#timestamp-aka-timestamp-without-time-zone)
		- [timestamptz (aka timestamp with time zone)](#timestamptz-aka-timestamp-with-time-zone)
		- [timetz (aka time with time zone)](#timetz-aka-time-with-time-zone)
		- [interval](#interval)
		- [tsrange](#tsrange)
		- [tstzrange](#tstzrange)
		- [daterange](#daterange)
		- [Time Zones: What They Are and Are Not](#time-zones--what-they-are-and-are-not)
		- [Datetime Operators and Functions](#datetime-operators-and-functions)
	- [Arrays](#arrays)
		- [Unnesting Arrays to Rows](#unnesting-arrays-to-rows)
		- [Array Slicing and Splicing](#array-slicing-and-splicing)
		- [Referencing Elements in an Array](#referencing-elements-in-an-array)
		- [Array Containment Checks](#array-containment-checks)
	- [Range Types](#range-types)
		- [Discrete Versus Continuous Ranges](#discrete-versus-continuous-ranges)
		- [Built-in Range Types](#built-in-range-types)
		- [Defining Ranges](#defining-ranges)


## Intro
* PostgreSQL supports the workhorse data types of any database: numerics, strings, dates, times, and booleans.
* But PostgreSQL sprints ahead by adding support for arrays, time zone−aware datetimes, time intervals, ranges, JSON, XML, and many more.
* If that’s not enough, you can invent custom types.
* No data type would be useful without a cast of supporting functions and operators.
	* And PostgreSQL has plenty of them

## Numerics
### Serials
* Serial and its bigger sibling, bigserial, are auto-incrementing integers often used as primary keys of tables in which a natural key is not apparent.
* In PostgreSQL, the sequence type is a database asset in its own right.
	* You can inspect and edit the sequences using SQL with the `ALTER SEQUENCE` command or using PGAdmin.
	* You can set the current value, boundary values (both the upper and lower bounds), and even how many numbers to increment each time.
	* Though decrementing is rare, you can do it by setting the increment value to a negative number.
* Because sequences are independent database assets, you can create them separately from a table using the `CREATE SEQUENCE` command,
	* and you can use the same sequence across multiple tables.
	* The cross-table sharing of the same sequence comes in handy when you’re assigning a universal key in your database.
	* To use an extant sequence for subsequent tables, create a new column in the table as integer or bigint
		* not as serial, then set the default value of the column using the `nextval(sequence_name)` function
	```sql
		CREATE SEQUENCE s START 1;
		CREATE TABLE stuff(id bigint DEFAULT nextval('s') PRIMARY KEY, name text);
	```

### Generate Series Function
* The function comes in two forms.
	* One is a numeric version that creates a sequence of integers incremented by some value and
	* one that creates a sequence of dates or timestamps incremented by some time interval.
* What makes `generate_series` so convenient is that it allows you to effectively mimic a for loop in SQL.
* The default step is 1.
	* can pass in an optional step argument to specify how many steps to skip for each successive element.
	* The end value will never exceed our prescribed range

## Textuals
* There are three primitive textual types in PostgreSQL:
	* character (abbreviable as char),
	* character varying (abbreviable as varchar), and
	* text.

### char
* Use char only when the values stored are fixed length,
	* such as postal codes, phone numbers, and Social Security numbers in the US.
* If your value is under the length specified, PostgreSQL automatically adds spaces to the end.
* When compared with varchar or text, the right-padding takes up more superfluous storage,
	* but you get the assurance of an invariable length.
* There is absolutely no speed performance benefit of using char over varchar or text
	* and char will always take up more disk space.

### varchar
* Use character varying to store strings with varying length.
* When defining varchar columns, you should specify the maximum length of a varchar.
* The max length modifier for varchar is optional.
	* Without it, varchar behaves almost identically to text.
	* Subtle differences do surface when connecting to PostgreSQL via drivers.
* Both varchar and text have a maximum storage of 1G for each value!
* Overriding operators is easier for varchar than it is for text.

### text
* Text is the most generic of the textual data types.
* With text, you cannot specify a maximum length.

## String Functions
* Common string manipulations are
	* padding (lpad, rpad),
	* trimming whitespace (rtrim, ltrim, trim, btrim),
		* By default, trim functions remove spaces,
		* but you can pass in an optional argument indicating other characters to trim.
	* extracting substrings (substring), and
	* concatenating (||)
* A helpful function for aggregating strings is the `string_agg` function
	* input values concatenated into a string, separated by delimiter

### Splitting Strings into Arrays, Tables, or Substrings
* The `split_part` function is useful for extracting an element from a delimited string
* The `string_to_array` function is useful for creating an array of elements from a delimited string.
* By combining `string_to_array` with the `unnest` function, you can expand the returned array into a set of rows

### Regular Expressions and Pattern Matching
* You can return matches as tables or arrays and choreograph replaces and updates.
* Back-referencing and other fairly advanced search patterns are also supported.
* `regexp_matches` returns a string array consisting of matches of a regular expression.
	* The last input to our function is the flags parameter.
	* We set this to g, which stands for global and returns all matches of a regular expression as separate elements.
		* If you leave out this flags parameter, then your array will only contain the first match.
	* In addition to the global flag, other allowed flags are listed in [POSIX EMBEDDED OPTIONS](https://www.postgresql.org/docs/current/static/functions-matching.html#POSIX-EMBEDDED-OPTIONS-TABLE).
* `unnest` explodes an array into a row set.
* If you only care about the first match, you can utilize the `substring` function
* In addition to the wealth of regular expression functions, you can use regular expressions with the SIMILAR TO (~) operators

## Temporals
* In addition to the usual dates and times types, PostgreSQL supports time zones, enabling the automatic handling of daylight saving time (DST) conversions by region.
* At last count, PostgreSQL has nine temporal data types.
	* Understanding their distinctions is important in ensuring that you choose the right data type for the job.
	* All the types except range abide by ANSI SQL standards.
* Specialized data types such as interval offer datetime arithmetic.
* PostgreSQL also understands infinity and negative infinity
* Range types provide support for temporal ranges with a slew of companion operators, functions, and indexes.
* PostgreSQL temporal types vary in a number of ways to handle different situations.
	* If a type is time zone−aware, the time changes if you change your server’s time zone.

### date
* Stores the month, day, and year, with no time zone awareness and no concept of hours, minutes, or seconds.

### time (aka time without time zone)
* Stores hours, minutes, and seconds with no awareness of time zone or calendar dates.

### timestamp (aka timestamp without time zone)
* Stores both calendar dates and time (hours, minutes, seconds) but does not care about the time zone.

### timestamptz (aka timestamp with time zone)
* A time zone−aware date and time data type.
* Internally, timestamptz is stored in Coordinated Universal Time (UTC),
	* but its display defaults to the time zone of the server, the service config, the database, the user, or the session.
* Yes, you can observe different time zones at different levels.
	* If you input a timestamp with no time zone and cast it to one with the time zone, PostgreSQL assumes the default time zone in effect.
	* If you don’t set your time zone in postgresql.conf, the server’s default takes effect.
	* This means that if you change your server’s time zone, you’ll see all the displayed times change after the PostgreSQL server restarts.

### timetz (aka time with time zone)
* The lesser-used sister of timestamptz.
* It is time zone−aware but does not store the date.
* It always assumes DST of the current date and time.
* Some programming languages with no concept of time without date might map timetz to a timestamp with some arbitrary date such as Unix Epoch 1970, resulting in year 1970 being assumed.

### interval
* A duration of time in hours, days, months, minutes, and others.
* It comes in handy for datetime arithmetic.

### tsrange
* Allows you to define opened and closed ranges of timestamp with no timezone.
* The type consists of two timestamps and opened/closed range qualifiers.
	* For example, `'[2012-01-01 14:00, 2012-01-01 15:00)'::tsrange` defines a period starting at 14:00 but ending before 15:00.

### tstzrange
* Allows you to define opened and closed ranges of timestamp with timezone.

### daterange
* Allows you to define opened and closed ranges of dates.

### Time Zones: What They Are and Are Not
* A common misconception with PostgreSQL time zone−aware data types is that PostgreSQL records an extra time marker with the datetime value itself.
* PostgreSQL doesn’t store the time zone, but uses it only to convert the datetime to UTC before storage.
	* After that, the time zone information is discarded.
* When PostgreSQL displays datetime, it does so in the default time zone dictated by the session, user, database, or server, in that order.
* If you use time zone−aware data types, you should consider the consequence of a server move from one time zone to another.

### Datetime Operators and Functions
* The inclusion of a temporal interval data type greatly eases date and time arithmetic in PostgreSQL.
* With intervals, we can add and subtract timestamp data simply by using the arithmetic operators we’re intimately familiar with.
* The addition operator (+) adds an interval to a timestamp
	* You can also add intervals
* The subtraction operator (-) subtracts an interval from a temporal type
* `OVERLAPS` returns true if two temporal ranges overlap.
	* `OVERLAPS` takes four parameters, the first pair constituting one range and the last pair constituting the other range.
* By default, generate_series assumes timestamptz if you don’t explicitly cast values to timestamp


## Arrays
* particularly useful in building aggregate functions, forming IN and ANY clauses,
	* and holding intermediary values for morphing to other data types.
* In PostgreSQL, every data type has a companion array type.
* If you define your own data type, PostgreSQL creates a corresponding array type in the background for you.
* The most rudimentary way to create an array is to type the elements:
	* `SELECT ARRAY[2001, 2002, 2003] As yrs;`
* If the elements of your array can be extracted from a query, you can use the more sophisticated constructor function, array()
	* `SELECT array( SELECT <query> ... );`
* Although the array function has to be used with a query returning a single column, you can specify a composite type as the output, thereby achieving multicolumn results.
* You can cast a string representation of an array to an array with syntax of the form:
	* `SELECT '{Alex,Sonia}'::text[] As name, '{46,43}'::smallint[] As age;`
* You can convert delimited strings to an array with the `string_to_array` function
* `array_agg` is an aggregate function that can take a set of any data type and convert it to an array
	* array_agg support for arrays makes it much easier to build multidimensional arrays from one-dimensional arrays
	* In order to aggregate arrays, they must be of the same data type and the same dimension

### Unnesting Arrays to Rows
* A common function used with arrays is unnest, which allows you to expand the elements of an array into a set of rows
*skimmed*

### Array Slicing and Splicing
* PostgreSQL also supports array slicing using the start:end syntax.
* It returns another array that is a subarray of the original
* To glue two arrays together end to end, use the concatenation operator ||
	* You can also add additional elements to an existing array using ||

### Referencing Elements in an Array
* PostgreSQL array indexes start at 1.
* If you try to access an element above the upper bound, you won’t get an error—only NULL will be returned.
* `array_upper` function to get the upper bound of the array

### Array Containment Checks
* Arrays also support the following comparison operators: =, <>, <, >, @>, <@, and &&.
* These operators require both sides of the operator to be arrays of the same array data type.
	* If you have a GiST or GIN index on your array column, the comparison operators can utilize them.
* The overlap operator (&&) returns true if two arrays have any elements in common.
* The equality operator (=) returns true only if elements in all the arrays are equal and in the same order.
* If you don’t care about order of elements, and just need to know whether all the elements in one array appear as a subset of the other array, use the containment operators (@> , <@)

## Range Types
* Range data types represent data with a beginning and an end.
* many operators and functions to identify overlapping ranges,
	* check to see whether a value falls inside the range, and
	* combine adjacent smaller ranges into larger ranges.
* For number-like ranges, if either the start point or the end point is left blank, PostgreSQL replaces it with a null.
	* For practicality, you can interpret the null to represent either -infinity on the left or infinity on the right.
	* In actuality, you’re bound by the smallest and largest values for the particular data type

### Discrete Versus Continuous Ranges
* A range of integers or dates is discrete because you can enumerate each value within the range.
	*  PostgreSQL canonicalizes all discrete ranges, for both storage and display.
* A range of numerics or timestamps is continuous, because an infinite number of values lies between the end points.

### Built-in Range Types
* PostgreSQL comes with six built-in range types for numbers and datetimes
* int4range, int8range
	* A range of integers. Integer ranges are discrete and subject to canonicalization.
* numrange
	* A continuous range of decimals, floating-point numbers, or double-precision numbers.
* daterange
	* A discrete date range of calendar dates without time zone awareness.
* tsrange, tstzrange
	* A continuous date and time (timestamp) range allowing for fractional seconds. tstrange is not time zone−aware; tstzrange is time zone−aware.

### Defining Ranges
* In addition to the built-in range types, you can create your own range types.
	* When you do, you can set the range to be either discrete or continuous.
* A range, regardless of type, is always comprised of two elements of the same type with the bounding condition denoted by brackets or parentheses
