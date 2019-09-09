package pl.sda.eventorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventorganizer.dto.CommentForm;
import pl.sda.eventorganizer.model.Comment;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.service.CommentService;
import pl.sda.eventorganizer.service.EventService;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final EventService eventService;

    public CommentController(CommentService commentService, EventService eventService) {
        this.commentService = commentService;
        this.eventService = eventService;
    }




}
