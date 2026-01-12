# Security Webapp

A full-stack Spring Boot application demonstrating secure user management, personalized notes, and a responsive UI built with Thymeleaf and TailwindCSS.

## Features

- **User Authentication**: Secure Login and Registration pages with validation.
- **Note Management**: Create, view, and delete personal notes.
- **Security**: 
    - Password hashing using BCrypt.
    - Role-based access control.
    - CSRF protection.
- **UI**: Modern, responsive interface using Thymeleaf and TailwindCSS.
- **Database**: SQLite (local file) with Flyway for schema migrations.
- **Architecture**: Layered architecture (Controller, Service, Repository) with DTOs.

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

```
src/main/java/com/ramazanbatbay/security_webapp/
├── config/         # Security Configuration (SecurityConfig.java)
├── controller/     # REST Controllers for API endpoints
├── pages/          # UI Controllers for Thymeleaf pages
├── model/          # JPA Entities and DTOs
├── repository/     # Data Access Layer
├── service/        # Business Logic
├── validation/     # Custom Validation Logic
└── exception/      # Global Exception Handling

src/main/resources/
├── templates/      # Thymeleaf HTML Templates
├── static/         # Static assets (CSS, JS)
└── db/migration/   # Flyway SQL Migrations
```
