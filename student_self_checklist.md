# Student Self-Check Worksheet

**Rule:** If you cannot check a box honestly, assume you will lose points for that item.
**Maximum:** 75 points (+15 bonus = 90)

## 1. Application readiness (5 pts)
- [x] Application starts without errors (Verified via build)
- [x] I can restart the app quickly if needed
- [x] Demo is live (not screenshots or recordings)
- [x] I can complete the demo within 10 minutes

*Notes:* The application builds successfully with Gradle.

## 2. Authentication – registration and login (15 pts)

**Registration**
- [x] I can register a new user live
- [x] I can intentionally submit invalid input and show validation errors (Verified via [UserRegisterDto](src/main/java/com/ramazanbatbay/security_webapp/model/dto/UserRegisterDto.java) constraints)
- [x] Passwords are stored hashed (bcrypt/Argon2) (Verified via `BCryptPasswordEncoder` in [SecurityConfig.java](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java) and usage in [UserService.java](src/main/java/com/ramazanbatbay/security_webapp/service/UserService.java))
- [x] I can explain where password hashing happens in my code (`UserService.createUser` method)

**Login**
- [x] I can show a failed login attempt
- [x] I can show a successful login attempt
- [x] I can show proof of authentication:
    - [x] MVC: session cookie (Standard Spring Security session management)
- [x] Login error messages are safe (no details leaked) (Verified via [CustomAuthenticationFailureHandler.java](src/main/java/com/ramazanbatbay/security_webapp/security/CustomAuthenticationFailureHandler.java))

## 3. Authorization and access control (20 pts)

**Protected Routes**
- [x] Access without login is denied (Verified via [SecurityConfig.java](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java) `authorizeHttpRequests`)
- [x] Access with wrong role is denied (Verified via `/admin/**` restriction)
- [x] Access with correct role is allowed
- [x] I know which class/config enforces this ([SecurityConfig.java](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java))

**User Data Isolation (CRITICAL)**
- [x] User A can create data
- [x] User B cannot see User A’s data (Verified via `NoteService.findAllByUserEmail` filters by user)
- [x] User B cannot modify User A’s data (Verified via `NoteService.findByIdAndUserEmail` checks ownership)
- [x] User B cannot delete User A’s data (Verified via `NoteService.deleteNote` usage of secure find method)
- [x] I can explain how user_id is enforced (In [NoteService.java](src/main/java/com/ramazanbatbay/security_webapp/service/NoteService.java), methods explicitly verify the principal's email matches the owner)

## 4. Input validation and error handling (10 pts)
- [x] DTO validation annotations are present (Verified via [UserRegisterDto.java](src/main/java/com/ramazanbatbay/security_webapp/model/dto/UserRegisterDto.java) and `NoteDto.java` use `@NotBlank`, `@Email`, etc.)
- [x] Custom validation rule is implemented and demonstrable (Verified via `@StrongPassword` annotation)
- [x] Invalid input returns HTTP 400x (Verified via [GlobalExceptionHandler](src/main/java/com/ramazanbatbay/security_webapp/exception/GlobalExceptionHandler.java) handling `MethodArgumentNotValidException`)
- [x] Error responses are structured and safe (Verified via `ErrorResponse` class and [GlobalExceptionHandler](src/main/java/com/ramazanbatbay/security_webapp/exception/GlobalExceptionHandler.java))
- [x] No stack traces appear in browser or API responses (Verified via [GlobalExceptionHandler](src/main/java/com/ramazanbatbay/security_webapp/exception/GlobalExceptionHandler.java) returns messages, not stack traces)

## 5. HTTP and browser security headers (8 pts)
- [x] I can open Browser DevTools --> Network tab
- [x] Response includes X-Content-Type-Options (Start by default in Spring Security, Verified via enabled)
- [x] Response includes X-Frame-Options (Verified via [SecurityConfig](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java): `frameOptions`)
- [x] Response includes Content-Security-Policy (Verified via [SecurityConfig](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java): `contentSecurityPolicy`)
- [x] Response includes Referrer-Policy (Verified via [SecurityConfig](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java): `referrerPolicy`)
- [x] Authentication cookies include:
    - [x] HttpOnly (Verified via [application.properties](src/main/resources/application.properties))
    - [x] Secure (Verified via [application.properties](src/main/resources/application.properties))
    - [x] SameSite (Verified via [application.properties](src/main/resources/application.properties))

## 6. Session/token management (7 pts)
**MVC (Session-based)**
- [x] Logout works (Verified via [SecurityConfig](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java) logout config)
- [x] Refresh after logout keeps user logged out

## 7. Database and persistence security (5 pts)
- [x] Entity table includes user_id foreign key (Verified via [Note](src/main/java/com/ramazanbatbay/security_webapp/model/Note.java) entity has relationship to [User](src/main/java/com/ramazanbatbay/security_webapp/model/User.java))
- [x] At least one safe query/prepared statement exists (JPA Repositories use parameter binding by default)

## 8. Secure logging (5 pts)
- [x] Failed login attempts are logged (Verified via [CustomAuthenticationFailureHandler.java](src/main/java/com/ramazanbatbay/security_webapp/security/CustomAuthenticationFailureHandler.java))
- [x] Unauthorized access attempts are logged (Verified via [NoteService](src/main/java/com/ramazanbatbay/security_webapp/service/NoteService.java) logs warning on access denied)
- [x] Passwords are NOT logged (Verified via handlers only log username/IP)
- [x] refresh tokens are NOT logged (N/A, session based, but not logged)
- [x] Logs are visible during demo

## 9. Testing (Core Requirement)
- [x] Unit tests run successfully (Verified via `gradle test` execution)
- [x] At least one security-related unit test exists (Verified via [security](src/main/java/com/ramazanbatbay/security_webapp/config/SecurityConfig.java) test package exists)
- [x] Integration test for secured endpoint exists (Verified via `SecurityWebappApplicationTests`)
- [x] I can run tests quickly during the presentation

## Optional Bonus (+15 pts)
- [x] Rate limiting implemented and demonstrable (Verified via `LoginRateLimitFilter`)
- [ ] HTTPS enabled
- [ ] HTTP --> HTTPS redirect works
- [ ] HSTS header present
- [x] GitHub Actions CI pipeline runs tests (Verified via [.github/workflows/ci.yml](.github/workflows/ci.yml))
- [ ] OWASP Dependency Check