# Car Renting Backend

This repository includes the backend project for the Car Renting System. 
Communicates with the frontend project of the system. 
[Car Renting Frontend](https://github.com/altayelmas/carrenting_frontend)

## Technologies
- Java 17
- Spring Boot 3.2.0

## Requirements
- Java 17 or higher
- Apache Maven 3.9.1
- MySQL Database

## Installation
1. Get the project.
2. Change "spring.datasource.url" in application properties to your database address.
3. Change "spring.jpa.hibernate.ddl-auto" in application.properties from update to create if this is the first time you are using the project.
4. After database creates the tables you can change "spring.jpa.hibernate.ddl-auto" to update. Otherwise, database tables will be newly created every time the project runs.
5. Run "mvn clean install" to build the project.
6. Run project with "mvn spring-boot:run".

## How to Build & Run

In the project directory, you can run:

### Building: `mvn clean install`
### Running: `mvn spring-boot:run`
### Running Port: `localhost:8080`
