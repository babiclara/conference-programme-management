package hr.algebra.conference.web;

import hr.algebra.conference.service.ConferenceService;
import hr.algebra.conference.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final SessionService sessionService;

    public ConferenceController(ConferenceService conferenceService, SessionService sessionService) {
        this.conferenceService = conferenceService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("conferences", conferenceService.findAll());
        return "conferences/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("conference", conferenceService.findById(id));
        model.addAttribute("sessions", sessionService.findByConference(id));
        return "conferences/detail";
    }
}
