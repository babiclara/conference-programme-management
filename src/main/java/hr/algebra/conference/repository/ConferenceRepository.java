package hr.algebra.conference.repository;

import hr.algebra.conference.domain.Conference;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    List<Conference> findAllByOrderByStartDateDesc();
}
