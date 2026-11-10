package hr.algebra.conference.repository;

import hr.algebra.conference.domain.Session;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByConferenceIdOrderByTitleAsc(Long conferenceId);
}
