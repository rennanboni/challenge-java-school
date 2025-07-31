## Tech Stack

- Java 22
- Spring Boot 3.1.0
- Gradle
- PostgreSQL
- Docker
- Swagger OpenAPI

## Features

- CRUD operations for schools and students.
- Search schools by name (case-insensitive).
- Search students by name within a specific school (case-insensitive).
- Pagination for all endpoints that return a list of resources.
- Error handling with meaningful HTTP status codes and messages.
- Swagger UI for API documentation and testing.

## Setup Environment

### Running the Application

1. **Setup environment**
  download code, import into eclipse and run
  
  ```bash
    ./gradlew build
    ./gradlew eclipse
  ```   

2. **application.proprerties**
  change database connection string in 
  
  ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=postgres
  ```

3. **Setup database**
  create schema `school` if required or according with `spring.jpa.properties.hibernate.default_schema` in `application.properties`

4. **Running the application**
  run application with `./gradlew bootRun` or by running `Application.java`
  
5. **Access the application:**

   - The API will be available at `http://localhost:8080`.
   - The Swagger UI will be available at `http://localhost:8080/swagger-ui.html`.
