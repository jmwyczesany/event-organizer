package pl.sda.eventorganizer.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.dto.CommentForm;
import pl.sda.eventorganizer.dto.EventForm;
import pl.sda.eventorganizer.service.CommentService;
import pl.sda.eventorganizer.service.EventService;
import pl.sda.eventorganizer.service.EventValidator;


@Controller
public class EventController {

   private final EventValidator eventValidator;
   private final EventService eventService;
   private final CommentService commentService;

    public EventController(EventValidator eventValidator, EventService eventService, CommentService commentService) {
        this.eventValidator = eventValidator;
        this.eventService = eventService;
        this.commentService = commentService;
    }

    @GetMapping("allEvents")
    public ModelAndView getAllEvents(Pageable pageable) {
        return new ModelAndView("allEvents").addObject("allEvents", eventService.findAllEvents(pageable));
    }

    @GetMapping("addEvent")
    public ModelAndView getAddEventForm(){
        return new ModelAndView("addEvent").addObject("addEventForm", new EventForm());
    }

    @PostMapping("addEvent")
    public ModelAndView addNewEvent(@ModelAttribute(name = "addEventForm") EventForm eventForm, BindingResult bindingResult){



        eventValidator.validate(eventForm, bindingResult);

        if(bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("addEvent");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }

        eventService.createNewEvent(eventForm.getTitle(), eventForm.getDescription(), eventForm.getStart(), eventForm.getEnd());
        ModelAndView modelAndView = new ModelAndView("addEvent");
        modelAndView.addObject("eventCreated", eventForm.getTitle());
        return modelAndView;
    }

//   new Controller maybe?
    @GetMapping("eventDetails")
    public ModelAndView getEventDetails(@RequestParam("id") Long id, Pageable pageable){

        ModelAndView modelAndView = new ModelAndView("eventDetails");
        modelAndView.addObject("event", eventService.findEventById(id));
        modelAndView.addObject("allEventsComments", commentService.getAllComments(id, pageable));
        modelAndView.addObject("addCommentForm", new CommentForm());
        return modelAndView;
    }

    @PostMapping("/eventDetails")
    public ModelAndView addNewCommentToAnEvent(@ModelAttribute(name = "addCommentForm") CommentForm commentForm, @RequestParam("id") Long eventId, Pageable pageable){
        commentService.createNewComment(commentForm.getAuthor(), commentForm.getText(), eventService.findEventById(eventId));
        ModelAndView eventDetails = getEventDetails(eventId, pageable);
        eventDetails.addObject("commentCreated", eventService.findEventById(eventId).getTitle());
        return eventDetails;
    }

}
