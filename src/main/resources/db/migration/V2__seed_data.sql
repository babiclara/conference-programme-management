INSERT INTO conference (name, start_date, end_date, cfp_deadline, status) VALUES
    ('JavaCro 2027', '2027-05-12', '2027-05-13', '2027-02-28 23:59:00+01', 'CALL_CLOSED'),
    ('Algebra DevDays 2027', '2027-09-21', '2027-09-22', '2027-07-15 23:59:00+02', 'DRAFT');

INSERT INTO room (conference_id, name, capacity) VALUES
    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'), 'Hall A', 200),
    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'), 'Hall B', 80);

INSERT INTO time_slot (conference_id, starts_at, ends_at) VALUES
    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'), '2027-05-12 09:00:00+02', '2027-05-12 09:45:00+02'),
    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'), '2027-05-12 10:00:00+02', '2027-05-12 10:45:00+02'),
    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'), '2027-05-12 11:00:00+02', '2027-05-12 11:45:00+02');

INSERT INTO speaker (full_name, email, affiliation, bio) VALUES
    ('Andrej Babić', 'andrej.babic@example.com', 'Algebra', 'Student developer working on Spring Boot applications.'),
    ('Loris Babić', 'loris.babic@example.com', 'Infobip', 'Backend engineer working on messaging infrastructure.'),
    ('Vlatka Babić', 'vlatka.babic@example.com', 'Rimac Technology', 'Embedded systems and the JVM.'),
    ('Adam Mayall', 'adam.mayall@example.com', 'Independent', 'Consultant on database design and migrations.');

INSERT INTO session (conference_id, title, abstract_text, status, track, room_id, time_slot_id) VALUES
    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'),
     'Virtual Threads in Practice',
     'What changes when you stop pooling threads, and what does not.',
     'SCHEDULED', 'Platform',
     (SELECT id FROM room WHERE name = 'Hall A'),
     (SELECT id FROM time_slot WHERE starts_at = '2027-05-12 09:00:00+02')),

    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'),
     'Postgres Constraints as a Safety Net',
     'Pushing invariants into the database so concurrency cannot break them.',
     'SCHEDULED', 'Data',
     (SELECT id FROM room WHERE name = 'Hall B'),
     (SELECT id FROM time_slot WHERE starts_at = '2027-05-12 09:00:00+02')),

    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'),
     'Testing Spring Boot Without Mocks',
     'Testcontainers, slices, and when a full context is worth the wait.',
     'ACCEPTED', 'Testing', NULL, NULL),

    ((SELECT id FROM conference WHERE name = 'JavaCro 2027'),
     'Migrating a Monolith, Slowly',
     'A strangler-fig migration that took two years and was worth it.',
     'PROPOSED', 'Architecture', NULL, NULL);

INSERT INTO session_speaker (session_id, speaker_id, speaker_role, time_slot_id) VALUES
    ((SELECT id FROM session WHERE title = 'Virtual Threads in Practice'),
     (SELECT id FROM speaker WHERE email = 'andrej.babic@example.com'), 'PRESENTER',
     (SELECT time_slot_id FROM session WHERE title = 'Virtual Threads in Practice')),

    ((SELECT id FROM session WHERE title = 'Postgres Constraints as a Safety Net'),
     (SELECT id FROM speaker WHERE email = 'loris.babic@example.com'), 'PRESENTER',
     (SELECT time_slot_id FROM session WHERE title = 'Postgres Constraints as a Safety Net')),

    ((SELECT id FROM session WHERE title = 'Postgres Constraints as a Safety Net'),
     (SELECT id FROM speaker WHERE email = 'vlatka.babic@example.com'), 'CO_PRESENTER',
     (SELECT time_slot_id FROM session WHERE title = 'Postgres Constraints as a Safety Net')),

    ((SELECT id FROM session WHERE title = 'Testing Spring Boot Without Mocks'),
     (SELECT id FROM speaker WHERE email = 'adam.mayall@example.com'), 'PRESENTER', NULL),

    ((SELECT id FROM session WHERE title = 'Migrating a Monolith, Slowly'),
     (SELECT id FROM speaker WHERE email = 'andrej.babic@example.com'), 'PRESENTER', NULL);
