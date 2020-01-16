package pl.sda.eventorganizer.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventorganizer.dto.EventForm;
import pl.sda.eventorganizer.model.Event;

import java.time.LocalDateTime;

@Service
public class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {


        EventForm eventForm = (EventForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "empty.field", "All fields are required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "empty.field", "All fields are required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "start", "empty.field", "All fields are required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "end", "empty.field", "All fields are required");

        if (eventForm.getDescription().length() < 20) {
            errors.rejectValue("description", "too.short", "Description must be at least 20 characters long");
        }

        if(eventForm.getStart() == null) {
            errors.rejectValue("start", "empty.field", "All fields are required");
        } else if(eventForm.getEnd() == null) {
            errors.rejectValue("end", "empty.field", "All fields are required");
        } else
        if (eventForm.getStart().isAfter(eventForm.getEnd())) {
            errors.rejectValue("start", "invalid.time.range", "Traveling back in time is not allowed on this planet");
        } else if (eventForm.getStart().isBefore(LocalDateTime.now())) {
            errors.rejectValue("start", "past.datetime", "Sorry, you cannot add an Event in the past");
        } else if (eventForm.getStart().isBefore(LocalDateTime.now())) {
            errors.rejectValue("end", "past.datetime", "Sorry, you cannot add an Event in the past");
        } else if (eventForm.getStart().isBefore(LocalDateTime.now().plusHours(12))) {
            errors.rejectValue("start", "invalid.datetime", "Sorry, the event cannot start earlier than 12 hours from now");
        } else if (eventForm.getEnd().isBefore(LocalDateTime.now().plusHours(12))) {
            errors.rejectValue("end", "invalid.datetime", "Sorry, the event cannot start earlier than 12 hours from now");
        }

    }
}
