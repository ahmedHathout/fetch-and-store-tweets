# Mini-Analytics
A Spring Boot application that is a very simplified version of "Brandwatch Analytics" (just a smiplified backend). It keeps getting the tweets that match the given queries and stores them in the database so that they can be processed later.

### How it works
1. The user creates a new query with some search string. This query is saved in a mongo database. This is done through post requests which are handled by the `api` module.
2. The `crawler` module gets all the queries saved in the database every fixed amount of time and uses the twitter api to get 50 tweets for each query.
3. The 'crawler' then sends the tweets it got to the `storage module' using `kafka'.
4. The 'storage' gets the tweets and saves them in a solr database.

### Maven, Docker, Kafka
`Maven` is the dependency management tool used in this project. `Docker` and `docker-compose` files were used to create and run the containers running the 4 modules of the application. `Kafka` was used to communicate between the two modules `crawler` and `storage`
