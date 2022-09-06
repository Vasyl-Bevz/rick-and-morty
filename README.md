# Rick And Morty

### Description

This is a test project with two endpoints:

1. The request randomly generates a wiki about one character in the universe the animated series Rick & Morty.:
   > http://localhost:6868/movie-character/random

2. The request takes a string as an argument, and returns a list of all characters whose name contains the search string
   in the American adult animated science-fiction sitcom Rick & Morty:

   > http://localhost:6868/movie-character/by-name?name=XXX

### Project 3-tier architecture
1. Controllers - Presentation layer
2. Services - Application layer
3. DAO - Data access object layer

### Technologies used in project
- Spring Boot, Spring Web, Spring Jpa
- PostgreSQL
- Java v.18
- Apache Maven
- Docker, Docker compose
- Junit
- Swagger

### Additional info
- Synchronization local and external data, once per day.
- All requests get data from local database.
- You can use swagger documentation page via url:
  > http://localhost:6868/swagger-ui/#/

### For launch project
1. Install Docker Desktop

2. Download project to you PC

3. Build package: run next command in terminal from `rickandmorty` directory:

   > mvn clean package

4. Run next command in terminal from the main directory:

   > docker-compose up --build

5. Go to http://localhost:6868/movie-characters/random (get random character)

6. Go to http://localhost:6868/movie-characters/by-name?name=XXX (get character where name contains XXX)
