# ⬢⬡ Hexagonal Architecture ⬡⬢


This project consists of a microservice with basic CRUD operations that demonstrates the hexagonal architecture structure using Domain Driven Design software design patterns.

## Technologies
* **Language:** Java 17
* **Framework:** Spring Boot 3+
* **Persistence:** JPA/Hibernate with MySQL
* **Architecture:** Hexagonal (Ports & Adapters)
* **Service Exposure:** RESTFUL APIs
* **Testing:** JUnit5 & Mockito
* **Password Encryption:** Using BCryptPasswordEncoder for Secure Storage.

## Project Architecture
The system is designed following the principles of hexagonal architecture to decouple business logic from frameworks and infrastructure.

### Key components
+ **Domain:** Contains pure business logic (domain entities, rules, and services).
+ **Application:** Defines use cases and exposes ports for interacting with the infrastructure layer.
+ **Infrastructure:** Implements adapters for the database and APIs.

## Endpoints
+ `POST /users` - Create a user.
+ `GET /users/{userId}` - Get user by ID.
+ `GET /users/email/{email}` - Get user by email.
+ `GET /users` - Get all users.
+ `PUT /users/{userId}` - Update user.
+ `PUT /users/{userId}/password` - Update user password.
+ `DELETE /users/{userId}` - Delete a user.

## Run
You can run this application of 2 ways:
### General prerequisites
+ JDK 17
+ Maven

### 1- Without Docker
#### Prerequisites
+ MySQL Workbench installed on your system (for MySQL)

### Steps
+ #### Clone the Repository
    ```
    https://github.com/john-almaraz/arquitectura-hexagonal
    ```
+ #### Change property from application.yml
  url: jdbc:mysql://`localhost`:3306/user_db?createDatabaseIfNotExist=true
+ #### Run App
  You can import the collection of methods `docs/postman_collection.json` into your Postman and start testing the methods.
  
### 2- With Docker
#### Prerequisites
+ Docker installed on your system (for MySQL and user-service)
+ Docker Compose installed (often included with Docker Desktop)

### Steps
+ #### Clone the Repository
    ```
    https://github.com/john-almaraz/arquitectura-hexagonal
    ```
+ ### App and Database Configuration
  To setup app and MySQL with Docker services using the provided file: `docker-compose.yml`
    ```
    docker-compose up -d
    ```
+ ### Verify App and Data Base is Running
    + Ensure the App and MySQL is running by checking the Docker containers:
   ```
    docker ps
   ```
    + You should see containers named user-service and mysql-user.
+ ### Test the methods 
  You can import the collection of methods `docs/postman_collection.json` into your Postman and start testing the methods. 
