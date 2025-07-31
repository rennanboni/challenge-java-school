https://github.com/rennanboni/shalion.teamtailor-challenge2.git# Shalion Teamtailor Challenge

This is a simple REST API to manage schools and students, built with Java, Spring Boot, Gradle, and PostgreSQL.

## Tech Stack

- Java 17
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

## API Specification

The API provides the following endpoints:

### Schools

- `GET /schools`: List all schools with pagination.
  - `name` (optional): Filter by school name (case-insensitive).
- `GET /schools/{id}`: Get a school by its ID.
- `POST /schools`: Create a new school.
- `PUT /schools/{id}`: Update a school.
- `DELETE /schools/{id}`: Delete a school.

### Students

- `GET /students`: List all students for a given school with pagination.
  - `schoolId` (required): The ID of the school.
  - `name` (optional): Filter by student name (case-insensitive).
- `GET /students/{id}`: Get a student by their ID.
- `POST /students`: Create a new student.
- `PUT /students/{id}`: Update a student.
- `DELETE /students/{id}`: Delete a student.

## Getting Started

### Prerequisites

- Docker
- Docker Compose

### Running the Application

1. **Clone the repository:**

   ```bash
   git clone git@github.com:rennanboni/challenge-java-school.git
   cd challenge-java-school
   ```

2. **Start the PostgreSQL database:**

   ```bash
   docker-compose up -d
   ```

3. **Build and run the application:**

   You can run the application using the provided Gradle wrapper.

   On Windows:
   ```bash
   ./gradlew bootRun
   ```
   
   On macOS/Linux:
    ```bash
   ./gradlew bootRun
   ```

   Alternatively, you can build the application and run the JAR file:
    ```bash
   ./gradlew build
   java -jar build/libs/shalion.teamtailor-challenge2-0.0.1-SNAPSHOT.jar
   ```

4. **Access the application:**

   - The API will be available at `http://localhost:8080`.
   - The Swagger UI will be available at `http://localhost:8080/swagger-ui.html`.

### Running with Docker

You can also run the application using Docker.

1. **Build the Docker image:**

   ```bash
   docker build -t school-challenge .
   ```

2. **Run the Docker container:**

   ```bash
   docker run -p 8080:8080 --name school-db --link postgres_db:postgres -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres school-challenge
   ```

   This will start the application in a Docker container and connect it to the PostgreSQL container.
   
## Database Seeder

This project does not include a data seeder. Therefore, upon starting the application, the database will be empty. To begin, you should first create a school and then add students to it.
