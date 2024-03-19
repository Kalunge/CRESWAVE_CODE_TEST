# Blog API
The Blog API is a Spring Boot application that provides RESTful endpoints for managing blog posts and user authentication.

## Features
* **User Authentication:** Users can register, login, and manage their profiles.
* **Blog Post Management:** Authenticated users can create, read, update, and delete blog posts.
* **Role-Based Access Control (RBAC):** Administrators have additional privileges, such as deleting posts.

## Data Model
```mermaid
erDiagram
    USERS {
        id INT
        username VARCHAR
        email VARCHAR
        password VARCHAR
        role ENUM
    }

    POSTS {
        id INT 
        userId INT 
        title VARCHAR
        content TEXT
    }

    COMMENTS {
        id INT 
        postId INT 
        userId INT 
        content TEXT
    }

    USERS ||--o{ POSTS : "Creates"
    USERS ||--o{ COMMENTS : "Posts"
    COMMENTS ||--o{ POSTS : "Belongs to"

```

## User Flow Diagram
```mermaid
graph TD;
    A[Register] -->|Success| B[Login];
    B -->|Success| C[Create Post];
    C -->|Success| D[Get All Posts];
    D -->|Success| E[Get Post by ID];
    E -->|Success| F[Update Post];
    F -->|Success| G[Delete Post];
    B -->|Success| H[Get User Profile];
    H -->|Success| I[Update User Profile];
    H -->|Success| J[Delete User Profile];
    C -->|Failure| K[Error];
    D -->|Failure| K[Error];
    E -->|Failure| K[Error];
    F -->|Failure| K[Error];
    G -->|Failure| K[Error];
    H -->|Failure| K[Error];
    I -->|Failure| K[Error];
    J -->|Failure| K[Error];
    K -->|Retry| B;

```
### Auth Endpoints:

#### Register User
- **Method:** POST
- **Endpoint:** /api/v1/auth/signup
- **Description:** Register a new user.
- **Request Body:**
  ```json
  {
      "username": "exampleUser",
      "email": "user@example.com",
      "password": "examplePassword",
      "role": "user"
  }

#### Login User
- **Method:** POST
- **Endpoint:** /api/v1/auth/signin
- **Description:** Login a  user.
- **Request Body:**
  ```json
  {
      "username": "exampleUser",
      "password": "examplePassword"
  }
- **Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}

```
- other endpoints can be found in the linked Swagger documentation page. 

## Technologies Used
- Spring Boot
- Spring Security
- JWT Authentication
- Docker
- MySQL
- Git

## Prerequisites
Before running the application, make sure you have the following software installed on your machine:
* [Java JDK](https://www.oracle.com/ke/java/technologies/downloads/)
* [Maven](https://maven.apache.org/install.html)
* [Docker](https://www.docker.com/)
* [Git](https://git-scm.com/)

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
[Design Document:](https://docs.google.com/document/d/1pEGVYG3KxskXQN3nRbLbn0y-HHZ75ZQx3lKTHuHbASY/edit?usp=sharing) Link to the design document for technical decisions explainer, and further improvements proposals.



