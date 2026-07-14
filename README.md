# Bank App

A RESTful API for banking operations, built with Spring Boot. Supports customer, account, and user management with JWT-based authentication.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.15, Spring Data JPA, Spring Security
- **Database:** MySQL 8.0
- **Auth:** JWT (Nimbus JOSE JWT), BCrypt password hashing
- **Mapping:** MapStruct
- **Boilerplate reduction:** Lombok
- **Build tool:** Maven (with Maven Wrapper)

## Architecture

Layered architecture: `Controller → Service (interface) → ServiceImpl → Repository → Entity → DB`

```
com.example.demo
├── controller          # REST endpoints (@RestController)
├── service              # Business logic interfaces
├── service.impl         # Service implementations
├── repository           # Spring Data JPA interfaces
├── entity                # JPA entities (Account, Customer, User, CustomerType)
├── dto
│   ├── request           # Incoming request DTOs
│   └── response          # Outgoing response DTOs (wrapped in ApiResponse)
├── mapper                # MapStruct interfaces (impls generated at compile time)
├── exception             # AppException, ErrorCode enum, GlobalExceptionHandler
├── specification         # JPA Specifications for dynamic queries
└── config                # Configuration classes
```

All API responses are wrapped in a standard `ApiResponse<T>` envelope:

```json
{ "code": 1000, "message": "Success", "result": { ... } }
```

## Features

- User registration and JWT-based authentication (token issuance and introspection)
- Customer management (create, read, update, delete) with support for `PERSONAL` and `BUSINESS` customer types
- Account management (create, read, update, delete) linked to customers, with balance tracking
- Centralized exception handling with structured error codes
- Dynamic query filtering via JPA Specifications
- DTO-based request/response mapping with MapStruct

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/auth/token` | Authenticate user and issue a JWT |
| POST | `/auth/introspect` | Validate a JWT token |
| POST | `/users` | Register a new user |
| GET | `/customers` | List/search customers |
| POST | `/customers` | Create a new customer |
| PUT | `/customers` | Update a customer |
| DELETE | `/customers` | Delete a customer |
| GET | `/accounts` | List/search accounts |
| POST | `/accounts` | Create a new account |
| PUT | `/accounts` | Update an account |
| DELETE | `/accounts` | Delete an account |

## Data Model

| Entity | Table | Key Fields |
|--------|-------|------------|
| `User` | `users` | id, username (unique), password (BCrypt), name, role |
| `Customer` | `customers` | id, name, birthday, address, identityNo (unique), mobile, customerType, status |
| `Account` | `accounts` | id, accountNumber (unique), customer_id (FK), balance, status |
| `CustomerType` | enum | `PERSONAL`, `BUSINESS` |

Schema is managed automatically via `spring.jpa.hibernate.ddl-auto=update` (development setting — a proper migration tool is recommended for production).

## Getting Started

### Prerequisites

- Java 21 (Temurin/OpenJDK)
- MySQL 8.0 running on `localhost:3306`
- A database named `Bank_app`

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/tinhonne/Fresher-Be-Java.git
   cd Fresher-Be-Java
   ```

2. Create the database:
   ```sql
   CREATE DATABASE Bank_app;
   ```

3. Configure database credentials in `src/main/resources/application.properties` (defaults to user `root` / password `root`).

4. Build and run tests:
   ```bash
   ./mvnw clean verify
   ```

5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The API will be available at `http://localhost:8080`.

### Useful Commands

```bash
# Build only, skip tests
./mvnw clean compile -DskipTests

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=DemoApplicationTests

# Package as JAR
./mvnw clean package -DskipTests

# Regenerate MapStruct mapper implementations
./mvnw clean compile
```

## Testing

Currently limited to a basic Spring context load test (`DemoApplicationTests`). Integration tests and Testcontainers are not yet set up.

```bash
./mvnw test
```

## Known Limitations / Roadmap

- No Spring Security filter chain configured yet — JWT validation currently happens inside the service layer rather than at the request-filter level; endpoints are not yet protected by role-based access control
- JWT signing key is currently hardcoded and should be externalized (e.g. environment variables) before any production use
- New user passwords are not yet BCrypt-encoded on creation — planned fix
- Database schema uses auto-update (`ddl-auto=update`); a migration tool (e.g. Flyway/Liquibase) is planned
- No pagination on list endpoints yet
- Broader integration test coverage planned

## License

This project is for educational/portfolio purposes.
