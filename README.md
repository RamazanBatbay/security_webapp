# Security Webapp

A Spring Boot application demonstrating secure user management, SQLite integration with Flyway migrations, and a layered architecture.

## Features
- **User Management**: Register and Authenticate users.
- **Security**: Password hashing using BCrypt.
- **Database**: SQLite (local file) with Hibernate 7.
- **Migrations**: Database schema management with Flyway.
- **REST API**: Simple endpoints for testing.

## Prerequisites
- Java 21
- Gradle (Wrapper included)

## Setup and Configuration

1. **Environment Variables**
   The application requires a `.env` file for database configuration.
   Copy the example file:
   ```bash
   cp .env.example .env
   ```
   *Ensure `.env` contains: `DB_URL=jdbc:sqlite:security_webapp.db`*

2. **Database**
   The application uses a local SQLite database file `security_webapp.db`. It will be automatically created and migrated on the first run.

## Running the Application

Use the Gradle Wrapper to run the app:

**Windows (PowerShell/CMD):**
```powershell
.\gradlew bootRun
```

**Mac/Linux:**
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

## API Endpoints

### Public Endpoints
- **GET /hello**: Returns a greeting string.
- **GET /user**: Returns a sample User JSON object.

### User Management
- **POST /api/users/register**: Register a new user.
  ```bash
  curl -X POST http://localhost:8080/api/users/register \
       -H "Content-Type: application/json" \
       -d '{"username": "testuser", "email": "test@example.com", "password": "password123"}'
  ```

- **POST /api/users/login**: Access protected endpoints (or verify credentials).
  ```bash
  curl -X POST "http://localhost:8080/api/users/login?email=test@example.com&password=password123"
  ```

## Project Structure
```
src/main/java/com/ramazanbatbay/security_webapp/
├── config/         # Security Configuration
├── controller/     # REST Controllers
├── model/          # JPA Entities and DTOs
├── repository/     # Data Access Layer
└── service/        # Business Logic
```
