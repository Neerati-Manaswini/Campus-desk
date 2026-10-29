# Student Management System

A full stack CRUD app: Spring Boot, Spring Data JPA, Thymeleaf, and an in-memory H2 database, so there is no database to install.

## Features
- Add, edit, delete students
- Search by name or course, 5 per page with pagination
- Server-side validation (required fields, email format, year 1-4) and duplicate-email check
- Sample data loaded on startup

## Run it
Requirements: JDK 17 or newer and Maven (or open the folder in IntelliJ IDEA / Eclipse / VS Code and run `StudentApplication`).

```bash
cd 2-student-management-system
mvn spring-boot:run
```
Open http://localhost:8080. The H2 console is at http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:studentsdb`, user `sa`, empty password).

## Project layout
```
StudentController  -> HTTP + validation flow
StudentRepository  -> Spring Data JPA queries
Student            -> entity + validation rules
templates/         -> students.html, form.html (Thymeleaf)
```

## Switching to MySQL
See the comment at the bottom of `application.properties`.

## Ideas to extend
- Add Spring Security with an admin login
- Add a REST API (`@RestController`) beside the web pages
- Write `@DataJpaTest` and `MockMvc` tests
- Add a Dockerfile and deploy to Render
