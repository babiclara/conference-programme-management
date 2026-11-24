# 0002 - A small, fixed-size connection pool

## Context

A connection pool keeps a set of open database connections and lends them out to requests.
Opening a PostgreSQL connection is expensive: the server forks a process for it. So the pool
exists to avoid paying that cost on every request.

The tempting instinct is to treat the pool size as a performance dial and turn it up. It is not.
Every connection costs memory on the database side, and PostgreSQL can only do useful work on
roughly as many queries at once as it has CPU for. Ask it for more and the queries queue inside
the database, where nothing in the application can see them, instead of queueing in the pool,
where a timeout and a log line will tell you what happened.

Three facts about this project set the numbers:

- The deployed instance runs on a free Render plan, which gives a fraction of one CPU.
- The database is a free Neon instance, which caps how many connections an account may hold.
- The traffic is a student demonstration: a few people clicking, not a production load.

The brief also requires the pool to be "configured deliberately and the choice justified", so
leaving HikariCP on its default of ten was not an option even if ten happened to be right.

## Decision

```properties
spring.datasource.hikari.maximum-pool-size=5
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=10000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.leak-detection-threshold=20000
```

**Five connections.** A useful rule of thumb is that a database serves about `cores x 2`
concurrent queries well. On a fraction of a CPU that number is small, and five already exceeds
what this application will ask for. It also leaves room under Neon's connection cap for the
scheduled job that arrives later, which needs connections of its own.

**Minimum equals maximum, so the pool never shrinks.** A pool that releases idle connections has
to reopen them when traffic returns, which means paying the fork cost at exactly the moment
someone is waiting. HikariCP's own guidance is a fixed-size pool, and at five connections there
is nothing worth reclaiming.

**Ten-second connection timeout.** If every connection is busy, a request should fail with an
error that appears in the log, not hang until the browser gives up. Ten seconds is long enough
that a brief spike recovers and short enough that a real problem is visible.

**Thirty-minute maximum lifetime.** Managed databases and the proxies in front of them close
connections that have been open too long. If the pool hands out a connection the other side has
already killed, the request fails with a confusing network error. Retiring connections on a
schedule shorter than any such timeout means the pool does the closing, quietly.

**Twenty-second leak detection.** `spring.jpa.open-in-view` is off (see Consequences), so a
connection is held only for the length of a service method. Anything holding one for twenty
seconds is a bug, and this logs a stack trace pointing at it.

## Alternatives rejected

**HikariCP's defaults.** Ten connections, no timeout tuning. Possibly harmless, but the brief
asks for a justified choice and a default is the absence of one.

**A large pool, for example fifty.** This looks like headroom and is the most common mistake.
It would exceed the free tier's connection allowance, and the queries would pile up inside
PostgreSQL rather than in the pool, making the slow case both slower and harder to diagnose.

**Sizing per environment.** Development and production could differ, since a laptop has more CPU
than a free Render instance. Rejected for now: two configurations mean two behaviours to reason
about, and five connections is ample in both places.

## Consequences

`spring.jpa.open-in-view=false` was set at the same time, and the two belong together. With it
on, Spring holds a database connection for the entire request, including while Thymeleaf renders
the page, so lazy associations still load inside templates. That makes a small pool dangerous:
slow rendering holds connections. With it off, a connection is held only for the service call,
which is why five is enough.

The cost is that templates can no longer trigger lazy loads. A template that touches an
unfetched association now throws `LazyInitializationException` rather than quietly running an
extra query. That is the intended trade: the failure is loud, during development, instead of
being an invisible N+1 query in production.

These numbers are a starting point, not a measurement. The performance work due at M4 should
load the application, look at how long requests actually wait for a connection, and raise the
pool only if the evidence says to.
