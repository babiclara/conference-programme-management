package hr.algebra.conference.web;

import hr.algebra.conference.domain.Session;
import hr.algebra.conference.domain.SessionStatus;
import hr.algebra.conference.service.ConferenceService;
import hr.algebra.conference.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SessionController {

    private final SessionService sessionService;
    private final ConferenceService conferenceService;

    public SessionController(SessionService sessionService, ConferenceService conferenceService) {
        this.sessionService = sessionService;
        this.conferenceService = conferenceService;
    }

    @ModelAttribute("statuses")
    public SessionStatus[] statuses() {
        return SessionStatus.values();
    }

    @GetMapping("/conferences/{conferenceId}/sessions/new")
    public String createForm(@PathVariable Long conferenceId, Model model) {
        model.addAttribute("form", new SessionForm());
        model.addAttribute("conference", conferenceService.findById(conferenceId));
        model.addAttribute("heading", "New session");
        model.addAttribute("action", "/conferences/" + conferenceId + "/sessions");
        return "sessions/form";
    }

    @PostMapping("/conferences/{conferenceId}/sessions")
    public String create(@PathVariable Long conferenceId,
                         @Valid @ModelAttribute("form") SessionForm form,
                         BindingResult binding,
                         Model model,
                         RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("conference", conferenceService.findById(conferenceId));
            model.addAttribute("heading", "New session");
            model.addAttribute("action", "/conferences/" + conferenceId + "/sessions");
            return "sessions/form";
        }
        sessionService.create(conferenceId, form.toData());
        redirect.addFlashAttribute("message", "Session created.");
        return "redirect:/conferences/" + conferenceId;
    }

    @GetMapping("/sessions/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Session session = sessionService.findById(id);
        model.addAttribute("form", SessionForm.of(session));
        model.addAttribute("conference", session.getConference());
        model.addAttribute("heading", "Edit session");
        model.addAttribute("action", "/sessions/" + id);
        return "sessions/form";
    }

    @PostMapping("/sessions/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") SessionForm form,
                         BindingResult binding,
                         Model model,
                         RedirectAttributes redirect) {
        Session session = sessionService.findById(id);
        if (binding.hasErrors()) {
            model.addAttribute("conference", session.getConference());
            model.addAttribute("heading", "Edit session");
            model.addAttribute("action", "/sessions/" + id);
            return "sessions/form";
        }
        sessionService.update(id, form.toData());
        redirect.addFlashAttribute("message", "Session updated.");
        return "redirect:/conferences/" + session.getConference().getId();
    }

    @PostMapping("/sessions/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        Session session = sessionService.findById(id);
        Long conferenceId = session.getConference().getId();
        String title = session.getTitle();
        sessionService.delete(id);
        redirect.addFlashAttribute("message", "Deleted \"" + title + "\".");
        return "redirect:/conferences/" + conferenceId;
    }
}
