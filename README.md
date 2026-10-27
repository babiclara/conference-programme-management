# Conference Programme Management

Student project **P01** — Algebra Bernays University, 2026/27. Author: Lara Babić.

Conference organisers publish a call for papers, speakers submit sessions, and the programme
committee assembles those sessions into a schedule of rooms and time slots.

**Status:** Milestone M1. The application starts and connects to PostgreSQL. No entities or
screens yet.

**Technology:** Java 25, Spring Boot 4.1.2, Thymeleaf, Spring Data JPA, PostgreSQL 17, Flyway, Maven.

**Requirements:** JDK 25 and Docker Desktop.

## Docker

| Command | What it does |
|---|---|
| `docker compose up --build` | Build and start. Use after changing Java code |
| `docker compose up -d` | Start again, in the background |
| `docker compose ps` | What is running |
| `docker compose logs app -f` | Follow the application log |
| `docker compose stop` | Stop, keep containers |
| `docker compose down` | Remove containers, keep the data |
| `docker compose down -v` | Remove containers **and delete the database** |

Check it works: <http://localhost:8080/actuator/health> returns `{"status":"UP"}`.

## Running from the IDE

Database in Docker, application outside it — faster, and the debugger works:

```bash
docker compose up db -d
./mvnw spring-boot:run
```

Set `JAVA_HOME` to JDK 25 first, and always use `./mvnw` rather than `mvn`.

## Database

```bash
docker compose exec db psql -U conference -d conference
```

Local credentials are `conference` / `conference`, port 5432. Development only — the deployed
instance reads its datasource from environment variables.
