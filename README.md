# Kalah Game Backend

The repository includes the backend project of my Kalah Game Implementation. Communicates with the frontend project of Kalah Game Implementation, https://github.com/caoguzelmas/kalah-game-frontend. 

Available on, https://hub.docker.com/r/caoguzelmas/kalah-game-backend

Also live on, https://kalah-game-backend-coe.herokuapp.com/

## About the Kalah Game

The game is developed based on https://en.wikipedia.org/wiki/Kalah and implements the standard gameplay and it’s variations.

- Player can create a game that implements the combinations of these variations. 
- Player can create multiple games one after another.
- Player can stop the game and reach out the other pages.
- Player can resume the unfinished game that listed on “All Games” section. 
- All the game records are stored on MongoDb Atlas.

## Technologies

- Java 1.8
- Spring Boot 2.7.0
- MongoDb
- Docker

## How to Build & Run

- Building:  mvn clean install
- Running:  mvn spring-boot:run
- Running Port: localhost:8080/




