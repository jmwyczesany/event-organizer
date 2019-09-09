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

    private final EventService eventService;

    public EventValidator(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EventForm eventForm = (EventForm) target;

        Event event = new Event();
        event.setTitle(eventForm.getTitle());
        event.setDescription(eventForm.getDescription());
        event.setStart(eventForm.getStart());
        event.setEnd(eventForm.getEnd());

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "All fields required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "All fields required");
        if(event.getDescription().length() < 20 ) {
            errors.rejectValue("description", "Too short", "Description must be at least 20 characters long");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "start", "All fields required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "end", "All fields required");

//        if(event.getStart().isAfter(event.getEnd())){
//            errors.rejectValue("start", "incorrect Date", "Start Time must be before End Time");
//        }



    }
}
