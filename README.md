# Product Management System

A simple web-based product management system built with Spring Boot 3, supporting full CRUD operations, user authentication using JWT, and a minimal UI using Thymeleaf.

---

## Features

- **RESTful API**: Full Create, Read, Update, Delete operations for managing products.
- **Authentication & Authorization**: Secure login with JWT (JSON Web Tokens).
- **Data Validation**: Robust field-level validation using JSR-380 (`jakarta.validation`).
- **Thymeleaf UI**: Simple and responsive user interface for managing products.
- **Exception Handling**: Centralized error handling with custom error pages.
- **Profile-based Configuration**:
  - `dev`: Uses H2 in-memory database.
  - `test`: Uses PostgreSQL database.

---

## Tech Stack

| Component      | Version           |
| -------------- | ----------------- |
| Java           | 17                |
| Spring Boot    | 3.4.7             |
| Spring WebFlux | 3.1.x             |
| Thymeleaf      | 3.x               |
| PostgreSQL     | 15.x              |
| H2 Database    | 2.x (in-memory)   |
| JWT            | Nimbus JOSE + JWT |
| Maven          | 3.8+              |

---

## Build & Run

### Run with H2 (development mode)

```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev