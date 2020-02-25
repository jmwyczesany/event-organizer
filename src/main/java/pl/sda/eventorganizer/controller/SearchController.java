package pl.sda.eventorganizer.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.sda.eventorganizer.dto.SearchingForm;
import pl.sda.eventorganizer.dto.TimeRange;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.service.EventService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class SearchController {

    EventService eventService;

    public SearchController(EventService eventService) {
        this.eventService = eventService;
    }

    // WE ARE THE KNIGHTS WHO SAY NEE!
    // WE DEMAND A SHRUBBERY!
    //but nice one, and not too expensive
    @GetMapping("eventSearch")
    public ModelAndView getSearchingPageWithAForm() {

        return new ModelAndView("eventSearch").addObject("searchingForm", new SearchingForm());
    }


    @PostMapping("eventSearch")
    public RedirectView postSearchingForm(@ModelAttribute SearchingForm searchingForm, RedirectAttributes redirectAttributes) {

        String titleLiteral = searchingForm.getTitlePhrase().trim().toLowerCase();
        TimeRange timeRange = searchingForm.getTimeRange();
        redirectAttributes.addAttribute("titlePhrase",titleLiteral);
        redirectAttributes.addAttribute("timeRange", timeRange);
        redirectAttributes.addAttribute("pageNo", "1");

        return new RedirectView("/result");
    }

    @GetMapping("result")
    public ModelAndView getResultPage(@RequestParam(value = "pageNo") int pageNo, @RequestParam("titlePhrase") String titlePhrase,
                                      @RequestParam("timeRange") TimeRange timeRange){

        Page<Event> searchingResult = eventService.getResultOfSearchingByTitlePhrase
                (PageRequest.of(pageNo - 1, 5, Sort.by("start").descending()), titlePhrase, timeRange );
        ModelAndView modelAndView = new ModelAndView("result").addObject("searchingResult", searchingResult);

        if(searchingResult.isEmpty()){
            modelAndView.addObject("noResult", "No results");
        }
        List<Integer> pagination = IntStream.rangeClosed(1, searchingResult.getTotalPages()).boxed().collect(Collectors.toList());
        modelAndView.addObject("pagination", pagination);
        modelAndView.addObject("allPages", searchingResult.getTotalPages()).addObject("pageSize", 5)
        .addObject("titlePhrase", titlePhrase).addObject("pageNo", pageNo).addObject("timeRange", timeRange);

        return modelAndView;
    }

}
