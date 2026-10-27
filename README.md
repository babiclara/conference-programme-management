# Conference Programme Management

Student project **P01** — *Rapid Development of Java Applications Using Programming Frameworks*,
Algebra Bernays University, 2026/27. Author: Lara Babić.

Conference organisers publish a call for papers, speakers submit sessions, and the programme
committee assembles those sessions into a schedule of rooms and time slots.

## Status

Milestone M1 — project scaffold only. There is no application logic yet, and the application
does not start until the database is in place.

## Technology

Java 25, Spring Boot 4.1.2, Spring MVC with Thymeleaf, Spring Data JPA over PostgreSQL,
Flyway for the schema, Maven.

## Building

Requires JDK 25.

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 25)
./mvnw clean package
```
