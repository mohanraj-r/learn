# [PostgreSQL for Data Architects](https://www.safaribooksonline.com/library/view/postgresql-for-data/9781783288601/)


## [12. PostgreSQL – Extras](https://www.safaribooksonline.com/library/view/postgresql-for-data/9781783288601/ch12.html)
### JSON/JSONB
* The jsonb data type stores data in binary format.
* It also removes white spaces and
	* avoids duplicate object keys.
* jsonb has an overhead when data goes in,
	* while json has extra processing overhead when data is retrieved
* if we plan to use operators extensively and want indexing support, jsonb is a better choice
	* If the data will be stored as it is and retrieved without any operations, json should suffice
	* if we want to preserve whitespace, key ordering, and duplicate keys, json is the right choice.
* 
