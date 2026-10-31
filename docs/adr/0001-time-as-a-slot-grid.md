# ADR 0001 - Time is a grid of slots, not a start and end on each session

**Status:** accepted · 2026-10-27

## Context

The same room must never hold two sessions at once, and the same speaker must never give two
sessions at once. The difficult part is not the rule itself but two organisers saving at the
same moment.

Say Ana and Marko are both building the programme, and both click Save at 14:03:07. Ana puts a
keynote in Main Hall on Monday at 09:00. Marko puts a workshop in the same room at the same
time. One of them has to be refused.

Checking in Java does not refuse either of them:

| Time | Ana | Marko |
|---|---|---|
| 14:03:07.100 | asks whether Main Hall is free at 09:00 - yes | |
| 14:03:07.101 | | asks the same question - yes |
| 14:03:07.102 | saves | |
| 14:03:07.103 | | saves |

Both asked before either saved, so both were told the room was free. There is a gap between
checking and saving, and the other person fits inside it. Two sessions end up in one room.

The database does not have this gap. A unique index is checked at the moment of writing, and
writes happen one at a time, so the second one is rejected. But for the database to say "this
room at this time must not repeat", the time has to be a fixed value it can compare.

## Decision

The conference defines its time slots in advance: Monday 09:00–09:45, Monday 10:00–10:45, and
so on. Each slot is a row with an id. A session points at one slot and one room, and both are
empty until the committee schedules it.

A clash is then simply the same pair of values twice, which a unique index expresses directly:

```sql
CREATE UNIQUE INDEX session_room_not_double_booked
    ON session (room_id, time_slot_id)
    WHERE room_id IS NOT NULL AND time_slot_id IS NOT NULL;
```

The rule for speakers is the same idea, but a speaker is linked to a session in the
`session_speaker` table while the time slot is stored on `session`. A unique index can only
look at one table, so `session_speaker` carries a copy of the slot id, and the same kind of
index applies to it.

## Alternatives rejected

**Each session stores its own start and end time.** This allows a talk of any length instead
of forcing everything onto a grid. Rejected because a clash is then no longer the same value
twice: 09:00–10:00 and 09:30–10:30 clash without being equal anywhere. PostgreSQL can express
that, with an `EXCLUDE` constraint over a `tstzrange` and a GiST index, but it is a far more
advanced feature to map in JPA and to explain, and a conference programme is published as a
grid anyway.

**Checking only in Java, inside a transaction.** Rejected for the reason in the context above:
with the database's default isolation level, both transactions see a free room and both write.
Closing that gap needs the `SERIALIZABLE` isolation level plus code that retries failed
transactions, which is considerably more machinery than one index.

## Consequences

The rule is enforced by PostgreSQL, so it holds no matter how many people save at the same
instant, without locking anything or changing the isolation level.

The service still checks for a clash before saving. That check exists so the ordinary case
shows a normal validation message next to the field, rather than a database error - it is for
the person using the screen, not for correctness. The index is what makes the rule actually
true. The service must therefore also catch the constraint violation when it loses the race,
and turn it into a readable message instead of a 500 page.

Every session has to fit the grid. A session needing an unusual length requires a slot of that
length to be created for it, which is a scheduling decision rather than a change to the data
model.

The copy of the slot id on `session_speaker` means the same fact is stored in two places. If a
session is moved to a different slot and only `session` is updated, the copy goes stale and the
speaker rule quietly stops protecting anything. This is the real price of the decision.
Rescheduling must update both tables in one transaction, and there is an integration test for
rescheduling specifically to keep that honest.
