# [Rethinking REST: A hands-on guide to GraphQL and queryable APIs | Live Training](https://www.safaribooksonline.com/live-training/courses/rethinking-rest-a-hands-on-guide-to-graphql-and-queryable-apis/0636920174301/)

## Resources
* [Introduction to GraphQL | GraphQL](https://graphql.org/learn/)
	* [GraphQL spec](http://facebook.github.io/graphql/October2016/)
	* [Zero to GraphQL in 30 Minutes – Steven Luscher - YouTube](https://www.youtube.com/watch?v=UBGzsb2UkeY&feature=youtu.be)
	* [GraphQL: A data query language | GraphQL](https://graphql.org/blog/graphql-a-query-language/)
	* [GraphQL Clients | GraphQL.js Tutorial](https://graphql.org/graphql-js/graphql-clients/)
* [chentsulin/awesome-graphql: Awesome list of GraphQL & Relay](https://github.com/chentsulin/awesome-graphql)
* [Versioning an API in GraphQL vs. REST](https://symfony.fi/entry/versioning-an-api-in-graphql-vs-rest)
* [Security and GraphQL Tutorial](https://www.howtographql.com/advanced/4-security/)
* [Business logic layer | GraphQL](https://graphql.org/learn/thinking-in-graphs/#business-logic-layer)
* [GraphQL resource limitations | GitHub Developer Guide](https://developer.github.com/v4/guides/resource-limitations/)
* [...And GraphQL for all? A few things to think about before blindly dumping REST for GraphQL | API Handyman](https://apihandyman.io/and-graphql-for-all-a-few-things-to-think-about-before-blindly-dumping-rest-for-graphql/)

### dev tools
* [Altair](https://altair-gql.sirmuel.design/)
* [GraphQL developer tools - Chrome Web Store](https://chrome.google.com/webstore/detail/graphql-developer-tools/hflnkihcpgldmkepajmpooacmmhglpff?hl=en-US)
* [GraphQL Network - Chrome Web Store](https://chrome.google.com/webstore/detail/graphql-network/igbmhmnkobkjalekgiehijefpkdemocm?hl=en-US)

### Go libs
* [Go Libraries](https://github.com/chentsulin/awesome-graphql#go-libraries)
* [rs/rest-layer: REST Layer, Go (golang) REST API framework](https://github.com/rs/rest-layer)

## REST
### Cons
* Overfetching of resources
	* static nature of the requests
	* inability by clients to fetch exactly only what they want
		* and having to stick to what is offered by the existing apis
		* regardless of if they need that part of the info or not
	* wasted bandwidth
* under-fetching of resources
	* limited to dealing with one resource per call in general
	* multiple calls to accomplish a workflow
		* get primary key
		* get resource with primary key
		* get related resources ..
* Not great for creating some views e.g. dashboards, summary views etc
	* unless there are specific apis to service the views
* too much nested code at times
	* mirroring the json response structure
* complicated updates
	* in auto-save workflows
	* getting current state of objects after

### not so good uses of rest
* same end-points for web and mobile apps
* rich dashboard, summary views
* unpredicatble api usage
* keeping track of state after updates

## Graphql
* [Introduction to GraphQL | GraphQL](https://graphql.org/learn/)
* [GraphQL spec](http://facebook.github.io/graphql/October2016/)
* created by facebook
	* when their APIs for their mobile usage were inefficient
* popularized by github implementing their v4 api
	* [GitHub GraphQL API v4 | GitHub Developer Guide](https://developer.github.com/v4/)
	* [GraphQL API Explorer | GitHub Developer Guide](https://developer.github.com/v4/explorer/)
* uses HTTP methods: GET, POST (preferred)
* one url for the whole API
* only uses error code 200
	* errors listed in reponse body
* versioning is not required
	* [Versioning an API in GraphQL vs. REST](https://symfony.fi/entry/versioning-an-api-in-graphql-vs-rest)
* supports
	* queries
	* mutation - updates to data
	* directives
		* modify query to skip/include fields
	* subscriptions
		* server pushes data to client

### language features
* fragments
	* clients can create their own query fragments
	* composed
#### non-spec api features
* pagination
* filters
* ordering

### Concepts
* Schema
* Types
	* Object
	* Scalar
		* int, string, id ..
	* List
* Fields
* Resolvers
