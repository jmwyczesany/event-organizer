package pl.sda.eventorganizer.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
                getAllFutureEvents(PageRequest.of(pageNo - 1, 5, Sort.by("start").ascending()));
        ModelAndView modelAndView = new ModelAndView("allEvents").addObject("allEvents", eventsAsPage);

        return getPaginationAndEmptyList(eventsAsPage, modelAndView);
    }

    @GetMapping("allEvents/archive")
    public ModelAndView getArchiveAllEventsPage(@RequestParam("pageNo") int pageNo) {
        Page<Event> eventArchivePage = eventService.findAllEventsInArchive(PageRequest.of(pageNo - 1, 5, Sort.by("start").descending()));
        ModelAndView modelAndView = new ModelAndView("allEvents");
        modelAndView.addObject("allEvents", eventArchivePage);

        return getPaginationAndEmptyList(eventArchivePage, modelAndView);
    }

    private ModelAndView getPaginationAndEmptyList(Page<Event> eventAsPage, ModelAndView modelAndView) {
        if (eventAsPage.isEmpty()) {
            modelAndView.addObject("noEventsAvailable", "No events available");
        }

        if (eventAsPage.getTotalPages() > 0) {
            List<Integer> pagination = IntStream.rangeClosed(1, eventAsPage.getTotalPages()).boxed().collect(Collectors.toList());
            modelAndView.addObject("pagination", pagination);
        }
        return modelAndView;
    }


}




