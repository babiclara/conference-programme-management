# Conference Programme Management

Student project **P01** - Algebra Bernays University, 2026/27. Author: Lara Babić.

Conference organisers publish a call for papers, speakers submit sessions, and the programme
committee assembles those sessions into a schedule of rooms and time slots.

**Status:** Milestone M1. The application starts and connects to PostgreSQL. No entities or
screens yet.

**Technology:** Java 25, Spring Boot 4.1.2, Thymeleaf, Spring Data JPA, PostgreSQL 17, Flyway, Maven.

**Requirements:** JDK 25 and Docker Desktop.

## Prototype

Figma file: <https://www.figma.com/make/8qScEHl1xTnz1NMIzN4P7I/Conference-Programme-P01>

Clickable demo: <https://hacks-viral-51888929.figma.site/>

Six screens: sessions list, session detail, proposal form, the same form showing validation
errors, confirmation, and the panel where the committee reviews the AI suggestions.

## Planned deployment

| | Choice |
|---|---|
| Hosting | Render, from the `Dockerfile` |
| Database | Neon, managed PostgreSQL |
| AI provider | Google Gemini |

Gemini was chosen because it is the only one of the candidates with a free tier that needs no
credit card, and the AI feature has to run against the real provider in the deployed instance.
The reasoning is recorded in an architecture decision record when the feature is built.

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

Database in Docker, application outside it - faster, and the debugger works:

```bash
docker compose up db -d
./mvnw spring-boot:run
```

Set `JAVA_HOME` to JDK 25 first, and always use `./mvnw` rather than `mvn`.

## Database

```bash
docker compose exec db psql -U conference -d conference
```

Local credentials are `conference` / `conference`, port 5432. Development only - the deployed
instance reads its datasource from environment variables.
