This project for AC32007 Assignment 2 aims to produce a scenario in which full text-search with Solr can be effectively demonstrated.

The project includes a website crawling bot which visits a page, collects the links from that page and saves the list of words found on that page. We can then perform a full-text search of this information to produce a fairly effective internet search engine. As the project aims to demonstrate the capabilities of DSE, extensive use of Cassandra and Solr has been made.

An example of the project running can be found at http://51.254.181.89:8080/
As of writing, the example on this server has crawled 66,234 pages and found 707,948 unvisited URLs. This accounts for approximately 3GB of the data stored in DSE Cassandra.
