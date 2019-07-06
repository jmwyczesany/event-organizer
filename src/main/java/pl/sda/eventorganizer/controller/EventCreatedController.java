package pl.sda.eventorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.service.EventService;

@Controller
public class EventCreatedController {

    private EventService eventService;

    public EventCreatedController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("eventCreated")
    public ModelAndView getEventCreatedPage(@RequestParam("eventId") Long eventId){

        ModelAndView modelAndView = new ModelAndView("eventCreated");
        modelAndView.addObject("event", eventService.findEventById(eventId));
        return modelAndView;

    }
}
