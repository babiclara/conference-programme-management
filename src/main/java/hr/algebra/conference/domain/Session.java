package hr.algebra.conference.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;

    @Column(nullable = false)
    private String title;

    @Column(name = "abstract_text", nullable = false)
    private String abstractText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status = SessionStatus.PROPOSED;

    private String track;

    @Column(name = "public_summary")
    private String publicSummary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    @Version
    private Long version;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionSpeaker> sessionSpeakers = new ArrayList<>();

    protected Session() {
    }

    public Session(Conference conference, String title, String abstractText) {
        this.conference = conference;
        this.title = title;
        this.abstractText = abstractText;
    }

    public SessionSpeaker addSpeaker(Speaker speaker, SpeakerRole role) {
        SessionSpeaker link = new SessionSpeaker(this, speaker, role);
        sessionSpeakers.add(link);
        return link;
    }

    public Long getId() {
        return id;
    }

    public Conference getConference() {
        return conference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getPublicSummary() {
        return publicSummary;
    }

    public void setPublicSummary(String publicSummary) {
        this.publicSummary = publicSummary;
    }

    public Room getRoom() {
        return room;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Long getVersion() {
        return version;
    }

    public List<SessionSpeaker> getSessionSpeakers() {
        return sessionSpeakers;
    }
}
