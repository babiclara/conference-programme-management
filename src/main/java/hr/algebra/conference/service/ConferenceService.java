package hr.algebra.conference.service;

import hr.algebra.conference.domain.Conference;
import hr.algebra.conference.repository.ConferenceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ConferenceService {

    private final ConferenceRepository conferences;

    public ConferenceService(ConferenceRepository conferences) {
        this.conferences = conferences;
    }

    public List<Conference> findAll() {
        return conferences.findAllByOrderByStartDateDesc();
    }

    public Conference findById(Long id) {
        return conferences.findById(id)
                .orElseThrow(() -> new NotFoundException("Conference " + id + " does not exist."));
    }

    @Transactional
    public Conference create(Conference conference) {
        return conferences.save(conference);
    }

    @Transactional
    public Conference update(Long id, Conference changes) {
        Conference conference = findById(id);
        conference.setName(changes.getName());
        conference.setStartDate(changes.getStartDate());
        conference.setEndDate(changes.getEndDate());
        conference.setCfpDeadline(changes.getCfpDeadline());
        conference.setStatus(changes.getStatus());
        return conference;
    }

    @Transactional
    public void delete(Long id) {
        conferences.delete(findById(id));
    }
}
