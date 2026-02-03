# Security Webapp

A full-stack Spring Boot application demonstrating secure user management, personalized notes, and a responsive UI built with Thymeleaf and TailwindCSS.

# Student Self-check Worksheet Included[Student Checklist](./student_self_checklist.md)

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
   **Required Variables:**
   - `DB_FILE_NAME`: The name of the SQLite database file (e.g., `security_webapp.db`).
   - `DB_URL`: The JDBC URL (e.g., `jdbc:sqlite:security_webapp.db`).

   *Example `.env` content:*
   ```properties
   DB_URL=jdbc:sqlite:security_webapp.db
   DB_FILE_NAME=security_webapp.db
   ```

2. **Database**
   The application uses a local SQLite database.
   - **Location**: The database file (default: `security_webapp.db`) is stored in the project root.
   - **Initialization**: The file is automatically created and schema migrated (via Flyway) on the first run.
   - **Reset**: To reset the database, simply delete the `.db` file and restart the application.

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

### Test Results

**Overall Status**: 20/20 Tests Passed ✅

| Test Class | Test Name | Status |
|:---|:---|:---:|
| **SecurityWebappApplicationTests** | `contextLoads` | ✅ Passed |
| **RateLimitIntegrationTest** | `testRateLimit_Login_Exceeded` | ✅ Passed |
| **SecurityIntegrationTest** | `publicEndpoints_Reachability` | ✅ Passed |
| **SecurityIntegrationTest** | `securedEndpoints_Unauthenticated_RedirectsToLogin` | ✅ Passed |
| **SecurityIntegrationTest** | `adminEndpoints_UserRole_AccessDenied` | ✅ Passed |
| **SecurityIntegrationTest** | `adminEndpoints_AdminRole_AccessAllowed` | ✅ Passed |
| **SecurityIntegrationTest** | `login_Success` | ✅ Passed |
| **SecurityIntegrationTest** | `csrf_Protection_MissingToken_Forbidden` | ✅ Passed |
| **UserServiceTest** | `createUser_Success` | ✅ Passed |
| **UserServiceTest** | `createUser_EmailTaken` | ✅ Passed |
| **UserServiceTest** | `authenticate_Success` | ✅ Passed |
| **UserServiceTest** | `authenticate_Failure_UserNotFound` | ✅ Passed |
| **UserServiceTest** | `authenticate_Failure_WrongPassword` | ✅ Passed |
| **ValidationTest** | `isValid_ValidPassword_ReturnsTrue` | ✅ Passed |
| **ValidationTest** | `isValid_TooShort_ReturnsFalse` | ✅ Passed |
| **ValidationTest** | `isValid_NoUppercase_ReturnsFalse` | ✅ Passed |
| **ValidationTest** | `isValid_NoLowercase_ReturnsFalse` | ✅ Passed |
| **ValidationTest** | `isValid_NoDigit_ReturnsFalse` | ✅ Passed |
| **ValidationTest** | `isValid_NoSpecialChar_ReturnsFalse` | ✅ Passed |
| **ValidationTest** | `isValid_NullPassword_ReturnsFalse` | ✅ Passed |

> [!NOTE]
> Tests are automatically run on every push and pull request via GitHub Actions. You can see the current status at the top of this README.


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

```text
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
```


```text
src/main/resources/
├── db/migration/   # Flyway SQL Migrations
├── static/         # Static assets (CSS, JS)
└── templates/      # Thymeleaf HTML Templates
```
