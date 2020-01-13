package pl.sda.eventorganizer.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.service.EventService;

@Controller
@RequestMapping("/")
public class IndexController {

    private EventService eventService;

    public IndexController(EventService eventService) {
        this.eventService = eventService;
    }

//    @GetMapping
//    public ModelAndView getAllEvents() {
//        return new ModelAndView("allEvents").addObject("allEvents", eventService.getAllEventsSortedByDate());
//    }



}
