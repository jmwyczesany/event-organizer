package pl.sda.eventorganizer.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.sda.eventorganizer.dto.EventForm;
import pl.sda.eventorganizer.exceptions.EventNotFoundException;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.service.EventService;
import pl.sda.eventorganizer.service.EventValidator;
import pl.sda.eventorganizer.service.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller

public class AddEventController {

    private EventService eventService;
    private UserService userService;

    private Validator validator;

    public AddEventController(EventService eventService, UserService userService, @Qualifier("eventValidator") Validator validator) {
        this.eventService = eventService;
        this.userService = userService;
        this.validator = validator;
    }

    // get empty or completed form for creating / updating purposes
    @GetMapping("addEvent")
    public ModelAndView getAddEventForm(@RequestParam(name = "eventId", required = false) Long eventId) {
        ModelAndView modelAndView = new ModelAndView("addEvent");
        Event theEvent = eventService.findEventById(eventId);
        if (theEvent == null) {
            return modelAndView.addObject("addEventForm", new EventForm());
        } else {
            EventForm eventForm = new EventForm();
            eventForm.setTitle(theEvent.getTitle());
            eventForm.setDescription(theEvent.getDescription());
            eventForm.setStart(theEvent.getStart());
            eventForm.setEnd(theEvent.getEnd());
            eventForm.setEventId(theEvent.getEventId());
            return modelAndView.addObject("addEventForm", eventForm);
        }
    }


    // creating new or updating existing event

    @PostMapping("addEvent")
    public ModelAndView addNewEvent(@RequestParam(name = "eventId", required = false) Long eventId,
                                    @ModelAttribute("addEventForm") EventForm eventForm,
                                    BindingResult bindingResult, Principal principal, RedirectAttributes rd) {

//        new EventValidator().validate(eventForm, bindingResult);
        validator.validate(eventForm, bindingResult);
        User theUser = userService.findByUsername(principal.getName());
        RedirectView redirectView = new RedirectView("/eventCreated");
        ModelAndView modelAndView = new ModelAndView("addEvent");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        Event existingEvent = eventService.findEventById(eventId);
        if (existingEvent == null) {
            if (eventService.checkUniqueTitleInDataBase(eventForm.getTitle())) {
                modelAndView.addObject("eventAlreadyDefined", eventForm.getTitle());
                return modelAndView;
            }
            Event theEvent = eventService.createNewEvent(eventForm.getTitle(), eventForm.getDescription(), eventForm.getStart(), eventForm.getEnd(), theUser);
            eventService.addEventToAuthorsList(theEvent, principal.getName());
            modelAndView.setView(redirectView);
            rd.addAttribute("eventId", theEvent.getEventId());
//            return modelAndView;
//            updating an event
        } else {
            if (!eventService.checkIfUpdatedTitleRemainedSame(eventForm.getTitle(), eventId)) {
                if (eventService.checkUniqueTitleInDataBase(eventForm.getTitle())) {
                    modelAndView.addObject("eventAlreadyDefined", eventForm.getTitle());
                    return modelAndView;
                }
            }
            eventService.updateTheEvent(eventForm.getTitle(),
                    eventForm.getDescription(), eventForm.getStart(), eventForm.getEnd(), theUser, eventId);
            rd.addAttribute("eventId", eventId);
//            modelAndView.addObject("eventId", eventId);

        }
        modelAndView.setView(redirectView);
        return modelAndView;
    }

//    @DeleteMapping("addEvent")
//    public ModelAndView deleteEvent(@RequestParam(name = "eventId") Long eventId, RedirectAttributes rd){
//        Event eventToDelete = eventService.findEventById(eventId);
//        ModelAndView modelAndView = new ModelAndView("addEvent");
//        RedirectView redirectView = new RedirectView("/userProfile");
//
//        if(eventToDelete != null) {
//            eventService.deleteEventByEventId(eventId);
//        } else throw new EventNotFoundException("event not found");
//        rd.addFlashAttribute("messageForDelete", "Event Deleted");
//
//        modelAndView.setView(redirectView);
//        return modelAndView ;
//    }
    @DeleteMapping("addEvent")
    public RedirectView deleteEvent(@RequestParam(name = "eventId") Long eventId, RedirectAttributes rd){
        Event eventToDelete = eventService.findEventById(eventId);
        RedirectView redirectView = new RedirectView("/userProfile");

        if(eventToDelete != null) {
            eventService.deleteEventByEventId(eventId);
        } else throw new EventNotFoundException("event not found");
        rd.addFlashAttribute("messageForDelete", "Event Deleted");

        return redirectView;
    }
}




