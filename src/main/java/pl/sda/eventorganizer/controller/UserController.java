package pl.sda.eventorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.service.EventService;
import pl.sda.eventorganizer.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {

     private final UserService userService;
     private final EventService eventService;


    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

//    my idea here it to create a possibility for a normie user to became The Organizer User
//    but not now. Later.
// TODO: 29.11.2019  eventsCreatedByUserAsPage is not a Page, wtf xD
    @GetMapping("/userProfile")
    public ModelAndView getUserProfileMainPage(Principal principal){

        User currentlyLoggedUser = userService.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("userProfile");
        List<Event> eventsCreatedByUserAsPage = sortUserEventsByStartDate(eventService.findAllEventsCreatedByUser(principal.getName()));

        modelAndView
                .addObject("allUsersEvents", sortUserEventsByStartDate(currentlyLoggedUser.getUserEvents()))
                .addObject("allEventsCreatedByUser", eventsCreatedByUserAsPage);
        return modelAndView;
    }

    private List<Event> sortUserEventsByStartDate(List<Event> allUserEvents){
        allUserEvents.sort(Comparator.comparing(Event::getStart));
        return allUserEvents;
    }



}
