# Conference Programme Management

Student project **P01** - Algebra Bernays University, 2026/27. Author: Lara Babić.

Conference organisers publish a call for papers, speakers submit sessions, and the programme
committee assembles those sessions into a schedule of rooms and time slots.

**Status:** Milestone M2 complete. Schema and seed data in Flyway, entities and repositories
mapped, full create-read-update-delete on conferences and their sessions across five Thymeleaf
screens, and the Figma prototype finished. Next is M3: security, the REST API and the AI feature.

**Technology:** Java 25, Spring Boot 4.1.2, Thymeleaf, Spring Data JPA, PostgreSQL 17, Flyway, Maven.

**Requirements:** JDK 25 and Docker Desktop.

## Prototype

Figma file: <https://www.figma.com/design/GVvt1DJBjjtCDQDyC8vwjI/Conference-Programme-P01>

Three pages, as section 4 of the brief requires:

- **Wireframes** - six low-fidelity greyscale frames, `W1` to `W6`, produced before any styling.
- **Components** - seven components built with auto layout and bound to the `Color` and `Layout`
  variables: Button, Text input, Text area, Select, Table row, Status chip, Flash message.
- **Screens** - six high-fidelity screens, `S1` to `S6`, assembled from those components.

The variables hold the same values as `src/main/resources/static/css/app.css`, so the
prototype and the running application share one palette and spacing scale.

| Figma frame | Thymeleaf template |
|---|---|
| `S1 Conference list` | `templates/conferences/list.html` |
| `S2 Conference detail` | `templates/conferences/detail.html` |
| `S3 Empty state` | `conferences/detail.html`, shown when a conference has no sessions |
| `S4 Session form` | `templates/sessions/form.html` |
| `S5 Form with errors` | `sessions/form.html`, rendered when Bean Validation rejects the submit |
| `S6 Proposal review` | not implemented yet - the AI feature is due at M3 |

Deviations from the prototype, as the brief asks them to be listed:

- The conference create and edit form (`conferences/form.html`) has no prototype frame of its
  own. It is the session form with different fields, so `S4` covers both.
- `S1` shows Edit and Delete actions on each row. The implemented list links the conference
  name through to its detail screen and keeps those actions there instead, which avoids
  repeating a destructive action in two places.
- The prototype's Status chips are flat grey. The templates render the same chips, and colour
  per status is deliberately left out until the AI feature introduces a reason to distinguish
  them.

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

The connection pool is sized deliberately at five fixed connections. The reasoning is in
[ADR 0002](docs/adr/0002-connection-pool-sizing.md).
