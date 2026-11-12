package hr.algebra.conference.web;

import hr.algebra.conference.domain.Conference;
import hr.algebra.conference.domain.ConferenceStatus;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final SessionService sessionService;

    public ConferenceController(ConferenceService conferenceService, SessionService sessionService) {
        this.conferenceService = conferenceService;
        this.sessionService = sessionService;
    }

    @ModelAttribute("statuses")
    public ConferenceStatus[] statuses() {
        return ConferenceStatus.values();
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

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ConferenceForm());
        model.addAttribute("heading", "New conference");
        model.addAttribute("action", "/conferences");
        return "conferences/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") ConferenceForm form,
                         BindingResult binding,
                         Model model,
                         RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("heading", "New conference");
            model.addAttribute("action", "/conferences");
            return "conferences/form";
        }
        Conference saved = conferenceService.create(form.toConference());
        redirect.addFlashAttribute("message", "Conference created.");
        return "redirect:/conferences/" + saved.getId();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Conference conference = conferenceService.findById(id);
        model.addAttribute("form", ConferenceForm.of(conference));
        model.addAttribute("heading", "Edit conference");
        model.addAttribute("action", "/conferences/" + id);
        return "conferences/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") ConferenceForm form,
                         BindingResult binding,
                         Model model,
                         RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("heading", "Edit conference");
            model.addAttribute("action", "/conferences/" + id);
            return "conferences/form";
        }
        conferenceService.update(id, form.toConference());
        redirect.addFlashAttribute("message", "Conference updated.");
        return "redirect:/conferences/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        Conference conference = conferenceService.findById(id);
        conferenceService.delete(id);
        redirect.addFlashAttribute("message", "Deleted \"" + conference.getName() + "\".");
        return "redirect:/conferences";
    }
}
