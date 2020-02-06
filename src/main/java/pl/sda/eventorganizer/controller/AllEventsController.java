package pl.sda.eventorganizer.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.service.EventService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class AllEventsController {

    private final EventService eventService;


    public AllEventsController(EventService eventService) {
        this.eventService = eventService;

    }

    @RequestMapping(value = "/allEvents/page/{pageNo}", method = RequestMethod.GET)
    public ModelAndView getAllEvents(@PathVariable(name = "pageNo") int pageNo) {

        Page<Event> eventsAsPage = eventService.
                getAllEventsSortByWhatever(PageRequest.of(pageNo - 1, 5, Sort.by("start").ascending()));
        ModelAndView modelAndView = new ModelAndView("allEvents").addObject("allEvents", eventsAsPage);

        if(eventsAsPage.isEmpty()){
            modelAndView.addObject("noEventsAvailable", "No events available");
        }

        if (eventsAsPage.getTotalPages() > 0) {
            List<Integer> pagination = IntStream.rangeClosed(1, eventsAsPage.getTotalPages()).boxed().collect(Collectors.toList());
            modelAndView.addObject("pagination", pagination);

        }
        return modelAndView;
    }


}




