# Employee Management Microservice

A production-ready **Spring Boot microservice** for managing employees and departments, with **Docker** support, health monitoring via **Actuator**, and full **Swagger** documentation.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Database | H2 (in-memory) |
| ORM | Spring Data JPA / Hibernate |
| Testing | JUnit 5 + Mockito |
| Containerization | Docker + Docker Compose |
| Monitoring | Spring Boot Actuator |
| API Docs | Swagger / OpenAPI 3 |
| Build | Maven |

## Features

- Full **CRUD** for Employees and Departments
- **One-to-Many** relationship: Department → Employees
- Employee **search by name** (case-insensitive)
- Filter employees by **department**
- Email **uniqueness validation**
- **Health check** endpoint via Spring Actuator
- **Dockerized** with multi-stage build for minimal image size
- Pre-loaded sample data on startup

## Getting Started

### Option 1: Run with Maven (no Docker needed)

```bash
git clone https://github.com/YOUR_USERNAME/employee-management-microservice.git
cd employee-management-microservice
mvn spring-boot:run
```

App runs at `http://localhost:8081`

### Option 2: Run with Docker

```bash
# Build and run
docker-compose up --build

# Stop
docker-compose down
```

### Useful URLs

| URL | Description |
|-----|-------------|
| `http://localhost:8081/swagger-ui.html` | API documentation |
| `http://localhost:8081/actuator/health` | Health check |
| `http://localhost:8081/h2-console` | H2 database console |

## API Endpoints

### Employees

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get by ID |
| POST | `/api/employees` | Create employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/employees/department/{id}` | Get by department |
| GET | `/api/employees/search?query=alice` | Search by name |

## Sample Request

```bash
# Create an employee (assign to department with id=1)
curl -X POST http://localhost:8081/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe@company.com",
    "jobTitle": "Software Engineer",
    "salary": 130000,
    "department": { "id": 1 }
  }'
```

## Running Tests

```bash
mvn test
```

## Project Structure

```
src/
├── main/java/com/pavani/employeeservice/
│   ├── controller/     # REST controllers
│   ├── model/          # JPA entities (Employee, Department)
│   ├── repository/     # Spring Data JPA repositories
│   ├── service/        # Business logic
│   └── EmployeeServiceApplication.java
├── test/               # JUnit + Mockito unit tests
Dockerfile              # Multi-stage Docker build
docker-compose.yml      # Container orchestration
```

## Docker Details

The Dockerfile uses a **multi-stage build**:
1. **Build stage** — Maven compiles and packages the JAR
2. **Run stage** — Lightweight JRE Alpine image runs the JAR

This keeps the final image small (~200MB vs ~600MB for a single-stage build).
