package hr.algebra.conference.web;

import hr.algebra.conference.domain.Session;
import hr.algebra.conference.domain.SessionStatus;
import hr.algebra.conference.service.SessionData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SessionForm {

    @NotBlank(message = "Title must not be blank.")
    @Size(max = 200, message = "Title must be at most 200 characters.")
    private String title;

    @NotBlank(message = "Abstract must not be blank.")
    @Size(min = 20, message = "Abstract must be at least 20 characters.")
    private String abstractText;

    @Size(max = 100, message = "Track must be at most 100 characters.")
    private String track;

    @Size(max = 400, message = "Public summary must be at most 400 characters.")
    private String publicSummary;

    @NotNull(message = "Status is required.")
    private SessionStatus status = SessionStatus.PROPOSED;

    public static SessionForm of(Session session) {
        SessionForm form = new SessionForm();
        form.title = session.getTitle();
        form.abstractText = session.getAbstractText();
        form.track = session.getTrack();
        form.publicSummary = session.getPublicSummary();
        form.status = session.getStatus();
        return form;
    }

    public SessionData toData() {
        return new SessionData(title, abstractText, blankToNull(track), blankToNull(publicSummary), status);
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
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

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }
}
