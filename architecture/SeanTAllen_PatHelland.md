# [SeanTAllen/pat-helland-and-me: Materials related to my talk "Pat Helland and Me"](https://github.com/SeanTAllen/pat-helland-and-me)
* To scale infinitely we can't have co-ordination
* Distributed transactions are a form of co-ordination
* To scale infinitely we can't use transactions
* Two layer architecture
	* scale-agnostic layer
		* business logic layer
	* scale-aware layer
	* API
		* that allows to write to scale-agnostic layer
		* that runs on scale-aware layer
* Entities
	* Objects with unique keys
	* to which access is serialized
