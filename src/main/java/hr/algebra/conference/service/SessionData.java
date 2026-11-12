package hr.algebra.conference.service;

import hr.algebra.conference.domain.SessionStatus;

public record SessionData(
        String title,
        String abstractText,
        String track,
        String publicSummary,
        SessionStatus status) {
}
