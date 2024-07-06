# Spring Boot Application and Tests

This guide provides instructions on how to set up and run a Spring Boot application, execute tests, and additional notes.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java Development Kit (JDK) 21 or higher
- Maven Wrapper (`mvnw`) included in the project

## Running the Spring Boot Application

To run the Spring Boot application locally:

1. Open a terminal or command prompt.
2. Navigate to the root directory of your Spring Boot project.
3. Run the following command:

   ```bash
   ./mvnw spring-boot:run


## Running Unit and Integration Tests

To execute both Unit and Integration tests:

   ```bash
   ./mvnw clean verify
```

The clean phase clears the target directory.
The verify phase compiles the source code and runs all tests.
