package hr.algebra.conference.web;

import hr.algebra.conference.domain.Conference;
import hr.algebra.conference.domain.ConferenceStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.format.annotation.DateTimeFormat;

public class ConferenceForm {

    @NotBlank(message = "Name must not be blank.")
    @Size(max = 200, message = "Name must be at most 200 characters.")
    private String name;

    @NotNull(message = "Start date is required.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime cfpDeadline;

    @NotNull(message = "Status is required.")
    private ConferenceStatus status = ConferenceStatus.DRAFT;

    @AssertTrue(message = "The end date must not be before the start date.")
    public boolean isDatesOrdered() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }

    public static ConferenceForm of(Conference conference) {
        ConferenceForm form = new ConferenceForm();
        form.name = conference.getName();
        form.startDate = conference.getStartDate();
        form.endDate = conference.getEndDate();
        form.cfpDeadline = conference.getCfpDeadline() == null
                ? null
                : conference.getCfpDeadline().atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        form.status = conference.getStatus();
        return form;
    }

    public Conference toConference() {
        Conference conference = new Conference(name, startDate, endDate);
        conference.setStatus(status);
        conference.setCfpDeadline(cfpDeadline == null
                ? null
                : cfpDeadline.atZone(ZoneId.systemDefault()).toOffsetDateTime());
        return conference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCfpDeadline() {
        return cfpDeadline;
    }

    public void setCfpDeadline(LocalDateTime cfpDeadline) {
        this.cfpDeadline = cfpDeadline;
    }

    public ConferenceStatus getStatus() {
        return status;
    }

    public void setStatus(ConferenceStatus status) {
        this.status = status;
    }
}
