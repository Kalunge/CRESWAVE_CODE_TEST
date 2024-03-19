# Blog API
The Blog API is a Spring Boot application that provides RESTful endpoints for managing blog posts and user authentication.

## Features
* **User Authentication:** Users can register, login, and manage their profiles.
* **Blog Post Management:** Authenticated users can create, read, update, and delete blog posts.
* **Role-Based Access Control (RBAC):** Administrators have additional privileges, such as deleting posts.

## Technologies Used
- Spring Boot
- Spring Security
- JWT Authentication
- Docker
- MySQL

## Prerequisites
Before running the application, make sure you have the following software installed on your machine:
* [Java JDK](https://www.oracle.com/ke/java/technologies/downloads/)
* [Maven](https://maven.apache.org/install.html)
* [Docker](https://www.docker.com/)

## Setup instructions
1. Clone the repository: 
```git clone git@github.com:Kalunge/CRESWAVE_CODE_TEST.git```
2. Navigate to the project repository: 
```cd CRESWAVE_CODE_TEST```
3. Install dependencies
`mvn install`
4. Get MySQL DB up and running: 
```docker-compose up -d```
5. Run the application: 
``` mvn spring-boot:run``` or run  the `BlogApiApplication` class

## Documentation
[Link to Swagger Documentation](http://localhost:8080/swagger-ui/index.html): Link to the Swagger documentation for API reference.

## Design Document
Design Document: Link to the design document for technical decisions explainer, and further improvements proposals.


