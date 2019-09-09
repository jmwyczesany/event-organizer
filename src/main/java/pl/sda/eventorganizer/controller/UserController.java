package pl.sda.eventorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.service.UserService;

@Controller
public class UserController {

     private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

////    @GetMapping("events")
////    public ModelAndView allEvents(){
////        return new ModelAndView("allEvents").addObject("eventsTable", eventTable);
////    }
//
//    @GetMapping("addEvent")
//    public ModelAndView addEvent() {
//        return null;
//    }







}
