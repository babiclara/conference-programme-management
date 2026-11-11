package hr.algebra.conference.service;

import hr.algebra.conference.domain.Conference;
import hr.algebra.conference.domain.Session;
import hr.algebra.conference.repository.SessionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SessionService {

    private final SessionRepository sessions;
    private final ConferenceService conferenceService;

    public SessionService(SessionRepository sessions, ConferenceService conferenceService) {
        this.sessions = sessions;
        this.conferenceService = conferenceService;
    }

    public List<Session> findByConference(Long conferenceId) {
        return sessions.findByConferenceIdOrderByTitleAsc(conferenceId);
    }

    public Session findById(Long id) {
        return sessions.findById(id)
                .orElseThrow(() -> new NotFoundException("Session " + id + " does not exist."));
    }

    @Transactional
    public Session create(Long conferenceId, Session changes) {
        Conference conference = conferenceService.findById(conferenceId);
        Session session = new Session(conference, changes.getTitle(), changes.getAbstractText());
        session.setTrack(changes.getTrack());
        session.setPublicSummary(changes.getPublicSummary());
        session.setStatus(changes.getStatus());
        return sessions.save(session);
    }

    @Transactional
    public Session update(Long id, Session changes) {
        Session session = findById(id);
        session.setTitle(changes.getTitle());
        session.setAbstractText(changes.getAbstractText());
        session.setTrack(changes.getTrack());
        session.setPublicSummary(changes.getPublicSummary());
        session.setStatus(changes.getStatus());
        return session;
    }

    @Transactional
    public void delete(Long id) {
        sessions.delete(findById(id));
    }
}
