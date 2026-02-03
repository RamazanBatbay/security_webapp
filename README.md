# Security Webapp

A full-stack Spring Boot application demonstrating secure user management, personalized notes, and a responsive UI built with Thymeleaf and TailwindCSS.

## Features

- **User Authentication**: Secure Login and Registration pages with validation.
- **Note Management**: Create, view, and delete personal notes.
- **Security**: 
    - Password hashing using BCrypt.
    - Role-based access control.
    - CSRF protection.
    - Rate Limiting (Brute-force protection).
- **UI**: Modern, responsive interface using Thymeleaf and TailwindCSS.
- **Database**: SQLite (local file) with Flyway for schema migrations.
- **Architecture**: Layered architecture (Controller, Service, Repository) with DTOs.
- **CI/CD**: GitHub Actions for automated testing and code coverage (JaCoCo).

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

### Running Tests

To run tests with JaCoCo code coverage:

**Windows:**
```powershell
.\gradlew test jacocoTestReport
```

**Mac/Linux:**
```bash
./gradlew test jacocoTestReport
```

Coverage reports will be generated in `build/reports/jacoco/test/html/index.html`.

The application will start on `http://localhost:8080`.

## Accessing the Application

### Web Interface (Browser)
- **Home**: `http://localhost:8080/` (Redirects to `/notes` or `/login`)
- **Login**: `http://localhost:8080/login`
- **Register**: `http://localhost:8080/register`
- **My Notes**: `http://localhost:8080/notes` (Protected, requires login)

### API Endpoints (For Clients)
The application also exposes REST endpoints for programmatic access:

- **Auth**:
    - `POST /api/register` - Create a new user (JSON body).
    - `POST /api/login` - Authenticate and receive a session/token.
- **Public**:
    - `GET /hello` - Health check.

## Project Structure

src/main/java/com/ramazanbatbay/security_webapp/
├── config/         # Security Configuration (SecurityConfig.java)
├── controller/     # REST Controllers for API endpoints
├── exception/      # Global Exception Handling
├── model/          # JPA Entities and DTOs
├── pages/          # UI Controllers for Thymeleaf pages
├── repository/     # Data Access Layer
├── security/       # Security Filters and Logic
├── service/        # Business Logic
└── validation/     # Custom Validation Logic


src/main/resources/
├── db/migration/   # Flyway SQL Migrations
├── static/         # Static assets (CSS, JS)
└── templates/      # Thymeleaf HTML Templates
```
