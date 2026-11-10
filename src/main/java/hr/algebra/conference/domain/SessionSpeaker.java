package hr.algebra.conference.domain;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "session_speaker")
public class SessionSpeaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "speaker_id", nullable = false)
    private Speaker speaker;

    @Enumerated(EnumType.STRING)
    @Column(name = "speaker_role", nullable = false)
    private SpeakerRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    protected SessionSpeaker() {
    }

    SessionSpeaker(Session session, Speaker speaker, SpeakerRole role) {
        this.session = session;
        this.speaker = speaker;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public SpeakerRole getRole() {
        return role;
    }

    public void setRole(SpeakerRole role) {
        this.role = role;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
