package hr.algebra.conference.web;

import hr.algebra.conference.service.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(NotFoundException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "not-found";
    }
}
