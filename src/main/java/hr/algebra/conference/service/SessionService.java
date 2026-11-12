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
    public Session create(Long conferenceId, SessionData data) {
        Conference conference = conferenceService.findById(conferenceId);
        Session session = new Session(conference, data.title(), data.abstractText());
        apply(session, data);
        return sessions.save(session);
    }

    @Transactional
    public Session update(Long id, SessionData data) {
        Session session = findById(id);
        session.setTitle(data.title());
        session.setAbstractText(data.abstractText());
        apply(session, data);
        return session;
    }

    @Transactional
    public void delete(Long id) {
        sessions.delete(findById(id));
    }

    private void apply(Session session, SessionData data) {
        session.setTrack(data.track());
        session.setPublicSummary(data.publicSummary());
        session.setStatus(data.status());
    }
}
