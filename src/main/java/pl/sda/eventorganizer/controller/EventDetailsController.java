package pl.sda.eventorganizer.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.sda.eventorganizer.dto.AddUserToAnEventForm;
import pl.sda.eventorganizer.dto.CommentForm;
import pl.sda.eventorganizer.exceptions.EventNotFoundException;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.service.CommentService;
import pl.sda.eventorganizer.service.EventService;
import pl.sda.eventorganizer.service.UserService;

import java.util.List;

@Controller
public class EventDetailsController {

    private EventService eventService;
    private UserService userService;
    private CommentService commentService;

    public EventDetailsController(EventService eventService, UserService userService, CommentService commentService) {
        this.eventService = eventService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ModelAndView handleException(EventNotFoundException ex) {
        String message = "There's no such event in our database :( ";
        return new ModelAndView("error").addObject("message", ex.getMessage());
    }


    @GetMapping("eventDetails")
    public ModelAndView getEventDetails(@RequestParam("id") Long id, Pageable pageable) {
        if (eventService.findEventById(id) == null) throw new EventNotFoundException("No such event in database");

        ModelAndView modelAndView = new ModelAndView("eventDetails");
        modelAndView.addObject("event", eventService.findEventById(id));
        modelAndView.addObject("allEventsComments", commentService.getAllComments(id, pageable));
        modelAndView.addObject("addCommentForm", new CommentForm());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            User theUser = userService.findByUsername(authentication.getName());
            List<Event> userEvents = theUser.getUserEvents();
            if (userEvents.isEmpty()){
                modelAndView.addObject("participationForm", new AddUserToAnEventForm());
            } else if (userEvents.contains(eventService.findEventById(id))) {
                AddUserToAnEventForm participationForm = new AddUserToAnEventForm(theUser, eventService.findEventById(id));
                modelAndView.addObject("participationForm", participationForm);
            } else modelAndView.addObject("participationForm", new AddUserToAnEventForm());
        } else modelAndView.addObject("messageForAnonymousUser", "You have to be registered user to sing in to an event");

        return modelAndView;
    }


    @PostMapping("/eventDetails")
    public String addNewCommentToAnEvent(@ModelAttribute(name = "addCommentForm") CommentForm commentForm,
                                         @RequestParam("id") Long eventId, RedirectAttributes rd, BindingResult br) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String userName = authentication.getName();
            Event currentEvent = eventService.findEventById(eventId);
            User currentlyLoggedUser = userService.findByUsername(userName);
            commentService.createNewComment(currentlyLoggedUser, commentForm.getText(), currentEvent);
            if (br.hasErrors()) {
                rd.addAttribute("id", currentEvent.getEventId()).addFlashAttribute("message", "oops, something went wrong!");
                return "redirect:/eventDetails?id=" + eventId;
            }
            rd.addAttribute("id", currentEvent.getEventId()).addFlashAttribute("message", "Thanks for your comment");
        } else {
            rd.addFlashAttribute("message", "You need to be registered User to comment");
        }

        return "redirect:/eventDetails?id=" + eventId;
    }

//    @PutMapping("/eventDetails")
//    public String signInToAnEvent(@ModelAttribute(name = "participationForm") AddUserToAnEventForm participationForm,
//                                  @RequestParam(name = "id") Long eventId, RedirectAttributes rd) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            String userName = authentication.getName();
//            User theUser = userService.findByUsername(userName);
//            Long currentlyLoggedUserId = theUser.getId();
//            Event theEvent = eventService.findEventById(eventId);
//            List<Event> userEvents = theUser.getUserEvents();
//
//            if (userEvents.contains(theEvent)) {
//                theUser.getUserEvents().remove(theEvent);
//                userService.save(theUser);
//            } else {
//                participationForm.setEvent(theEvent);
//                participationForm.setUser(theUser);
//                theUser.getUserEvents().add(theEvent);
//                userService.save(theUser);
//                rd.addAttribute("id", eventId).addFlashAttribute("userId", currentlyLoggedUserId).
//                        addFlashAttribute("messageForParticipation", "Now you can come for this amazing event");
//            }
//        } else {
//            rd.addFlashAttribute("messageForParticipation", "You have to be registered user to sing in to an Event");
//        }
//
//        return "redirect:/eventDetails?id=" + eventId;
//    }

    @PutMapping("/eventDetails")
    public RedirectView signInToAnEvent(@ModelAttribute(name = "participationForm") AddUserToAnEventForm participationForm,
                                        @RequestParam(name = "id") Long eventId, RedirectAttributes rd) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RedirectView redirectView = new RedirectView("/userProfile");
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String userName = authentication.getName();
            User theUser = userService.findByUsername(userName);
            Long currentlyLoggedUserId = theUser.getId();
            Event theEvent = eventService.findEventById(eventId);
            List<Event> userEvents = theUser.getUserEvents();

            if (userEvents.contains(theEvent)) {
                theUser.getUserEvents().remove(theEvent);
                userService.save(theUser);
                rd.addFlashAttribute("eventRemoved", "You have removed event " + theEvent.getTitle() + " from your list");
                return redirectView;
            } else {
                participationForm.setEvent(theEvent);
                participationForm.setUser(theUser);
                theUser.getUserEvents().add(theEvent);
                userService.save(theUser);
                rd.addFlashAttribute("eventAdded", "You have added event " + theEvent.getTitle() + " to your list");
            }
        }

        return redirectView;
    }

}